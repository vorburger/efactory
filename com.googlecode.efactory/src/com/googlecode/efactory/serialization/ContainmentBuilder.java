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
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Value;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class ContainmentBuilder extends FeatureBuilder {

	private EReference containment;
	
	@SuppressWarnings("unused")
	private Factory context;

	private ContainmentBuilder(EReference containment, FactoryBuilder factoryBuilder) {
		super(factoryBuilder);
		this.containment = containment;
	}

	@Override
	protected Value createValue() throws NoNameFeatureMappingException {
		Containment newContainment = EFactoryFactory.eINSTANCE
				.createContainment();
		newContainment.setValue(createNewObject());
		return newContainment;
	}

	private NewObject createNewObject() throws NoNameFeatureMappingException {
		if (value instanceof EObject) {
			EObject eObjectValue = (EObject) value;
			return factoryBuilder.getOrBuildNewObject(eObjectValue);
		} else {
			throw new IllegalStateException("Value of containment feature '"
					+ containment.getName() + "' was no an EObject, but was '"
					+ value.getClass() + "'");
		}
	}
	public ContainmentBuilder factory(Factory context) {
		this.context = context;
		return this;
	}

	@Override
	protected EStructuralFeature getEFeature() {
		return containment;
	}

	public static ContainmentBuilder containment(EReference containment, FactoryBuilder factoryBuilder) {
		return new ContainmentBuilder(containment, factoryBuilder);
	}

}
