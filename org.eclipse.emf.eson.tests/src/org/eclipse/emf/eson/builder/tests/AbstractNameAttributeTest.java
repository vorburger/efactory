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

public abstract class AbstractNameAttributeTest extends
		AbstractModelBuilderTest {

	private static final String TEST_NAME = "test";

	public AbstractNameAttributeTest() {
		super();
	}

	public AbstractNameAttributeTest(String name) {
		super(name);
	}

	@Override
	protected String getTestModelName() {
		return "NameAttribute.efactory";
	}

	protected NameAttributeContainer getNameTestContainer() {
		NameAttributeContainer fixture = find(NameAttributeContainer.class,
				TEST_NAME);
		return fixture;
	}

}
