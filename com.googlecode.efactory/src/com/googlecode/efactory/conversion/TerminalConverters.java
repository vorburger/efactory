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
package com.googlecode.efactory.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractValueConverter;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TerminalConverters extends DefaultTerminalConverters {

	private static final String EXCEPTION_WHEN_PARSING_DOUBLE_VALUE = "Exception when parsing double value";
	private static final String EXCEPTION_WHEN_PARSING_INT_VALUE = "Exception when parsing int value";
	private static final String EXCEPTION_WHEN_PARSING_LONG_VALUE = "Exception when parsing long value";
	protected static final Object EXCEPTION_WHEN_PARSING_DATE_VALUE = "Exception when parsing date value";
	public static final String DATE_FORMAT_KEY = "date_format";

	@Inject
	@Named(value = DATE_FORMAT_KEY)
	private SimpleDateFormat dateFormat = null;

	Logger logger = Logger.getLogger(this.getClass());

	@ValueConverter(rule = "LONG")
	public IValueConverter<Long> LONG() {
		return new AbstractValueConverter<Long>() {
			public Long toValue(String string, INode node) {
				Long value = -1L;
				try {
					value = Long.valueOf(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_LONG_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_LONG_VALUE, ex);
				}
				return value;
			}

			public String toString(Long value) throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}

	@ValueConverter(rule = "DOUBLE")
	public IValueConverter<Double> DOUBLE() {
		return new AbstractValueConverter<Double>() {
			public Double toValue(String string, INode node) {
				Double value = -1.0;
				try {
					value = Double.valueOf(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_DOUBLE_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_DOUBLE_VALUE, ex);
				}
				return value;
			}

			public String toString(Double value) throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}
	@ValueConverter(rule = "S_INT")
	public IValueConverter<Integer> S_INT() {
		return new AbstractValueConverter<Integer>() {
			public Integer toValue(String string, INode node) {
				Integer value = -1;
				try {
					value = Integer.valueOf(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				}
				return value;
			}

			public String toString(Integer value)
					throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}

	@ValueConverter(rule = "BIG_INT")
	public IValueConverter<BigInteger> BIG_INT() {
		return new AbstractValueConverter<BigInteger>() {
			public BigInteger toValue(String string, INode node) {
				BigInteger value = BigInteger.valueOf(-1);
				try {
					value = new BigInteger(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				}
				return value;
			}

			public String toString(BigInteger value)
					throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}

	@ValueConverter(rule = "SHORT")
	public IValueConverter<Short> SHORT() {
		return new AbstractValueConverter<Short>() {
			public Short toValue(String string, INode node) {
				Short value = -1;
				try {
					value = Short.valueOf(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				}
				return value;
			}

			public String toString(Short value) throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}

	@ValueConverter(rule = "BIG_DECIMAL")
	public IValueConverter<BigDecimal> BIG_DECIMAL() {
		return new AbstractValueConverter<BigDecimal>() {
			public BigDecimal toValue(String string, INode node) {
				BigDecimal value = BigDecimal.valueOf(-1);
				try {
					value = new BigDecimal(string);
				} catch (NullPointerException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				} catch (NumberFormatException ex) {
					logger.error(EXCEPTION_WHEN_PARSING_INT_VALUE, ex);
				}
				return value;
			}

			public String toString(BigDecimal value)
					throws ValueConverterException {
				return String.valueOf(value);
			}
		};
	}

	@ValueConverter(rule = "DATE")
	public IValueConverter<Date> DATE() {
		return new AbstractValueConverter<Date>() {

			public Date toValue(String string, INode node)
					throws ValueConverterException {
				try {
					return dateFormat.parse(string);
				} catch (ParseException e) {
					logger.error(EXCEPTION_WHEN_PARSING_DATE_VALUE, e);
					return null;
				}
			}

			public String toString(Date date) {
				return dateFormat.format(date);
			}
		};
	}
}
