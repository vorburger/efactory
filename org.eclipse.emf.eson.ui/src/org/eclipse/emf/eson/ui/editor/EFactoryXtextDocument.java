/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger and others
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.ui.editor;

import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;

import com.google.inject.Inject;

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
