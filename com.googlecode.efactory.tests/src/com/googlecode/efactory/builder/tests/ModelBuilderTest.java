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

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import testmodel.TestmodelPackage;

import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;

/**
 * Unit Test (!) for ModelBuilder.
 * This is NOT an integration test (the others in this package are).
 * 
 * @author Michael Vorburger
 */
public class ModelBuilderTest {

	@Test
	public void testBuild_getNewObject_Caching() throws Exception {
		Factory factory = EFactoryFactory.eINSTANCE.createFactory();
		NewObject newObject = EFactoryFactory.eINSTANCE.createNewObject();
		newObject.setEClass(TestmodelPackage.Literals.TEST_MODEL);
		factory.setRoot(newObject);
		
		ModelBuilder modelBuilder = new ModelBuilder();
		EObject result = modelBuilder.build(factory);
		EObject result2 = modelBuilder.build(factory);
		Assert.assertEquals(result, result2);
	}

	@Test
	public void testBuild_getNewObject_Null() throws Exception {
		try {
			new ModelBuilder().build((Factory) null);
		} catch (IllegalArgumentException e) {
			// expected
			return;
		}
		fail("Expected IllegalArgumentException for null argument");
	}

}
