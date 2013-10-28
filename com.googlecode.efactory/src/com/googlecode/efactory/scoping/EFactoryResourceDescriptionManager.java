/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.scoping;

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.resource.impl.EObjectDescriptionLookUp;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * DerivedStateAwareResourceDescriptionManager which only exports
 * a resource's derrived getContent.get(1) and skips the original get(0).
 * 
 * @author Michael Vorburger
 */
public class EFactoryResourceDescriptionManager	extends	DerivedStateAwareResourceDescriptionManager {
	private final static Logger log = Logger.getLogger(EFactoryResourceDescriptionManager.class);

	// TODO ugly, remove once DerivedStateAwareResourceDescriptionManager's cache is protected..
	@Inject
	private IResourceScopeCache cache = IResourceScopeCache.NullImpl.INSTANCE;
	
	@Override
	protected IResourceDescription createResourceDescription(Resource resource, final IDefaultResourceDescriptionStrategy strategy) {
		return new DefaultResourceDescription(resource, strategy, cache) {
			@Override
			protected EObjectDescriptionLookUp getLookUp() {
				if (lookup == null)
					lookup = new EObjectDescriptionLookUp(computeExportedObjects());
				return lookup;
			}
			
			@Override
			protected List<IEObjectDescription> computeExportedObjects() {
				if (!getResource().isLoaded()) {
					try {
						getResource().load(null);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						return Collections.<IEObjectDescription> emptyList();
					}
				}
				final List<IEObjectDescription> exportedEObjects = newArrayList();
				IAcceptor<IEObjectDescription> acceptor = new IAcceptor<IEObjectDescription>() {
					public void accept(IEObjectDescription eObjectDescription) {
						exportedEObjects.add(eObjectDescription);
					}
				};
				// TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(getResource(), false);
				TreeIterator<EObject> allProperContents = getContents(getResource(), false);
				while (allProperContents.hasNext()) {
					EObject content = allProperContents.next();
					if (!strategy.createEObjectDescriptions(content, acceptor))
						allProperContents.prune();
				}
				return exportedEObjects;
			}
			
			@Override
			protected List<IReferenceDescription> computeReferenceDescriptions() {
				final List<IReferenceDescription> referenceDescriptions = Lists.newArrayList();
				IAcceptor<IReferenceDescription> acceptor = new IAcceptor<IReferenceDescription>() {
					public void accept(IReferenceDescription referenceDescription) {
						referenceDescriptions.add(referenceDescription);
					}
				};
				EcoreUtil2.resolveLazyCrossReferences(getResource(), CancelIndicator.NullImpl);
				Map<EObject, IEObjectDescription> eObject2exportedEObjects = createEObject2ExportedEObjectsMap(getExportedObjects());
				// TreeIterator<EObject> contents = EcoreUtil.getAllProperContents(getResource(), true);
				TreeIterator<EObject> contents = getContents(getResource(), true);
				while (contents.hasNext()) {
					EObject eObject = contents.next();
					URI exportedContainerURI = findExportedContainerURI(eObject, eObject2exportedEObjects);
					if (!strategy.createReferenceDescriptions(eObject, exportedContainerURI, acceptor))
						contents.prune();
				}
				return referenceDescriptions;
			}

			protected TreeIterator<EObject> getContents(Resource resource, boolean resolve) {
				// return EcoreUtil.getAllProperContents(resource, false);
				EObject realRootEObject = EFactoryResource.getEFactoryEObject(resource);
				if (realRootEObject != null) {
					return EcoreUtil.getAllProperContents(realRootEObject, false);
				} else {
					return EMPTY_EOBJECT_TREE_ITERATOR;
				}
			}

		};

	}
	
	private static TreeIterator<EObject> EMPTY_EOBJECT_TREE_ITERATOR = new EmptyTreeIterator<EObject>();
	private static class EmptyTreeIterator<E> implements TreeIterator<E> {

		public boolean hasNext() {
			return false;
		}

		public E next() {
			return null;
		}

		public void remove() {
		}

		public void prune() {
		}
		
	}
}
