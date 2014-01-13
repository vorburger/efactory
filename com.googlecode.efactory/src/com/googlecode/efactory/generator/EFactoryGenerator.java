/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

/**
 * JSON Generator (from ESON).
 * 
 * @author Michael Vorburger
 */
public class EFactoryGenerator implements IGenerator {

	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		if (isJSONGenerationActive()) {
			fsa.generateFile("yo.json", "Hello!"); // TODO do it for real... ;)
		}
	}

	protected boolean isJSONGenerationActive() {
		String sysProp = System.getProperty("eson2json");
		if (sysProp != null && sysProp.toLowerCase().equals("true"))
			return true;
		else
			return false;
	}
}
