/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.ui.outline;

import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

import org.eclipse.emf.eson.eFactory.Factory;

/**
 * Outline.
 * @author Michael Vorburger
 */
public class EFactoryOutlineTreeProvider extends DefaultOutlineTreeProvider {

	// This Outline is currently intentionally deactivated, because it doesn't really work (labels were wrong etc.)
	// The new full split DSL/Tree view editor, with working right-click Add.. kind of replaces the Outline.
	
	/** For the Factory, jump straight to the Node - skip Factory, import, and use. */
	protected void _createChildren(DocumentRootNode parentNode, Factory factory) {
		// createNode(parentNode, factory.getRoot());
	}
	
	/** Needed for technical reasons; remove it, and other _createNode won't be hit anymore (Xtext PolymorphicDispatcher bug?) */
//	protected void _createNode(DocumentRootNode parentNode, EObject modelElement) {
//		if (modelElement instanceof NewObject)
//			createNewObjectNode(parentNode, (NewObject) modelElement);
//		else
//			super._createNode(parentNode, modelElement);
//	}
	
	/** For each NewObject, don't actually show the Outline of the NewObject, but of the EObject we created from it. */
//	protected void _createNode(IOutlineNode parentNode, NewObject nObject) {
//		createNewObjectNode(parentNode, nObject);
//	}
	
	/** Note, this has it's "inverse" in org.eclipse.emf.eson.resource.EFactoryLocationInFileProvider.findNodeFor(EObject) */
//	protected void createNewObjectNode(IOutlineNode parentNode, NewObject nObject) {
//		Resource resource = nObject.eResource();
//		EObject eObject = EFactoryResource.getEFactoryEObject(resource);
//		if (eObject != null) {
//			this.createNode(parentNode, eObject);
//		} else {
//			// if we cannot get the built EObject (ModelBuilder), 
//			// we could e.g. just show the source in the Outline instead?
//			// but this doesn't work, leads to StackOverflowError.. TODO needs more thought.
//			// this.createNode(parentNode, nObject);
//		}
//	}

}
