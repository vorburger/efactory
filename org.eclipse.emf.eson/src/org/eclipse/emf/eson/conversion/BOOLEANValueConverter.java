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
