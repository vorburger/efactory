/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2002 - 2014 Sebastian Benz, Michael Vorburger and others.
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.serialization;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.eson.eFactory.Containment;
import org.eclipse.emf.eson.eFactory.EFactoryFactory;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.eclipse.emf.eson.eFactory.Value;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class ContainmentBuilder extends FeatureBuilder {

	private EReference containment;
	
	@SuppressWarnings("unused")
	private Factory context;

	protected ContainmentBuilder(EReference containment, IFactoryBuilder factoryBuilder) {
		super(factoryBuilder);
		this.containment = containment;
	}

	@Override
	protected Value createValue() {
		Containment newContainment = EFactoryFactory.eINSTANCE.createContainment();
		newContainment.setValue(createNewObject());
		return newContainment;
	}

	private NewObject createNewObject() {
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

	public static ContainmentBuilder containment(EReference containment, IFactoryBuilder factoryBuilder) {
		return new ContainmentBuilder(containment, factoryBuilder);
	}

}
