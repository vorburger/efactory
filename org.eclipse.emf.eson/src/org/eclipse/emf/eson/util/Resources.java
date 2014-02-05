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
package org.eclipse.emf.eson.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class Resources {

	private Resources() {
		// not intended to be instantiated
	}

	public static <T extends EObject> T getRoot(Resource resource,
			final Class<T> type) {
		if (resource != null) {
			return type.cast(Iterables.find(resource.getContents(),
					new Predicate<EObject>() {

						public boolean apply(EObject input) {
							return type.isInstance(input);
						}
					}));
		}
		throw new IllegalArgumentException("Resource must not be null");
	}

}
