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

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;

import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.Factory;

/**
 * Adds the actual EObject.
 * Uses the FactoryBuilder.
 * Xtext Index Builder calls this at appropriate times.
 *  
 * @author Michael Vorburger
 */
public class EFactoryDerivedStateComputer implements IDerivedStateComputer {

	private static final Logger logger = Logger.getLogger(EFactoryDerivedStateComputer.class);

	public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		// skeleton code here is inspired by XcoreModelAssociator's implementation (more than JvmModelAssociator).. 

	    final IParseResult parseResult = resource.getParseResult();
		if (parseResult != null && parseResult.getRootASTElement() instanceof Factory)
	    {
	    	Factory model = (Factory)parseResult.getRootASTElement();
	    	EFactoryResource efResource = (EFactoryResource) resource;
			ModelBuilder builder = efResource.getBuilder();
			try {
				EObject eModel = builder.build(model);
				if (eModel != null) {
					resource.getContents().add(eModel);
					if (!preLinkingPhase) {
						// TODO builder.link();
					}
				}
			} catch (Exception e) {
				logger.error("ModelBuilder.build() failed for " + resource.getURI(), e);
				resource.getErrors().add(new ExceptionDiagnostic(e));
			}
			resource.getCache().clear(resource);
	    }
	}

	public void discardDerivedState(DerivedStateAwareResource resource) {
    	EFactoryResource efResource = (EFactoryResource) resource;
		ModelBuilder builder = efResource.getBuilder();
		builder.clear();
	}

}
