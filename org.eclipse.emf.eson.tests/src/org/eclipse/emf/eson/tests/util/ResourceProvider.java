/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.tests.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

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

	public URI getUri(String plugInRootRelativePath) {
		URI uri;
		if (Platform.isRunning()) {
			uri = createPlatformURI(plugInRootRelativePath);
		} else {
			uri = createFileUri(plugInRootRelativePath);
		}
		return uri;
	}

	private URI createFileUri(String plugInRootRelativePath) {
		return URI.createFileURI(new File(plugInRootRelativePath).getAbsolutePath());
	}

	private URI createPlatformURI(String plugInRootRelativePath) {
		return URI.createPlatformPluginURI(pluginId + "/" + plugInRootRelativePath, true);
	}

	public EList<EObject> load(String plugInRootRelativePath, boolean validate) throws IOException, DiagnosticException {
		URI uri = getUri(plugInRootRelativePath);
		return load(uri, validate);
	}
	
	public EList<EObject> load(File file, boolean validate) throws IOException, DiagnosticException {
		URI uri = URI.createFileURI(file.getAbsolutePath());
		return load(uri, validate);
	}
	
	public EList<EObject> load(URI uri, boolean validate) throws IOException, DiagnosticException {
		Resource resource = rs.getResource(uri, true);
		final EList<EObject> contents = resource.getContents();
		
		// This is IMPORTANT - without this, some tests won't catch what they're supposed to catch!
		if (validate) {
			BasicDiagnostic chain = new BasicDiagnostic();
			for (EObject content : contents) {
				Diagnostician.INSTANCE.validate(content, chain);
			}
			if (!BasicDiagnostic.toIStatus(chain).isOK()) {
				throw new DiagnosticExceptionWithURIAndToString(chain, uri);
			}
			
			logResourceDiagnostics(resource);
			if (!resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty()) {
				// This is important, because as the case of a completely empty resource used in 
				// org.eclipse.emf.eson.builder.resync.tests.BuilderResyncTest.testCreateCompletelyNew()
				// shows (change validate = true to see it), the BasicDiagnostic.toIStatus(chain).isOK()
				// ignores resource.getErrors() problems!
				throw new IOException(uri.toString() + " resource load produced warnings or errors (see log on System.out console)");
			}
		}
		
		if (contents.isEmpty())
			throw new IOException("Could no load / no content (see log!) in resource: " + uri.toPlatformString(true));
		return contents;
	}
	
	public <T> T loadModel(String plugInRootRelativePath, Class<T> clazz, boolean validate) throws IOException, DiagnosticException {
		EList<EObject> contents = load(plugInRootRelativePath, validate);
		if (contents.size() == 1)
			throw new IOException("Could load, but found no EObject in content, other than EFactory (so the EFactoryDerivedStateComputer failed; see log!) in resource: " + plugInRootRelativePath);		
		// get(1) because 0 is the root EFactory NewObject, 1 is the EObject from it
		EObject eObject = contents.get(1);
		return clazz.cast(eObject);
	}

	public <T> T loadModel(String plugInRootRelativePath, Class<T> clazz) throws IOException, DiagnosticException {
		return loadModel(plugInRootRelativePath, clazz, true);
	}
	
	public EObject loadModel(String plugInRootRelativePath) throws IOException, DiagnosticException {
		return loadModel(plugInRootRelativePath, EObject.class);
	}
	
	public String loadAsStringFromURI(URI uri) throws IOException {
		URIConverter uriConverter = rs.getURIConverter();
		InputStream is = uriConverter.createInputStream(uri);
		String content = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
		Closeables.closeQuietly(is);
		return content;
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
