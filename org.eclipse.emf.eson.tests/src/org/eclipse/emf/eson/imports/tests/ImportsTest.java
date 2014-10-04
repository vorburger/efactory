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
package org.eclipse.emf.eson.imports.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import testmodel.TestModel;

import org.eclipse.emf.eson.tests.util.ResourceProvider;
import org.eclipse.emf.eson.tests.util.TestConstants;
import org.eclipse.emf.eson.tests.util.TestSetup;

/**
 * Tests import of a XMI from an EFactory.
 */
public class ImportsTest {

	private ResourceProvider resourceProvider;
	private TestModel testModel;

	@Before
	public void setUp() throws Exception {
		TestSetup.INSTANCE.doSetup();
		this.resourceProvider = new ResourceProvider(TestConstants.PLUGIN_ID);
		resourceProvider.load("res/ImportTests/Imported.xmi", false); // This is an XMI file!
		this.testModel = (TestModel) resourceProvider.loadModel("res/ImportTests/Importing.efactory");
	}

	@Test
	@Ignore // TODO re-enable later; this broke as part of https://github.com/vorburger/efactory/pull/30 
	public void testImport_FromEfactory() throws Exception {
		TestModel parentReference = testModel.getSingleRequired().getParentReference();
		assertNotNull(parentReference);
		assertFalse(parentReference.eIsProxy());
		Assert.assertNotNull(parentReference.eResource());
	}

}
