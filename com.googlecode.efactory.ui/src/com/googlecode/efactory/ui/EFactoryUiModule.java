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
/*
 * generated by Xtext
 */
package com.googlecode.efactory.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.ui.editor.contentassist.IContentAssistantFactory;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import com.googlecode.efactory.ui.contentassist.EFactoryContentAssistantFactory;
import com.googlecode.efactory.ui.editor.EFactoryFoldingRegionProvider;
import com.googlecode.efactory.ui.highlighting.EFactoryHighlightingCalculator;
import com.googlecode.efactory.ui.resource.EFactoryResourceFactory;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EFactoryUiModule extends
		com.googlecode.efactory.ui.AbstractEFactoryUiModule {

	public EFactoryUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(String.class)
				.annotatedWith(
						Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))
				.toInstance("="); //$NON-NLS-1$
		binder.bind(IResourceFactory.class).to(EFactoryResourceFactory.class);
		binder.bind(ISemanticHighlightingCalculator.class).to(
				EFactoryHighlightingCalculator.class);
		binder.bind(IFoldingRegionProvider.class).to(
				EFactoryFoldingRegionProvider.class);
	}

	@Override
	public Class<? extends IContentAssistantFactory> bindIContentAssistantFactory() {
		return EFactoryContentAssistantFactory.class;
	}

}
