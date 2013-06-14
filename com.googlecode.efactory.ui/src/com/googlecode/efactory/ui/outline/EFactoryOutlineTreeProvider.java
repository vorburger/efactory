/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * Outline.
 * @author Michael Vorburger
 */
public class EFactoryOutlineTreeProvider extends DefaultOutlineTreeProvider {
	
	/** For the Factory, jump straight to the Node - skip Factory, import, and use. */
	protected void _createChildren(DocumentRootNode parentNode, Factory factory) {
		createNode(parentNode, factory.getRoot());
	}
	
	/** Needed for technical reasons; remove it, and other _createNode won't be hit anymore (Xtext PolymorphicDispatcher bug?) */
	protected void _createNode(DocumentRootNode parentNode, EObject modelElement) {
		if (modelElement instanceof NewObject)
			createNewObjectNode(parentNode, (NewObject) modelElement);
		else
			super._createNode(parentNode, modelElement);
	}
	
	/** For each NewObject, don't actually show the Outline of the NewObject, but of the EObject we created from it. */
	protected void _createNode(IOutlineNode parentNode, NewObject nObject) {
		createNewObjectNode(parentNode, nObject);
	}
	
	/** Note, this has it's "inverse" in com.googlecode.efactory.resource.EFactoryLocationInFileProvider.findNodeFor(EObject) */
	protected void createNewObjectNode(IOutlineNode parentNode, NewObject nObject) {
		Resource resource = nObject.eResource();
		EFactoryResource efResource = (EFactoryResource) resource;
		EObject eObject = efResource.getEFactoryEObject(nObject);
		this.createNode(parentNode, eObject);
	}
}
