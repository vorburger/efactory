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
import org.eclipse.xtext.util.CancelIndicator;
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
	
	/**
	 * Test validation of invalid attempt to name an object (which isn't
	 * possible if it doesn't have a name mapping; i.e. no setName()).
	 */
	@Test
	public void testNoNameFeature() throws Exception {
		InputStream is = getClass().getResourceAsStream("/BuilderTests/NameAttribute.efactory");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is, URI.createURI("BuilderTests/NameAttribute.efactory"), null, rs);
		tester.validate(testModel).assertError(EFactoryJavaValidator.ERR_CANNOT_NAME);
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
	
	/**
	 * There used to be 3 or 4 highly technical error markers in case of broken
	 * references, which made non sense to end-users. This non-regression test
	 * ensure that there is only one, and that it is clear (it must include the
	 * text of the broken reference).
	 */
	@Test
	public void testOnlyOneErrorForBrokenReference() throws Exception {
		EObject testModel = parseHelper.parse("use testmodel.* TestModel test { singleRequired: SingleRequired { parentReference: ItsNotLinkedYet } }");
		AssertableDiagnostics diag = tester.validate(testModel);
		// dumpDiagnostics(diag);
		diag.assertError(org.eclipse.xtext.diagnostics.Diagnostic.LINKING_DIAGNOSTIC, "ItsNotLinkedYet");
		// Message must include correct type of broken link. This is less
		// obvious than it may seem at first.. because a Reference value is
		// just an [EObject] in the grammar, LinkingDiagnosticMessageProvider
		// cannot get this right - but we do in our custom
		// EFactoryJavaValidator.checkIsBrokenReference().
		// TODO diag.assertErrorContains("TestModel");
		
		// It's important to additionally test using an IResourceValidator that
		// we really do only have 1 error, because the ValidatorTester doesn't
		// catch errors added from LazyLinkingResource's getEObject() &
		// createAndAddDiagnostic(). Implementation wise this tests that the
		// suppression in ESONLinkingDiagnosticMessageProvider works.
		List<Issue> issues = resourceValidator.validate(testModel.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
		assertEquals(1, issues.size());
	}
	
	protected void dumpDiagnostics(AssertableDiagnostics diag) {
		Iterable<Diagnostic> all = diag.getAllDiagnostics();
		for (Diagnostic diagnostic : all) {
			System.out.println(diagnostic.getMessage());
			if (diagnostic.getException() != null)
				diagnostic.getException().printStackTrace();
		}
	}
}
