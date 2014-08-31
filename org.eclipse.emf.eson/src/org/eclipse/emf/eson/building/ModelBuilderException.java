/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.building;

/**
 * Exception for stuff going wrong in the ModelBuilder.
 * 
 * @author Michael Vorburger
 */
public class ModelBuilderException extends Exception {
	private static final long serialVersionUID = 218749336949211916L;

	public ModelBuilderException(String message) {
		super(message);
	}

	public ModelBuilderException(String message, Throwable e) {
		super(message, e);
	}
}
