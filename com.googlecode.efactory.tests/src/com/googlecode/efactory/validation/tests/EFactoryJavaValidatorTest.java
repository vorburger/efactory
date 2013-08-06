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
package com.googlecode.efactory.validation.tests;

import javax.inject.Inject;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestmodelPackage;

import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.validation.EFactoryJavaValidator;

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
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		EcorePackage.eINSTANCE.toString();
		TestmodelPackage.eINSTANCE.toString();
		setInjector(injector); // instead of with(EFactoryStandaloneSetup.class);
	}

	/**
	 * There used to be two identical error markers (one at the correct position
	 * and one on row 0) for each missing required property; this tests ensure
	 * that there is only one (non-regression).
	 */
	@Test
	public void testOnlyOneErrorForMissingRequiredProperty() throws Exception {
		EObject testModel = getModel("use testmodel.* TestModel { }");
		AssertableDiagnostics diags = tester.validate(testModel);
		for (Diagnostic diag : diags.getAllDiagnostics()) {
			System.out.println(diag.toString());
		}
		diags.assertDiagnosticsCount(1);
	}

}
