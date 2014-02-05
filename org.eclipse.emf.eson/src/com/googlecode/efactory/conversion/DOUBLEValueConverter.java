/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.conversion;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

public class DOUBLEValueConverter extends AbstractLexerBasedConverter<Double> {

	@Override
	protected String toEscapedString(Double value) {
		return String.valueOf(value);
	}
	
	@Override
	protected void assertValidValue(Double value) {
		super.assertValidValue(value);
//		if (value < 0)
//			throw new ValueConverterException(getRuleName() + "-value may not be negative (value: " + value + ").", null, null);
	}
	
	public Double toValue(String string, INode node) {
		if (Strings.isEmpty(string))
			throw new ValueConverterException("Couldn't convert empty string to a double value.", node, null);
		try {
			double doubleValue = Double.parseDouble(string);
			return Double.valueOf(doubleValue);
		} catch (NumberFormatException e) {
			throw new ValueConverterException("Couldn't convert '" + string + "' to a double value.", node, e);
		}
	}

}
