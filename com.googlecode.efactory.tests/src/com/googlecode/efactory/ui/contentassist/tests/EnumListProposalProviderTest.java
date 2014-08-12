package com.googlecode.efactory.ui.contentassist.tests;

import org.junit.Test;

public class EnumListProposalProviderTest extends
		AbstractEFactoryContentAssistProcessorTest {

	private static final String body = "use testmodel.* \n"
			+ "TestModel testModelName {\n"
			+ "singleRequired : SingleRequired {}\n\n"
			+ "enumListTestContainer : [\n"
			+ "EnumListTestContainer enumList {\n" + "sampleLiterals :[  ]}]}";

	@Test
	public void testShowEnumLiterals() throws Exception {
		newBuilder().append(body).cursorBack(5).assertText(":Sample", ":Sample2");
	}
}
