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

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;

import testmodel.TestmodelPackage;

import com.googlecode.efactory.util.EcoreUtil3;

public class EcoreUtilTest extends TestCase {

	public void testIsInstantiatable_Abstract() throws Exception {
		EClass abstractEClass = EcoreFactory.eINSTANCE.createEClass();
		abstractEClass.setAbstract(true);
		assertFalse(EcoreUtil3.isInstantiatable(abstractEClass));
	}

	public void testIsInstantiatable() throws Exception {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		assertTrue(EcoreUtil3.isInstantiatable(eClass));
	}

	public void testIsInstantiatable_Interface() throws Exception {
		EClass interf = EcoreFactory.eINSTANCE.createEClass();
		interf.setInterface(true);
		assertFalse(EcoreUtil3.isInstantiatable(interf));
	}

	public void testIsSubclass_Same() {
		EClass eClass = TestmodelPackage.Literals.ATTRIBUTE_SAMPLE;
		assertTrue(EcoreUtil3.isSubClass(eClass, eClass));
	}

	public void testIsSubclass_DirectSubclass() {
		EClass subClass = TestmodelPackage.Literals.SINGLE_REQUIRED;
		EClass eClass = TestmodelPackage.Literals.SINGLE_OPTIONAL;
		assertTrue(EcoreUtil3.isSubClass(eClass, subClass));
	}

	public void testIsSubclass_IndirectSubclass() {
		EClass eClass = TestmodelPackage.Literals.INTERFACE;
		EClass subClass = TestmodelPackage.Literals.SINGLE_REQUIRED;
		assertTrue(EcoreUtil3.isSubClass(eClass, subClass));
	}

	public void testIsSubclass_False() {
		EClass eClass = TestmodelPackage.Literals.SINGLE_OPTIONAL;
		EClass subClass = TestmodelPackage.Literals.TEST_MODEL;
		assertFalse(EcoreUtil3.isSubClass(eClass, subClass));
	}
}
