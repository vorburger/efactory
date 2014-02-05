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
