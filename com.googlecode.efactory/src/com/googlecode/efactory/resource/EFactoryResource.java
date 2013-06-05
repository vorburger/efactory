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
		return builder.getSource(eObject);
	}

	public EObject getEFactoryEObject(NewObject nObject) {
		if (builder == null) {
			return null;
		}
		builder.build(getFactory()); // need to do this to ensure it's fully initialized
		return builder.build(nObject);
	}
	
	// package-private, as only used by EFactoryDerivedStateComputer
	ModelBuilder getBuilder() {
		return builder;
	}

	public com.googlecode.efactory.eFactory.Factory getFactory() {
		if (getContents().isEmpty()) {
			return null;
		}
		return (com.googlecode.efactory.eFactory.Factory) getContents().get(0);
	}

}
