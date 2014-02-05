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
