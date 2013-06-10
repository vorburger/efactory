/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - changes based on new architecture
 ******************************************************************************/
package com.googlecode.efactory.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

import com.google.inject.Inject;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Import;
import com.googlecode.efactory.eFactory.PackageImport;

public final class EFactoryUtil {

	@Inject
	private EPackageResolver packageResolver;

	/**
	 * Gets EPackages from the 'use' clause, as well as from the 'import' clause,
	 * in case any imported files dynamically define new EClass (see
	 * DynamicEmfTest).
	 */
	public Iterable<EPackage> getEPackages(Factory factory) {
		List<PackageImport> ePackageUris = factory.getEpackages();
		List<EPackage> result = new ArrayList<EPackage>(ePackageUris.size());
		for (PackageImport packageImport : ePackageUris) {
			EPackage ePackage = packageImport.getEPackage();
			result.add(ePackage);
		}

		EList<Import> imports = factory.getImports();
		for (Import zimport : imports) {
			String ePackageURI = zimport.getImportURI();
			try {
				EPackage ePackage = packageResolver.resolve(factory.eResource(), ePackageURI);
				if (ePackage != null)
					result.add(ePackage);
			} catch (EPackageNotFoundException e) {
				// user will be informed during validation
			}
		}
		
		return result;
	}
}
