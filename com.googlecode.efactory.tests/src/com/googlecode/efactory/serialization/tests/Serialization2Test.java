/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger (http://www.vorburger.ch).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.googlecode.efactory.serialization.tests;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class Serialization2Test {

	@Inject protected ResourceProvider resourceProvider;
	
	@BeforeClass public static void beforeTestClass() {
		TestSetup.INSTANCE.doSetup();
	}
	
	@Test public void testReferenceTestSimpleAlternative() throws Exception {
		TestModel testModel = resourceProvider.loadModel("res/SerializationTests/ReferenceTestSimple.efactory", TestModel.class);
		String oldSerializedText = SerializationUtils.toString(testModel.eResource());
		
		testModel.getReferenceTestContainer().get(0).getContainments().get(0).setName("target2");
		String newSerializedText = SerializationUtils.toString(testModel.eResource());
		
		assertEquals(oldSerializedText.replace("target1", "target2"), newSerializedText);
	}
}
