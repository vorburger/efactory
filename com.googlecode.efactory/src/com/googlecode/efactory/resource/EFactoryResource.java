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
package com.googlecode.efactory.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.resource.DerivedStateAwareResource;

import com.google.inject.Inject;
import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.NewObject;

public class EFactoryResource extends DerivedStateAwareResource {

	@Inject
	private ModelBuilder builder;
	
	public NewObject getEFactoryElement(EObject eObject) {
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
		// need to do this to ensure ModelBuilder is fully initialized
		builder.build(factory);
		return true;
	}

	public EObject getEFactoryEObject(NewObject nObject) {
		if (builder == null) {
			return null;
		}
		if (!isModelBuilderAvailable())
			return null;
		return builder.build(nObject);
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
