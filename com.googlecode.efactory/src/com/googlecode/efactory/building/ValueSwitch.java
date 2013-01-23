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

public class ValueSwitch extends AbstractValueSwitch<Object> {

	@Override
	protected Object caseLong(long value) {
		return value;
	}

	@Override
	protected Object caseDouble(double value) {
		return value;
	}

	@Override
	protected Object caseBigDecimal(BigDecimal value) {
		return value;
	}

	@Override
	protected Object caseInteger(BigInteger value) {
		return value;
	}

	@Override
	protected Object caseShort(short value) {
		return value;
	}

	@Override
	protected Object caseInteger(int value) {
		return value;
	}

	@Override
	protected Object caseEnum(EEnumLiteral value) {
		return value.getInstance();
	}

	@Override
	protected Object caseBoolean(Boolean value) {
		return value;
	}

	@Override
	protected Object caseString(String value) {
		return value;
	}

	@Override
	protected Object caseDate(Date value) {
		return value;
	}

	@Override
	protected Object caseEnum(Enumerator value) {
		return value;
	}

}
