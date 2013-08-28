/**
 * <copyright>
 *
 * Copyright (c) 2002-2006 Sebastian Benz and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Sebastian Benz - Initial API and implementation
 *
 * </copyright>
 *
 * 
 */
package com.googlecode.efactory.serialization;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.googlecode.efactory.building.NameAccessor;
import com.googlecode.efactory.building.NoNameFeatureMappingException;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.MultiValue;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Value;

// intentionally a package local class, the entry point to this package is FactoryBuilder, only
class NewObjectBuilder {

	private final Factory context;
	private NameAccessor nameAccessor = new NameAccessor();
	private EAttribute nameEAttribute;
	private final IFactoryBuilder factoryBuilder;
	
	private NewObjectBuilder(Factory context, IFactoryBuilder factoryBuilder) {
		this.context = context;
		this.factoryBuilder = factoryBuilder;
	}

	public static NewObjectBuilder context(Factory context, IFactoryBuilder factoryBuilder) {
		return new NewObjectBuilder(context, factoryBuilder);
	}

	public NewObject build(EObject input) {
		NewObject newObject = EFactoryFactory.eINSTANCE.createNewObject();
		newObject.setEClass(input.eClass());
		try {
			newObject.setName(getName(input));
		} catch (NoNameFeatureMappingException e) {
			// OK, we cannot name the object... ignore and move on!
		}
		addAttributes(newObject, input);
		addContainments(newObject, input);
		addReferences(newObject, input);
		addIsManyStructuralFeatures(newObject, input);
		return newObject;
	}

	private void addContainments(NewObject newObject, EObject input) {
		final EList<Feature> features = newObject.getFeatures();
		for (EReference containment : input.eClass().getEAllContainments()) {
			if (!containment.isMany()) { // isMany are handled by addIsManyStructuralFeatures
				Object containmentValue = input.eGet(containment);
				if (containmentValue != null) {
					features.add(createContainment(containment, containmentValue));
				}
			}
		}
	}

	private Feature createContainment(EReference containment, Object containmentValue) {
		return ContainmentBuilder.containment(containment, factoryBuilder).factory(context)
				.value(containmentValue).build();
	}
	
	private void addReferences(NewObject newObject, EObject input) {
		final EList<Feature> features = newObject.getFeatures();
		for (EReference eReference : input.eClass().getEAllReferences()) {
			if (!eReference.isContainment()) { // isContainment are handled by addContainments
				if (!eReference.isMany()) {    // isMany are handled by addIsManyStructuralFeatures
					Object referenceValue = input.eGet(eReference);
					if (referenceValue != null) {
						features.add(createReference(eReference, referenceValue));
					}
				}
			}
		}
	}

	private Feature createReference(EReference eReference, Object referencedElement) {
		return ReferenceBuilder.reference(eReference, factoryBuilder).value(referencedElement).build();
	}

	private void addAttributes(NewObject newObject, EObject input) {
		final EList<Feature> features = newObject.getFeatures();
		for (EAttribute attribute : input.eClass().getEAllAttributes()) {
			if (attribute != nameEAttribute) {
				if (!attribute.isMany()) {    // isMany are handled by addIsManyStructuralFeatures
					Object attributeValue = input.eGet(attribute);
					if (attributeValue != null) {
						features.add(createAttribute(attribute, attributeValue));
					}
				}
			}
		}
	}

	private Feature createAttribute(EAttribute attribute, Object value) {
		return AttributeBuilder.attribute(attribute, factoryBuilder).value(value).build();
	}

	private String getName(EObject input) throws NoNameFeatureMappingException {
		nameEAttribute = nameAccessor.getNameAttribute(context, input);
		return (String) input.eGet(nameEAttribute);
	}

	private void addIsManyStructuralFeatures(NewObject newObject, EObject input) {
		final EList<Feature> features = newObject.getFeatures();
		for (EStructuralFeature eFeature : input.eClass().getEAllStructuralFeatures()) {
			if (eFeature.isMany()) {
				List<?> eListValues = (List<?>) input.eGet(eFeature);
				if (eListValues.isEmpty())
					continue;
				
				final Feature newFeature = EFactoryFactory.eINSTANCE.createFeature();
				newFeature.setEFeature(eFeature);
				features.add(newFeature);
				MultiValue multiValue = EFactoryFactory.eINSTANCE.createMultiValue();
				newFeature.setValue(multiValue);
				EList<Value> values = multiValue.getValues();
				
				for (Object value : eListValues) {
					Value multiValueItem = MultiValueBuilder.multiValue(eFeature, factoryBuilder).value(value).createValue();
					values.add(multiValueItem);
				}
			}
		}
	}
}
