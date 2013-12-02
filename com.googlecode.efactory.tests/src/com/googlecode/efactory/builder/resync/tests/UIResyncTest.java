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
package com.googlecode.efactory.builder.resync.tests;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.DocumentPartitioner;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.junit.Test;
import org.junit.runner.RunWith;

import testmodel.TestModel;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryUiInjectorProvider;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * Tests the "re-sychronization" of changes to the derived "real" EObjects to
 * the source EFactory model at the UI (XtextEditor) integration level. This is
 * similar to the more lower level BuilderResyncTest, but works at the
 * XtextDocument level, so this one makes sure that the IWriteAccess stuff & Co.
 * we have to use work as they should in EFactoryAdapter.
 * 
 * @see BuilderResyncTest
 * @see org.eclipse.xtext.util.concurrent.IWriteAccess
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryUiInjectorProvider.class)
public class UIResyncTest extends AbstractXtextTests {

	@Inject Injector injector;
	@Inject XtextDocument document;
	@Inject DocumentPartitioner partitioner; 
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		setInjector(injector);
		disableSerializerTest();
	}

	@Test
	public void testDocumentChangesWhenModifyingDerrivedEObject() throws Exception {
		String text = readFileIntoString("BuilderResyncTests/1TestModelWithNameProperty.efactory");
		XtextResource resource = getResourceAndExpect(getAsStream(text), 0);

		document.set(text);
		document.setInput(resource);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		
		assertEquals("use testmodel.* \n\nTestModel {\n	name: \"test\"\n	singleRequired: SingleRequired { }\n}\n", document.get());

		TestModel testModel = EFactoryResource.getEFactoryEObject(resource, TestModel.class);
		assertNotNull(testModel);
		Preconditions.checkNotNull(testModel);
		assertEquals("test", testModel.getName());

		testModel.setName("tested");
		assertEquals("use testmodel.* \n\nTestModel {\n	name: \"tested\"\n	singleRequired: SingleRequired { }\n}\n", document.get());
	}
}
