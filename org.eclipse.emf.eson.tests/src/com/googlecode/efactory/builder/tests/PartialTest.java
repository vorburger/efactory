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

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class PartialTest {

	@Inject ResourceProvider rp;

	@BeforeClass public static void beforeTest() {
		TestSetup.INSTANCE.doSetup();
	}
	
	@Test
	public void testEmptyResourceNoExceptions() throws Exception {
		rp.load("res/BuilderTests/PartialEmptyTest.efactory", false);
	}
	
	@Test
	public void testPartiallyTypedResourceNoExceptions() throws Exception {
		TestModel testModel = rp.loadModel("res/BuilderTests/PartialTest.efactory", TestModel.class, false);
		Assert.assertEquals("Hi", testModel.getName());
		Assert.assertNull(testModel.getSingleRequired());
	}
	
	@Test
	public void testAnotherPartiallyTypedResourceNoExceptions() throws Exception {
		TestModel testModel = rp.loadModel("res/BuilderTests/Partial2Test.efactory", TestModel.class, false);
		Assert.assertEquals("Hi", testModel.getName());
		Assert.assertNull(testModel.getSingleRequired());
	}
}
