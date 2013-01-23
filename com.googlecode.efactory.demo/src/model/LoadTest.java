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
package model;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.googlecode.efactory.EFactoryStandaloneSetup;
import com.googlecode.efactory.examples.entity.EntityPackage;
import com.googlecode.efactory.examples.entity.Model;

public class LoadTest extends TestCase {

	protected void setUp() throws Exception {

	}

	public void testLoad() throws Exception {

		/* ---- Only in standalone mode --- */
		EFactoryStandaloneSetup.doSetup();
		EntityPackage.eINSTANCE.getClass();
		/* --------------------------------- */

		ResourceSet rs = new XtextResourceSet();
		Resource r = rs.getResource(URI.createURI("model/Entity.efactory"),
				true);
		EObject content = r.getContents().get(0);
		assertTrue(content instanceof Model);
	}

}
