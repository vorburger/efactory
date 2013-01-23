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

import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.Value;

public class ReferenceBuilder extends FeatureBuilder {

	private EReference eReference;
	private EObject referencedElement;

	private ReferenceBuilder(EReference reference) {
		this.eReference = reference;
	}

	@Override
	protected Value createValue() {
		Reference value = EFactoryFactory.eINSTANCE.createReference();
		value.setValue(referencedElement);
		return value;
	}

	@Override
	protected EStructuralFeature getEFeature() {
		return eReference;
	}

	public static ReferenceBuilder reference(EReference reference) {
		return new ReferenceBuilder(reference);

	}

	public FeatureBuilder element(EObject referencedElement) {
		this.referencedElement = referencedElement;
		return this;
	}

}
