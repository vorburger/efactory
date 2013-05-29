/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial implementation
 ******************************************************************************/
package com.googlecode.efactory.builder.tests;

public class EmptyTest extends AbstractModelBuilderTest {

	@Override
	protected String getTestModelName() {
		return "EmptyTest.efactory";
	}

	public void testEmptyResourceReturnsNullModelButNoExceptions() {
		assertNull(testModel);
	}
	
}
