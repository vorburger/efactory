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
package com.googlecode.efactory.ui.outline.tests;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryUiInjectorProvider;
import com.googlecode.efactory.ui.outline.EFactoryOutlineTreeProvider;

/**
 * Test for the EFactoryOutlineTreeProvider.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryUiInjectorProvider.class)
public class EFactoryOutlineTreeProviderTest extends AbstractXtextTests {
	// Implementation inspired by org.eclipse.xtext.xtext.ui.editor.outline.XtextOutlineTreeProviderTest
	// TODO suggest an Xtext patch refactoring common re-usable code into an e.g. AbstractXtextOutlineTests, or an OutlineTestsFixture, and document it in Xtext UG doc 

	@Inject Injector injector;
	@Inject XtextDocument document;
	@Inject EFactoryOutlineTreeProvider treeProvider;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		setInjector(injector);
		disableSerializerTest();
	}
	
	@Test
	public void testEcoreOutline() throws Exception {
		IOutlineNode root = getOutlineRootNode("/Outline/Entity.efactory");
		assertNode(root, "entity", 5);
		assertNode(root.getChildren().get(0), "Type", 1);
	}

	protected void assertNode(IOutlineNode node, String text, int numChildren) {
		assertEquals(numChildren, node.getChildren().size());
		assertEquals(text, node.getText().toString());
	}
	
	private IOutlineNode getOutlineRootNode(String resourceName) throws IOException, Exception {
		String text = readFileIntoString(resourceName);
		XtextResource resource = getResourceAndExpect(getAsStream(text), 0);
		document.setInput(resource);
		IOutlineNode root = treeProvider.createRoot(document);
		traverseChildren(root);
		root = root.getChildren().get(0);
		return root;
	}

	private void traverseChildren(IOutlineNode node) {
		for (IOutlineNode child : node.getChildren()) {
			traverseChildren(child);
		}
	}
}
