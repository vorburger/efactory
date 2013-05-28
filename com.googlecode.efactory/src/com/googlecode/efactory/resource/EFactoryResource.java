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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
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
	
	// TODO could this use DerivedStateAwareResource concepts instead of my stuff below?
	
	
	private static final Logger logger = Logger.getLogger(EFactoryResource.class);
	
	private static final String INTERNAL = "internal_";

	// The "internal" Resource is the com.googlecode.efactory.eFactory.Factory (DSL)
	// while "this" EMF Resource is the EObject expressed by this EFactory DSL
	//
	// To avoid an issue where the ClusteringBuilderState ends up loading this
	// but not all references in "this" EObject can be resolved, yet (which used 
	// to cause IllegalStateException from ModelBuilder.getOrCreateTarget()),
	// we initially "this" EMF Resource from the "internal" Resource only
	// lazily, when someone actually asks for the content.
	//
	// The laziness probably doesn't hurt for performance and memory consumption, either.
	//
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
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		EList<EObject> thisContents = getContentsSuper();
		if (thisContents.isEmpty()) {
			throw new IllegalArgumentException("Empty resource cannot be saved. Resource must contain at least one element");
		}
		setEncodingFromOptions(options);
		FactoryBuilder builder = new FactoryBuilder();
		internalResource.getContents().clear();
		internalResource.getContents().add(builder.build(thisContents.get(0)));
		internalResource.doSave(outputStream, options);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		loadEFactory(inputStream, options);
		getErrors().addAll(internalResource.getErrors());
		getWarnings().addAll(internalResource.getWarnings());
	}

	private void ensureThisResourceContentsIsAvailable() {
		if (builder != null) // TODO REVIEW! probably too simplistic..
			return;
		if (hasParseErrors(internalResource)) {
			logger.warn("Should have ensuredThisResourceContentsIsAvailable(), but EFactory DSL has parse errors, so resource at URI: " + getURI());
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info("Now (lazily, was initially deferred up to this point) ensuringThatThisResourceContentsIsAvailable at URI: " + getURI());
		}
		com.googlecode.efactory.eFactory.Factory factory = getFactory();
		unloadThisResource();
		if (factory != null) {
			loadImportedResources(factory);
			builder = new ModelBuilder();
			try {
				EObject newModel = builder.build(factory);
				if (newModel != null) {
					getContentsSuper().add(newModel);
				}
			} catch (Exception e) {
				logger.error("doTransformation() build() failed for resource at URI: " + getURI(), e);
				getErrors().add(new ExceptionDiagnostic(e));
			}
		}
	}

	private void unloadThisResource() {
		EList<EObject> thisContents = getContentsSuper();
		if (thisContents.isEmpty()) {
			return;
		}
		getUnloader().unloadRoot(thisContents.get(0));
		thisContents.clear();
	}

	private void loadImportedResources(com.googlecode.efactory.eFactory.Factory factory) {
		for (Resource importedResource : internalResourceSet.getResources()) {
			if (importedResource != internalResource) {
				getResourceSet().getResource(importedResource.getURI(), true);
			}
		}
	}

	private boolean hasParseErrors(XtextResource resource) {
		return !resource.getErrors().isEmpty();
	}

	private void loadEFactory(InputStream inputStream, Map<?, ?> options) throws IOException {
		initResourceSet();
		internalResource.setURI(getURI());
		internalResourceSet.getResources().add(internalResource);
		internalResource.load(inputStream, options);
	}

	private void initResourceSet() {
		internalResourceSet = new XtextResourceSet();
		if (getResourceSet() == null) {
			return;
		}
		internalResourceSet.setURIConverter(getResourceSet().getURIConverter());
		if (getResourceSet() instanceof XtextResourceSet) {
			XtextResourceSet xtextResourceSet = (XtextResourceSet) getResourceSet();
			internalResourceSet.setClasspathURIContext(xtextResourceSet.getClasspathURIContext());
			internalResourceSet.setClasspathUriResolver(internalResourceSet.getClasspathUriResolver());
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
		return (com.googlecode.efactory.eFactory.Factory) internalResource.getContents().get(0);
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
	}

	@Override
	public void reparse(String newContent) throws IOException {
		internalResource.reparse(newContent);
	}

	@Override
	public EObject getEObject(String uriFragment) {
		if (uriFragment.startsWith(INTERNAL)) {
			return internalResource.getEObject(uriFragment);
		} else {
			ensureThisResourceContentsIsAvailable();
			return super.getEObject(uriFragment);
		}
	}

	@Override
	public String getURIFragment(EObject object) {
		if (object.eClass().getEPackage() == EFactoryPackage.eINSTANCE) {
			return INTERNAL + internalResource.getURIFragment(object);
		}
		ensureThisResourceContentsIsAvailable();
		return super.getURIFragment(object);
	}

	/**
	 * This method must be used inside this class instead of getContents(),
	 * otherwise we're going around in circles (StackOverflowError) obviously.
	 */
	private EList<EObject> getContentsSuper() {
		return super.getContents();
	}
	
	@Override
	public EList<EObject> getContents() {
		ensureThisResourceContentsIsAvailable();
		return super.getContents();
	}

	@Override
	public TreeIterator<EObject> getAllContents() {
		ensureThisResourceContentsIsAvailable();
		return super.getAllContents();
	}

	@Override
	protected TreeIterator<EObject> getAllProperContents(EObject eObject) {
		ensureThisResourceContentsIsAvailable();
		return super.getAllProperContents(eObject);
	}

	@Override
	protected TreeIterator<EObject> getAllProperContents(List<EObject> contents) {
		ensureThisResourceContentsIsAvailable();
		return super.getAllProperContents(contents);
	}

	@Override
	protected String getURIFragmentRootSegment(EObject eObject) {
		ensureThisResourceContentsIsAvailable();
		return super.getURIFragmentRootSegment(eObject);
	}

	@Override
	protected EObject getEObjectForURIFragmentRootSegment(String uriFragmentRootSegment) {
		ensureThisResourceContentsIsAvailable();
		return super.getEObjectForURIFragmentRootSegment(uriFragmentRootSegment);
	}

	@Override
	protected EObject getEObject(List<String> uriFragmentPath) {
		ensureThisResourceContentsIsAvailable();
		return super.getEObject(uriFragmentPath);
	}

	@Override
	protected EObject getEObjectByID(String id) {
		ensureThisResourceContentsIsAvailable();
		return super.getEObjectByID(id);
	}

}
