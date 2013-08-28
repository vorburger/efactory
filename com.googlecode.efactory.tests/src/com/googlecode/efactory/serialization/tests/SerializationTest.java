package com.googlecode.efactory.serialization.tests;

public class SerializationTest extends AbstractSerializationTest {

	public void testFactoryCreation() throws Exception {
		performSerializationTest("FactoryCreation.efactory");
	}

	public void testSimplestMultiValue() throws Exception {
		performSerializationTest("SimplestMultiValue.efactory");
	}
	
	public void testAttributeTest() throws Exception {
		performSerializationTest("AttributeTest.efactory");
	}
	
	public void testReferenceTest() throws Exception {
		performSerializationTest("ReferenceTest.efactory");
	}

}
