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

import org.eclipse.emf.ecore.EObject;

import testmodel.TestModel;

public class Partial2Test extends AbstractModelBuilderTest {

	@Override
	protected String getTestModelName() {
		return "Partial2Test.efactory";
	}
	
	public void testPartiallyTypedResourceNoExceptions() throws Exception {
		assertNotNull(testModel);
		
		EObject result = modelBuilder.build(factory);
		TestModel testModel = checkType(TestModel.class, result);
		
		assertNull(testModel.getSingleRequired());
	}
	
}
