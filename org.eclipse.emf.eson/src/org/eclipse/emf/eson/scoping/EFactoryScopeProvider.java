/*
 * #%L
 * org.eclipse.emf.eson
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
package org.eclipse.emf.eson.scoping;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eson.util.EcoreUtil3;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.FilteringScope;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import org.eclipse.emf.eson.eFactory.Attribute;
import org.eclipse.emf.eson.eFactory.CustomNameMapping;
import org.eclipse.emf.eson.eFactory.EnumAttribute;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.Feature;
import org.eclipse.emf.eson.eFactory.MultiValue;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.eclipse.emf.eson.eFactory.PackageImport;
import org.eclipse.emf.eson.eFactory.Reference;

public class EFactoryScopeProvider extends AbstractDeclarativeScopeProvider {

	@Inject
	private IEPackageScopeProvider ePackageScopeProvider;

	public IScope scope_PackageImport_ePackage(PackageImport packageImport, EReference eReference) {
		final IScope parent = delegateGetScope(packageImport, eReference);
		return ePackageScopeProvider.createEPackageScope(packageImport.eResource(), parent);
	}
	
	public IScope scope_NewObject_eClass(Factory factory, EReference eReference) {
		final IScope parent = delegateGetScope(factory, eReference);
		return ePackageScopeProvider.createEClassScope(factory.eResource(), parent);
	}

	public IScope scope_EnumAttribute_value(EnumAttribute attribute, EReference reference) {
		Feature feature = getFeature(attribute);
		if (feature.getEFeature().getEType() instanceof EEnum) {
			EEnum enumType = (EEnum) feature.getEFeature().getEType();
			Iterable<IEObjectDescription> elements = Scopes.scopedElementsFor(enumType.getELiterals());
			return new SimpleScope(elements);
		}
		return IScope.NULLSCOPE;
	}

	// Feature == Containment here, always, is it?
	public IScope scope_NewObject_eClass(Feature feature, EReference eReference) {
		if (feature.getEFeature() instanceof EReference) {
			final IScope parent = delegateGetScope(feature, eReference);
			return ePackageScopeProvider.createEClassScope(feature.eResource(), (EClass) feature.getEFeature().getEType(), parent);
		} else
			return IScope.NULLSCOPE;
			
	}

	// This may look a bit strange, but is required for 
	// org.eclipse.emf.eson.ui.contentassist.EFactoryProposalProvider.completeFeature_EFeature()
	public IScope scope_Feature_eFeature(NewObject newObject, EReference reference) {
		EClass eClass = newObject.getEClass();
		Iterable<? extends EObject> assignableFeature = EcoreUtil3.getAssignableFeatures(eClass);
		return new SimpleScope(Scopes.scopedElementsFor(assignableFeature));
	}
	public IScope scope_Feature_eFeature(Feature feature, EReference reference) {
		NewObject newObject = (NewObject) feature.eContainer();
		return scope_Feature_eFeature(newObject, reference);
	}

	public IScope scope_Feature_reference(NewObject newObject, EReference reference) {
		if (newObject.getEClass() == null) {
			return IScope.NULLSCOPE;
		}
		return new SimpleScope(Scopes.scopedElementsFor(newObject.getEClass().getEAllStructuralFeatures()));
	}

	public IScope scope_NewObject_eClass(Reference reference, EReference eReference) {
		return IScope.NULLSCOPE;
	}

	public IScope scope_Reference_value(Feature feature, EReference eReference) {
		EStructuralFeature sourceFeature = feature.getEFeature();
		if (EcoreUtil3.isEReference(sourceFeature)) {
			EReference realEReference = (EReference) sourceFeature;
			IScope parentScope = delegateGetScope(feature, realEReference);
			final EClass referenceType = realEReference.getEReferenceType();
			return new FilteringScope(parentScope, new Predicate<IEObjectDescription>() {
				public boolean apply(IEObjectDescription desc) {
					return referenceType.isSuperTypeOf(desc.getEClass());
				}
			});
		}
		return IScope.NULLSCOPE;
	}

	public IScope scope_CustomNameMapping_nameFeature(
			CustomNameMapping mapping, EReference reference) {
		Iterable<EAttribute> attributes = EcoreUtil3.getAllAttributes(
				mapping.getEClass(), String.class);
		Iterable<IEObjectDescription> elements = Scopes
				.scopedElementsFor(attributes);
		return new SimpleScope(elements);
	}

	public IScope scope_CustomNameMapping_eClass(EObject context, EReference eReference) {
		final IScope parent = delegateGetScope(context, eReference);
		return ePackageScopeProvider.createEClassScope(context.eResource(), parent);
	}

	public IScope scope_Containment_value(Feature feature, EReference eReference) {
		if (EcoreUtil3.isEContainment(feature.getEFeature())) {
			return super.getDelegate().getScope(feature, eReference);
		}
		return IScope.NULLSCOPE;
	}

	protected Feature getFeature(Attribute attribute) {
		 EObject container = attribute.eContainer();
		 if (container instanceof MultiValue) {
			 // MultiValue mv = (MultiValue) container;
			 container = container.eContainer();
		 }
		 return (Feature) container; 
	}
}
