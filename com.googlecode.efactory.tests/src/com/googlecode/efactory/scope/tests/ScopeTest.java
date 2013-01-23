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
package com.googlecode.efactory.scope.tests;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.googlecode.efactory.tests.util.AbstractEFactoryTest;

public abstract class ScopeTest extends AbstractEFactoryTest {

	protected void checkScopeType(IScope scope, EClass filter) {
		for (IEObjectDescription element : scope.getAllContents()) {
			EObject scopedElement = element.getEObjectOrProxy();
			assertTrue(filter.isInstance(scopedElement));
		}
	}

	protected void checkScopeContains(IScope result, EObject object) {
		for (IEObjectDescription element : result.getAllContents()) {
			System.out.println(element.getName());
			if (element.getEObjectOrProxy() == object) {
				return;
			}
		}
		fail("Object not in scope " + object);
	}

}
