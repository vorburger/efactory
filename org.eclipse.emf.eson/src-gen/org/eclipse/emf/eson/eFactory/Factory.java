/**
 */
package org.eclipse.emf.eson.eFactory;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Factory</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eson.eFactory.Factory#getEpackages <em>Epackages</em>}</li>
 *   <li>{@link org.eclipse.emf.eson.eFactory.Factory#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.eson.eFactory.Factory#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eson.eFactory.EFactoryPackage#getFactory()
 * @model
 * @generated
 */
public interface Factory extends EObject
{
  /**
   * Returns the value of the '<em><b>Epackages</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.eson.eFactory.PackageImport}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Epackages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Epackages</em>' containment reference list.
   * @see org.eclipse.emf.eson.eFactory.EFactoryPackage#getFactory_Epackages()
   * @model containment="true"
   * @generated
   */
  EList<PackageImport> getEpackages();

  /**
   * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.eson.eFactory.Annotation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Annotations</em>' containment reference list.
   * @see org.eclipse.emf.eson.eFactory.EFactoryPackage#getFactory_Annotations()
   * @model containment="true"
   * @generated
   */
  EList<Annotation> getAnnotations();

  /**
   * Returns the value of the '<em><b>Root</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Root</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root</em>' containment reference.
   * @see #setRoot(NewObject)
   * @see org.eclipse.emf.eson.eFactory.EFactoryPackage#getFactory_Root()
   * @model containment="true"
   * @generated
   */
  NewObject getRoot();

  /**
   * Sets the value of the '{@link org.eclipse.emf.eson.eFactory.Factory#getRoot <em>Root</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root</em>' containment reference.
   * @see #getRoot()
   * @generated
   */
  void setRoot(NewObject value);

} // Factory
