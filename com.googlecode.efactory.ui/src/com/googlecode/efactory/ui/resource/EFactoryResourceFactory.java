package com.googlecode.efactory.ui.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.efactory.resource.EFactoryResource;

public class EFactoryResourceFactory extends XtextResourceFactory {
	@Inject
	private Provider<EFactoryResource> efactoryResourceProvider;

	@Inject
	public EFactoryResourceFactory(Provider<XtextResource> resourceProvider) {
		super(resourceProvider);
	}

	@Override
	public Resource createResource(URI uri) {
		EFactoryResource resource = efactoryResourceProvider.get();
		resource.setURI(uri);
		return resource;
	}
}
