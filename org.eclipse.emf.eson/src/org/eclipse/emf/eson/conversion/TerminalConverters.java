/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.conversion;

import java.util.Date;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;

import com.google.inject.Inject;

public class TerminalConverters extends DefaultTerminalConverters {

	@Inject
	private LONGValueConverter longValueConverter;
	
	@ValueConverter(rule = "LONG")
	public IValueConverter<Long> LONG() {
		return longValueConverter;
	}
	
	@Inject
	private DOUBLEValueConverter doubleValueConverter;
	
	@ValueConverter(rule = "DOUBLE")
	public IValueConverter<Double> DOUBLE() {
		return doubleValueConverter;
	}
	
	@Inject
	private DATEValueConverter dateValueConverter;
	
	@ValueConverter(rule = "DATE")
	public IValueConverter<Date> DATE() {
		return dateValueConverter;
	}
	
	@Inject BOOLEANValueConverter booleanValueConverter;
	
	@ValueConverter(rule = "BOOLEAN")
	public IValueConverter<Boolean> BOOLEAN() {
		return booleanValueConverter;
	}
}
