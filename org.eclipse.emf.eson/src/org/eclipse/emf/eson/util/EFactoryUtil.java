/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.PackageImport;

public final class EFactoryUtil {

	//@Inject
	//private EPackageResolver packageResolver;

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
		return result;
	}
}
