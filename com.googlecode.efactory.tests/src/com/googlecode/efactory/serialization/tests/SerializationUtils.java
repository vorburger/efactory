/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger (http://www.vorburger.ch).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.googlecode.efactory.serialization.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.emf.ecore.resource.Resource;

public class SerializationUtils {
	private SerializationUtils() {
	}

	public static String toString(Resource eResource) throws IOException {
		OutputStream outputStream = new ByteArrayOutputStream();
		eResource.save(outputStream, null);
		return outputStream.toString();
	}
	
}
