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
package org.eclipse.emf.eson.tests.util.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eson.tests.util.ESONWithTestmodelInjectorProvider;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test illustrating problem with lost *Package.eINSTANCE in @Before.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(ESONWithTestmodelInjectorProvider.class) // Not just EFactoryInjectorProvider
public class ESONWithTestmodelInjectorProviderTest {
	
	@Test
	public void testNoNameFeature() throws Exception {
		assertNotNull(EPackage.Registry.INSTANCE.getEPackage("http://testmodel/1.0"));
	}

	@Test
	public void testOnlyOneErrorForMissingRequiredProperty() throws Exception {
		assertNotNull(EPackage.Registry.INSTANCE.getEPackage("http://testmodel/1.0"));
	}
}
