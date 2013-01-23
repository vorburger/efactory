package com.googlecode.efactory.proposal.tests;

public class FeatureProposalProviderTest extends
		AbstractContentAssistProcessorTest {

	private static final String body = "use \"http://testmodel/1.0\" "
			+ "TestModel testModelName {\n"
			+ "referenceTestContainer +=ReferenceTestContainer test1 {\n";

	public void testCompleteFeatures_All() throws Exception {
		newBuilder().append(body).assertText("containments", "referenceToOne",
				"referenceToMany", "name", "containment", "oneAttribute",
				"manyAttributes", "}");
	}

	public void testCompleteBooleanAttribute_Value_One() throws Exception {
		newBuilder().append(
				body + "containments += ReferenceTarget target1 {}\n")
				.assertText("containments", "referenceToOne",
						"referenceToMany", "name", "containment",
						"oneAttribute", "manyAttributes", "}");
	}

	public void testCompleteBooleanAttribute_Value_Many() throws Exception {
		newBuilder().append(
				body + "containment += ReferenceTarget target1 {}\n")
				.assertText("containments", "referenceToOne",
						"referenceToMany", "name", "oneAttribute",
						"manyAttributes", "}");
	}

}
