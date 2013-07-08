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
package com.googlecode.efactory.xcore.tests;

import javax.inject.Inject;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xcore.XcoreStandaloneSetup;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;

/**
 * Tests specifically related to Xcore integration.
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class XcoreTest {

	@Inject ResourceProvider resourceProvider;

	@Test
	@SuppressWarnings("unused")
	public void testXcore() throws Exception {
		XcoreStandaloneSetup.doSetup();
		GenModel genModel = (GenModel) resourceProvider.loadModel("model/TestModel2.xcore");
		// feature = how to get the 'age' out of this??
		EObject eObject = resourceProvider.loadModel("res/xcore/XcoreTest.efactory");
		// TODO ideally should eObject.eGet(feature) - to check age is 99
	}
}
