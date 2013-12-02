/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.formatting.tests;

import javax.inject.Inject;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.INodeModelFormatter;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

/**
 * Tests Formatting.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class FormatterTest {

	// using our ResourceProvider instead of a ParseHelper<com.googlecode.efactory.eFactory.Factory>
	// just because it has some convenience methods we like - if you're looking
	// at this code to write your own Xtext Formatter unit test, just use a
	// ParseHelper.
	@Inject ResourceProvider resourceProvider;
	
	@Inject INodeModelFormatter formatter;
	
	
	@BeforeClass public static void beforeTestClass() {
		TestSetup.INSTANCE.doSetup();
	}

	
	@Test
	@Ignore
	public void testFormatting() throws Exception {
		URI uri = resourceProvider.getUri("res/FormatterTests/FormatterTest.efactory");
		String text = resourceProvider.loadAsStringFromURI(uri);
		EObject eo = resourceProvider.load(uri, true).get(0);
        IParseResult parseResult = ((XtextResource) eo.eResource()).getParseResult();
        Assert.assertNotNull(parseResult);
		ICompositeNode rootNode = parseResult.getRootNode();
		String formattedText = formatter.format(rootNode, 0, text.length()).getFormattedText();
		Assert.assertEquals(text, formattedText);
	}

}
