/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
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
