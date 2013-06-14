/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - rewritten following new Outline work 
 ******************************************************************************/

package com.googlecode.efactory.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;

import com.googlecode.efactory.eFactory.NewObject;

public class EFactoryLocationInFileProvider extends DefaultLocationInFileProvider {

	/** Inverse of com.googlecode.efactory.ui.outline.EFactoryOutlineTreeProvider.createNewObjectNode(IOutlineNode, NewObject) */
//	@Override
//	protected ICompositeNode findNodeFor(EObject semanticObject) {
//		EObject newObject = getRealObject(semanticObject);
//		return super.findNodeFor(newObject);
//	}

	@Override
	protected ITextRegion getTextRegion(EObject obj, boolean isSignificant) {
		return super.getTextRegion(getRealObject(obj), isSignificant);
/*		
		if (obj.eResource() instanceof EFactoryResource) {
			EFactoryResource efactoryResource = (EFactoryResource) obj.eResource();
			EObject eFactoryElement = efactoryResource.getEFactoryElement(obj);
			if (eFactoryElement == null) {
				return new TextRegion(0, 0);
			}
			return super.getTextRegion(eFactoryElement, isSignificant);
		}
		return super.getTextRegion(obj, isSignificant);
*/		
	}
	
	protected EObject getRealObject(EObject semanticObject) {
		Resource resource = semanticObject.eResource();
		EFactoryResource efResource = (EFactoryResource) resource;
		NewObject newObject = efResource.getEFactoryElement(semanticObject);
		if (newObject != null)
			return newObject;
		else
			return semanticObject;
	}
}
