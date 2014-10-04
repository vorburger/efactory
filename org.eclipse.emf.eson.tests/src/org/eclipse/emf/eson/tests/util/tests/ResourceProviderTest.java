/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.tests.util.tests;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.tests.util.ResourceProvider;

/**
 * Tests ResourceProvider.
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class ResourceProviderTest {

	@Inject ResourceProvider resourceProvider;
	
	/**
	 * Test that ResourceProvider validates what it reads.
	 */
	@Test(expected=DiagnosticException.class)
	public void testResourceProviderValidation() throws IOException, Exception {
		resourceProvider.loadModel("res/UtilsTests/MissingRequired.efactory");
	}

}
