/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.ui.labeling;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

public class EFactoryLabelProvider extends DefaultEObjectLabelProvider {

//	@Inject	private NameAccessor nameAccessor;

	@Inject
	protected EFactoryLabelProvider(AdapterFactory adapterFactory) {
		super(new AdapterFactoryLabelProvider(adapterFactory));
	}

	@Override
	protected Object doGetText(Object element) {
/*		
		if (element instanceof DynamicEObjectImpl) {
			DynamicEObjectImpl eObject = (DynamicEObjectImpl) element;
			if (eObject.eResource() instanceof EFactoryResource) {
				EFactoryResource eFactoryResource = (EFactoryResource) eObject.eResource();
				Factory factory = eFactoryResource.getFactory();
				try {
					return nameAccessor.getName(factory, eObject);
				} catch (NoNameFeatureMappingException e) {
					return eObject.eClass().getName();
				}
			}
		}
*/		
		final Object text = super.doGetText(element);
		return text;
	}

}
