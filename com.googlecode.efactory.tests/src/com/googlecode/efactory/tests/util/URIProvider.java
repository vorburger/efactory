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
package com.googlecode.efactory.tests.util;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;

public final class URIProvider {

	private URIProvider() {
	}

	public static URI createURI(String relativePath) {
		URI uri;
		if (Platform.isRunning()) {
			String pluginId = TestConstants.PLUGIN_ID;
			uri = createURI(relativePath, pluginId);
		} else {
			uri = URI.createFileURI(new File(relativePath).getAbsolutePath());
		}
		return uri;
	}

	public static URI createURI(String pluginId, String relativePath) {
		URI uri;
		uri = URI.createPlatformPluginURI(pluginId + "/"
				+ relativePath, true);
		return uri;
	}

}
