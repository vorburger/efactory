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
/**
 * <copyright>
 *
 * Copyright (c) 2002-2006 Sebastian Benz and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Sebastian Benz - Initial API and implementation
 *
 * </copyright>
 *
 * 
 */
package com.googlecode.efactory.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingletonIterator<T> implements Iterator<T> {

	T content;

	public SingletonIterator(T content) {
		this.content = content;
	}
	public boolean hasNext() {
		return content != null;
	}
	public T next() {
		if (content == null)
			throw new NoSuchElementException();
		T tmp = content;
		content = null;
		return tmp;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public static <T> SingletonIterator<T> create(T content) {
		return new SingletonIterator<T>(content);
	}

}
