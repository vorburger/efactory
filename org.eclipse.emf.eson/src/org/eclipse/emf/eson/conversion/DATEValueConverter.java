/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class DATEValueConverter extends AbstractLexerBasedConverter<Date> {

	public static final String DATE_FORMAT_KEY = "date_format";

	@Inject
	@Named(value = DATE_FORMAT_KEY)
	private SimpleDateFormat dateFormat = null;

	@Override
	protected String toEscapedString(Date value) {
		return dateFormat.format(value);
	}
	
	@Override
	protected void assertValidValue(Date value) {
		super.assertValidValue(value);
	}
	
	public Date toValue(String string, INode node) {
		if (Strings.isEmpty(string))
			throw new ValueConverterException("Couldn't convert empty string to a date value.", node, null);
		try {
			return dateFormat.parse(string);
		} catch (ParseException e) {
			throw new ValueConverterException("Couldn't convert '" + string + "' to a date value.", node, e);
		}
	}

}
