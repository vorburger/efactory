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
package com.googlecode.efactory.tests.util;

import junit.framework.TestCase;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryRuntimeModule;
import com.googlecode.efactory.EFactoryStandaloneSetup;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.tests.Activator;
import com.googlecode.efactory.ui.EFactoryUiModule;

public abstract class AbstractEFactoryTest extends TestCase {

	protected ResourceProvider resourceProvider = new ResourceProvider(
			TestConstants.PLUGIN_ID);

	public AbstractEFactoryTest() {
		super();
	}

	public AbstractEFactoryTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		EFactoryStandaloneSetup.doSetup();
		TestSetup.INSTANCE.doSetup();

	}

	protected Injector getInjector() {
		return Guice.createInjector(new EFactoryRuntimeModule());
	}

	protected NewObject getNewObject(Feature feature) {
		return (NewObject) feature.eContainer();
	}

	public Injector getUiInjector() {
		return Guice.createInjector(new EFactoryUiModule(Activator.getInstance()));
	}

}
