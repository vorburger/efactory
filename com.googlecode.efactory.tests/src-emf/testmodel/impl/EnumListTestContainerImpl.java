/**
 */
package testmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import testmodel.EnumListTestContainer;
import testmodel.SampleEnum;
import testmodel.TestmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum List Test Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link testmodel.impl.EnumListTestContainerImpl#getSampleLiterals <em>Sample Literals</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumListTestContainerImpl extends EObjectImpl implements EnumListTestContainer {
	/**
	 * The cached value of the '{@link #getSampleLiterals() <em>Sample Literals</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSampleLiterals()
	 * @generated
	 * @ordered
	 */
	protected EList<SampleEnum> sampleLiterals;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumListTestContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestmodelPackage.Literals.ENUM_LIST_TEST_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SampleEnum> getSampleLiterals() {
		if (sampleLiterals == null) {
			sampleLiterals = new EDataTypeUniqueEList<SampleEnum>(SampleEnum.class, this, TestmodelPackage.ENUM_LIST_TEST_CONTAINER__SAMPLE_LITERALS);
		}
		return sampleLiterals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestmodelPackage.ENUM_LIST_TEST_CONTAINER__SAMPLE_LITERALS:
				return getSampleLiterals();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestmodelPackage.ENUM_LIST_TEST_CONTAINER__SAMPLE_LITERALS:
				getSampleLiterals().clear();
				getSampleLiterals().addAll((Collection<? extends SampleEnum>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TestmodelPackage.ENUM_LIST_TEST_CONTAINER__SAMPLE_LITERALS:
				getSampleLiterals().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TestmodelPackage.ENUM_LIST_TEST_CONTAINER__SAMPLE_LITERALS:
				return sampleLiterals != null && !sampleLiterals.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (sampleLiterals: ");
		result.append(sampleLiterals);
		result.append(')');
		return result.toString();
	}

} //EnumListTestContainerImpl
