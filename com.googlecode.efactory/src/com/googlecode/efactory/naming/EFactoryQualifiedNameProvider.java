/*******************************************************************************
 * Copyright (c) 2014 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.googlecode.efactory.eFactory.EFactoryPackage;

/**
 * IQualifiedNameProvider for ESON/EFactory.
 * 
 * The most important thing this does is to NOT expose our own internal EFactory
 * objects.. as we want only the "real" derived objects to be in the index
 * (scope) and referenced.
 *
 * EFactoryResourceDescriptionManager does something similar, but seemed to not suffice.
 * TODO Retest if using this approach we don't need a custom EFactoryResourceDescriptionManager anymore at all?!
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
