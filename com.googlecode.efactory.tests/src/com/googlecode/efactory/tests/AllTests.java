package com.googlecode.efactory.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.googlecode.efactory.builder.tests.AttributeTest;
import com.googlecode.efactory.builder.tests.NameAttributeTest;
import com.googlecode.efactory.builder.tests.NameSetterTest;
import com.googlecode.efactory.builder.tests.NewObjectTest;
import com.googlecode.efactory.builder.tests.ReferenceTest;
import com.googlecode.efactory.imports.tests.ImportsTest;
import com.googlecode.efactory.proposal.tests.AttributeValueProposalProviderTest;
import com.googlecode.efactory.proposal.tests.FeatureProposalProviderTest;
import com.googlecode.efactory.proposal.tests.MultiplicityProposalProviderTest;
import com.googlecode.efactory.proposal.tests.NewObjectValueProposalProviderTest;
import com.googlecode.efactory.serialization.tests.SerializationTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.googlecode.efactory.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(AttributeTest.class);
		suite.addTestSuite(NameAttributeTest.class);
		suite.addTestSuite(NameSetterTest.class);
		suite.addTestSuite(NewObjectTest.class);
		suite.addTestSuite(ReferenceTest.class);
		suite.addTestSuite(ImportsTest.class);
		suite.addTestSuite(AttributeValueProposalProviderTest.class);
		suite.addTestSuite(FeatureProposalProviderTest.class);
		suite.addTestSuite(MultiplicityProposalProviderTest.class);
		suite.addTestSuite(NewObjectValueProposalProviderTest.class);
		suite.addTestSuite(SerializationTest.class);
		//$JUnit-END$
		return suite;
	}

}
