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
 *   Michael Vorburger - simplified publicly visible methods & optimized
 *
 * </copyright>
 *
 * 
 */
package com.googlecode.efactory.building;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.util.Check;

public class ModelBuilder {

	private static Logger logger = Logger.getLogger(ModelBuilder.class);
	private NameAccessor nameSetter = new NameAccessor();
	private FeatureSwitch featureSwitch;
	private BiMap<NewObject, EObject> mapping = HashBiMap.create();

	public ModelBuilder() {
		this.featureSwitch = new FeatureSwitch();
	}

	public EObject build(NewObject newObject) throws ModelBuilderException {
		Check.notNull("Argument must not be null", newObject);
		EObject target = mapping.get(newObject);
		if (target != null) {
			return target;
		}
		EObject eObject = createTarget(newObject);
		setName(eObject, newObject);
		buildFeatures(eObject, newObject.getFeatures());
		return eObject;
	}

	private EObject createTarget(NewObject from) throws ModelBuilderException {
		EClass eClass = from.getEClass();
		if (eClass == null) {
			return null;
		}
		if (eClass.getEPackage() == null) {
			EcoreUtil.resolve(from.eClass(), from);
		}
		EPackage ePackage = eClass.getEPackage();
		if (ePackage == null) {
			throw new ModelBuilderException(
					"No EPackage registered for EClass '" + eClass.getName()
							+ "' defined in New Object"
							+ " with name '" + from.getName()
							+ "' at URI " + from.eResource().getURI() + "#" + from.eResource().getURIFragment(from));

		}
		EFactory eFactoryInstance = ePackage.getEFactoryInstance();
		if (eFactoryInstance == null) {
			throw new ModelBuilderException("No EFactory registered for "
					+ ePackage.getNsURI());
		}
		EObject target = eFactoryInstance.create(eClass);
		mapping.put(from, target);
		return target;
	}

	public EObject build(Factory factory) throws ModelBuilderException {
		return build(factory.getRoot());
	}

	private void setName(EObject target, NewObject source) throws ModelBuilderException {
		String name = source.getName();
		if (name != null) {
			try {
				nameSetter.setName(source, target, name);
			} catch (NoNameFeatureMappingException e) {
				logger.error(e.getMessage(), e);
				throw new ModelBuilderException("nameSetter.setName() failed", e);
			}
		}
	}

	private void buildFeatures(EObject eObject, List<Feature> features) throws ModelBuilderException {
		for (Feature feature : features) {
			FeatureBuilder featureBuilder = featureSwitch.doSwitch(feature);
			if (featureBuilder != null) {
				featureBuilder.modelBuilder(this).container(eObject).feature(feature).build();
			}
		}
	}

	/**
	 * Gets the "source" NewObject for the built EObject.
	 * 
	 * @param value the EObject
	 * @return new object
	 * @throws IllegalStateException if build ModelBuilder is uninitialized, build() needs to called with non-empty Factory/NewObject before this. 
	 */
	public NewObject getSource(EObject value) {
		Check.notNull("Argument must not be null", value);
		if (mapping.isEmpty())
			throw new IllegalStateException("ModelBuilder is uninitialized, build() needs to called with non-empty Factory/NewObject before getSource()");
		return mapping.inverse().get(value);
	}

	public void clear() {
		mapping.clear();
	}

}
