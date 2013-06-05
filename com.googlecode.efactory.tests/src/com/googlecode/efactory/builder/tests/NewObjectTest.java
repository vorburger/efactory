/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.builder.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import testmodel.TestModel;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.util.ValueResolver;

public class NewObjectTest extends AbstractModelBuilderTest {

	private static final String TEST_MODEL_NAME = "test";

	public void testBuild_NewObject() throws Exception {
		NewObject newObject = factory.getRoot();
		EObject result = modelBuilder.build(newObject);
		TestModel testModel = checkType(TestModel.class, result);
		checkName(testModel, TEST_MODEL_NAME);
		checkFeatures(newObject, testModel);
		checkListFeatures(newObject, testModel);
		checkAttributeValues(newObject, testModel);
	}

	private void checkAttributeValues(NewObject newObject, EObject eObject) {
		for (Feature feature : Iterables.filter(newObject.getFeatures(),
				new Predicate<Feature>() {

					public boolean apply(Feature input) {
						return (input.getValue() instanceof Attribute);
					}
				})) {
			checkAttributeValue(feature, eObject);
		}
	}

	private void checkAttributeValue(Feature feature, EObject eObject) {
		Object expectedValue = new ValueResolver().apply(feature.getValue());
		Object actualValue = eObject.eGet(feature.getEFeature());
		assertEquals(expectedValue, actualValue);
	}

	private void checkListFeatures(NewObject newObject, EObject eObject) {
		Map<EStructuralFeature, Integer> featureValueCount = new HashMap<EStructuralFeature, Integer>();
		for (Feature feature : newObject.getFeatures()) {
			if (feature.isIsMany()) {
				increaseValueCount(featureValueCount, feature);
			}
		}
		checkFeatureValueCount(featureValueCount, eObject);
	}

	private void checkFeatureValueCount(
			Map<EStructuralFeature, Integer> featureValueCount, EObject eObject) {
		for (Entry<EStructuralFeature, Integer> entry : featureValueCount
				.entrySet()) {
			EStructuralFeature eFeature = entry.getKey();
			EList<?> featureList = (EList<?>) eObject.eGet(eFeature);
			Integer valueCount = entry.getValue();
			assertEquals(valueCount.intValue(), featureList.size());
		}

	}

	private void checkFeatures(NewObject newObject, EObject eObject) {
		for (Feature feature : newObject.getFeatures()) {
			if (!feature.isIsMany()) {
				assertNotNull(eObject.eGet(feature.getEFeature()));
			}
		}

	}

	private void increaseValueCount(
			Map<EStructuralFeature, Integer> featureValueCount, Feature feature) {
		Integer count = featureValueCount.get(feature.getEFeature());
		if (count == null) {
			count = Integer.valueOf(1);
		} else {
			count = Integer.valueOf(count.intValue() + 1);
		}
		featureValueCount.put(feature.getEFeature(), count);
	}

	public void testBuild_getNewObject_Caching() throws Exception {
		NewObject newObject = factory.getRoot();
		EObject result = modelBuilder.build(newObject);
		EObject result2 = modelBuilder.build(newObject);
		assertEquals(result, result2);
	}

	public void testBuild_getNewObject_Null() throws Exception {
		try {
			modelBuilder.build((NewObject) null);
		} catch (IllegalArgumentException e) {
			// expected
			return;
		}
		fail("Expected IllegalArgumentException for null argument");
	}

	@Override
	protected String getTestModelName() {
		return "BuilderTest.efactory";
	}

}
