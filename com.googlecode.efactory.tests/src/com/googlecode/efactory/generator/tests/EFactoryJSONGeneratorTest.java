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
package com.googlecode.efactory.generator.tests;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.generator.EFactoryJSONGenerator;
import com.googlecode.efactory.generator.tests.douglascrockford.JSONObject;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestSetup;

/**
 * Tests for EFactoryJSONGenerator.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class EFactoryJSONGeneratorTest {

	private static final String TEST_JSON = "/test.json";
	
	@Inject ResourceProvider rp;
	
	@BeforeClass public static void beforeTest() {
		TestSetup.INSTANCE.doSetup();
	}
	
	@Test public void testJSONGenerator() throws Exception {
		Resource resource = rp.loadModel("res/BuilderTests/AttributeTest.efactory").eResource();
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		new EFactoryJSONGenerator() { // sub class for test to be able to access protected methods 
			@Override protected boolean isJSONGenerationActive() { 
				return true; 
			};
			@Override protected String getProjectRelativeFileName(URI uri) {
				return TEST_JSON;
			};
		}.doGenerate(resource, fsa);
		// TODO switch @deprecated getFiles() to getTextFiles() when switching to newer Xtext version (currently still on 2.3, due to in-house reasons) 
		String jsonText = fsa.getFiles().get(IFileSystemAccess.DEFAULT_OUTPUT + TEST_JSON).toString();
		//System.out.println(EFactoryJSONGeneratorTest.class.getName() + ":\n" + jsonText);
		JSONObject json = new JSONObject(jsonText);
		String[] names = JSONObject.getNames(json);
		assertEquals(2, names.length);
	}
	
	@Test public void testGetProjectRelativeFileName() {
		final URI uri = URI.createPlatformResourceURI("MYPROJECT/some/sub/dir/myfile.ext", true);
		new EFactoryJSONGenerator() { // sub class for test to be able to access protected methods 
			void test() {
				assertEquals("some/sub/dir/myfile.ext", getProjectRelativeFileName(uri));
			}
		}.test();
	}

	@Test public void testGetJSONFileName() {
		final URI uri = URI.createPlatformResourceURI("MYPROJECT/some/sub/dir/myfile.ext", true);
		new EFactoryJSONGenerator() { // sub class for test to be able to access protected methods 
			void test() {
				assertEquals("some/sub/dir/myfile.json", getJSONFileName(uri));
			}
		}.test();
	}
	
}
