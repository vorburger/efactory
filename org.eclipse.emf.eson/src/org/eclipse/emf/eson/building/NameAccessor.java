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
package org.eclipse.emf.eson.building;

import java.util.Collections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eson.eFactory.CustomNameMapping;
import org.eclipse.emf.eson.eFactory.EFactoryPackage;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.emf.eson.util.Check;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Iterables;

public class NameAccessor {

	protected static final String DFEAULT_NAME_FEATURE = "name";

	public void setName(EObject context, EObject eObject, String name) throws NoNameFeatureMappingException {
		Check.hasEPackage(context, EFactoryPackage.eINSTANCE);
		EAttribute nameAttribute = getNameAttribute(context, eObject);
		setName(eObject, name, nameAttribute);
	}

	public String getName(EObject context, EObject eObject) throws NoNameFeatureMappingException {
		Check.hasEPackage(context, EFactoryPackage.eINSTANCE);
		EAttribute nameAttribute = getNameAttribute(context, eObject);
		Object name = eObject.eGet(nameAttribute);
		if (name == null) {
			name = "";
		}
		return name.toString();
	}
	
	protected void setName(EObject eObject, String name, EAttribute nameAttribute) {
		eObject.eSet(nameAttribute, name);
	}

	public @NonNull EAttribute getNameAttribute(EObject context, EObject eObject) throws NoNameFeatureMappingException {
		for (CustomNameMapping mapping : getCustomNameMappings(context)) {
			if (EcoreUtil2.isAssignableFrom(mapping.getEClass(), eObject.eClass())) {
				EAttribute attribute = mapping.getNameFeature();
				if (attribute != null)
					return attribute;
				else
					throw newNoNameFeatureMappingException(eObject);
			}
		}
		// If no explicit mapping found, fall back to default:
		EStructuralFeature defaultNameFeature = eObject.eClass().getEStructuralFeature(DFEAULT_NAME_FEATURE);
		if (defaultNameFeature != null)
			return (EAttribute) defaultNameFeature;
		else
			throw newNoNameFeatureMappingException(eObject);
	}

	protected NoNameFeatureMappingException newNoNameFeatureMappingException(EObject eObject) throws NoNameFeatureMappingException {
		return new NoNameFeatureMappingException(
				"No name mapping defined for type '"
						+ eObject.eClass().getName() + "'");
	}

	protected Iterable<CustomNameMapping> getCustomNameMappings(EObject context) {
		Resource eResource = context.eResource();
		if (eResource != null) {
			EFactoryResource eFactoryResource = (EFactoryResource) eResource;
			Factory factory = eFactoryResource.getEFactoryFactory();
			if (factory != null)
				return Iterables.filter(factory.getAnnotations(), CustomNameMapping.class);
		}
		return Collections.emptyList();
	}

}
