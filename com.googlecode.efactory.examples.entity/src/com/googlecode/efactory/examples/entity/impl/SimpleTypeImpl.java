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
 * <copyright>
 * </copyright>
 *
 * $Id: SimpleTypeImpl.java 82 2009-11-16 05:39:21Z sebastian.benz $
 */
package com.googlecode.efactory.examples.entity.impl;

import org.eclipse.emf.ecore.EClass;

import com.googlecode.efactory.examples.entity.EntityPackage;
import com.googlecode.efactory.examples.entity.SimpleType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Simple Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class SimpleTypeImpl extends TypeImpl implements SimpleType {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SimpleTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EntityPackage.Literals.SIMPLE_TYPE;
	}

} // SimpleTypeImpl
