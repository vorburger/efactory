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

package com.googlecode.efactory.ui.editor;

import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;

import com.google.inject.Inject;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * XtextDocument with overridden method to give the XtextResource its document.
 * 
 * @author Michael Vorburger
 */
public class EFactoryXtextDocument extends XtextDocument {

	@Inject
	public EFactoryXtextDocument(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer);
	}

	@Override
	public void setInput(XtextResource resource) {
		super.setInput(resource);
		EFactoryResource eFactoryResource = (EFactoryResource) resource;
		eFactoryResource.setWriteAccess(this);
	}

}
