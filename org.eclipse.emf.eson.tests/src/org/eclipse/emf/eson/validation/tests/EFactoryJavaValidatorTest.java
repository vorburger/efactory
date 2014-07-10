/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.eson.validation.tests;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.validation.EFactoryJavaValidator;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestmodelPackage;

/**
 * Tests for EFactoryJavaValidator.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class EFactoryJavaValidatorTest {

	@Inject ResourceSet rs;
	@Inject ParseHelper<EObject> parseHelper;
	@Inject ValidatorTester<EFactoryJavaValidator> tester;
	
	@BeforeClass // This *HAS* to be @BeforeClass, a @Before doesn't work 
	public static void setUp() throws Exception {
		TestmodelPackage.eINSTANCE.toString();
	}
	
	/**
	 * Test validation of invalid attempt to name an object (which isn't
	 * possible if it doesn't have a name mapping; i.e. no setName()).
	 */
	@Test
	public void testNoNameFeature() throws Exception {
		InputStream is = getClass().getResourceAsStream("/BuilderTests/NameAttribute.efactory");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is, URI.createURI("BuilderTests/NameAttribute.efactory"), null, rs);
		tester.validate(testModel).assertErrorContains("Cannot name");
	}

	/**
	 * There used to be two identical error markers (one at the correct position
	 * and one on row 0) for each missing required property; this test ensures
	 * that there is only one (non-regression).
	 * 
	 * The solution to fix the problem above was to bind the DerivedStateAwareResourceValidator as IResourceValidator.
	 */
	@Test
	public void testOnlyOneErrorForMissingRequiredProperty() throws Exception {
		EObject testModel = parseHelper.parse("use testmodel.* TestModel { }");
		tester.validate(testModel).assertErrorContains("The required feature");
	}
}
