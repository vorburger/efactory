package com.googlecode.efactory.dynamic.tests;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.util.Find;

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
