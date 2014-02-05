/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger (http://www.vorburger.ch).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.googlecode.efactory.serialization.tests;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.TestSetup;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class SerializationTest extends AbstractSerializationTest {

	@BeforeClass public static void beforeTestClass() {
		TestSetup.INSTANCE.doSetup();
	}
	
	@Test public void testFactoryCreation() throws Exception {
		performSerializationTest("FactoryCreation.efactory");
	}

	@Test public void testSimplestMultiValue() throws Exception {
		performSerializationTest("SimplestMultiValue.efactory");
	}
	
	@Test public void testAttributeTest() throws Exception {
		performSerializationTest("AttributeTest.efactory");
	}
	
	@Ignore // TODO how to make this work?? This is red, probably because the new /tmp/*.efactory that we copied into wasn't indexed yet?
	@Test public void testReferenceTest() throws Exception {
		performSerializationTest("ReferenceTest.efactory");
	}

	@Ignore // TODO how to make this work?? This is red, probably because the new /tmp/*.efactory that we copied into wasn't indexed yet?
	@Test public void testReferenceTestSimple() throws Exception {
		performSerializationTest("ReferenceTestSimple.efactory");
	}
}
