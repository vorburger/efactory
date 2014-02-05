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

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eson.validation.EFactoryJavaValidator;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestmodelPackage;

import com.google.inject.Injector;
import org.eclipse.emf.eson.EFactoryInjectorProvider;

/**
 * Tests for EFactoryJavaValidator.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class EFactoryJavaValidatorTest extends AbstractXtextTests {

	@Inject Injector injector;
	@Inject ValidatorTester<EFactoryJavaValidator> tester;
	@Inject IResourceValidator resourceValidator;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		EcorePackage.eINSTANCE.toString();
		TestmodelPackage.eINSTANCE.toString();
		setInjector(injector); // instead of with(EFactoryStandaloneSetup.class);
	}
	
/*
	@Test
	public void test...() throws Exception {
		AssertableDiagnostics diags = tester.validate(testModel);
		for (Diagnostic diag : diags.getAllDiagnostics()) {
			System.out.println(diag.toString());
		}
		diags.assertDiagnosticsCount(1);
	}
*/	

	/**
	 * There used to be two identical error markers (one at the correct position
	 * and one on row 0) for each missing required property; this test ensures
	 * that there is only one (non-regression).
	 * 
	 * The solution to fix the problem above was to bind the DerivedStateAwareResourceValidator as IResourceValidator.
	 * 
	 * Unfortunately the test doesn't currently work.. before the DerivedStateAwareResourceValidator
	 * it used to always return 1 issue (instead of 2), and now 0 (instead of 1). 
	 */
	@Test
	@Ignore // TODO I don't know how to get this test to work.. can anyone help?
	public void testOnlyOneErrorForMissingRequiredProperty() throws Exception {
		EObject testModel = getModel("use testmodel.* TestModel { }");
		List<Issue> issues = resourceValidator.validate(testModel.eResource(), CheckMode.ALL, null);
		for (Issue issue : issues) {
			System.out.println(issue.toString());
		}
		assertEquals(1, issues.size());
	}
}
