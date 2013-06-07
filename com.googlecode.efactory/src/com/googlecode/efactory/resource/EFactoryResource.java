/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - significant changes re. EFactoryDerivedStateComputer 
 ******************************************************************************/
package com.googlecode.efactory.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.resource.DerivedStateAwareResource;

import com.google.inject.Inject;
import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.NewObject;

public class EFactoryResource extends DerivedStateAwareResource {

	@Inject
	private ModelBuilder builder;
	
	@Nullable public NewObject getEFactoryElement(EObject eObject) {
		if (builder == null) {
			return null;
		}
		if (!isModelBuilderAvailable())
			return null;
		return builder.getSource(eObject);
	}

	private boolean isModelBuilderAvailable() {
		final com.googlecode.efactory.eFactory.Factory factory = getFactory();
		if (factory == null || factory.getRoot() == null || factory.getRoot().getEClass() == null)
			return false;
		else
			return true;
/*
		// need to do this to ensure ModelBuilder is fully initialized
		try {
			// TODO THIS IS BAD / STUPID - THE RETURNED EObject is lost / never added to Resource content!! :(
			// If we must have something like this because getEFactoryElement() could be called before
			// we are ready, then instead we should rather just force by calling getContents()..
			// which in turn will call the EFactoryDerivedStateComputer correctly.
			// NOT builder.build(factory);
			return true;
		} catch (ModelBuilderException e) {
			return false;
		}
*/		
	}

	@NonNull public EObject getEFactoryEObject(NewObject nObject) {
		if (builder == null) {
			throw new IllegalStateException("EFactoryResource is missing @Injected ModelBuilder?!");
		}
		return builder.getBuilt(nObject);
	}
	
	// package-private, as only used by EFactoryDerivedStateComputer
	ModelBuilder getBuilder() {
		return builder;
	}

	@Nullable public com.googlecode.efactory.eFactory.Factory getFactory() {
		if (getContents().isEmpty()) {
			return null;
		}
		return (com.googlecode.efactory.eFactory.Factory) getContents().get(0);
	}

}
