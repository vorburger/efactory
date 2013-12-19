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
package com.googlecode.efactory.values;

import java.util.regex.Pattern;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import com.googlecode.efactory.conversion.TerminalConverters;


public class EfactoryValueConverterService
		extends
		TerminalConverters {

	
private static final Pattern ID_PATTERN = Pattern.compile("\\p{Alpha}\\w*");

	@ValueConverter(rule = "StringId")
	public IValueConverter<String> NewID() {
		return new IValueConverter<String>() {

			public String toValue(String string, INode node) throws ValueConverterException {
				if((string.startsWith("\"") && string.endsWith("\""))) {
					return "\'"+string.substring(1, string.length()-1)+"\'";
				}
				return ID().toValue(string, node);
			}

			public String toString(String value) throws ValueConverterException {if(ID_PATTERN.matcher(value).matches()) {
				return ID().toString(value);
			} else {
				value = STRING().toString(value);
				return "\'"+value.substring(1, value.length()-1)+"\'";
			}}
			
		};
	}

	
}
