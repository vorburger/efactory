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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Function;

public class ValueResolver implements Function<EObject, Object> {

	public Object apply(EObject eObject) {
		EStructuralFeature valueFeature = eObject.eClass()
				.getEStructuralFeature("value");
		if (valueFeature == null) {
			throw new IllegalArgumentException("'" + eObject.eClass().getName()
					+ "' has no feature 'value'");
		}
		return eObject.eGet(valueFeature);
	}

}
