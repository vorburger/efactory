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
package com.googlecode.efactory.building;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.util.EcoreUtil3;
import com.googlecode.efactory.util.ValueResolver;

public class AttributeBuilder extends FeatureBuilder {

	private Attribute attribute;
	private ValueResolver valueResolver = new ValueResolver();

	public AttributeBuilder(Attribute object) {
		this.attribute = object;
	}

	@Override
	public void build() throws ModelBuilderException {
		EStructuralFeature eFeature = getFeature().getEFeature();
		if (!(eFeature instanceof EAttribute))
			return;
		EAttribute eAttribute = (EAttribute) eFeature;
		if (eAttribute.eIsProxy())
			return;
		Class<?> clazz = eAttribute.getEAttributeType().getInstanceClass();
		Object newValue = valueResolver.apply(attribute);
		try {
			newValue = convertToTargetType(clazz, newValue);
		} catch (RuntimeException e) {
			throw new ModelBuilderException("convertToTargetType() failed for feature: " + eAttribute.toString(), e);
		}
		EcoreUtil3.setOrAddValue(getContainer(), eAttribute, newValue);
	}

	private Object convertToTargetType(Class<?> clazz, Object newValue) throws IllegalArgumentException {
		return new ValueSwitch().doSwitch(clazz, newValue);
	}
}
