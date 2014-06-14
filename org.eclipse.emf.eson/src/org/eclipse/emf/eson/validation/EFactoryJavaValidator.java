/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - extensions and bug fixes
 ******************************************************************************/
package org.eclipse.emf.eson.validation;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.eson.building.NameAccessor;
import org.eclipse.emf.eson.eFactory.Attribute;
import org.eclipse.emf.eson.eFactory.BooleanAttribute;
import org.eclipse.emf.eson.eFactory.Containment;
import org.eclipse.emf.eson.eFactory.DoubleAttribute;
import org.eclipse.emf.eson.eFactory.EFactoryPackage;
import org.eclipse.emf.eson.eFactory.EnumAttribute;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.Feature;
import org.eclipse.emf.eson.eFactory.IntegerAttribute;
import org.eclipse.emf.eson.eFactory.MultiValue;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.eclipse.emf.eson.eFactory.Reference;
import org.eclipse.emf.eson.eFactory.StringAttribute;
import org.eclipse.emf.eson.eFactory.Value;
import org.eclipse.emf.eson.eFactory.util.EFactorySwitch;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.emf.eson.util.EcoreUtil3;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

public class EFactoryJavaValidator extends AbstractEFactoryJavaValidator {
	// There are a lot of possible NullPointerException in here in the scenario where some reference types are still proxies.. but the NPEs get swallowed silently by the validation infrastructure 
	
	@Inject NameAccessor nameAccessor;
	
	public final class AttributeValidator extends EFactorySwitch<Boolean> {
		private Feature feature;

		@Override
		public Boolean caseBooleanAttribute(BooleanAttribute object) {
			EAttribute featureId = EFactoryPackage.Literals.BOOLEAN_ATTRIBUTE__VALUE;
			return performAssert(featureId, EcorePackage.Literals.EBOOLEAN, EcorePackage.Literals.EBOOLEAN_OBJECT);
		}

		@Override
		public Boolean caseStringAttribute(StringAttribute object) {
			EAttribute featureId = EFactoryPackage.Literals.STRING_ATTRIBUTE__VALUE;
			return performAssert(featureId, EcorePackage.Literals.ESTRING);
		}

		@Override
		public Boolean caseIntegerAttribute(IntegerAttribute object) {
			EAttribute featureId = EFactoryPackage.Literals.INTEGER_ATTRIBUTE__VALUE;
			return performAssert(featureId, EcorePackage.Literals.EINT,
					EcorePackage.Literals.EINTEGER_OBJECT,
					EcorePackage.Literals.ELONG,
					EcorePackage.Literals.ELONG_OBJECT,
					EcorePackage.Literals.EBIG_INTEGER,
					EcorePackage.Literals.ESHORT,
					EcorePackage.Literals.ESHORT_OBJECT,
					EcorePackage.Literals.EBYTE,
					EcorePackage.Literals.EBYTE_OBJECT
					);
		}

		@Override
		public Boolean caseEnumAttribute(EnumAttribute object) {
			EReference featureId = EFactoryPackage.Literals.ENUM_ATTRIBUTE__VALUE;
			boolean success = EcoreUtil3.isEnum(feature.getEFeature()
					.getEType());
			assertTrue("Attribute must be of type "
					+ feature.getEFeature().getEType().getName()
					+ " but was an Enumeration", featureId, success);
			return success;
		}

		public Boolean validate(Feature feature, Attribute attribute) {
			this.feature = feature;
			return doSwitch(attribute);
		}

		@Override
		public Boolean caseMultiValue(MultiValue multiValue) {
			boolean multiValueValidation = true; 
			for (Value value : multiValue.getValues()) {
				if (value instanceof Attribute) {
					if (!this.doSwitch(value))
						multiValueValidation = false;
				}
			}
			return multiValueValidation;
		}

		@Override
		public Boolean caseDoubleAttribute(DoubleAttribute object) {
			EAttribute featureId = EFactoryPackage.Literals.DOUBLE_ATTRIBUTE__VALUE;
			return performAssert(featureId, EcorePackage.Literals.EDOUBLE,
					EcorePackage.Literals.EDOUBLE_OBJECT,
					EcorePackage.Literals.EFLOAT,
					EcorePackage.Literals.EFLOAT_OBJECT,
					EcorePackage.Literals.EBIG_DECIMAL);
		}

		private boolean performAssert(EStructuralFeature featureId,
				EDataType... validDatatypes) {
			EClassifier expected = feature.getEFeature().getEType();

			boolean success = false;
			for (EDataType validDataType : validDatatypes) {
				if (equals(expected, validDataType)) {
					success = true;
					break;
				}
			}
			if (!success) {
				final StringBuffer msg = new StringBuffer("EF Attribute must be one of types ");
				for (EDataType eDataType : validDatatypes) {
					msg.append(eDataType.getName());
					msg.append(", ");
				}
				msg.append(" but was ");
				msg.append(feature.getEFeature().getEType().getName());
				error(msg.toString(), featureId);
			}
			return success;
		}

