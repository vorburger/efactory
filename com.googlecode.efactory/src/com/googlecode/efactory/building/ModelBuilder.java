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

import java.util.Iterator;
import java.util.LinkedList;
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
	private final FeatureSwitch featureSwitch = new FeatureSwitch();
	private BiMap<NewObject, EObject> mapping = HashBiMap.create();
	private List<ReferenceBuilder> deferredLinkingFeatureBuilder = new LinkedList<ReferenceBuilder>();

	// intentionally package local - outside clients shouldn't need to build individual NewObject, they only build(Factory)
	// NOTE: It is the caller's (!) responsibility to add the returned EObject into another EObject (or a Resource) eContainer. 
	EObject build(NewObject newObject) throws ModelBuilderException {
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
			throw new ModelBuilderException("No EClass for New Object " + getNewObjectDescriptionForErrorMessage(from));
		}
		if (eClass.getEPackage() == null) {
			EcoreUtil.resolve(from.eClass(), from);
		}
		if (eClass.eIsProxy()) {
			throw new ModelBuilderException("The EClass for NewObject " + getNewObjectDescriptionForErrorMessage(from) + " is still an unresolved EMF Proxy, something isn't working in your cross-Resource reference resolution");
		}
		EPackage ePackage = eClass.getEPackage();
		if (ePackage == null) {
			throw new ModelBuilderException("No EPackage registered for EClass '" + eClass.getName() + "' defined in NewObject " + getNewObjectDescriptionForErrorMessage(from));
		}
		EFactory eFactoryInstance = ePackage.getEFactoryInstance();
		if (eFactoryInstance == null) {
			throw new ModelBuilderException("No EFactory registered for " + ePackage.getNsURI());
		}
		EObject target = eFactoryInstance.create(eClass);
		mapping.put(from, target);
		return target;
	}

	private String getNewObjectDescriptionForErrorMessage(NewObject from) {
		return "name '" + from.getName()
		+ "' at URI " + from.eResource().getURI()
		+ "#" + from.eResource().getURIFragment(from);
	}

	/**
	 * Builds an EObject from a Factory.
	 * It is the caller's responsibility to add the returned EObject into a Resource as eContainer, failing to do so may result in dangling inter-Resource references. 
	 * 
	 * @param factory the Factory
	 * @return the EObject built from the Factory
	 * @throws ModelBuilderException if the content of the Factory prevented creation of a matching EObject
	 */
	public EObject build(Factory factory) throws ModelBuilderException {
		EObject unlinkedRoot = buildWithoutLinking(factory);
		link();
		return unlinkedRoot;
	}

	public EObject buildWithoutLinking(Factory factory) throws ModelBuilderException {
		Check.notNull("Argument must not be null", factory);
		return build(factory.getRoot());
	}
	
	private void setName(EObject target, NewObject source) {
		String name = source.getName();
		if (name != null) {
			try {
				nameSetter.setName(source, target, name);
			} catch (NoNameFeatureMappingException e) {
				logger.warn(e.getMessage(), e);
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

	public EObject getBuilt(NewObject newObject) throws ModelBuilderException {
		Check.notNull("Argument must not be null", newObject);
		checkNotEmpty();
		EObject target = mapping.get(newObject);
		if (target == null) {
			throw new IllegalArgumentException("NewObject passed as argument is not in this ModelBuilder, may be it's from a different Resource? " + newObject.toString());
		}
		return target;
	}
	
	/**
	 * Gets the "source" NewObject for the built EObject.
	 * 
	 * @param value the EObject
	 * @return new object, or null if the value EObject wasn't built by this ModelBuilder 
	 * @throws ModelBuilderException if build ModelBuilder is uninitialized, build() needs to called with non-empty Factory/NewObject before this. 
	 */
	public NewObject getSource(EObject value) throws ModelBuilderException {
		Check.notNull("Argument must not be null", value);
		checkNotEmpty();
		return mapping.inverse().get(value);
	}

	private void checkNotEmpty() throws ModelBuilderException {
		if (mapping.isEmpty())
			throw new ModelBuilderException("ModelBuilder is uninitialized, build() needs to called with non-empty Factory/NewObject before getSource()");
	}

	public void clear() {
		mapping.clear();
		deferredLinkingFeatureBuilder.clear();
	}

	public boolean isBuilt() {
		return !mapping.isEmpty();
	}

	public void addDeferredLinkingFeatureBuilder(ReferenceBuilder builder) {
		deferredLinkingFeatureBuilder.add(builder);
	}

	public void link() throws ModelBuilderException {
		Iterator<ReferenceBuilder> it = deferredLinkingFeatureBuilder.iterator();
		while (it.hasNext()) {
			ReferenceBuilder fb = it.next();
			try {
				fb.link();
			} finally {
				it.remove();
			}
		}
	}
	
	public void putEObjectNewObjectPair(EObject eObject, NewObject newObject) {
		mapping.put(newObject, eObject);
	}
}
