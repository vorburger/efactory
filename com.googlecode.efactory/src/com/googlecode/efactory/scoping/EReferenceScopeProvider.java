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
/**
 * <copyright>
 *
 * Copyright (c) 2002-2006 Sebastian Benz and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Sebastian Benz - Initial API and implementation
 *
 * </copyright>
 *
 * 
 */
package com.googlecode.efactory.scoping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;
import com.googlecode.efactory.building.NameAccessor;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Import;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.PackageImport;
import com.googlecode.efactory.util.Resources;

public class EReferenceScopeProvider {

	@Inject
	private NameAccessor nameAccessor;

	public IScope get(Resource resource, EClass type) {
		Factory factory = Resources.getRoot(resource, Factory.class);
		return new EFactoryScope(factory, initElements(factory, resource, type));
	}

	private List<IEObjectDescription> initElements(Factory factory,
			Resource resource, EClass type) {

		Resource context = factory.eResource();
		List<IEObjectDescription> elementList = getNewObjects(type,
				factory.eResource());
		elementList.addAll(getImports(factory, context, type));

		return elementList;
	}
	
	private List<IEObjectDescription> getImports(Factory factory,
			Resource context, EClass type) {
		List<IEObjectDescription> elementList = new LinkedList<IEObjectDescription>();
		final Set<String> uniqueImportURIs = new HashSet<String>(10);
		final List<String> orderedImportURIs = new ArrayList<String>(10);
		for (Import imp : factory.getImports()) {

			String uri = imp.getImportURI();
			if (uri != null && uniqueImportURIs.add(uri)
					&& EcoreUtil2.isValidUri(factory, URI.createURI(uri))) {
				orderedImportURIs.add(uri);
			}
		}

		for (PackageImport imp : factory.getEpackages()) {
			String uri = imp.getEPackage().getNsURI();
			if (uri != null && uniqueImportURIs.add(uri)) {
				orderedImportURIs.add(uri);
			}
		}

		for (String uri : orderedImportURIs) {
			Resource importedResource = getResource(context, uri);
			Iterator<EObject> iter = importedResource.getAllContents();
			while (iter.hasNext()) {
				EObject eObject = iter.next();

				if (eObject instanceof NewObject) {
					NewObject newObject = (NewObject) eObject;
					if (isAssignable(type, newObject)
							&& newObject.getName() != null) {
						elementList.add(EObjectDescription.create(
								newObject.getName(), eObject));
					}
				} else if (EcoreUtil2.isAssignableFrom(type, eObject.eClass())) {
					try {
						String name = nameAccessor.getName(factory, eObject);
						if (name != null) {
							elementList.add(EObjectDescription.create(name,
									eObject));
						}
					} catch (Exception e) {
						// skip element
					}
				}
			}
		}
		return elementList;
	}

	private Resource getResource(Resource context, String uri) {
		return EcoreUtil2.getResource(context, uri);
	}
	
	private List<IEObjectDescription> getNewObjects(EClass type,
			Resource resource) {
		List<IEObjectDescription> elementList = new ArrayList<IEObjectDescription>();
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator
				.hasNext();) {
			EObject element = iterator.next();
			if (element instanceof NewObject) {
				NewObject newObject = (NewObject) element;
				if (isAssignable(type, newObject)) {
					String name = SimpleAttributeResolver.NAME_RESOLVER
							.apply(element);
					if (name != null) {
						elementList.add(EObjectDescription
								.create(name, element));
					}
				}
			}
		}
		return elementList;
	}

	private boolean isAssignable(EClass type, NewObject newObject) {
		return EcoreUtil2.isAssignableFrom(type, newObject.getEClass());
	}

}
