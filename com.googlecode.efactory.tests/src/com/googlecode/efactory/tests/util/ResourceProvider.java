/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.tests.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.resource.XtextResource;

import com.googlecode.efactory.EFactoryStandaloneSetup;
import com.googlecode.efactory.eFactory.Factory;

public class ResourceProvider {
	public ResourceSet rs;
	private String pluginId;

	public ResourceProvider(ResourceSet rs, String pluginId) {
		this.rs = rs;
		this.pluginId = pluginId;
	}

	public ResourceProvider(String pluginId) {
		this(new ResourceSetImpl(), pluginId);
	}

	public Factory loadFactory(String relativepath) throws IOException {
		URI uri = getUri(relativepath);
		return loadFactory(uri);
	}

	public Factory loadFactory(URI uri) throws IOException {
		Resource resource = new EFactoryStandaloneSetup().createInjector()
				.getInstance(XtextResource.class);
		resource.setURI(uri);
		rs.getResources().add(resource);
		resource.load(Collections.EMPTY_MAP);
		logResourceDiagnostics(resource);
		checkResource(resource, Factory.class);
		return getRootInstance(resource, Factory.class);
	}

	<T> T getRootInstance(Resource r, Class<T> clazz) {
		checkResource(r, clazz);
		EList<EObject> contents = r.getContents();
		EObject eObject = contents.get(0);
		return clazz.cast(eObject);
	}

	private void checkResource(Resource r, Class<?> clazz) {
		if (r == null) {
			throw new NullPointerException("Resource is null");
		}
		EList<EObject> contents = r.getContents();
		if (contents.isEmpty()) {
			throw new NoSuchElementException("Resource '" + r.getURI()
					+ "' is empty");
		}
		EObject rootObject = contents.get(0);
		if (!clazz.isInstance(rootObject)) {
			throw new IllegalArgumentException("Root element in resource '"
					+ r.getURI() + "' is of type '"
					+ rootObject.getClass().getName() + "' and not '"
					+ clazz.getName() + "'");
		}
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

	public EObject loadModel(String path) throws IOException {
		URI uri = getUri(path);
		Resource resource = rs.getResource(uri, true);
		
		logResourceDiagnostics(resource);

		final EList<EObject> contents = resource.getContents();
		if (contents.isEmpty())
			throw new IOException("Could no load / no content (see log!) in resource: " + path);
		if (contents.size() == 1)
			throw new IOException("Could load, but found no EObject in content, other than EFactory (so the EFactoryDerivedStateComputer failed; see log!) in resource: " + path);		
		// get(1) because 0 is the root EFactory NewObject, 1 is the EObject from it
		return contents.get(1);
	}

	private void logResourceDiagnostics(Resource resource) {
		for (Diagnostic diag : resource.getErrors()) {
			System.err.println("ERR in test resource: " + resource.getURI() + " :: " + diag.getMessage());
		}
		for (Diagnostic diag : resource.getWarnings()) {
			System.out.println("WARN in test resource: " + resource.getURI() + " :: " + diag.getMessage());
		}
	}

	public void save(EObject model, URI uri) throws IOException {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(uri);
		r.getContents().add(model);
		r.save(Collections.EMPTY_MAP);
		
	}
}
