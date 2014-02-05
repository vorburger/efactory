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
