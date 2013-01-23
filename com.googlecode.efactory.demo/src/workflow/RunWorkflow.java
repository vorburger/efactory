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
package workflow;

import java.util.Collections;

import org.eclipse.emf.mwe.core.WorkflowRunner;

public class RunWorkflow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkflowRunner runner = new WorkflowRunner();
		runner.run("workflow/EntityGenerator.mwe", null, Collections
				.<String, String> emptyMap(), Collections
				.<String, String> emptyMap());

	}

}
