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
package com.googlecode.efactory.imports.tests;

import junit.framework.TestCase;

import org.junit.Assert;

import testmodel.TestModel;

import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestConstants;
import com.googlecode.efactory.tests.util.TestSetup;

/**
 * Tests import of a XMI from an EFactory.
 */
public class ImportsTest extends TestCase {

	private ResourceProvider resourceProvider;
	private TestModel testModel;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestSetup.INSTANCE.doSetup();
		this.resourceProvider = new ResourceProvider(TestConstants.PLUGIN_ID);
		this.testModel = (TestModel) resourceProvider
				.loadModel("res/ImportTests/Importing.efactory");

	}

	public void testImport_FromEfactory() throws Exception {
		TestModel parentReference = testModel.getSingleRequired()
				.getParentReference();
		assertNotNull(parentReference);
		assertFalse(parentReference.eIsProxy());
		Assert.assertNotNull(parentReference.eResource());
	}

}
