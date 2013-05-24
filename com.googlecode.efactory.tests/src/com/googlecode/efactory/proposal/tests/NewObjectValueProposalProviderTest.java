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
public class NewObjectValueProposalProviderTest extends
		AbstractEFactoryContentAssistProcessorTest {

	private static final String body = "use testmodel.* "
			+ "TestModel testModelName {\n"
			+ "referenceTestContainer +=ReferenceTestContainer test1 {\n";

	@Test
	public void testMultiplicityContainment_One() throws Exception {
		newBuilder().append(body + "containments += ").assertText(
				"ReferenceTarget", "ReferenceTargetSubclass");
	}

}
