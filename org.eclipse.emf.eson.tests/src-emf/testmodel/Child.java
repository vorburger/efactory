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
 * $Id: Child.java 82 2009-11-16 05:39:21Z sebastian.benz $
 */
package testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Child</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link testmodel.Child#getReferenceList <em>Reference List</em>}</li>
 * </ul>
 * </p>
 *
 * @see testmodel.TestmodelPackage#getChild()
 * @model
 * @generated
 */
public interface Child extends EObject {
	/**
	 * Returns the value of the '<em><b>Reference List</b></em>' reference list.
	 * The list contents are of type {@link testmodel.SingleRequired}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference List</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reference List</em>' reference list.
	 * @see testmodel.TestmodelPackage#getChild_ReferenceList()
	 * @model
	 * @generated
	 */
	EList<SingleRequired> getReferenceList();

} // Child
