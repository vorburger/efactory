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
package com.googlecode.efactory.scoping;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.xtext.scoping.impl.MultimapBasedSelectable;

import com.google.inject.Inject;

/**
 * ImportedNamespaceAwareLocalScopeProvider which handles some things differently.
 * 
 * @author Michael Vorburger
 */
public class EFactoryImportedNamespaceAwareScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Inject
	// Has to be repeated because it's private in ImportedNamespaceAwareLocalScopeProvider and no getQualifiedNameConverter() :(
	// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=409003
	private IQualifiedNameConverter nameConverter;

	/**
	 * Local elements should be referable by their simple name
	 */
	@Override
	protected ISelectable internalGetAllDescriptions(final Resource resource) {
		Iterable<EObject> allContents = new Iterable<EObject>(){
			public Iterator<EObject> iterator() {
				return EcoreUtil.getAllContents(resource, false);
			}
		}; 
		Iterable<IEObjectDescription> allDescriptions = Scopes.scopedElementsFor(allContents);
		return new MultimapBasedSelectable(allDescriptions);
	}	

//	/**
//	 * We override this because in our grammar, the importedNamespace is of type EPackage instead of a String.
//	 */
//	@Override
//	protected List<ImportNormalizer> internalGetImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
//	    if (!(context instanceof Factory))
//	    	return Collections.emptyList();
//	    Factory factory = (Factory)context;
//	    List<ImportNormalizer> importedNamespaceResolvers = Lists.newArrayList();
//	    
//	    for (PackageImport anImport : factory.getEpackages()) {
//	    	// Usually, we CANNOT do getEPackage() etc. as below here, 
//	    	// because that would lead to a ""Cyclic resolution of lazy links".. so, instead we first try:
//	    	List<INode> nodes = NodeModelUtils.findNodesForFeature(anImport, EFactoryPackage.Literals.PACKAGE_IMPORT__EPACKAGE);
//	    	final String packageName;
//	    	if (!nodes.isEmpty()) {
//		        INode node = nodes.get(0);
//		        packageName = NodeModelUtils.getTokenText(node);
//	    	} else {
//	    		// no ICompositeNode adaptor on PackageImport.. can happen during SerializationTest
//	    		// let's try if it's actually available "normally" anyways
//	    		EPackage ePackage = anImport.getEPackage();
//		        QualifiedName packageQN = getQualifiedNameProvider().getFullyQualifiedName(ePackage);
//		        packageName = nameConverter.toString(packageQN);
//	    	}
//			importedNamespaceResolvers.add(createImportedNamespaceResolver(packageName, ignoreCase));
//		}
//	    return importedNamespaceResolvers;
//	}
//	  
//	// copy/pasted from ImportedNamespaceAwareLocalScopeProvider v2.4, so that this compiles on Xtext 2.3 as well
//	protected ImportNormalizer doCreateImportNormalizer(QualifiedName importedNamespace, boolean wildcard, boolean ignoreCase) {
//		return new ImportNormalizer(importedNamespace, wildcard, ignoreCase);
//	}
//	
//	/**
//	 * We override this because in our grammar, the trailing .* isn't part of the rule, so that we can use a reference.  
//	 */
//	@Override
//	protected ImportNormalizer createImportedNamespaceResolver(final String namespace, boolean ignoreCase) {
//		if (Strings.isEmpty(namespace))
//			return null;
//		QualifiedName importedNamespace = nameConverter.toQualifiedName(namespace);
//		if (importedNamespace == null || importedNamespace.getSegmentCount() < 1) {
//			return null;
//		}
//		// We know our language has a wildcard, but it's in the Grammar (not in Rule)
//		// instead of being part of the namespace String, so this can be simplified, 
//		// and should not use skipLast(1)
//		return doCreateImportNormalizer(importedNamespace, true, ignoreCase);
//	}

}
