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
package com.googlecode.efactory.util.tests;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.googlecode.efactory.tests.util.TestSetup;
import com.googlecode.efactory.util.EPackageResolver;

public class EPackageResolverTest extends TestCase {
	Resource context = new XMIResourceImpl();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestSetup.INSTANCE.doSetup();
	}

	public void testResolveString_Registry() {

		EPackage ePackage = new EPackageResolver().resolve(context,
				EcorePackage.eINSTANCE.getNsURI());
		assertNotNull(ePackage);
		assertEquals(EcorePackage.eINSTANCE.getNsURI(), ePackage.getNsURI()
				.toString());
	}

}
