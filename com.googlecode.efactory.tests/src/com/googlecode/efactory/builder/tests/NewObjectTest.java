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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import testmodel.TestModel;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.MultiValue;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.util.ValueResolver;

public class NewObjectTest extends AbstractModelBuilderTest {

	private static final String TEST_MODEL_NAME = "test";

	public void testBuild_NewObject() throws Exception {
		testModel.getSingleRequired().getParentReference(); // just to illustrate current problem
		// TODO asssert return value (and move to the end?)
		
		checkName(testModel, TEST_MODEL_NAME);
		
		NewObject newObject = factory.getRoot();
		checkFeatures(newObject, testModel);
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

	private void checkFeatures(NewObject newObject, EObject eObject) {
		for (Feature feature : newObject.getFeatures()) {
			assertNotNull(eObject.eGet(feature.getEFeature()));
			if (feature.getEFeature().isMany()) {
				MultiValue multiValue = (MultiValue) feature.getValue();
				EList<?> eList = (EList<?>) eObject.eGet(feature.getEFeature());
				assertEquals(multiValue.getValues().size(), eList.size());
			}
		}
	}

	@Override
	protected String getTestModelName() {
		return "BuilderTest.efactory";
	}

}
