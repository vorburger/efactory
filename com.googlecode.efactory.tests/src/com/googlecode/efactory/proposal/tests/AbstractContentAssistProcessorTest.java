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
package com.googlecode.efactory.proposal.tests;

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.junit.AbstractXtextTests;
import org.eclipse.xtext.ui.junit.editor.contentassist.ContentAssistProcessorTestBuilder;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryRuntimeModule;
import com.googlecode.efactory.EFactoryStandaloneSetup;
import com.googlecode.efactory.tests.Activator;
import com.googlecode.efactory.ui.EFactoryUiModule;

public abstract class AbstractContentAssistProcessorTest extends
		AbstractXtextTests {

	protected ContentAssistProcessorTestBuilder newBuilder() throws Exception {
		return new ContentAssistProcessorTestBuilder(getEFactorySetup(), this);
	}

	public ISetup getEFactorySetup() {
		return new EFactoryStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(Modules2.mixin(new EFactoryRuntimeModule(), new EFactoryUiModule(Activator.getInstance()), new SharedStateModule())) ;
			}
		};
	}

}
