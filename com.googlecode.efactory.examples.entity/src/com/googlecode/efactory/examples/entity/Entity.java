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
 * $Id: Entity.java 82 2009-11-16 05:39:21Z sebastian.benz $
 */
package com.googlecode.efactory.examples.entity;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Entity</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.googlecode.efactory.examples.entity.Entity#getExtends <em>
 * Extends</em>}</li>
 * <li>{@link com.googlecode.efactory.examples.entity.Entity#getProperties <em>
 * Properties</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.googlecode.efactory.examples.entity.EntityPackage#getEntity()
 * @model
 * @generated
 */
public interface Entity extends Type {
	/**
	 * Returns the value of the '<em><b>Extends</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Extends</em>' reference.
	 * @see #setExtends(Entity)
	 * @see com.googlecode.efactory.examples.entity.EntityPackage#getEntity_Extends()
	 * @model
	 * @generated
	 */
	Entity getExtends();

	/**
	 * Sets the value of the '
	 * {@link com.googlecode.efactory.examples.entity.Entity#getExtends
	 * <em>Extends</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Extends</em>' reference.
	 * @see #getExtends()
	 * @generated
	 */
	void setExtends(Entity value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link com.googlecode.efactory.examples.entity.Property}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Properties</em>' containment reference
	 *         list.
	 * @see com.googlecode.efactory.examples.entity.EntityPackage#getEntity_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

} // Entity
