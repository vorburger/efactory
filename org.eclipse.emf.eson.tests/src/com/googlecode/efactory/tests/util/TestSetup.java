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

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import testmodel.TestmodelPackage;

import com.googlecode.efactory.EFactoryStandaloneSetup;

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
