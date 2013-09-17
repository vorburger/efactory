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
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.AttributeSample;
import testmodel.AttributeTestContainer;
import testmodel.SampleEnum;
import testmodel.SingleRequired;
import testmodel.TestModel;
import testmodel.TestmodelFactory;
import testmodel.TestmodelPackage;

import com.google.inject.Provider;
import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.EnumAttribute;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.IntegerAttribute;
import com.googlecode.efactory.eFactory.MultiValue;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.StringAttribute;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.resource.EFactoryResource;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

/**
 * Tests EFactoryAdapter's "re-sychronization" of changes to the derived "real"
 * EObjects to the source EFactory model.
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
	public void testAddRemoveMoveListElements() throws Exception {
		// add (nested) stuff.. add element to list before (no change tracker attached yet) and after attaching it to resource
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/1TestModelWithNameProperty.efactory", true);

		// Change the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		AttributeTestContainer firstAttributeTestContainer = TestmodelFactory.eINSTANCE.createAttributeTestContainer();
		firstAttributeTestContainer.setOneInt(9876);
		testModel.getAttributeTest().add(firstAttributeTestContainer);

		// Check the EFactory model
		Factory eFactory = (Factory) resourceContents.get(0);
		checkListFeature(eFactory, 2, 0, 9876);
		assertEquals(1, getMultiValueValues(eFactory, 2).size());

		// add another element to existing list
		AttributeTestContainer secondAttributeTestContainer = TestmodelFactory.eINSTANCE.createAttributeTestContainer();
		secondAttributeTestContainer.setOneInt(5432);
		testModel.getAttributeTest().add(secondAttributeTestContainer);
		// check again just to make sure first (above) is still there
		checkListFeature(eFactory, 2, 0, 9876);
		// check second element just added
		checkListFeature(eFactory, 2, 1, 5432);
		assertEquals(2, getMultiValueValues(eFactory, 2).size());

		// move elements
		testModel.getAttributeTest().move(1, 0);
		checkListFeature(eFactory, 2, 0, 5432);
		checkListFeature(eFactory, 2, 1, 9876);
		assertEquals(2, getMultiValueValues(eFactory, 2).size());

		// remove the first element from list
		testModel.getAttributeTest().remove(0);
		checkListFeature(eFactory, 2, 0, 9876);
		assertEquals(1, getMultiValueValues(eFactory, 2).size());

		// addAll two new elements element to list
		Collection<AttributeTestContainer> moreAttributeTestContainers = new ArrayList<AttributeTestContainer>(2);
		AttributeTestContainer thirdAttributeTestContainer = TestmodelFactory.eINSTANCE.createAttributeTestContainer();
		thirdAttributeTestContainer.setOneInt(3487);
		moreAttributeTestContainers.add(thirdAttributeTestContainer);
		AttributeTestContainer forthAttributeTestContainer = TestmodelFactory.eINSTANCE.createAttributeTestContainer();
		forthAttributeTestContainer.setOneInt(1273);
		moreAttributeTestContainers.add(forthAttributeTestContainer);
		testModel.getAttributeTest().addAll(moreAttributeTestContainers);
		checkListFeature(eFactory, 2, 0, 9876);
		checkListFeature(eFactory, 2, 1, 3487);
		checkListFeature(eFactory, 2, 2, 1273);
		assertEquals(3, getMultiValueValues(eFactory, 2).size());
		
		// removeAll an elements from middle of list
		moreAttributeTestContainers.clear();
		// Note: need to add() two - because if it's only one, there is actually
		// an internal optimization somewhere in EMF which turns it into a
		// Notification.REMOVE (sufficient), but won't test REMOVE_MANY. 
		moreAttributeTestContainers.add(firstAttributeTestContainer);
		moreAttributeTestContainers.add(forthAttributeTestContainer);
		testModel.getAttributeTest().removeAll(moreAttributeTestContainers);
		assertEquals(1, getMultiValueValues(eFactory, 2).size());
		checkListFeature(eFactory, 2, 0, 3487);
	}

	protected void checkListFeature(Factory eFactory, int featureIndex, int multiValueIndex, int expectedInt) {
		NewObject newObject = checkNewObjectAttributeTestContainer(eFactory, featureIndex, multiValueIndex);
		IntegerAttribute oneInt = (IntegerAttribute)newObject.getFeatures().get(0).getValue();
		assertEquals(expectedInt, oneInt.getValue());
	}

	protected NewObject checkNewObjectAttributeTestContainer(Factory eFactory, int featureIndex, int multiValueIndex) {
		final EList<Value> values = getMultiValueValues(eFactory, featureIndex);
		Value listItemValue = values.get(multiValueIndex);
		assertTrue(listItemValue.eClass().toString(), listItemValue instanceof Containment);
		Containment efContainmentValue = (Containment) listItemValue;
		NewObject newObject = efContainmentValue.getValue();
		assertEquals(TestmodelPackage.Literals.ATTRIBUTE_TEST_CONTAINER, newObject.getEClass());
		return newObject;
	}

	protected EList<Value> getMultiValueValues(Factory eFactory, int featureIndex) {
		Feature newFeature = eFactory.getRoot().getFeatures().get(featureIndex);
		return getMultiValueValues(newFeature);
	}

	protected EList<Value> getMultiValueValues(Feature newFeature) {
		final Value value = newFeature.getValue();
		assertTrue(value.eClass().toString(), value instanceof MultiValue);
		MultiValue multiValue = (MultiValue) value;
		final EList<Value> values = multiValue.getValues();
		return values;
	}

	@Test
	public void testEnum() throws Exception {
		// add (nested) stuff.. add element to list before (no change tracker attached yet) and after attaching it to resource
		EList<EObject> resourceContents = rp.get().load("res/BuilderResyncTests/1TestModelWithNameProperty.efactory", true);

		// Change the TestModel
		TestModel testModel = (TestModel) resourceContents.get(1);
		AttributeTestContainer attributeTestContainer = TestmodelFactory.eINSTANCE.createAttributeTestContainer();
		attributeTestContainer.setOneEnum(SampleEnum.SAMPLE2);
		attributeTestContainer.getManyEnums().add(SampleEnum.SAMPLE);
		testModel.getAttributeTest().add(attributeTestContainer);

		// Check the EFactory model
		Factory eFactory = (Factory) resourceContents.get(0);
		NewObject newObject = checkNewObjectAttributeTestContainer(eFactory, 2, 0);
		EnumAttribute oneEnum = (EnumAttribute)newObject.getFeatures().get(3).getValue();
		assertEquals(SampleEnum.SAMPLE2.getName(), oneEnum.getValue().getName());
		
		EList<Value> manyEnums = getMultiValueValues(newObject.getFeatures().get(5));
		assertEquals(1, manyEnums.size());
		EnumAttribute firstOfManyEnums = (EnumAttribute)manyEnums.get(0);
		assertEquals(SampleEnum.SAMPLE.getName(), firstOfManyEnums.getValue().getName());
		
		// change TestModel by adding one more enum to list
		attributeTestContainer.getManyEnums().add(SampleEnum.SAMPLE2);
		// check it again
		manyEnums = getMultiValueValues(newObject.getFeatures().get(5));
		assertEquals(2, manyEnums.size());
		EnumAttribute secondOfManyEnums = (EnumAttribute)manyEnums.get(1);
		assertEquals(SampleEnum.SAMPLE2.getName(), secondOfManyEnums.getValue().getName());
		firstOfManyEnums = (EnumAttribute)manyEnums.get(0);
		assertEquals(SampleEnum.SAMPLE.getName(), firstOfManyEnums.getValue().getName());
	}
}














