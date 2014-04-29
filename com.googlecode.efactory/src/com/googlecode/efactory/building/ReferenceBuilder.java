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
package com.googlecode.efactory.building;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.impl.ReferenceImpl;
import com.googlecode.efactory.util.EcoreUtil3;

public class ReferenceBuilder extends FeatureBuilder {

	private Reference reference;

	public ReferenceBuilder(Reference reference) {
		this.reference = reference;
	}

	@Override
	public void build() throws ModelBuilderException {
		getModelBuilder().addDeferredLinkingFeatureBuilder(this);
	}

	public void link() throws ModelBuilderException {
		EObject newValue = getReferencedObject();
		final EStructuralFeature eFeature = getFeature().getEFeature();
		if (eFeature.eIsProxy())
			return;
		EcoreUtil3.setOrAddValue(getContainer(), eFeature, newValue);
	}
	
	private EObject getReferencedObject() throws ModelBuilderException {
		// TODO change reference to be ReferenceImpl not Reference, and save cast here
		EObject referencedObject = ((ReferenceImpl)reference).basicGetValue();
		if (referencedObject.eIsProxy()) {
			// TODO Think this over.. if this ends up working, I can get entirely rid of the ReferenceBuilder ..
			// TODO this is currently an EObjectImpl EMF proxy, but that leads to ClassCastException.. it needs to be made an instance of the right type. How?? 
			return referencedObject;
//			// @see Partial2Test.testPartiallyTypedResourceNoExceptions & Partial2Test.efactory
//			URI uri = ((InternalEObject)referencedObject).eProxyURI(); 
//			String fragment = uri.fragment();
//			if (new LazyURIEncoder().isCrossLinkFragment(referencedObject.eResource(), fragment))
//				return null;
		}
		if (referencedObject instanceof NewObject) {
			throw new IllegalStateException("So this is still needed? ;)");
//			NewObject referencedNewObject = (NewObject) referencedObject;
//			if (referencedNewObject.eResource().equals(reference.eResource())) {
//				return getModelBuilder().getBuilt(referencedNewObject);
//			} else {
//				// the referencedNewObject is in another resource.. so:
//				EFactoryResource referencedResource = (EFactoryResource) referencedNewObject.eResource();
//				// This implementation works, but could be optimized with some sort of Proxy which resolves the NewObject to an EObject lazily later only.. 
//				return referencedResource.getEFactoryEObject(referencedNewObject);
//			}
		}
		return referencedObject;
	}
}
