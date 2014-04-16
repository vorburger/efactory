/**
 */
package testmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum List Test Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link testmodel.EnumListTestContainer#getSampleLiterals <em>Sample Literals</em>}</li>
 * </ul>
 * </p>
 *
 * @see testmodel.TestmodelPackage#getEnumListTestContainer()
 * @model
 * @generated
 */
public interface EnumListTestContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Sample Literals</b></em>' attribute list.
	 * The list contents are of type {@link testmodel.SampleEnum}.
	 * The literals are from the enumeration {@link testmodel.SampleEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sample Literals</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sample Literals</em>' attribute list.
	 * @see testmodel.SampleEnum
	 * @see testmodel.TestmodelPackage#getEnumListTestContainer_SampleLiterals()
	 * @model
	 * @generated
	 */
	EList<SampleEnum> getSampleLiterals();

} // EnumListTestContainer
