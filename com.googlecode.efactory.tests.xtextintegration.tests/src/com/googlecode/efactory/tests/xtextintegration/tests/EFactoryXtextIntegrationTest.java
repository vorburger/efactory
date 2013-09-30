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
package com.googlecode.efactory.tests.xtextintegration.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import testModelWithXtextReference.TestModelWithXtextReference;

import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.xtextintegration.myDsl.Model;

/**
 * Tests if a reference from a "classic" manual ecore model
 * (TestModelWithXtextReference) in an efactory resource (b.efactory) to an
 * EObject in a "classic" (non-EFactory) Xtext generated ecore model (b.mydsl)
 * works.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(MyDslWithEFactoryInjectorProvider.class)
public class EFactoryXtextIntegrationTest {

	private static final String pluginId = "com.googlecode.efactory.tests.xtextintegration.tests";

	@Test
	public void testEFactoryXtextIntegration() throws Exception {
		ResourceProvider rp = new ResourceProvider(pluginId);
		EList<EObject> content = rp.load("res/XtextIntegration/a.mydsl", true);
		Model refXModel = (Model) content.get(0); 
		assertEquals("gr1", refXModel.getName());
		TestModelWithXtextReference efModel = rp.loadModel("res/XtextIntegration/b.efactory", TestModelWithXtextReference.class, true);
		assertEquals(refXModel, efModel.getModel());
	}

	// TODO Also do syntax completion UI test for above! that will need MyDslUiInjectorProvider instead.. separate test!
	
}
