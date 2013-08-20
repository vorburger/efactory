/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - significant changes re. EFactoryDerivedStateComputer 
 ******************************************************************************/
package com.googlecode.efactory.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IWriteAccess;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.building.ModelBuilderException;
import com.googlecode.efactory.eFactory.NewObject;

public class EFactoryResource extends DerivedStateAwareResource {

	@Inject private ModelBuilder builder;
	
	protected IWriteAccess<XtextResource> documentWriteAccess;
	
	public boolean isBuilt() {
		return getBuilder().isBuilt();
	}
	
	@Nullable public NewObject getEFactoryElement(EObject eObject) {
		if (builder == null) {
			return null;
		}
		if (!builder.isBuilt())
			return null;
		try {
			return builder.getSource(eObject);
		} catch (ModelBuilderException e) {
			return null;
		}
	}

	@NonNull public EObject getEFactoryEObject(NewObject nObject) throws ModelBuilderException {
		return getBuilder().getBuilt(nObject);
	}

	// package-private, as only used by EFactoryDerivedStateComputer
	@NonNull ModelBuilder getBuilder() throws IllegalStateException {
		// written in this weird style just to satisfy Eclipse' slightly dumb null check
		final ModelBuilder _builder = builder;
		if (_builder != null) {
			return _builder;
		} else {
			throw new IllegalStateException("EFactoryResource is missing @Injected ModelBuilder?!");
		}
	}
	
	@Nullable public com.googlecode.efactory.eFactory.Factory getFactory() {
		if (getContents().isEmpty()) {
			return null;
		}
		return (com.googlecode.efactory.eFactory.Factory) getContents().get(0);
	}

	public void setWriteAccess(IWriteAccess<XtextResource> xtextDocument) {
		this.documentWriteAccess = xtextDocument;
	}

	// package-private, as only used by EFactoryDerivedStateComputer
	@NonNull Provider<IWriteAccess<XtextResource>> getWriteAccessProvider() {
		return new Provider<IWriteAccess<XtextResource>>() {
			@SuppressWarnings("null") // JDT null check even in Kepler is still too dumb to understand the if null means return will never return null.. :( 
			public @NonNull IWriteAccess<XtextResource> get() {
				if (documentWriteAccess == null)
					// throw new IllegalStateException("setWriteAccess(IWriteAccess<XtextResource>) should have been called by com.googlecode.efactory.ui.editor.EFactoryXtextDocument.setInput(XtextResource), but wasnt't (yet) - how come?");
					return new XtextResourceDirectAccess(EFactoryResource.this);
				return documentWriteAccess;
			}
		};
	}
}
