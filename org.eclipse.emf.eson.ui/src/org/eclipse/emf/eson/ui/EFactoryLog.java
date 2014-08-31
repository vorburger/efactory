/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz, Michael Vorburger and others
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.emf.eson.ui.internal.EFactoryActivator;

public class EFactoryLog {

	public static void logError(Exception e) {
		EFactoryActivator.getInstance().getLog().log(createErrorStatus(e));
	}

	private static IStatus createErrorStatus(Exception e) {
		return new Status(IStatus.ERROR, getPluginId(), e.getMessage(), e);
	}

	private static String getPluginId() {
		return EFactoryActivator.getInstance().getBundle().getSymbolicName();
	}

}
