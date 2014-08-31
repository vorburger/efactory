/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2009 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EFactoryStandaloneResourceFactory extends XtextResourceFactory {

	@Inject
	private Provider<EFactoryStandaloneResource> efactoryResourceProvider;

	@Inject
	public EFactoryStandaloneResourceFactory(
			Provider<XtextResource> resourceProvider) {
		super(resourceProvider);
	}

	@Override
	public Resource createResource(URI uri) {
		EFactoryResource resource = efactoryResourceProvider.get();
		resource.setURI(uri);
		return resource;
	}

}
