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
package com.googlecode.efactory.builder.resync.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.AttributeSample;
import testmodel.TestModel;
import testmodel.TestmodelFactory;
import testmodel.TestmodelPackage;

import com.google.inject.Provider;
import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.IntegerAttribute;
import com.googlecode.efactory.eFactory.StringAttribute;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.resource.EFactoryResource;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

/**
 * Tests the "re-sychronization" of changes to the derived "real" EObjects to
 * the source EFactory model.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class BuilderResyncTest {

	// using a Provider because we want each test to get a fresh ResourceProvider 
	@Inject Provider<ResourceProvider> rp;

	@BeforeClass
	public static void beforeTest() {
		TestSetup.INSTANCE.doSetup();
	}

	@Test
	public void testChangeName() throws Exception {
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/1TestModelWithNameProperty.efactory", true);
		
		// Check the EFactory model
		Factory eFactory = (Factory) resourceContents.get(0);
		assertNotNull(eFactory);
		Value efValue = eFactory.getRoot().getFeatures().get(0).getValue();
		StringAttribute efStringValue = (StringAttribute) efValue;
		assertEquals("test", efStringValue.getValue());
		
		// Check the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		assertEquals("test", testModel.getName());
		
		// Change the EFactory model and re-check the test model (ModelBuilder recreated it)
		efStringValue.setValue("testit");
		((EFactoryResource)eFactory.eResource()).discardDerivedState();
		((EFactoryResource)eFactory.eResource()).installDerivedState(true);
		testModel = (TestModel) resourceContents.get(1);
		assertEquals("testit", testModel.getName());
		
		// Change the TestModel and re-check the EFactory model
		testModel.setName("tested");
		efValue = eFactory.getRoot().getFeatures().get(0).getValue();
		efStringValue = (StringAttribute) efValue;
		assertEquals("tested", efStringValue.getValue());
	}

	@Test
	@Ignore // TODO
	public void testSetNewFeature() throws Exception {
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/1TestModelWithNameProperty.efactory", true);

		// Change the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		AttributeSample attributeSample = TestmodelFactory.eINSTANCE.createAttributeSample();
		attributeSample.setSingleIntOptional(123);
		testModel.setAttributeSample(attributeSample);

		// Check the EFactory model
		Factory eFactory = (Factory) resourceContents.get(0);
		Value efValue = eFactory.getRoot().getFeatures().get(1).getValue();
		Containment efContainmentValue = (Containment) efValue;
		assertEquals(TestmodelPackage.Literals.ATTRIBUTE_SAMPLE, efContainmentValue.getValue().getEClass());
		IntegerAttribute singleIntOptional = (IntegerAttribute)efContainmentValue.getValue().getFeatures().get(0).getValue();
		assertEquals(123, singleIntOptional.getValue());
	}

}
