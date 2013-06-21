/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - some clean-up & simplifications
 ******************************************************************************/
package com.googlecode.efactory.tests.util;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

public class ResourceProvider {
	public ResourceSet rs;
	private String pluginId;

	@Inject
	public ResourceProvider(ResourceSet rs) {
		this(rs, TestConstants.PLUGIN_ID);
	}
	
	public ResourceProvider(ResourceSet rs, String pluginId) {
		if (rs == null)
			throw new IllegalArgumentException();
		this.rs = rs;
		this.pluginId = pluginId;
	}

	public ResourceProvider(String pluginId) {
		this(new ResourceSetImpl(), pluginId);
	}

	private <T> T get(EList<EObject> contents, int i, Class<T> clazz) {
		EObject object = contents.get(i);
		if (object == null)
			throw new IllegalArgumentException("Element in is null?!");
		if (!clazz.isInstance(object)) {
			throw new IllegalArgumentException("Element in resource '"
					+ object.eResource().getURI() + "' is of type '"
					+ object.getClass().getName() + "' and not '"
					+ clazz.getName() + "'");
		}
		return clazz.cast(object);
	}

	public URI getUri(String relativepath) {
		URI uri;
		if (Platform.isRunning()) {
			uri = createPlatformURI(relativepath);
		} else {
			uri = createFileUri(relativepath);
		}
		return uri;
	}

	private URI createFileUri(String relativepath) {
		return URI.createFileURI(new File(relativepath).getAbsolutePath());
	}

	private URI createPlatformURI(String relativepath) {
		return URI.createPlatformPluginURI(pluginId + "/" + relativepath, true);
	}

	public EList<EObject> load(String path, boolean validate) throws IOException, DiagnosticException {
		URI uri = getUri(path);
		Resource resource = rs.getResource(uri, true);
		final EList<EObject> contents = resource.getContents();
		
		// This is IMPORTANT - without this, some tests won't catch what they're supposed to catch!
		if (validate) {
			BasicDiagnostic chain = new BasicDiagnostic();
			for (EObject content : contents) {
				Diagnostician.INSTANCE.validate(content, chain);
			}
			logResourceDiagnostics(resource);
			if (!BasicDiagnostic.toIStatus(chain).isOK()) {
				throw new DiagnosticExceptionWithURIAndToString(chain, uri);
			}		
		}
		
		if (contents.isEmpty())
			throw new IOException("Could no load / no content (see log!) in resource: " + path);
		return contents;
	}
	
	public <T> T loadModel(String path, Class<T> clazz, boolean validate) throws IOException, DiagnosticException {
		EList<EObject> contents = load(path, validate);
		if (contents.size() == 1)
			throw new IOException("Could load, but found no EObject in content, other than EFactory (so the EFactoryDerivedStateComputer failed; see log!) in resource: " + path);		
		// get(1) because 0 is the root EFactory NewObject, 1 is the EObject from it
		return get(contents, 1, clazz);
	}

	public <T> T loadModel(String path, Class<T> clazz) throws IOException, DiagnosticException {
		return loadModel(path, clazz, true);
	}
	
	public EObject loadModel(String path) throws IOException, DiagnosticException {
		return loadModel(path, EObject.class);
	}
	
	private void logResourceDiagnostics(Resource resource) {
		for (Diagnostic diag : resource.getErrors()) {
			System.err.println("ERR in test resource: " + resource.getURI() + " :: " + diag.getMessage());
		}
		for (Diagnostic diag : resource.getWarnings()) {
			System.out.println("WARN in test resource: " + resource.getURI() + " :: " + diag.getMessage());
		}
	}

}
