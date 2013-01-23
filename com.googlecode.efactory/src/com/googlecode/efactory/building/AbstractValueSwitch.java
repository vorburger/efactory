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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnumLiteral;

public abstract class AbstractValueSwitch<T> {

	protected abstract T caseString(String value);

	protected abstract T caseBoolean(Boolean value);

	protected abstract T caseEnum(EEnumLiteral value);

	protected abstract T caseInteger(int value);

	protected abstract T caseShort(short value);

	protected abstract T caseInteger(BigInteger value);

	protected abstract T caseBigDecimal(BigDecimal value);

	protected abstract T caseDouble(double value);

	protected abstract T caseLong(long value);

	protected abstract T caseDate(Date value);

	public T doSwitch(Class<?> clazz, Object value) {
		if (clazz == Long.class) {
			return caseLong(Long.parseLong(value.toString()));
		}
		if (clazz == double.class) {
			return caseDouble(Double.parseDouble(value.toString()));
		}
		if (clazz == BigDecimal.class) {
			return caseBigDecimal(new BigDecimal(value.toString()));
		}
		if (clazz == BigInteger.class) {
			return caseInteger(new BigInteger(value.toString()));
		}
		if (clazz == short.class) {
			return caseShort(Short.parseShort(value.toString()));
		}
		if (clazz == int.class) {
			return caseInteger(Integer.parseInt(value.toString()));
		}
		if (value instanceof EEnumLiteral) {
			return caseEnum((EEnumLiteral) value);
		}
		if (Enumerator.class.isAssignableFrom(clazz)) {
			return caseEnum((Enumerator) value);
		}
		if (clazz == boolean.class) {
			return caseBoolean(Boolean.valueOf(value.toString()));
		}
		if (clazz == String.class) {
			return caseString(value.toString());
		}
		if (clazz == Date.class) {
			return caseDate((Date) value);
		}
		throw new UnsupportedOperationException("Unsupported attribute type "
				+ clazz.getName());
	}

	protected abstract T caseEnum(Enumerator value);

}
