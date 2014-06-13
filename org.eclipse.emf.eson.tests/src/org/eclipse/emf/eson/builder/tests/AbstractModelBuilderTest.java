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
package org.eclipse.emf.eson.builder.tests;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.emf.eson.tests.util.ResourceProvider;
import org.eclipse.emf.eson.tests.util.TestConstants;
import org.eclipse.emf.eson.tests.util.TestSetup;
import org.eclipse.emf.eson.util.Find;

import testmodel.TestModel;

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
		this.testModel = resourceProvider.loadModel("res/BuilderTests/" + getTestModelName(), TestModel.class, /* HACK TODO UNDO */ false);
		this.factory = ((EFactoryResource) testModel.eResource()).getEFactoryFactory();
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
