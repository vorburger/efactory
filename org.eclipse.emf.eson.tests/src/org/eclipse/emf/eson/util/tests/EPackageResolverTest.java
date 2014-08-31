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
package org.eclipse.emf.eson.util.tests;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.eson.util.EPackageResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.emf.eson.tests.util.TestSetup;

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
