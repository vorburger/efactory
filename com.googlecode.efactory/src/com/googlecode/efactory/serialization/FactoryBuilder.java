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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.googlecode.efactory.building.NoNameFeatureMappingException;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.PackageImport;

public class FactoryBuilder implements IFactoryBuilder {

	private Map<EObject, NewObject> mapping = new HashMap<EObject, NewObject>();
	private Factory factory;
	
	public Factory buildFactory(EObject eObject) throws NoNameFeatureMappingException {
		factory = EFactoryFactory.eINSTANCE.createFactory();
		PackageImport packageImport = createPackageImport(eObject);
		factory.getEpackages().add(packageImport);
		
		NewObject root = createNewObject(eObject);
		factory.setRoot(root);
		
		return factory;
	}
	
	public NewObject getOrBuildNewObject(EObject eObject) {
		NewObject newObject = mapping.get(eObject);
		if (newObject == null) {
			newObject = createNewObject(eObject);
			mapping.put(eObject, newObject);
		}
		return newObject;
	}
	
	private NewObject createNewObject(EObject eObject) {
		NewObjectBuilder builder = NewObjectBuilder.context(factory, this);
		NewObject newObject = builder.build(eObject);
		return newObject;
	}
	
	private PackageImport createPackageImport(EObject eObject) {
		PackageImport packageImport = EFactoryFactory.eINSTANCE.createPackageImport();
		packageImport.setEPackage(eObject.eClass().getEPackage());
		return packageImport;
	}
}
