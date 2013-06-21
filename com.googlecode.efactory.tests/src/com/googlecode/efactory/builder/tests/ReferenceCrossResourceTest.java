/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.builder.tests;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class ReferenceCrossResourceTest {

	@Inject ResourceProvider provider;

	@Test public void testCrossResourceReference() throws Exception {
		TestSetup.INSTANCE.doSetup();
		/*TestModel aModel =*/ provider.loadModel("res/BuilderTests/ReferenceCrossResourceTestA.efactory", TestModel.class);
		TestModel bModel = provider.loadModel("res/BuilderTests/ReferenceCrossResourceTestB.efactory", TestModel.class);
		Assert.assertEquals("target1", bModel.getReferenceTestContainer().get(0).getReferenceToOne().getName());
	}
}
