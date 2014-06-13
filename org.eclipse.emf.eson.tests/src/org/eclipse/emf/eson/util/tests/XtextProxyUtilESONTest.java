/*******************************************************************************
 * Copyright (c) 2014 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.eson.util.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.emf.eson.util.XtextProxyUtil;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.Child;
import testmodel.SingleRequired;
import testmodel.TestModel;
import testmodel.TestmodelPackage;

/**
 * Tests XtextProxyUtil on ESON.
 * 
 * @see com.googlecode.efactory.tests.xtextintegration.tests.XtextProxyUtilXtextTest
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class XtextProxyUtilESONTest {

	@Inject
	XtextProxyUtil proxyUtil;
	
	@Inject
	ParseHelper<TestModel> ph;

	@Inject 
	IResourceValidator validator;
	
	@BeforeClass
	public static void setUp() throws Exception {
		// ? EcorePackage.eINSTANCE.toString();
		TestmodelPackage.eINSTANCE.toString();
	}
	
	@Test
	public void testGetSingleProxyCrossRefAsString() throws Exception {
		EObject esonEObject = ph.parse("use testmodel.* TestModel test { singleRequired: SingleRequired { parentReference: ItsNotLinkedYet } }");
		TestModel model = EFactoryResource.getEFactoryEObject(esonEObject.eResource(), TestModel.class); 
		// DO NOT validate using IResourceValidator, as it's -intentionally- broken
		
		SingleRequired singleRequired = model.getSingleRequired();
		TestModel refModel = singleRequired.getParentReference();
		assertNotNull(refModel);
		String refText = proxyUtil.getProxyCrossRefAsString(singleRequired, refModel);
		assertEquals("ItsNotLinkedYet", refText);
	}

	@Test
	public void testGetMultiProxyCrossRefAsString() throws Exception {
		EObject esonEObject = ph.parse("use testmodel.* TestModel test { singleRequired: SingleRequired { } children: [ Child { referenceList: [ ItsNotLinkedYet ] } ] }");
		// NO assertNoIssues(validator.validate(esonEObject.eResource(), CheckMode.ALL, null));
		TestModel model = EFactoryResource.getEFactoryEObject(esonEObject.eResource(), TestModel.class); 
		Child child = model.getChildren().get(0);
		EList<SingleRequired> referenceList = child.getReferenceList();
		SingleRequired refModel = referenceList.get(0);
		assertNotNull(refModel);
		String refText = proxyUtil.getProxyCrossRefAsString(child, refModel);
		assertEquals("ItsNotLinkedYet", refText);
	}

	@SuppressWarnings("unused")
	private void assertNoIssues(List<Issue> issues) {
		for (Issue issue : issues) {
			System.err.println(issue.toString());
		}
		if (!issues.isEmpty())
			fail(issues.get(0).toString());
	}

}
