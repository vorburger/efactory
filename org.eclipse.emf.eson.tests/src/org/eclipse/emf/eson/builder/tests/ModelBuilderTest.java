/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.builder.tests;

import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eson.building.ModelBuilder;
import org.eclipse.emf.eson.eFactory.EFactoryFactory;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.junit.Assert;
import org.junit.Test;

import testmodel.TestmodelPackage;

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
		} catch (NullPointerException e) {
			// expected
			return;
		}
		fail("Expected IllegalArgumentException for null argument");
	}

}
