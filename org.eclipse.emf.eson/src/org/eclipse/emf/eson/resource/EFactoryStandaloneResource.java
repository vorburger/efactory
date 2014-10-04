/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2002 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * A resource that registers all contained EPackages after loading. Should not
 * be used in a running eclipse.
 * 
 */
public class EFactoryStandaloneResource extends EFactoryResource {

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		super.doLoad(inputStream, options);
		for (EObject content : getContents()) {
			if (content instanceof EPackage) {
				EPackage ePackage = (EPackage) content;
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
			}
		}
	}

}