		/* This is required due to strange Data Type validation
		 * mismatch problem (occurs only with Xcore models),
		 * where in the EDataType is not a EcorePackage.Literals.EINT
		 * but has a default of '0' and appears to be from another
		 * ecore EPackage which doesn't have the same object identity.
		 * This is non-regression tested by XcoreTest.
		 */
		private boolean equals(EClassifier expected, EDataType validDataType) {
			if (expected == null)
				return validDataType == null;
			else if (validDataType == null)
				return expected == null;
			else
				return equals(expected.getEPackage(), validDataType.getEPackage())
						&& expected.getName().equals(validDataType.getName());
		}
		private boolean equals(EPackage package1, EPackage package2) {
			if (package1 == null)
				return package2 == null; 
			else if (package2 == null)
				return package1 == null; 
			else
				return equals(package1.getESuperPackage(), package2.getESuperPackage())
					&& package1.getName().equals(package2.getName())
					&& package1.getNsPrefix().equals(package2.getNsPrefix())
					&& package1.getNsURI().equals(package2.getNsURI());
		}
	}

	private AttributeValidator attributeValidator = new AttributeValidator();

	@Check
	public void checkNewObject(NewObject newObject) {
		checkNoDuplicateFeature(newObject);
		checkIsInstantiatable(newObject);
		checkObjectName(newObject);
	}

