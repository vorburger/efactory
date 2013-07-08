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

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;

import testmodel.TestModel;

import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.resource.EFactoryResource;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestConstants;
import com.googlecode.efactory.tests.util.TestSetup;
import com.googlecode.efactory.util.Find;

public abstract class AbstractModelBuilderTest extends TestCase {

	private ResourceProvider resourceProvider;
	protected Factory factory;
	protected TestModel testModel;

	public AbstractModelBuilderTest() {
		super();
	}

	public AbstractModelBuilderTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		TestSetup.INSTANCE.doSetup();
		this.resourceProvider = new ResourceProvider(TestConstants.PLUGIN_ID);
		this.testModel = resourceProvider.loadModel("res/BuilderTests/" + getTestModelName(), TestModel.class);
		this.factory = ((EFactoryResource) testModel.eResource()).getFactory();
	}

	protected abstract String getTestModelName();

	protected <T> T checkType(Class<T> clazz, EObject eObject) {
		assertTrue(clazz.isInstance(eObject));
		return clazz.cast(eObject);
	}

	protected void checkName(TestModel testModel, String testModelName) {
		assertEquals(testModelName, testModel.getName());
	}

	public <T extends EObject> T find(final Class<T> candidateClass,
			final String candidateName) {
		return Find.child(candidateClass, candidateName, testModel);
	}

}
