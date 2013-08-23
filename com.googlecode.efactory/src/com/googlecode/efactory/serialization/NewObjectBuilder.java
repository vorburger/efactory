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

import com.googlecode.efactory.building.NameAccessor;
import com.googlecode.efactory.building.NoNameFeatureMappingException;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;

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
		return newObject;
	}

	private void addContainments(NewObject newObject, EObject input) {
		EList<Feature> features = newObject.getFeatures();
		for (EReference containment : input.eClass().getEAllContainments()) {
			Object containmentValue = input.eGet(containment);

			if (containment.isMany()) {
				List<?> containmentValues = (List<?>) containmentValue;
				for (Object value : containmentValues) {
					features.add(createContainment(containment, value));
				}
			} else {
				if (containmentValue != null) {
					features.add(createContainment(containment,
							containmentValue));
				}
			}
		}
	}

	private Feature createContainment(EReference containment,
			Object containmentValue) {
		return ContainmentBuilder.containment(containment, factoryBuilder).factory(context)
				.value(containmentValue).build();
	}
	
	private void addReferences(NewObject newObject, EObject input) {
		EList<Feature> features = newObject.getFeatures();
		for (EReference eReference : input.eClass().getEAllReferences()) {
			if (!eReference.isContainment()) {
				Object referenceValue = input.eGet(eReference);
				if (eReference.isMany()) {
					List<?> references = (List<?>) referenceValue;
					for (Object referencedElement : references) {
						createReference(features, eReference, referencedElement);
					}
				} else {
					if (referenceValue != null) {
						createReference(features, eReference, referenceValue);
					}
				}
			}
		}
	}

	private void createReference(EList<Feature> features,
			EReference eReference, Object referencedElement) {
		features.add(ReferenceBuilder.reference(eReference, factoryBuilder)
				.value(referencedElement).build());
	}

	private void addAttributes(NewObject newObject, EObject input) {
		EList<Feature> features = newObject.getFeatures();

		for (EAttribute attribute : input.eClass().getEAllAttributes()) {
			if (attribute != nameEAttribute) {
				Object attributeValue = input.eGet(attribute);

				if (attribute.isMany()) {
					List<?> containmentValues = (List<?>) attributeValue;
					for (Object value : containmentValues) {
						createAttribute(features, attribute, value);
					}
				} else {
					if (attributeValue != null) {
						createAttribute(features, attribute, attributeValue);
					}
				}
			}
		}
	}

	private void createAttribute(EList<Feature> features, EAttribute attribute,
			Object value) {
		features.add(AttributeBuilder.attribute(attribute, factoryBuilder)
				.value(value).build());
	}

	private String getName(EObject input) throws NoNameFeatureMappingException {
		nameEAttribute = nameAccessor.getNameAttribute(context, input);
		return (String) input.eGet(nameEAttribute);
	}

}
