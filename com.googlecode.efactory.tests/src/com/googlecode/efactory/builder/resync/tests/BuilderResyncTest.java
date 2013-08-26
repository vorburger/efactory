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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.AttributeSample;
import testmodel.SingleRequired;
import testmodel.TestModel;
import testmodel.TestmodelFactory;
import testmodel.TestmodelPackage;

import com.google.inject.Provider;
import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.IntegerAttribute;
import com.googlecode.efactory.eFactory.NewObject;
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
	public void testChangeNameStringValueFeature() throws Exception {
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
		
		// Change the TestModel and re-check the EFactory model (EFactoryAdapter did it's thing)
		testModel.setName("tested");
		assertEquals("tested", getRootObjectFirstFeatureAsString(eFactory));
		assertNull(eFactory.getRoot().getName()); // as there already was name = "test", it should NOT become TestModel tested
		
		int nFeatures = eFactory.getRoot().getFeatures().size();
		testModel.setName(null);
		assertTrue(eFactory.getRoot().getFeatures().size() == nFeatures - 1);
	}

	protected String getRootObjectFirstFeatureAsString(Factory eFactory) {
		final Value efValue = eFactory.getRoot().getFeatures().get(0).getValue();
		final StringAttribute efStringValue = (StringAttribute) efValue;
		return efStringValue.getValue();
	}

	@Test
	public void testChangeNewObjectNameAfterClass() throws Exception {
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/2TestModelWithNameAfterEClass.efactory", true);
		Factory eFactory = (Factory) resourceContents.get(0);

		// Check the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		assertEquals("test", testModel.getName());
		
		// Change the TestModel and re-check the EFactory model
		testModel.setName("tested");
		assertEquals("tested", eFactory.getRoot().getName());
		
		// Check setting null (unsetting)
		testModel.setName(null);
		assertNull(eFactory.getRoot().getName());
	}

	@Test
	public void testSetNewFeature() throws Exception {
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/1TestModelWithNameProperty.efactory", true);

		// Change the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		AttributeSample attributeSample = TestmodelFactory.eINSTANCE.createAttributeSample();
		attributeSample.setSingleIntOptional(123);
		testModel.setAttributeSample(attributeSample);

		// Check the EFactory model
		Factory eFactory = (Factory) resourceContents.get(0);
		Value efValue = eFactory.getRoot().getFeatures().get(2).getValue();
		Containment efContainmentValue = (Containment) efValue;
		final NewObject newObject = efContainmentValue.getValue();
		assertEquals(TestmodelPackage.Literals.ATTRIBUTE_SAMPLE, newObject.getEClass());
		IntegerAttribute singleIntOptional = (IntegerAttribute)newObject.getFeatures().get(0).getValue();
		assertEquals(123, singleIntOptional.getValue());
	}

	/**
	 * New objects, with an empty resource. Note: For technical implementation
	 * reasons, this MUST be done by starting with loading an empty resource,
	 * not with creating a new resource and adding objects to its content and
	 * then saving it. This is because if it were done like this, then the
	 * content(0) EFactory Factory NewObject root object wouldn't be created
	 * first as it needs to be. This programmatic "trick" (loading empty
	 * resource) actually corresponds to how e.g. the Sample Ecore Model Editor
	 * (EcoreEditor), or other such UIs, would operate in practice anyways (the
	 * would open an empty new file), so... it actually isn't really a "hack".
	 */
	@Test
	public void testCreateCompletelyNew() throws Exception {
		// Java 7: Path newEmptyFilePath = Files.createTempFile(); File newEmptyFile = newEmptyFilePath.toFile();
		File newEmptyFile = File.createTempFile(getClass().getSimpleName() + "Model", ".efactory");
		com.google.common.io.Files.touch(newEmptyFile); 
		
		EList<EObject> resContents = rp.get().load(newEmptyFile, false);
		assertEquals(1, resContents.size()); // sic! Note: An empty resource - but it already has the Factory NewObject root object

		TestModel testModel = TestmodelFactory.eINSTANCE.createTestModel();
		SingleRequired singleRequired = TestmodelFactory.eINSTANCE.createSingleRequired();
		testModel.setSingleRequired(singleRequired);
		testModel.setName("testCreateCompletelyNew");
		resContents.add(testModel);
		assertEquals(2, resContents.size());
		
		Factory factory = (Factory) resContents.get(0);
		String nameAgain = factory.getRoot().getName();
		assertEquals("testCreateCompletelyNew", nameAgain);
		EClass eClass = factory.getRoot().getEClass();
		assertEquals(TestmodelPackage.Literals.TEST_MODEL, eClass);

		assertTrue(factory.getEpackages().isEmpty());
		assertTrue(factory.getImports().isEmpty());
		assertTrue(factory.getAnnotations().isEmpty());
	}
	
	@Test
	@Ignore // TODO
	public void testAddToList() throws Exception {
		// add (nested) stuff.. add element to list before (no change tracker attached yet) and after attaching it to resource
	}

}
