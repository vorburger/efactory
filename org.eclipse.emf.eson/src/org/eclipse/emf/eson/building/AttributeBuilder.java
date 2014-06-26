/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - bug fixes (incl. Enum Proxy handling, BrokenEnumTest story)
 ******************************************************************************/

package org.eclipse.emf.eson.building;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eson.eFactory.Attribute;
import org.eclipse.emf.eson.eFactory.Feature;
import org.eclipse.emf.eson.util.EcoreUtil3;
import org.eclipse.emf.eson.util.ValueResolver;

public class AttributeBuilder extends FeatureBuilder {
	private static final Logger logger = Logger.getLogger(AttributeBuilder.class);

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
		Object newValue = valueResolver.apply(attribute);
		
		// @see BrokenEnumTest
		if (newValue instanceof EEnumLiteral) {
			EEnumLiteral enumLiteral = (EEnumLiteral) newValue;
			if (enumLiteral.eIsProxy()) {
				return;
			}
		}
		
		Class<?> clazz = eAttribute.getEAttributeType().getInstanceClass();
		try {
			newValue = convertToTargetType(clazz, newValue, getFeature());
		} catch (RuntimeException e) {
			throw new ModelBuilderException("convertToTargetType() failed for feature: " + eAttribute.toString(), e);
		}
		EcoreUtil3.setOrAddValue(getContainer(), eAttribute, newValue);
	}

	protected Object convertToTargetType(Class<?> clazz, Object newValue, Feature feature) throws IllegalArgumentException {
		Object o = new ValueSwitch().doSwitch(clazz, newValue);
		if (!clazz.isPrimitive() && o != null && !clazz.isInstance(o)) {
			logger.warn("Likely upcoming ClassCastException 'heads up' - failed to convert value '"
					+ newValue.toString() + "' to an instance of class '"
					+ clazz.toString() + "', instead its: " + o.toString());
		}
		return o;
	}
}
