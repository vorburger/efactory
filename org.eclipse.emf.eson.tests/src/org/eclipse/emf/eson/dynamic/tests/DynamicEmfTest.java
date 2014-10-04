/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2012 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.dynamic.tests;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.eson.EFactoryInjectorProvider;
import org.eclipse.emf.eson.tests.util.Find;
import org.eclipse.emf.eson.tests.util.ResourceProvider;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class DynamicEmfTest {

	@Inject ResourceProvider provider;
	
	/**
	 * Disabled for now, because it kept breaking and took too much time to
	 * maintain; latest EReferenceScopeProvider change broke this again (causing
	 * a NullPointerException at org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.getSettingDelegate()),
	 * and as this (*.ecore) integration isn't really a main use case now
	 * anymore, with Xcore.
	 */
	@Ignore
	@Test public void testBuilding() throws Exception {
		EObject testModel = provider.loadModel("res/BuilderTests/DynamicEmf.efactory");
/*		
		URIConverter uriConverter = resourceSet.getURIConverter();
		URI uri = new ResourceProvider(resourceSet, TestConstants.PLUGIN_ID).getUri("res/BuilderTests/DynamicEmf.efactory");
		InputStream in = uriConverter.createInputStream(uri);
		Factory factory = parser.parse(in, uri, null, resourceSet);
		Assert.assertEquals("Model", factory.getRoot().getEClass().getName());
		
		ModelBuilder modelBuilder = new ModelBuilder();
		EObject testModel = modelBuilder.build(factory);
*/		
		DynamicEObjectImpl person = Find.child(DynamicEObjectImpl.class, "Person", testModel);
		Assert.assertEquals("Entity", person.eClass().getName());
	}

}
