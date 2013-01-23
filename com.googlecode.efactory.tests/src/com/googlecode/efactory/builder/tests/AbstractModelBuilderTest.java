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

import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestConstants;
import com.googlecode.efactory.tests.util.TestSetup;
import com.googlecode.efactory.util.Find;

public abstract class AbstractModelBuilderTest extends TestCase {

	protected ModelBuilder modelBuilder;
	private ResourceProvider resourceProvider;
	protected Factory factory;
	protected EObject testModel;
	private NewObject root;

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
		this.factory = resourceProvider.loadFactory("res/BuilderTests/"
				+ getTestModelName());
		this.modelBuilder = new ModelBuilder();
		root = factory.getRoot();
		testModel = modelBuilder.build(root);
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
