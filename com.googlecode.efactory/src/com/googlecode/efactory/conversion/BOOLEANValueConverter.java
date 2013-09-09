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

public class BOOLEANValueConverter extends AbstractLexerBasedConverter<Boolean> {

	@Override
	protected String toEscapedString(Boolean value) {
		return value.toString();
	}

	public Boolean toValue(String string, INode node) throws ValueConverterException {
		return Boolean.valueOf(string);
	}

}
