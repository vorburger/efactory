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

import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;

public class ContainerResolver {

	public <T extends EObject> T resolve(Class<T> clazz, EObject eObject)
			throws NoSuchElementException {
		EObject parent = eObject.eContainer();
		while (parent != null) {
			if (clazz.isInstance(parent)) {
				return clazz.cast(parent);
			}
			parent = parent.eContainer();
		}
		throw new NoSuchElementException(
				"Could not resolve container with type " + clazz.getName());
	}

}
