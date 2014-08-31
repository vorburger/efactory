/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.imports.tests;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.tests.util.EFactoryTestUtil;
import org.eclipse.emf.eson.tests.util.ResourceProvider;

/**
 * Tests import of EFactory from another EFactory.
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class ImportsEFactory2Test {
	
	@Inject ResourceProvider resourceProvider;
	@Inject EFactoryTestUtil setup;
	
	@Test
	public void testImports() throws Exception {
		resourceProvider.loadModel("res/ImportTests/Imported2.efactory");
		// TODO HACK UNDO TestModel testModel = (TestModel) resourceProvider.loadModel("res/ImportTests/Importing2.efactory", TestModel.class, false);
		TestModel testModel = (TestModel) resourceProvider.loadModel("res/ImportTests/Importing2.efactory");
		TestModel parentReference = testModel.getSingleRequired().getParentReference();
		Assert.assertNotNull(parentReference);
		Assert.assertFalse(parentReference.eIsProxy());
		Assert.assertNotNull(parentReference.eResource());
	}
}
