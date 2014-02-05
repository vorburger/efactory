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
package org.eclipse.emf.eson.generator.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eson.generator.EFactoryJSONGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.generator.tests.eclipsesource_json.JsonObject;
import org.eclipse.emf.eson.tests.util.ResourceProvider;
import org.eclipse.emf.eson.tests.util.TestSetup;

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
		JsonObject jsonObject = JsonObject.readFrom(jsonText);
		List<String> names = jsonObject.names();
		assertEquals(2, names.size());
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
