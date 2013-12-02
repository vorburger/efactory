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

public interface KeywordFormatter {

	void apply(FormattingConfig c, Iterable<Keyword> keywords);

}
