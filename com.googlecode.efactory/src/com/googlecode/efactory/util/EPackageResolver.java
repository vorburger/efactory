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

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.SimpleCache;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class EPackageResolver {

	private SimpleCache<Pair<Resource, String>, EPackage> cache = new SimpleCache<Pair<Resource, String>, EPackage>(
			new Function<Pair<Resource, String>, EPackage>() {

				public EPackage apply(Pair<Resource, String> tuple) {

					String packageUri = tuple.getSecond();
					EPackage ePackage = getPackageFromRegistry(packageUri);
					if (ePackage == null) {
						ePackage = loadPackageAsResource(tuple.getFirst(),
								packageUri);
					}
					return ePackage;
				}
			});

	/**
	 * Get the packageUri from the resource.
	 * @param resource an EMF Resource
	 * @param packageUri an EMF Package URI
	 * @return EPackage, or null if the resource does not contain an EPackage
	 */
	public EPackage resolve(Resource resource, String packageUri) {
		Pair<Resource, String> pair = Tuples.create(resource, packageUri);
		return cache.get(pair);
	}

	private EPackage loadPackageAsResource(Resource context, String packageUri) {
		return getEPackage(EcoreUtil2.getResource(context, packageUri));
	}

	private EPackage getPackageFromRegistry(String packageUri) {
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageUri);
		return ePackage;
	}

	private /*@Nullable*/ EPackage getEPackage(Resource resource) {
		// This will (has to, see DynamicEmfTest) find
		// an EPackage created dynamically in an EFactory as well
		EList<EObject> contents = resource.getContents();
		for (EObject eObject : contents) {
			if (eObject instanceof EPackage)
				return (EPackage) eObject;
		}
		return null;
	}

	public Iterable<EPackage> getAllRegisteredEPackages() {
		// copy due to potential CME while resolving EPackages
		List<String> packageUris = Lists
				.newArrayList(EPackage.Registry.INSTANCE.keySet());
		return Iterables.transform(packageUris,
				new Function<String, EPackage>() {
					public EPackage apply(String uri) {
						return EPackage.Registry.INSTANCE.getEPackage(uri);
					}
				});
	}
}
