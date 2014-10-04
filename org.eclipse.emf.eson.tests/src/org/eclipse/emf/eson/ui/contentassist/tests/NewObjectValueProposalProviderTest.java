/*
 * #%L
 * org.eclipse.emf.eson.tests
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.ui.contentassist.tests;

import org.junit.Test;

public class NewObjectValueProposalProviderTest extends	AbstractEFactoryContentAssistProcessorTest {

	private static final String body = "use testmodel.* "
			+ "TestModel testModelName {\n"
			+ "referenceTestContainer: [ ReferenceTestContainer test1 {  } ] }";

	@Test
	public void testMultiplicityContainment_One() throws Exception {
		newBuilder().append(body).cursorBack(6).insert("containments: [  ]")
			.cursorBack(2).assertText("ReferenceTarget", "ReferenceTargetSubclass");
	}

}
