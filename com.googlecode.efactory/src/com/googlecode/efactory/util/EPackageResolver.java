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
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.SimpleCache;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class EPackageResolver {

	private SimpleCache<Pair<Resource, String>, EPackage> cache = new SimpleCache<Pair<Resource, String>, EPackage>(
			new Function<Pair<Resource, String>, EPackage>() {
				@SuppressWarnings("null") // OK, because we know cache is only used from resolve() below, which has @NonNull args
				public EPackage apply(Pair<Resource, String> tuple) {
					String packageUri = tuple.getSecond();
					EPackage ePackage = getPackageFromRegistry(packageUri);
					if (ePackage == null) {
						ePackage = loadPackageAsResource(tuple.getFirst(), packageUri);
					}
					return ePackage; // could still be null here..
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

	protected EPackage loadPackageAsResource(Resource context, String packageUri) {
		final Resource resource = EcoreUtil2.getResource(context, packageUri);
		if (resource == null)
			return null;
		return getEPackage(resource);
	}

	/**
	 * We've seen a corner case (DS-6421) where there is no EMF ECore model gen.
	 * code, in the case of another Xtext lang. which "overloaded" Xbase, but doesn't have
	 * ANY grammar rules or terminals of it's own.
	 * 
	 * @return EPackage for nsURI, or null if there was a lookup problem.
	 */
	protected EPackage getPackageFromRegistry(String nsURI) {
		try {
			return EPackage.Registry.INSTANCE.getEPackage(nsURI);
		} catch (WrappedException e) {
			return null;
		}
	}

	private EPackage getEPackage( Resource resource) {
		// This will (has to, see DynamicEmfTest) find
		// an EPackage created dynamically in an EFactory as well
		EList<EObject> contents = resource.getContents();
		for (EObject eObject : contents) {
			if (eObject instanceof EPackage)
				return (EPackage) eObject;
		}
		return null;
	}

	/**
	 * All known EPackages.
	 * @return Iterable of EPackage, WITHOUT ANY null ENTRIES!
	 */
	public Iterable<EPackage> getAllRegisteredEPackages() {
		// copy due to potential CME while resolving EPackages
		List<String> packageUris = Lists.newArrayList(EPackage.Registry.INSTANCE.keySet());
		Iterable<String> packageUrisWithoutAnyNulls = Iterables.filter(packageUris, Predicates.notNull());
		Iterable<EPackage> packagesMaybeWithNull = Iterables.transform(packageUrisWithoutAnyNulls,
				new Function<String, EPackage>() {
					// @since Luna (Eclipse 4.4), @NonNull here leads to:
					// "Illegal redefinition of parameter uri, inherited method from Function<String,EPackage> does not constrain this parameter"
					// so we have to make this handstand here instead:
					public EPackage apply(String uri) {
						if (uri == null)
							throw new IllegalArgumentException();
						return getPackageFromRegistry(uri);
					}
				});
		Iterable<EPackage> packagesWithoutAnyNulls = Iterables.filter(packagesMaybeWithNull, Predicates.notNull());
		return packagesWithoutAnyNulls;
	}
}
