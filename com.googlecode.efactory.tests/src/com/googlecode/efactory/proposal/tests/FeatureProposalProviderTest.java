package com.googlecode.efactory.proposal.tests;

import org.junit.Test;

@SuppressWarnings("restriction")
public class FeatureProposalProviderTest extends
		AbstractEFactoryContentAssistProcessorTest {

	private static final String body = "use testmodel.* "
			+ "TestModel testModelName {\n"
			+ "referenceTestContainer = [ ReferenceTestContainer test1 {\n";

	@Test
	public void testCompleteFeatures_All() throws Exception {
		newBuilder().append(body).assertText("containments", "referenceToOne",
				"referenceToMany", "name", "containment", "oneAttribute",
				"manyAttributes", "}");
	}

	@Test
	public void testCompleteBooleanAttribute_Value_One() throws Exception {
		newBuilder().append(
				body + "containments = [ ReferenceTarget target1 {}\n")
				.assertText("containments", "referenceToOne",
						"referenceToMany", "name", "containment",
						"oneAttribute", "manyAttributes", "}");
	}

	@Test
	public void testCompleteBooleanAttribute_Value_Many() throws Exception {
		newBuilder().append(
				body + "containment = [ ReferenceTarget target1 {}\n")
				.assertText("containments", "referenceToOne",
						"referenceToMany", "name", "oneAttribute",
						"manyAttributes", "}");
	}

}
