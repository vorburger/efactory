/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.ui;

import org.eclipse.emf.eson.ui.contentassist.EFactoryContentAssistantFactory;
import org.eclipse.emf.eson.ui.contentassist.IEAttributeStringProposalProvider;
import org.eclipse.emf.eson.ui.contentassist.TerminalsEAttributeStringProposalProvider;
import org.eclipse.emf.eson.ui.editor.EFactoryFoldingRegionProvider;
import org.eclipse.emf.eson.ui.editor.EFactoryXtextDocument;
import org.eclipse.emf.eson.ui.highlighting.EFactoryHighlightingCalculator;
import org.eclipse.emf.eson.ui.highlighting.EFactorySemanticHighlightingConfiguration;
import org.eclipse.emf.eson.ui.highlighting.EFactoryTokenTypeToStringMapper;
import org.eclipse.emf.eson.ui.resource.EFactoryResourceFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.ui.editor.contentassist.IContentAssistantFactory;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EFactoryUiModule extends AbstractEFactoryUiModule {

	public EFactoryUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		
		// @see http://christiandietrich.wordpress.com/2011/09/19/xtext-content-assist-auto-activation/
		binder.bind(String.class)
				.annotatedWith(
						Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))
				.toInstance(":{"); //$NON-NLS-1$

		binder.bind(IResourceFactory.class).to(EFactoryResourceFactory.class);
		
		// it's important to bind to XtextDocument instead of to IXtextDocument
		// (I think), because there is no default declaration, and @Inject in
		// Xtext code base directly uses XtextDocument instead of IXtextDocument
		binder.bind(XtextDocument.class).to(EFactoryXtextDocument.class);
	}

	@Override
	public Class<? extends IContentAssistantFactory> bindIContentAssistantFactory() {
		return EFactoryContentAssistantFactory.class;
	}

	public Class<? extends IFoldingRegionProvider> bindIFoldingRegionProvider() {
		return EFactoryFoldingRegionProvider.class;
	}
	
	public Class<? extends IEAttributeStringProposalProvider> bindIEAttributeProposalProvider() {
		return TerminalsEAttributeStringProposalProvider.class;
	}

	public Class<? extends org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return EFactorySemanticHighlightingConfiguration.class;
	}
	
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return EFactoryHighlightingCalculator.class;
	}
	
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return EFactoryTokenTypeToStringMapper.class;
	}

}
