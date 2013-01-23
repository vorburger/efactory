package com.googlecode.efactory.dynamic.tests;

import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

import com.googlecode.efactory.builder.tests.AbstractModelBuilderTest;

public class DynamicEmfTest extends AbstractModelBuilderTest {

	@Override
	protected String getTestModelName() {
		return "DynamicEmf.efactory";
	}

	public void testBuilding() throws Exception {
		try {
			find(DynamicEObjectImpl.class, "Person");
		} catch (NoSuchElementException ex) {
			fail("Dynamic model not created");
		}
	}

}
