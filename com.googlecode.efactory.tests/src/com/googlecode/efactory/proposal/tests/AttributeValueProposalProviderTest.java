/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.proposal.tests;

public class AttributeValueProposalProviderTest extends
		AbstractContentAssistProcessorTest {

	private static final String body = "use \"http://testmodel/1.0\" "
			+ "TestModel testModelName {\n"
			+ "attributeTest += AttributeTestContainer test {\n";

	public void testCompleteBooleanAttribute_Value() throws Exception {
		newBuilder().append(body + "oneBool = ").assertText("true", "false");
	}

	public void testCompleteStringAttribute_Value() throws Exception {
		newBuilder().append(body + "oneString = ").assertText("\"Value\"");
	}

	public void testCompleteIntegerAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneInt = ").assertText("1");
	}

	public void testCompleteDateAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneDate = ").assertText("12.31.2008");
	}

	public void testCompleteBigIntegerAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneBigInteger = ").assertText("1");
	}

	public void testCompleteShortAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneShort = ").assertText("1");
	}

	public void testCompleteBigDecimalAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneBigDecimal = ").assertText("0.0");
	}

	public void testCompleteEnumAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneEnum = ").assertText(":Sample",
				":Sample2");
	}

	public void testCompleteDoubleAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneDouble = ").assertText("0.0");
	}

}
