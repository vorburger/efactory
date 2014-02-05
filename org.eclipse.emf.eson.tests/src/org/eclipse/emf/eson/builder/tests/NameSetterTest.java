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

import org.eclipse.emf.eson.building.NameAccessor;
import org.eclipse.emf.eson.building.NoNameFeatureMappingException;

import testmodel.CustomName;
import testmodel.DefaultName;
import testmodel.NameAttributeContainer;
import testmodel.NoName;

public class NameSetterTest extends AbstractNameAttributeTest {

	private static final String EXPECTED_NAME = "expected";
	private NameAccessor nameAccessor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		nameAccessor = new NameAccessor();
	}

	public void testSetName_Default() throws Exception {
		NameAttributeContainer fixture = getNameTestContainer();
		DefaultName nameSetTarget = fixture.getDefaultNameTest();
		nameAccessor.setName(factory, nameSetTarget, EXPECTED_NAME);
		assertEquals(EXPECTED_NAME, nameSetTarget.getName());
		assertEquals(EXPECTED_NAME, nameAccessor
				.getName(factory, nameSetTarget));
	}

	public void testSetName_Custom() throws Exception {
		NameAttributeContainer fixture = getNameTestContainer();
		CustomName nameSetTarget = fixture.getCustomNameTest();
		nameAccessor.setName(factory, nameSetTarget, EXPECTED_NAME);
		assertEquals(EXPECTED_NAME, nameSetTarget.getId());
		assertEquals(EXPECTED_NAME, nameAccessor
				.getName(factory, nameSetTarget));
	}

	public void testSetName_NoName() {
		NameAttributeContainer fixture = getNameTestContainer();
		NoName nameSetTarget = fixture.getNoNameTest();
		try {
			nameAccessor.setName(factory, nameSetTarget, EXPECTED_NAME);
			fail("No NoNameFeatureMappingException thrown");
		} catch (NoNameFeatureMappingException e) {
			// expected
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
	}

}
