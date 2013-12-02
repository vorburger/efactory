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

package com.googlecode.efactory.formatting;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

public class SimpleKeywordFormatter implements KeywordFormatter {

	private String keyword;
	private Procedure2<FormattingConfig, Keyword> function;

	private SimpleKeywordFormatter(String keyword,
			Procedure2<FormattingConfig, Keyword> function) {
		super();
		this.keyword = keyword;
		this.function = function;
	}

	public static SimpleKeywordFormatter create(String keyword,
			Procedure2<FormattingConfig, Keyword> function) {
		return new SimpleKeywordFormatter(keyword, function);
	}

	public void apply(FormattingConfig config, Iterable<Keyword> keywords) {
		for (Keyword currentKeyword : keywords) {
			if (keyword.equals(currentKeyword.getValue())) {
				function.apply(config, currentKeyword);
			}
		}
	}
}