	@Check(CheckType.NORMAL)
	public void checkFactory(Factory factory) {
		final EFactoryResource eFResource = (EFactoryResource) factory.eResource();
		EObject eObject = EFactoryResource.getEFactoryEObject(factory.eResource()) ;
		if (eObject == null) {
			return;
		}
		
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
		for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
			if (childDiagnostic.getSeverity() == Diagnostic.ERROR) {
				error(childDiagnostic.getMessage(),
						getSource(eFResource, diagnostic), null, null);
			} else if (childDiagnostic.getSeverity() == Diagnostic.WARNING) {
				warning(childDiagnostic.getMessage(),
						getSource(eFResource, diagnostic), null, null);
			}
		}
	}

	private EObject getSource(EFactoryResource resource, Diagnostic diagnostic) {
		for (Object data : diagnostic.getData()) {
			if (data instanceof EObject) {
				return resource.getEFactoryNewObject((EObject) data);
			}
		}
		return null;
	}

	private void checkObjectName(NewObject newObject) {
		String name = newObject.getName();
		if (name == null)
			return;
		assertFalse("Name cannot be blank", EFactoryPackage.Literals.NEW_OBJECT__NAME, name.trim().isEmpty()); // https://github.com/vorburger/efactory/pull/18
		EAttribute nameAttribute = nameAccessor.getNameAttribute(newObject);
		if (newObject.getEClass() != null)
			assertTrue("Cannot name " + newObject.getEClass().getName(), EFactoryPackage.Literals.NEW_OBJECT__NAME, nameAttribute != null);
	}

	private void checkIsInstantiatable(NewObject newObject) {
		EClass eClass = newObject.getEClass();
		if (eClass != null) {
			boolean isInstantiatable = EcoreUtil3.isInstantiatable(eClass);
			assertTrue("Abstract classes or interfaces cannot be instantiated",
					EFactoryPackage.Literals.NEW_OBJECT__ECLASS,
					isInstantiatable);
		}
	}

	private void checkNoDuplicateFeature(NewObject newObject) {
		Set<EStructuralFeature> existingFeatures = new HashSet<EStructuralFeature>();
		for (Feature feature : newObject.getFeatures()) {
			EStructuralFeature eFeature = feature.getEFeature();
			boolean hasDuplicate = existingFeatures.contains(eFeature);
			assertFalse("Duplicate feature '" + eFeature.getName() + "'", null, hasDuplicate);
			existingFeatures.add(eFeature);
		}
	}
	
	@Check
	public void checkFeature(Feature feature) {
		EStructuralFeature eFeature = feature.getEFeature();
		if (eFeature == null || eFeature.eIsProxy()) {
			return;
		}
		checkCardinality(feature);
		checkIsFeature(feature);
		checkMissingFeatureValue(feature);
	}

	private void checkMissingFeatureValue(Feature feature) {
		EStructuralFeature eFeature = feature.getEFeature();
		assertTrue("Feature missing value: " + eFeature.getName(), EFactoryPackage.Literals.FEATURE__VALUE, feature.getValue() != null);
	}

	private void checkIsFeature(Feature feature) {
		NewObject newObject = getNewObject(feature);
		assertTrue(newObject.getEClass().getName() + " has no feature "
				+ feature.getEFeature().getName(),
				EFactoryPackage.Literals.FEATURE__EFEATURE,
				hasEFeature(newObject.getEClass(), feature.getEFeature()));
	}

	private boolean hasEFeature(EClass eClass, EStructuralFeature feature) {
		return eClass.getEAllStructuralFeatures().contains(feature);
	}

	private void checkCardinality(Feature feature) {
		final boolean hasMany = feature.getEFeature().isMany();
		final Value value = feature.getValue();
		if (value == null)
			return;
		if (value instanceof MultiValue) {
			assertTrue(
					"Cannot assign multiple elements to a feature with cardinality 1 (remove [...])",
					EFactoryPackage.Literals.FEATURE__VALUE, hasMany);
		} else {
			assertFalse(
					"Cannot assign a single element to a feature with cardinality >1 (use [...])",
					EFactoryPackage.Literals.FEATURE__VALUE, hasMany);
		}
	}

	@Check
	public void checkReference(Reference reference) {
		Feature feature = getFeature(reference);
		EStructuralFeature eFeature = feature.getEFeature();
		if (eFeature == null || eFeature.eIsProxy()) {
			return;
		}
		checkIsEReference(eFeature);
		checkIsNotContainment(eFeature);
		checkIsAssignable(eFeature, EFactoryPackage.Literals.REFERENCE__VALUE,
				getReferencedType(reference));
	}

	private EClass getReferencedType(Reference reference) {
		EObject referenceObject = reference.getValue();
		if (referenceObject instanceof NewObject) {
			NewObject newObject = (NewObject) referenceObject;
			return newObject.getEClass();
		} else {
			return referenceObject.eClass();
		}
	}

	private void checkIsAssignable(EStructuralFeature eFeature,
			EReference containmentValue, EClass candidate) {
		boolean isAssignable = true;
		EClassifier eType = eFeature.getEType();
		if (eType == null)
			return;
		if (eType instanceof EClass) {
			isAssignable = false;
			EClass eClass = (EClass) eType;
			isAssignable = EcoreUtil2.isAssignableFrom(eClass, candidate);
		}
		assertTrue("Wrong type. Expected instance of '" + eType.getName() + "'", containmentValue, isAssignable);
	}

	private void checkIsNotContainment(EStructuralFeature eStructuralFeature) {
		boolean isContainment = isContainment(eStructuralFeature);
		assertFalse("Value must be a reference but is a containment",
				EFactoryPackage.Literals.REFERENCE__VALUE, isContainment);
	}

	private boolean isContainment(EStructuralFeature eStructuralFeature) {
		return EcoreUtil3.isEContainment(eStructuralFeature);
	}

	private void checkIsEReference(EStructuralFeature eFeature) {
		assertTrue("Value must be a reference but is an attribute",
				EFactoryPackage.Literals.REFERENCE__VALUE,
				isEReference(eFeature));
	}

	private boolean isEReference(EStructuralFeature eStructuralFeature) {
		return EcoreUtil3.isEReference(eStructuralFeature);
	}

	@Check
	public void checkContainment(Containment containment) {
		Feature feature = getFeature(containment);
		EStructuralFeature eFeature = feature.getEFeature();
		if (eFeature == null || eFeature.eIsProxy()) {
			return;
		}
		checkIsContainment(eFeature);
		checkIsAssignable(eFeature, EFactoryPackage.Literals.CONTAINMENT__VALUE,
				containment.getValue().getEClass());
	}

	private void checkIsContainment(EStructuralFeature eFeature) {
		assertTrue("Value must be a new object but is a reference",
				EFactoryPackage.Literals.FEATURE__VALUE,
				isContainment(eFeature));
	}

	private void assertTrue(String message, EStructuralFeature feature, boolean value) {
		if (!value) {
			error(message, feature);
		}
	}

	@Check
	public void checkAttribute(Attribute attribute) {
		Feature feature = getFeature(attribute);
		assertFalse("Value must be an attribute but is a reference", null, isEReference(feature.getEFeature()));

		checkAttributeType(feature, attribute);
	}

	private void assertFalse(String message, EStructuralFeature feature, boolean value) {
		if (value) {
			error(message, feature);
		}
	}

	private void checkAttributeType(Feature feature, Attribute attribute) {
		attributeValidator.validate(feature, attribute);
	}
	
	private Feature getFeature(Value value) {
		Feature feature = EcoreUtil2.getContainerOfType(value, Feature.class);
		if (feature == null)
			throw new NoSuchElementException("EFactory Value " + value.toString() + " is not contained in a Feature?!");
		return feature;
	}

	private NewObject getNewObject(Feature feature) {
		NewObject newObject = EcoreUtil2.getContainerOfType(feature, NewObject.class);
		if (newObject == null)
			throw new NoSuchElementException("EFactory Feature " + feature.getEFeature().getName() + " is not contained in a NewObject?!");
		return newObject;
	}
}
