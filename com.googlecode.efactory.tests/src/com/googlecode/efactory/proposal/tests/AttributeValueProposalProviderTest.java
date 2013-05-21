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

import org.junit.Test;

@SuppressWarnings("restriction")
public class AttributeValueProposalProviderTest extends
		AbstractEFactoryContentAssistProcessorTest {

	private static final String body = "use \"http://testmodel/1.0\" "
			+ "TestModel testModelName {\n"
			+ "attributeTest += AttributeTestContainer test {\n";

	@Test
	public void testCompleteBooleanAttribute_Value() throws Exception {
		newBuilder().append(body + "oneBool = ").assertText("true", "false");
	}

	@Test
	public void testCompleteStringAttribute_Value() throws Exception {
		newBuilder().append(body + "oneString = ").assertText("\"Value\"");
	}

	@Test
	public void testCompleteIntegerAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneInt = ").assertText("1");
	}

	@Test
	public void testCompleteDateAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneDate = ").assertText("12.31.2008");
	}

	@Test
	public void testCompleteBigIntegerAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneBigInteger = ").assertText("1");
	}

	@Test
	public void testCompleteShortAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneShort = ").assertText("1");
	}

	@Test
	public void testCompleteBigDecimalAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneBigDecimal = ").assertText("0.0");
	}

	@Test
	public void testCompleteEnumAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneEnum = ").assertText(":Sample",
				":Sample2");
	}

	@Test
	public void testCompleteDoubleAttribute_Attribute() throws Exception {
		newBuilder().append(body + "oneDouble = ").assertText("0.0");
	}
	
}
