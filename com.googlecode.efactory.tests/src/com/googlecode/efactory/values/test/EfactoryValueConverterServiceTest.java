/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Umesh - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.values.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.serialization.tests.SerializationUtils;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;


@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)

public class EfactoryValueConverterServiceTest {

		@Inject protected ResourceProvider resourceProvider;

		@BeforeClass
		public static void beforeTest() {
			TestSetup.INSTANCE.doSetup();
		}

		@Test public void testStringIdValueConversion() throws Exception {
			TestModel testModel = resourceProvider.loadModel("res/BuilderResyncTests/2TestModelWithNameAfterEClass.efactory", TestModel.class);
			assertEquals("test",testModel.getName());

			// set the id with value containing space
			testModel.setName("design model");
			String serializedText = SerializationUtils.toString(testModel.eResource());
			
			// assert for Id covered with single quotes
			assertTrue(serializedText.contains("'design model'"));
		}

}
