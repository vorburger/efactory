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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.efactory.tests.util.TestSetup;
import com.googlecode.efactory.util.EPackageResolver;

public class EPackageResolverTest {

	@Before
	public void setUp() throws Exception {
		TestSetup.INSTANCE.doSetup();
	}

	@Test
	public void testResolveString_Registry() {
		final Resource context = new XMIResourceImpl();
		final String nsURI = EcorePackage.eINSTANCE.getNsURI();
		Assert.assertNotNull(nsURI);
		EPackage ePackage = new EPackageResolver().resolve(context, nsURI);
		Assert.assertNotNull(ePackage);
		Assert.assertEquals(nsURI, ePackage.getNsURI().toString());
	}

}
