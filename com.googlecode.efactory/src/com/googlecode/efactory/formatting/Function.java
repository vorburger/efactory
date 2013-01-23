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

package com.googlecode.efactory.formatting;

/**
 * A function with two arguments
 * 
 * @param <ARG1>
 *            The first argument type
 * @param <ARG2>
 *            The second argument type
 */
public interface Function<ARG1, ARG2> {

	void apply(ARG1 arg1, ARG2 arg2);

}
