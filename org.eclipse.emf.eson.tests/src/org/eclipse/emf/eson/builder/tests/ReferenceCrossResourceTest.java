/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.builder.tests;

import javax.inject.Inject;

import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.tests.util.ResourceProvider;
import org.eclipse.emf.eson.tests.util.TestSetup;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class ReferenceCrossResourceTest {

	@Inject ResourceProvider provider;

	@Test public void testCrossResourceReference() throws Exception {
		TestSetup.INSTANCE.doSetup();
		/*TestModel aModel =*/ provider.loadModel("res/BuilderTests/ReferenceCrossResourceTestA.efactory", TestModel.class);
		TestModel bModel = provider.loadModel("res/BuilderTests/ReferenceCrossResourceTestB.efactory", TestModel.class);
		Assert.assertEquals("1target,1", bModel.getReferenceTestContainer().get(0).getReferenceToOne().getName());
	}
}
