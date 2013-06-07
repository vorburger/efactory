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
package com.googlecode.efactory.serialization;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.googlecode.efactory.building.NoNameFeatureMappingException;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.Value;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class ReferenceBuilder extends FeatureBuilder {

	private EReference eReference;
	private EObject referencedElement;

	private ReferenceBuilder(EReference reference, FactoryBuilder factoryBuilder) {
		super(factoryBuilder);
		this.eReference = reference;
	}

	@Override
	protected Value createValue() throws NoNameFeatureMappingException {
		Reference reference = EFactoryFactory.eINSTANCE.createReference();
		NewObject newObject = factoryBuilder.getOrBuildNewObject(referencedElement);
		reference.setValue(newObject);
		return reference;
	}

	@Override
	protected EStructuralFeature getEFeature() {
		return eReference;
	}

	public static ReferenceBuilder reference(EReference reference, FactoryBuilder factoryBuilder) {
		return new ReferenceBuilder(reference, factoryBuilder);
	}

	public FeatureBuilder element(EObject referencedElement) {
		this.referencedElement = referencedElement;
		return this;
	}

}
