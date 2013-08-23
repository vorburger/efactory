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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.googlecode.efactory.eFactory.Value;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class AttributeBuilder extends FeatureBuilder {

	private ValueSwitch valueSwitch;

	EAttribute attribute;
	private AttributeBuilder(EAttribute attribute, IFactoryBuilder factoryBuilder) {
		super(factoryBuilder);
		this.attribute = attribute;
		valueSwitch = new ValueSwitch(attribute);
	}
	
	public static FeatureBuilder attribute(EAttribute attribute, IFactoryBuilder factoryBuilder) {
		return new AttributeBuilder(attribute, factoryBuilder);
	}

	@Override
	protected Value createValue() {
		return valueSwitch.doSwitch(attribute.getEAttributeType()
				.getInstanceClass(), value);
	}

	@Override
	protected EStructuralFeature getEFeature() {
		return attribute;
	}

}
