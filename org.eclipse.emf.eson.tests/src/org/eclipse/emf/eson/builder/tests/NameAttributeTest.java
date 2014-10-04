/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.builder.tests;

import testmodel.NameAttributeContainer;

public class NameAttributeTest extends AbstractNameAttributeTest {

	public void testDefaultNameMapping() throws Exception {
		NameAttributeContainer fixture = getNameTestContainer();
		String actual = fixture.getDefaultNameTest().getName();
		String expected = "name";
		assertEquals(actual, expected);
	}

	public void testCustomNameMapping() throws Exception {
		NameAttributeContainer fixture = getNameTestContainer();
		String actual = fixture.getCustomNameTest().getId();
		String expected = "id";
		assertEquals(actual, expected);
	}

}
