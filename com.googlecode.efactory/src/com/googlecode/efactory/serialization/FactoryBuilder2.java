/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.serialization;

import org.eclipse.emf.ecore.EObject;

import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * FactoryBuilder based on an EFactoryResource.
 * 
 * @see FactoryBuilder
 * 
 * @author Michael Vorburger
 */
public class FactoryBuilder2 implements IFactoryBuilder {
	
	private final EFactoryResource resource;

	public FactoryBuilder2(EFactoryResource resource) {
		if (resource == null)
			throw new IllegalArgumentException("FactoryBuilder2(null)");
		this.resource = resource;
	}

	public NewObject getOrBuildNewObject(EObject eObject) {
		if (eObject == null)
			throw new IllegalArgumentException("getOrBuildNewObject(null)");
		// we cannot rely on eObject.eResource(), because programmatically newly added objects
		// might not be in our EFactoryResource, yet.  But if the EObject does have a Resource,
		// it better be ours.  (Or are there scenarios where this doesn't make sense?)
		if (eObject.eResource() != null && eObject.eResource() != resource) {
			throw new IllegalArgumentException("getOrBuildNewObject(eObject) eResource is not ours");
		}
		
		NewObject newObject = resource.getEFactoryNewObject(eObject);
		if (newObject != null)
			return newObject;
		
		Factory factory = resource.getEFactoryFactory();
		NewObjectBuilder builder = NewObjectBuilder.context(factory, this);
		newObject = builder.build(eObject);
		return newObject;
	}

}
