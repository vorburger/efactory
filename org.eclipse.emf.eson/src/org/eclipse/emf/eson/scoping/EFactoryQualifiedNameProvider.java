/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eson.eFactory.EFactoryPackage;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * IQualifiedNameProvider for ESON/EFactory.
 * 
 * The most important thing this does is to NOT expose our own internal EFactory
 * objects.. as we want only the "real" derived objects to be in the index
 * (scope) and referenced.
 * 
 * @author Michael Vorburger
 */
public class EFactoryQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {
	// TODO make this more pluggable.. the core logic below (skip our own) should be "core", the actual naming resolution itself should be Guice pluggable?
	
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj.eClass().getEPackage().equals(EFactoryPackage.eINSTANCE)) {
			return null; // skip!
		} else {
			return super.getFullyQualifiedName(obj);
		}
	}
}
