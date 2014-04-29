/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/

package com.googlecode.efactory.imports.tests;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.EFactoryTestUtil;
import com.googlecode.efactory.tests.util.ResourceProvider;

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
