/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2014 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.tests.util;

import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.tests.util.tests.ESONWithTestmodelInjectorProviderTest;

import testmodel.impl.TestmodelPackageImpl;

import com.google.inject.Injector;

/**
 * IInjectorProvider for ESON + TestmodelPackage.
 * 
 * This is needed because the approach of just putting
 * TestmodelPackageImpl.init() into the @Before of a *Test doesn't quite work
 * reliably, it works for one @Test but not several, because "*PackageImpl
 * init() will not always put itself into the registry. It does that only on
 * first call. IRegistryConfigurator . That will create a copy of the registries
 * before each test and set that state back after the test has run." Using @BeforeClass
 * works as well, but completely defeats the purpose.
 * 
 * @see ESONWithTestmodelInjectorProviderTest
 * 
 * @author Michael Vorburger, based on advise from Sven Efftinge
 */
public class ESONWithTestmodelInjectorProvider extends EFactoryInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		TestmodelPackageImpl.init();
		return super.internalCreateInjector();
	}

}
