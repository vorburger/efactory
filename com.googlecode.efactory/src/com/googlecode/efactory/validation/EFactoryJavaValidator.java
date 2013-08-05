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
package com.googlecode.efactory.validation;

import java.util.HashSet;
import java.util.Set;

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
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import com.googlecode.efactory.building.ModelBuilderException;
import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.eFactory.BooleanAttribute;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.DoubleAttribute;
import com.googlecode.efactory.eFactory.EFactoryPackage;
import com.googlecode.efactory.eFactory.EnumAttribute;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.IntegerAttribute;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.StringAttribute;
import com.googlecode.efactory.eFactory.util.EFactorySwitch;
import com.googlecode.efactory.resource.EFactoryResource;
import com.googlecode.efactory.util.ContainerResolver;
import com.googlecode.efactory.util.EcoreUtil3;

public class EFactoryJavaValidator extends AbstractEFactoryJavaValidator {
	// There are a lot of possible NullPointerException in here in the scenario where some reference types are still proxies.. but the NPEs get swallowed silently by the validation infrastructure 
	
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

	private ContainerResolver containerResolver = new ContainerResolver();
	private AttributeValidator attributeValidator = new AttributeValidator();

	@Check
	public void checkNewObject(NewObject newObject) {
		checkNoDuplicateFeature(newObject);
		checkIsInstantiatable(newObject);
	}

	@Check(CheckType.NORMAL)
	public void checkFactory(Factory factory) {
		final EFactoryResource eFResource = (EFactoryResource) factory.eResource();
		EObject eObject; 
		try {
			eObject = eFResource.getEFactoryEObject(factory.getRoot());
		} catch (ModelBuilderException e) {
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
				return resource.getEFactoryElement((EObject) data);
			}
		}
		return null;
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
			boolean hasDuplicate = EcoreUtil3.isDuplicate(existingFeatures,
					eFeature);
			assertFalse("Duplicate feature '" + eFeature.getName() + "'", null,
					hasDuplicate);
			existingFeatures.add(eFeature);
		}

	}
	@Check
	public void checkFeature(Feature feature) {
		EStructuralFeature eFeature = feature.getEFeature();
		if (eFeature == null) {
			return;
		}
		checkCardinality(feature);
		checkIsFeature(feature);
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
		if (feature.getEFeature().isMany()) {
			assertTrue(
					"Cannot assign a single element to a feature with cardinality >1",
					EFactoryPackage.Literals.FEATURE__VALUE, hasMany(feature));
		} else {
			assertFalse(
					"Cannot assign multiple elements to a feature with cardinality 1",
					EFactoryPackage.Literals.FEATURE__VALUE, hasMany(feature));
		}
	}

	private boolean hasMany(Feature feature) {
		return feature.isIsMany();
	}

	@Check
	public void checkReference(Reference reference) {
		Feature feature = getFeature(reference);
		checkIsEReference(feature);
		checkIsNotContainment(feature.getEFeature());
		checkIsAssignable(feature, EFactoryPackage.Literals.REFERENCE__VALUE,
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

	private void checkIsAssignable(Feature feature,
			EReference containmentValue, EClass candidate) {
		boolean isAssignable = true;
		EClassifier eType = feature.getEFeature().getEType();
		if (eType == null)
			return;
		if (eType instanceof EClass) {
			isAssignable = false;
			EClass eClass = (EClass) eType;
			isAssignable = EcoreUtil2.isAssignableFrom(eClass, candidate);
		}
		assertTrue(
				"Wrong type. Expected instance of '" + eType.getName() + "'",
				containmentValue, isAssignable);
	}

	private void checkIsNotContainment(EStructuralFeature eStructuralFeature) {
		boolean isContainment = isContainment(eStructuralFeature);
		assertFalse("Value must be a reference but is a containment",
				EFactoryPackage.Literals.REFERENCE__VALUE, isContainment);
	}

	private boolean isContainment(EStructuralFeature eStructuralFeature) {
		return EcoreUtil3.isEContainment(eStructuralFeature);
	}

	private void checkIsEReference(Feature feature) {
		EStructuralFeature eStructuralFeature = feature.getEFeature();
		assertTrue("Value must be a reference but is an attribute",
				EFactoryPackage.Literals.REFERENCE__VALUE,
				isEReference(eStructuralFeature));
	}

	private boolean isEReference(EStructuralFeature eStructuralFeature) {
		return EcoreUtil3.isEReference(eStructuralFeature);
	}

	@Check
	public void checkContainment(Containment containment) {
		Feature feature = getFeature(containment);
		checkIsContainment(feature);
		checkIsAssignable(feature, EFactoryPackage.Literals.CONTAINMENT__VALUE,
				containment.getValue().getEClass());

	}

	private void checkIsContainment(Feature feature) {
		EStructuralFeature eStructuralFeature = feature.getEFeature();
		assertTrue("Value must be a new object but is a reference",
				EFactoryPackage.Literals.FEATURE__VALUE,
				isContainment(eStructuralFeature));
	}

	private void assertTrue(String message, EStructuralFeature feature,
			boolean value) {
		if (!value) {
			error(message, feature);
		}
	}

	@Check
	public void checkAttribute(Attribute attribute) {
		Feature feature = getFeature(attribute);
		assertFalse("Value must be an attribute but is a reference", null,
				isEReference(feature.getEFeature()));

		checkAttributeType(feature, attribute);
	}

	private void assertFalse(String message, EStructuralFeature feature,
			boolean value) {
		if (value) {
			error(message, feature);
		}
	}

	private void checkAttributeType(Feature feature, Attribute attribute) {
		attributeValidator.validate(feature, attribute);

	}
	private Feature getFeature(EObject eObject) {
		return containerResolver.resolve(Feature.class, eObject);
	}

	private NewObject getNewObject(EObject eObject) {
		return containerResolver.resolve(NewObject.class, eObject);
	}
}
