/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.ui.contentassist.tests;

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.ui.ContentAssistProcessorTestBuilder;
import org.eclipse.xtext.junit4.util.ResourceLoadHelper;

import com.google.inject.Injector;

/**
 * ContentAssistProcessorTestBuilder with a more convenient insert() method.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=416926
 * 
 * @author Michael Vorburger
 */
@SuppressWarnings("restriction")
public class ContentAssistProcessorTestBuilder2 extends ContentAssistProcessorTestBuilder {

	public ContentAssistProcessorTestBuilder2(Injector injector, ResourceLoadHelper helper) throws Exception {
		super(injector, helper);
	}

	protected ContentAssistProcessorTestBuilder2(ISetup setupClazz, AbstractXtextTests tests) throws Exception {
		super(setupClazz, tests);
	}

	
	public ContentAssistProcessorTestBuilder2 insert(String model) throws Exception {
		return (ContentAssistProcessorTestBuilder2) super.insert(model, getCursorPosition());
	}

	
	@Override
	public ContentAssistProcessorTestBuilder2 append(String model) throws Exception {
		return (ContentAssistProcessorTestBuilder2) super.append(model);
	}

	@Override
	public ContentAssistProcessorTestBuilder2 cursorBack(int times) throws Exception {
		return (ContentAssistProcessorTestBuilder2) super.cursorBack(times);
	}

	@Override
	public ContentAssistProcessorTestBuilder2 assertText(String... expectedText) throws Exception {
		return (ContentAssistProcessorTestBuilder2) super.assertText(expectedText);
	}
	
}
