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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.validation.EFactoryJavaValidator;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
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
	@Inject IResourceValidator resourceValidator;
	
	@BeforeClass // This *HAS* to be @BeforeClass, a @Before doesn't work 
	public static void setUp() throws Exception {
		TestmodelPackage.eINSTANCE.toString();
	}
	
	@Test
	public void testNoNameFeature() throws Exception {
		InputStream is = getClass().getResourceAsStream("/BuilderTests/NameAttribute.efactory");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is, URI.createURI("BuilderTests/NameAttribute.efactory"), null, rs);
		AssertableDiagnostics diags = tester.validate(testModel);
		for (Diagnostic diag : diags.getAllDiagnostics()) {
			System.out.println(diag.toString());
		}
		diags.assertDiagnosticsCount(1);
		diags.assertErrorContains("Cannot name");
		// TODO assert it's the right error, not just any
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
		List<Issue> issues = resourceValidator.validate(testModel.eResource(), CheckMode.ALL, null);
		for (Issue issue : issues) {
			System.out.println(issue.toString());
		}
		assertEquals(1, issues.size());
	}
}
