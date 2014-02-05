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
