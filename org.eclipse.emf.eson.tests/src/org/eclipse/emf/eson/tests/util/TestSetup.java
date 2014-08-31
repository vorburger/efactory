/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.tests.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.eson.EFactoryStandaloneSetup;

import testmodel.TestmodelPackage;

public final class TestSetup {

	public static final TestSetup INSTANCE = new TestSetup();
	private boolean setupPerformed = false;

	private TestSetup() {
	}

	public void doSetup() {
		if (!setupPerformed) {
			performSetup();
			setupPerformed = true;
		}
	}

	private void performSetup() {
		if (!Platform.isRunning()) {
			EcorePackage.eINSTANCE.toString();
			TestmodelPackage.eINSTANCE.toString();
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
					"ecore", new EcoreResourceFactoryImpl());
			EFactoryStandaloneSetup.doSetup();
		}
	}

}
