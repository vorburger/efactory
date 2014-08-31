/*
 * #%L
 * org.eclipse.emf.eson.tests
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
package org.eclipse.emf.eson.tests.util;

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
			uri = createURI(pluginId, relativePath);
		} else {
			uri = URI.createFileURI(new File(relativePath).getAbsolutePath());
		}
		return uri;
	}

	public static URI createURI(String pluginId, String relativePath) {
		URI uri;
		uri = URI.createPlatformPluginURI(pluginId + "/" + relativePath, true);
		return uri;
	}

}
