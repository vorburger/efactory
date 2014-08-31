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
package org.eclipse.emf.eson.scoping;

import org.apache.log4j.Logger;
import org.eclipse.xtext.util.PolymorphicDispatcher.WarningErrorHandler;

/**
 * WarningErrorHandler which ignores NoSuchMethodException.
 * 
 * @see http://www.eclipse.org/forums/index.php/m/759778/
 */
public class WarningErrorHandlerWithoutNoSuchMethodException<RT> extends WarningErrorHandler<RT> {

	public WarningErrorHandlerWithoutNoSuchMethodException(Logger logger) {
		super(logger);
	}

	@Override
	public RT handle(Object[] params, Throwable throwable) {
		if( !(throwable instanceof NoSuchMethodException ) ) {
			return super.handle(params, throwable);
		} else {
			return null;
		}
	}

}
