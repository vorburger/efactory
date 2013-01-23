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
 * $Id: Model.java 82 2009-11-16 05:39:21Z sebastian.benz $
 */
package com.googlecode.efactory.examples.entity;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Model</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.googlecode.efactory.examples.entity.Model#getElements <em>
 * Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.googlecode.efactory.examples.entity.EntityPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link com.googlecode.efactory.examples.entity.Type}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see com.googlecode.efactory.examples.entity.EntityPackage#getModel_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getElements();

} // Model
