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
/**
 * $Id: Check.java 123 2010-06-03 20:07:43Z sebastian.benz $
 */
package org.eclipse.emf.eson.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A class that provides different check methods.
 */
public abstract class Check {

	private static final String FEATURE_ERROR_MESSAGE = "' has no feature '";
	private static final String APOSTROPHE = "'";
	private static final String TYPE_ERROR_MESSAGE2 = "' but was '";
	private static final String TYPE_ERROR_MESSAGE = "Expected '";

	/**
	 * Checks if the passed obj is null.
	 * 
	 * @param message
	 *            the message
	 * @param obj
	 */
	public static void notNull(String message, Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks if the passed obj is an instance of an class.
	 * 
	 * @param obj
	 *            the object
	 * @param clazz
	 *            the class
	 */
	public static void type(Object obj, Class<?> clazz) {
		if (!clazz.isInstance(obj)) {
			throw new IllegalArgumentException(TYPE_ERROR_MESSAGE
					+ clazz.getName() + TYPE_ERROR_MESSAGE2
					+ obj.getClass().getName() + APOSTROPHE);
		}
	}

	/**
	 * Checks if the class of the eObject has the passed feature.
	 * 
	 * @param obj
	 *            the EMF Object
	 * @param feature
	 *            the EMF feature
	 */
	public static void hasFeature(EObject obj, EStructuralFeature feature) {
		EClass eClass = obj.eClass();
		if (!eClass.getEAllReferences().contains(feature)) {
			throw new IllegalArgumentException(APOSTROPHE + eClass.getName()
					+ FEATURE_ERROR_MESSAGE + feature.getName() + APOSTROPHE);
		}
	}

	/**
	 * Checks if the EMF class has the passed feature.
	 * 
	 * @param eClass
	 *            the EMF Class
	 * @param feature
	 *            the EMF feature
	 */
	public static void hasFeature(EClass eClass, EStructuralFeature feature) {
		if (!eClass.getEAllStructuralFeatures().contains(feature)) {
			throw new IllegalArgumentException(APOSTROPHE + eClass.getName()
					+ FEATURE_ERROR_MESSAGE + feature.getName() + APOSTROPHE);
		}
	}

	/**
	 * Checks whether the EObject is contained in the EPackage
	 * 
	 * @param eObject
	 * @param einstance
	 */
	public static void hasEPackage(EObject eObject, EPackage ePackage) {
		if (eObject.eClass().getEPackage() != ePackage) {
			throw new IllegalArgumentException(eObject
					+ " is not an element of the package " + ePackage.getName());
		}
	}
}
