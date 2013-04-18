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
package com.googlecode.efactory.scoping;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.googlecode.efactory.eFactory.Factory;

public class EFactoryScope extends SimpleScope implements IScope {

	// private NameAccessor nameAccessor = new NameAccessor();
	// private Factory context;

	public EFactoryScope(Factory factory, Iterable<IEObjectDescription> elements) {
		super(elements);
		// this.context = factory;
	}

	// @Override
	// public IEObjectDescription getSingleElement(EObject object) {
	// return getContentByName(nameAccessor.getName(context, object));
	// }
	//
	// @Override
	// public IEObjectDescription getContentByEObject(EObject object) {
	// }

}
