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
 * $Id: TestModel.java 82 2009-11-16 05:39:21Z sebastian.benz $
 */
package testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Test Model</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link testmodel.TestModel#getChildren <em>Children</em>}</li>
 *   <li>{@link testmodel.TestModel#getSingleOptional <em>Single Optional</em>}</li>
 *   <li>{@link testmodel.TestModel#getSingleRequired <em>Single Required</em>}</li>
 *   <li>{@link testmodel.TestModel#getName <em>Name</em>}</li>
 *   <li>{@link testmodel.TestModel#getAttributeSample <em>Attribute Sample</em>}</li>
 *   <li>{@link testmodel.TestModel#getLinkToSingleRequired <em>Link To Single Required</em>}</li>
 *   <li>{@link testmodel.TestModel#getReferenceTestContainer <em>Reference Test Container</em>}</li>
 *   <li>{@link testmodel.TestModel#getAttributeTest <em>Attribute Test</em>}</li>
 *   <li>{@link testmodel.TestModel#getNameAttributeTest <em>Name Attribute Test</em>}</li>
 *   <li>{@link testmodel.TestModel#getEnumListTestContainer <em>Enum List Test Container</em>}</li>
 * </ul>
 * </p>
 *
 * @see testmodel.TestmodelPackage#getTestModel()
 * @model
 * @generated
 */
public interface TestModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference
	 * list. The list contents are of type {@link testmodel.Child}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getTestModel_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Child> getChildren();

	/**
	 * Returns the value of the '<em><b>Single Optional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Optional</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Optional</em>' containment reference.
	 * @see #setSingleOptional(SingleOptional)
	 * @see testmodel.TestmodelPackage#getTestModel_SingleOptional()
	 * @model containment="true"
	 * @generated
	 */
	SingleOptional getSingleOptional();

	/**
	 * Sets the value of the '{@link testmodel.TestModel#getSingleOptional <em>Single Optional</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Optional</em>' containment reference.
	 * @see #getSingleOptional()
	 * @generated
	 */
	void setSingleOptional(SingleOptional value);

	/**
	 * Returns the value of the '<em><b>Single Required</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Required</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Required</em>' containment reference.
	 * @see #setSingleRequired(SingleRequired)
	 * @see testmodel.TestmodelPackage#getTestModel_SingleRequired()
	 * @model containment="true" required="true"
	 * @generated
	 */
	SingleRequired getSingleRequired();

	/**
	 * Sets the value of the '{@link testmodel.TestModel#getSingleRequired <em>Single Required</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Required</em>' containment reference.
	 * @see #getSingleRequired()
	 * @generated
	 */
	void setSingleRequired(SingleRequired value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see testmodel.TestmodelPackage#getTestModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link testmodel.TestModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Attribute Sample</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Sample</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Sample</em>' containment reference.
	 * @see #setAttributeSample(AttributeSample)
	 * @see testmodel.TestmodelPackage#getTestModel_AttributeSample()
	 * @model containment="true"
	 * @generated
	 */
	AttributeSample getAttributeSample();

	/**
	 * Sets the value of the '{@link testmodel.TestModel#getAttributeSample <em>Attribute Sample</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Sample</em>' containment reference.
	 * @see #getAttributeSample()
	 * @generated
	 */
	void setAttributeSample(AttributeSample value);

	/**
	 * Returns the value of the '<em><b>Link To Single Required</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link To Single Required</em>' reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link To Single Required</em>' reference.
	 * @see #setLinkToSingleRequired(SingleRequired)
	 * @see testmodel.TestmodelPackage#getTestModel_LinkToSingleRequired()
	 * @model
	 * @generated
	 */
	SingleRequired getLinkToSingleRequired();

	/**
	 * Sets the value of the '{@link testmodel.TestModel#getLinkToSingleRequired <em>Link To Single Required</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link To Single Required</em>' reference.
	 * @see #getLinkToSingleRequired()
	 * @generated
	 */
	void setLinkToSingleRequired(SingleRequired value);

	/**
	 * Returns the value of the '<em><b>Reference Test Container</b></em>' containment reference list.
	 * The list contents are of type {@link testmodel.ReferenceTestContainer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Test Container</em>' containment
	 * reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Test Container</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getTestModel_ReferenceTestContainer()
	 * @model containment="true"
	 * @generated
	 */
	EList<ReferenceTestContainer> getReferenceTestContainer();

	/**
	 * Returns the value of the '<em><b>Attribute Test</b></em>' containment reference list.
	 * The list contents are of type {@link testmodel.AttributeTestContainer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Test</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Test</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getTestModel_AttributeTest()
	 * @model containment="true"
	 * @generated
	 */
	EList<AttributeTestContainer> getAttributeTest();

	/**
	 * Returns the value of the '<em><b>Name Attribute Test</b></em>' containment reference list.
	 * The list contents are of type {@link testmodel.NameAttributeContainer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Attribute Test</em>' containment
	 * reference list isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Attribute Test</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getTestModel_NameAttributeTest()
	 * @model containment="true"
	 * @generated
	 */
	EList<NameAttributeContainer> getNameAttributeTest();

	/**
	 * Returns the value of the '<em><b>Enum List Test Container</b></em>' containment reference list.
	 * The list contents are of type {@link testmodel.EnumListTestContainer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum List Test Container</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum List Test Container</em>' containment reference list.
	 * @see testmodel.TestmodelPackage#getTestModel_EnumListTestContainer()
	 * @model containment="true"
	 * @generated
	 */
	EList<EnumListTestContainer> getEnumListTestContainer();

} // TestModel
