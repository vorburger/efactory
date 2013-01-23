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
package com.googlecode.efactory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.inject.Inject;
import com.googlecode.efactory.eFactory.PackageImport;

public final class EFactoryUtil {

	@Inject
	private EPackageResolver packageResolver;

	public Iterable<EPackage> getEPackages(EObject root) {
		List<PackageImport> ePackageUris = getEPackageUris(root);
		List<EPackage> result = new ArrayList<EPackage>(ePackageUris.size());
		for (PackageImport packageImport : ePackageUris) {
			try {
				EPackage ePackage = packageResolver.resolve(root.eResource(),
						packageImport.getEPackageURI());
				result.add(ePackage);
			} catch (EPackageNotFoundException e) {
				// user will be informed during validation
			}
		}
		return result;
	}

	private List<PackageImport> getEPackageUris(EObject root) {
		EStructuralFeature packagesFeature = root.eClass()
				.getEStructuralFeature("epackages");
		if (packagesFeature == null || !packagesFeature.isMany()) {
			return Collections.emptyList();
		}
		EList<?> elements = (EList<?>) root.eGet(packagesFeature);
		List<PackageImport> result = new ArrayList<PackageImport>(elements
				.size());
		for (Object object : elements) {
			if (object instanceof PackageImport) {
				PackageImport pImport = (PackageImport) object;
				result.add(pImport);
			}
		}
		return result;

	}

	public static boolean isEFactoryResource(URI uri) {
		return uri.fileExtension().equals("efactory");
	}
}
