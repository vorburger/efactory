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

import org.eclipse.emf.ecore.EObject;

import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.PackageImport;

public class FactoryBuilder {

	public Factory build(EObject eObject) {
		Factory factory = createFactory(eObject);
		return factory;
	}

	private Factory createFactory(EObject eObject) {
		Factory factory = EFactoryFactory.eINSTANCE.createFactory();
		PackageImport packageImport = createPackageImport(eObject);
		factory.getEpackages().add(packageImport);
		NewObjectBuilder builder = NewObjectBuilder.context(factory);
		NewObject root = builder.build(eObject);
		factory.setRoot(root);
		return factory;
	}

	private PackageImport createPackageImport(EObject eObject) {
		PackageImport packageImport = EFactoryFactory.eINSTANCE
				.createPackageImport();
		packageImport.setEPackage(eObject.eClass().getEPackage());
		return packageImport;
	}
}
