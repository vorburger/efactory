/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.resource;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eson.building.ModelBuilder;
import org.eclipse.emf.eson.building.ModelBuilderException;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Adds the actual EObject. Uses the FactoryBuilder.
 * Xtext Index Builder calls this at appropriate times.
 * 
 * Credit where credit is due: the idea of using (something like) this was
 * originally raised by Sebastian Benz in private email exchange - I only dug
 * into it and coded it out.
 * 
 * @author Michael Vorburger
 */
public class EFactoryDerivedStateComputer implements IDerivedStateComputer {
	private static final Logger logger = Logger.getLogger(EFactoryDerivedStateComputer.class);

	@Inject
	private IReferableElementsUnloader unloader;
	
	public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=437848 (AKA DS-7543)
		try {
			safeInstallDerivedState(resource, preLinkingPhase);
		} catch (RuntimeException e) {
			handleRuntimeException("installDerivedState", resource, e);
		}
	}

	protected void safeInstallDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		// implementation inspired by XcoreModelAssociator (more than JvmModelAssociator) 
		Factory model = getFactory(resource);
		if (model == null)
			return;
    	if (model.getRoot() != null && model.getRoot().getEClass() == null) {
    		// Special handling for common use case of completely empty *.efactory.
    		// It would work without this as well, but this avoids the Exception & log below.
    		return;
    	}
    	if (model.getRoot() != null && model.getRoot().getEClass() != null && model.getRoot().getEClass().eIsProxy()) {
    		// If linking to the used eClass is not available yet,
    		// then just give up (as it will come back and retry)
    		return;
    	}
    	
    	EFactoryResource efResource = (EFactoryResource) resource;
		ModelBuilder builder = efResource.getBuilder();
		try {
			EObject eModel = builder.buildWithoutLinking(model);
			if (!preLinkingPhase) {
				builder.link();
			}
			// Do add() only AFTER the buildWithoutLinking() + link(),
			// because we don't want/need to get the notifications from our
			// ModelBuilder - only from external clients (e.g. Generic Ecore
			// editor UI, etc.)
			//
			// Note that our EFactoryAdapter change notification listener
			// gets added by org.eclipse.emf.eson.resource.EFactoryResource.attached(EObject)
			resource.getContents().add(eModel);
		} catch (ModelBuilderException e) {
			builder.clear();
			// TODO make this a logger.debug() again LATER.. it's only logger.error() so that we can conveniently see this one better while developing..
			logger.error(resource.getURI() + " could not be transformed by ModelBuilder (this may be normal if incomplete while editing)", e);
			// No need for something like this:
			// resource.getErrors().add(new ExceptionDiagnostic(e));
			// that would only lead to duplicate errors - the resource
			// will (most likely, should) already have error markers if
			// not, it's better to write new specific validation rules.
		}
		resource.getCache().clear(resource);
	}

	protected Factory getFactory(DerivedStateAwareResource resource) {
		EObject rootEObject = null;
	    final IParseResult parseResult = resource.getParseResult();
		if (parseResult != null && parseResult.getRootASTElement() != null)
	    {
			rootEObject = parseResult.getRootASTElement();
	    } else if (resource.getContents().size() == 1) {
	    	// This can happen from tests which programmatically add Factory
	    	rootEObject = resource.getContents().get(0);
	    }
		if (rootEObject != null) {
			if (!(rootEObject instanceof Factory)) {
				logger.warn("Resource's parseResult rootASTElement was not an instanceof Factory, but " + rootEObject + ": " + resource.getURI());
				return null;
			}
	    	Factory model = (Factory)rootEObject;
	    	return model;
		}
		return null;
	}
	
	public void discardDerivedState(DerivedStateAwareResource resource) {
		// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=437848 (AKA DS-7543)
		try {
			safeDiscardDerivedState(resource);
		} catch (RuntimeException e) {
			handleRuntimeException("discardDerivedState", resource, e);
		}
	}
	
	protected void safeDiscardDerivedState(DerivedStateAwareResource resource) {
		// implementation again inspired by XcoreModelAssociator and JvmModelAssociator 
		EFactoryResource efResource = (EFactoryResource) resource;
		ModelBuilder builder = efResource.getBuilder();
		builder.clear();
		
	    EList<EObject> contents = resource.getContents();
	    if (contents.size() > 1) // first content (size 1) is original Xtext (non-derived) model
	    {
	    	List<EObject> derived = Lists.newArrayList();
	    	// note start index 1 instead of 0 - we need to skip first content, see above
	    	for (int i = 1; i< contents.size(); i++) {
				EObject eObject = contents.get(i);
	    		unloader.unloadRoot(eObject);
	    		derived.add(eObject);
			}
	    	contents.removeAll(derived);
	    }
	}

	protected  void handleRuntimeException(String method, Resource resource, RuntimeException e) {
		String uri = "?";
		if (resource != null && resource.getURI() != null) {
			uri = resource.getURI().toString();
		}
		logger.error("RuntimeException occured during " + method + "() of " + uri + " :" + e.getMessage(), e);
	}

}
