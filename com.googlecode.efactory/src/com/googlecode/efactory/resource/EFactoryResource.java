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
/**
 * <copyright>
 *
 * Copyright (c) 2002-2006 Sebastian Benz and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Sebastian Benz - Initial API and implementation
 *
 * </copyright>
 *
 * 
 */
package com.googlecode.efactory.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.EFactoryPackage;
import com.googlecode.efactory.serialization.FactoryBuilder;

public class EFactoryResource extends XtextResource {
	
	private static final Logger logger = Logger.getLogger(EFactoryResource.class);
	
	private static final String INTERNAL = "internal_";

	private XtextResource internalResource;
	private XtextResourceSet internalResourceSet;
	private ModelBuilder builder;

	public EFactoryResource(URI uri) {
		super(uri);
	}

	public EFactoryResource() {
		super();
	}

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		if (getContents().isEmpty()) {
			throw new IllegalArgumentException(
					"Emptry resource cannot be saved. Resource must contain at least one element");
		}
		setEncodingFromOptions(options);
		FactoryBuilder builder = new FactoryBuilder();
		internalResource.getContents().clear();
		internalResource.getContents().add(builder.build(getContents().get(0)));
		internalResource.doSave(outputStream, options);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		com.googlecode.efactory.eFactory.Factory factory = loadEFactory(
				inputStream, options);
		getErrors().addAll(internalResource.getErrors());
		getWarnings().addAll(internalResource.getWarnings());
		if (hasParseErrors(internalResource)) {
			return;
		}
		doTransformation(factory);
	}

	private void doTransformation(
			com.googlecode.efactory.eFactory.Factory factory) {

		internalUnload();
		if (factory != null) {
			loadImportedResources(factory);
			builder = new ModelBuilder();
			try {
				EObject newModel = builder.build(factory);
				if (newModel != null) {
					getContents().add(newModel);
				}
			} catch (Exception e) {
				logger.error("doTransformation() build " + getURI(), e);
				getErrors().add(new ExceptionDiagnostic(e));
			}
		}
	}

	private void internalUnload() {
		if (getContents().isEmpty()) {
			return;
		}
		getUnloader().unloadRoot(getContents().get(0));
		getContents().clear();
	}

	private void loadImportedResources(
			com.googlecode.efactory.eFactory.Factory factory) {
		for (Resource importedResource : internalResourceSet.getResources()) {
			if (importedResource != internalResource) {
				getResourceSet().getResource(importedResource.getURI(), true);
			}
		}
	}

	private boolean hasParseErrors(XtextResource resource) {
		return !resource.getErrors().isEmpty();
	}

	private com.googlecode.efactory.eFactory.Factory loadEFactory(
			InputStream inputStream, Map<?, ?> options) throws IOException {
		initResourceSet();
		internalResource.setURI(getURI());
		internalResourceSet.getResources().add(internalResource);
		internalResource.load(inputStream, options);
		if (internalResource.getContents().isEmpty()) {
			return null;
		} else {
			return getFactory();
		}
	}

	private void initResourceSet() {
		internalResourceSet = new XtextResourceSet();
		if (getResourceSet() == null) {
			return;
		}
		internalResourceSet.setURIConverter(getResourceSet().getURIConverter());
		if (getResourceSet() instanceof XtextResourceSet) {
			XtextResourceSet xtextResourceSet = (XtextResourceSet) getResourceSet();
			internalResourceSet.setClasspathURIContext(xtextResourceSet
					.getClasspathURIContext());
			internalResourceSet.setClasspathUriResolver(internalResourceSet
					.getClasspathUriResolver());
		}
	}

	@Override
	public IParseResult getParseResult() {
		return internalResource.getParseResult();
	}

	public void setXtextResource(XtextResource xtextResource) {
		if (xtextResource == null)
			throw new IllegalArgumentException("xtextResource == null");
		this.internalResource = xtextResource;
	}

	@Override
	public EList<Diagnostic> getErrors() {
		EList<Diagnostic> joinedErrors = new BasicEList<Diagnostic>();
		joinedErrors.addAll(super.getErrors());
		joinedErrors.addAll(internalResource.getErrors());
		return joinedErrors;
	}

	@Override
	public EList<Diagnostic> getWarnings() {
		EList<Diagnostic> joinedWarnings = new BasicEList<Diagnostic>();
		joinedWarnings.addAll(super.getWarnings());
		joinedWarnings.addAll(internalResource.getWarnings());
		return joinedWarnings;
	}

	public com.googlecode.efactory.eFactory.Factory getFactory() {
		if (internalResource.getContents().isEmpty()) {
			return null;
		}
		return (com.googlecode.efactory.eFactory.Factory) internalResource
				.getContents().get(0);
	}

	public EObject getEFactoryElement(EObject eObject) {
		if (builder == null) {
			return null;
		}
		return builder.getSource(eObject);
	}

	@Override
	public IParser getParser() {
		return internalResource.getParser();
	}

	@Override
	public void update(int offset, int replacedTextLength, String newText) {
		internalResource.update(offset, replacedTextLength, newText);
		doTransformation(getFactory());
	}

	@Override
	public void reparse(String newContent) throws IOException {
		internalResource.reparse(newContent);
		doTransformation(getFactory());
	}

	@Override
	public EObject getEObject(String uriFragment) {
		if (uriFragment.startsWith(INTERNAL)) {
			return internalResource.getEObject(uriFragment);
		} else {
			return super.getEObject(uriFragment);
		}
	}

	@Override
	public String getURIFragment(EObject object) {
		if (object.eClass().getEPackage() == EFactoryPackage.eINSTANCE) {
			return INTERNAL + internalResource.getURIFragment(object);
		}
		return super.getURIFragment(object);
	}
}
