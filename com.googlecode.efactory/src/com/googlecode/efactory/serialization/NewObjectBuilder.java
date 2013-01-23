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
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;

public class NewObjectBuilder {

	private Factory context;
	private NameAccessor nameAccessor = new NameAccessor();
	private EAttribute nameEAttribute;

	private NewObjectBuilder(Factory context) {
		this.context = context;

	}

	public static NewObjectBuilder context(Factory context) {
		return new NewObjectBuilder(context);
	}

	public NewObject build(EObject input) {
		NewObject newObject = EFactoryFactory.eINSTANCE.createNewObject();
		newObject.setEClass(input.eClass());
		newObject.setName(getName(input));
		addAttributes(newObject, input);
		addReferences(newObject, input);
		addContainments(newObject, input);
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
		return ContainmentBuilder.containment(containment).factory(context)
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
		features.add(ReferenceBuilder.reference(eReference)
				.element((EObject) referencedElement).build());
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
		features.add(AttributeBuilder.attribute(attribute)
				.value(value).build());
	}

	private String getName(EObject input) {
		nameEAttribute = nameAccessor.getNameAttribute(context, input);
		return (String) input.eGet(nameEAttribute);
	}

}
