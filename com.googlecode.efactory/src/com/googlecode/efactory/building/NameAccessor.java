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
package com.googlecode.efactory.building;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Iterators;
import com.googlecode.efactory.eFactory.CustomNameMapping;
import com.googlecode.efactory.eFactory.EFactoryPackage;
import com.googlecode.efactory.eFactory.GlobalNameMapping;
import com.googlecode.efactory.eFactory.impl.GlobalNameMappingImpl;
import com.googlecode.efactory.util.Check;
import com.googlecode.efactory.util.Find;
import com.googlecode.efactory.util.SingletonIterator;

public class NameAccessor {

	protected static final String DFEAULT_NAME_FEATURE = "name";
	GlobalNameMapping defaultNameMapping = new GlobalNameMappingImpl() {
		@Override
		public String getNameFeature() {
			return DFEAULT_NAME_FEATURE;
		};
	};

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
	private void setName(EObject eObject, String name, EAttribute nameAttribute) {
		eObject.eSet(nameAttribute, name);
	}

	public EAttribute getNameAttribute(EObject context, EObject eObject) throws NoNameFeatureMappingException {
		Iterator<CustomNameMapping> customMappings = getCustomNameMappings(context);
		while (customMappings.hasNext()) {
			CustomNameMapping mapping = customMappings.next();
			if (EcoreUtil2.isAssignableFrom(mapping.getEClass(), eObject
					.eClass())) {
				return mapping.getNameFeature();
			}
		}

		Iterator<GlobalNameMapping> globalMappings = getGlobalNameMappings(context);
		if (globalMappings.hasNext()) {
			GlobalNameMapping mapping = globalMappings.next();
			if (hasEAttribute(eObject, mapping.getNameFeature())) {
				return getNameAttribute(mapping, eObject);
			}
		}

		throw new NoNameFeatureMappingException(
				"No name mapping defined for type '"
						+ eObject.eClass().getName() + "'");
	}

	private boolean hasEAttribute(EObject eObject, String nameFeature) {
		return getFeature(eObject, nameFeature) != null;
	}

	private EStructuralFeature getFeature(EObject eObject, String nameFeature) {
		return eObject.eClass().getEStructuralFeature(nameFeature);
	}

	private EAttribute getNameAttribute(GlobalNameMapping mapping,
			EObject eObject) {
		return (EAttribute) getFeature(eObject, mapping.getNameFeature());
	}
	private Iterator<GlobalNameMapping> getGlobalNameMappings(EObject context) {
		Iterator<GlobalNameMapping> globalMappings = Find.allInResourceSet(
				context, GlobalNameMapping.class);
		globalMappings = Iterators.concat(globalMappings, SingletonIterator
				.create(defaultNameMapping));
		return globalMappings;
	}

	private Iterator<CustomNameMapping> getCustomNameMappings(EObject context) {
		return Find.allInResourceSet(context, CustomNameMapping.class);
	}

}
