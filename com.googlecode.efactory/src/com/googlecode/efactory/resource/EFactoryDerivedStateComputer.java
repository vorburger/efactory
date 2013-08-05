/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/

package com.googlecode.efactory.resource;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.building.ModelBuilderException;
import com.googlecode.efactory.eFactory.Factory;

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
	private static Logger logger = Logger.getLogger(EFactoryDerivedStateComputer.class);

	@Inject
	private IReferableElementsUnloader unloader;
	
	// implementation inspired by XcoreModelAssociator (more than JvmModelAssociator) 
	public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
	    final IParseResult parseResult = resource.getParseResult();
		if (parseResult != null && parseResult.getRootASTElement() != null)
	    {
			final EObject rootASTElement = parseResult.getRootASTElement();
			if (!(rootASTElement instanceof Factory))
				return;
	    	Factory model = (Factory)rootASTElement;
	    	if (model != null && model.getRoot() != null && model.getRoot().getEClass() != null && model.getRoot().getEClass().eIsProxy()) {
	    		// If linking to the used eClass is not available yet,
	    		// then just give up (as it will come back and retry)
	    		return;
	    	}
	    	
	    	EFactoryResource efResource = (EFactoryResource) resource;
			ModelBuilder builder = efResource.getBuilder();
			try {
				EObject eModel = builder.buildWithoutLinking(model);
				resource.getContents().add(eModel);
				if (!preLinkingPhase) {
					builder.link();
				}
			} catch (ModelBuilderException e) {
				builder.clear();
				// TODO make this a logger.debug() again.. it's only logger.error() so that I can see this better while developping..
				logger.error(resource.getURI() + " could not be transformed by ModelBuilder (this may be normal if incomplete while editing; this Log is a DEBUG, not a WARN/ERROR)", e);
				// No need for something like this:
				// resource.getErrors().add(new ExceptionDiagnostic(e));
				// that would only lead to duplicate errors - the resource
				// will (most likely, should) already have error markers if
				// not, it's better to write new specific validation rules.
			}
			resource.getCache().clear(resource);
	    }
	}

	// implementation again inspired by XcoreModelAssociator and JvmModelAssociator 
	public void discardDerivedState(DerivedStateAwareResource resource) {
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
	
}
