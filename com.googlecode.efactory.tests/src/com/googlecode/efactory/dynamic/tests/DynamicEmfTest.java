package com.googlecode.efactory.dynamic.tests;

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.efactory.EFactoryInjectorProvider;
import com.googlecode.efactory.building.ModelBuilder;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.tests.util.ResourceProvider;
import com.googlecode.efactory.tests.util.TestConstants;
import com.googlecode.efactory.util.Find;

@RunWith(XtextRunner.class)
@InjectWith(EFactoryInjectorProvider.class)
public class DynamicEmfTest {

	@Inject ParseHelper<Factory> parser;
	@Inject ResourceSet resourceSet;
	
	@Test public void testBuilding() throws Exception {
		URIConverter uriConverter = resourceSet.getURIConverter();
		URI uri = new ResourceProvider(resourceSet, TestConstants.PLUGIN_ID).getUri("res/BuilderTests/DynamicEmf.efactory");
		InputStream in = uriConverter.createInputStream(uri);
		Factory factory = parser.parse(in, uri, null, resourceSet);
		Assert.assertEquals("Model", factory.getRoot().getEClass().getName());
		
		ModelBuilder modelBuilder = new ModelBuilder();
		EObject testModel = modelBuilder.build(factory);
		DynamicEObjectImpl person = Find.child(DynamicEObjectImpl.class, "Person", testModel);
		Assert.assertEquals("Entity", person.eClass().getName());
	}

}
