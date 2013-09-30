/**
 */
package testModelWithXtextReference.impl;

import com.googlecode.efactory.xtextintegration.myDsl.Model;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import testModelWithXtextReference.TestModelWithXtextReference;
import testModelWithXtextReference.TestModelWithXtextReferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Model With Xtext Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link testModelWithXtextReference.impl.TestModelWithXtextReferenceImpl#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestModelWithXtextReferenceImpl extends MinimalEObjectImpl.Container implements TestModelWithXtextReference {
	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected Model model;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestModelWithXtextReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestModelWithXtextReferencePackage.Literals.TEST_MODEL_WITH_XTEXT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getModel() {
		if (model != null && model.eIsProxy()) {
			InternalEObject oldModel = (InternalEObject)model;
			model = (Model)eResolveProxy(oldModel);
			if (model != oldModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL, oldModel, model));
			}
		}
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model basicGetModel() {
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(Model newModel) {
		Model oldModel = model;
		model = newModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL, oldModel, model));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL:
				if (resolve) return getModel();
				return basicGetModel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL:
				setModel((Model)newValue);
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
			case TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL:
				setModel((Model)null);
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
			case TestModelWithXtextReferencePackage.TEST_MODEL_WITH_XTEXT_REFERENCE__MODEL:
				return model != null;
		}
		return super.eIsSet(featureID);
	}

} //TestModelWithXtextReferenceImpl
