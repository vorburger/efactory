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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.googlecode.efactory.building.AbstractValueSwitch;
import com.googlecode.efactory.eFactory.BooleanAttribute;
import com.googlecode.efactory.eFactory.DateAttribute;
import com.googlecode.efactory.eFactory.DoubleAttribute;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.EnumAttribute;
import com.googlecode.efactory.eFactory.IntegerAttribute;
import com.googlecode.efactory.eFactory.StringAttribute;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.util.EcoreUtil3;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class ValueSwitch extends AbstractValueSwitch<Value> {

	private EAttribute eAttribute;

	public ValueSwitch(EAttribute attribute) {
		this.eAttribute = attribute;
	}

	@Override
	protected Value caseBigDecimal(BigDecimal value) {
		double doubleValue = value.doubleValue();
		return createDoubleAttribute(doubleValue);
	}

	private Value createDoubleAttribute(double doubleValue) {
		DoubleAttribute attribute = EFactoryFactory.eINSTANCE
				.createDoubleAttribute();
		attribute.setValue(doubleValue);
		return attribute;
	}

	@Override
	protected Value caseBoolean(Boolean value) {
		BooleanAttribute attribute = EFactoryFactory.eINSTANCE
				.createBooleanAttribute();
		if (value) {
			attribute.setValue(value);
		}
		return attribute;
	}

	@Override
	protected Value caseDouble(double value) {
		return createDoubleAttribute(value);
	}

	@Override
	protected Value caseEnum(EEnumLiteral value) {
		EnumAttribute attribute = EFactoryFactory.eINSTANCE
				.createEnumAttribute();
		attribute.setValue(value);
		return attribute;
	}

	@Override
	protected Value caseEnum(final Enumerator value) {
		EnumAttribute attribute = EFactoryFactory.eINSTANCE
				.createEnumAttribute();
		EEnum enumType = (EEnum) eAttribute.getEType();
		EEnumLiteral literal = EcoreUtil3.getEnumLiteral(enumType, value);
		attribute.setValue(literal);;
		return attribute;
	}
	
	@Override
	protected Value caseInteger(int value) {
		return createIntValue(value);
	}
	
	private Value createIntValue(int value) {
		IntegerAttribute attribute = EFactoryFactory.eINSTANCE
				.createIntegerAttribute();
		attribute.setValue(value);
		return attribute;
	}

	@Override
	protected Value caseInteger(BigInteger value) {
		return createIntValue(value.intValue());
	}

	@Override
	protected Value caseLong(long value) {
		IntegerAttribute attribute = EFactoryFactory.eINSTANCE
				.createIntegerAttribute();
		attribute.setValue(value);
		return attribute;
	}

	@Override
	protected Value caseShort(short value) {
		IntegerAttribute attribute = EFactoryFactory.eINSTANCE
				.createIntegerAttribute();
		attribute.setValue(value);
		return attribute;
	}

	@Override
	protected Value caseString(String value) {
		StringAttribute attribute = EFactoryFactory.eINSTANCE
				.createStringAttribute();
		attribute.setValue(value);
		return attribute;
	}

	@Override
	protected Value caseDate(Date value) {
		DateAttribute attribute = EFactoryFactory.eINSTANCE
				.createDateAttribute();
		attribute.setValue(value);
		return attribute;
	}

}
