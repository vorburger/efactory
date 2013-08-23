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
package com.googlecode.efactory.serialization;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.util.concurrent.IWriteAccess;

import com.google.inject.Provider;
import com.googlecode.efactory.building.NameAccessor;
import com.googlecode.efactory.building.NoNameFeatureMappingException;
import com.googlecode.efactory.eFactory.EFactoryFactory;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * EMF Adapter (Observer) which get notified by the observed derived "real"
 * EObjects so that it can "re-sychronize" changes made to them to the source
 * EFactory model.
 * 
 * This is in the serialization package because, while it has nothing to do with
 * Serialization per se, it can use a lot of the infrastructure in this package.
 * Serialization is actually simply a more "coarse grained" (entire NewObject,
 * not just individual features of [possibly sub-] NewObjects) application of
 * the same "re-sychronization" requirement.
 * 
 * @author Michael Vorburger
 */
public class EFactoryAdapter extends EContentAdapter {
	// private static Logger logger = Logger.getLogger(EFactoryAdapter.class);
	
	// Provider<> is used to keep this lazy - at the time this is constructed, it might not be available, yet
	protected final Provider<IWriteAccess<XtextResource>> writeAccessProvider;

	protected NameAccessor nameAccessor = new NameAccessor();
	
	public EFactoryAdapter(Provider<IWriteAccess<XtextResource>> writeAccessProvider) {
		this.writeAccessProvider = writeAccessProvider;
	}

	@Override
	public void notifyChanged(Notification msg) {
		super.notifyChanged(msg); // MUST do first

		if (msg.isTouch())
			return;

		final EObject eNotifier = (EObject) msg.getNotifier();
		if (eNotifier == null)
			throw new IllegalArgumentException("Huh, Notification without notifier?! " + msg.toString());
		
		final NewObject newObject = getChangedNewObject(eNotifier);
		if (newObject == null) // TODO once auto-creation is implemented, this should never be null anymore
			return;
		
		Feature factoryFeature = getChangedFactoryFeature(msg, newObject);
		if (factoryFeature == null) {
			// The NewObject doesn't have a dedicated name Feature, so try to set name property of NewObject
			if (handledAsNameChange(msg, eNotifier, newObject)) {
				return;
			} else {
				factoryFeature = newFactoryFeature(msg, newObject);
			}
		}
		
		try {
			switch (msg.getEventType()) {
				case Notification.SET :
					setOrRemoveValue(factoryFeature, msg);
					break;
			}
		} catch (NoNameFeatureMappingException e) {
			// ignore (TODO rework later? shouldn't be throw exception at all, instead use is..() check method pattern)
			// NO logger.warn("NoNameFeatureMappingException in notifyChanged() handling for: " + msg.toString(), e);
		}
	}

	protected boolean handledAsNameChange(Notification msg, EObject eNotifier, NewObject newObject) {
		if (msg.getEventType() != Notification.SET  &&  msg.getEventType() != Notification.UNSET)
			return false;
		Factory contextFactory = getEFactoryResource(eNotifier).getEFactoryFactory();
		try {
			EAttribute nameAttribute = nameAccessor.getNameAttribute(contextFactory, eNotifier);
			if (nameAttribute.equals(msg.getFeature())) {
				String newName = msg.getNewStringValue();
				newObject.setName(newName);
				return true;
			} else
				return false;
		} catch (NoNameFeatureMappingException e) {
			return false;
		}
	}

	protected void setOrRemoveValue(final Feature factoryFeature, final Notification msg) throws NoNameFeatureMappingException {
		if (msg.getNewValue() != null)
			setValue(factoryFeature, msg);
		else
			removeValue(factoryFeature, msg);
	}
	
	// setOrAddValue ?
	protected void setValue(final Feature factoryFeature, final Notification msg) throws NoNameFeatureMappingException {
		final EFactoryResource resource = getEFactoryResource(msg);
		final IFactoryBuilder factoryBuilder = new FactoryBuilder2(resource);
		FeatureBuilder builder;
		final EStructuralFeature eFeature = factoryFeature.getEFeature();
		if (eFeature instanceof EAttribute) {
			final EAttribute eAttribute = (EAttribute) eFeature;
			builder = AttributeBuilder.attribute(eAttribute, factoryBuilder).value(msg.getNewValue());
		} else if (eFeature instanceof EReference) {
			final EReference eReference = (EReference) eFeature;
			if (eReference.isContainment()) {
				builder = ContainmentBuilder.containment(eReference, factoryBuilder).value(msg.getNewValue());
			} else {
				builder = ReferenceBuilder.reference(eReference, factoryBuilder).value(msg.getNewValue());
			}
		} else {
			throw new IllegalArgumentException("Huh, WTF is an EStructuralFeature that is neither an EAttribute nor an EReference?! " + eFeature.toString());
		}
		final Value newEFValue = builder.createValue(); // OR: builder.build(); // if the Features doesn't exist yet..
		
		// do NOT just: factoryFeature.setValue(newEFValue); // @see http://koehnlein.blogspot.ch/2010/06/semantic-model-access-in-xtext.html
		final String uriFragment = factoryFeature.eResource().getURIFragment(factoryFeature);
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final Feature localFactoryFeature = (Feature) resource.getEObject(uriFragment);
				localFactoryFeature.setValue(newEFValue);
			}
		});
	}
	
	protected void removeValue(Feature factoryFeature, Notification msg) {
		NewObject newObject = (NewObject) factoryFeature.eContainer();
		EList<Feature> newObjectFeatures = newObject.getFeatures();
		newObjectFeatures.remove(factoryFeature);
	}
		
	protected @Nullable Feature getChangedFactoryFeature(final Notification msg, final NewObject newObject) {
		final EStructuralFeature changedEFeature = (EStructuralFeature) msg.getFeature();
		final EList<Feature> newObjectAllFeatures = newObject.getFeatures();
		for (Feature feature : newObjectAllFeatures) {
			if (changedEFeature.equals(feature.getEFeature())) {
				return feature;
			}
		}
		return null;
	}

	private @NonNull Feature newFactoryFeature(Notification msg, NewObject newObject) {
		final Feature newFeature = EFactoryFactory.eINSTANCE.createFeature();
		final EStructuralFeature changedEFeature = (EStructuralFeature) msg.getFeature();
		newFeature.setEFeature(changedEFeature);
		// TODO handle newFeature.setIsMany() ! but do that LATER, after I've changed syntax from += to []
		newObject.getFeatures().add(newFeature);
		return newFeature;
	}

	// TODO this is wrong actually - later it has to work for EObject which are not yet in the Factory, too.. so create them on the fly, if needed @see com.googlecode.efactory.builder.resync.tests.BuilderResyncTest.testSetNewFeature()
	// TODO @NonNull
	protected @Nullable NewObject getChangedNewObject(EObject eNotifier) {
		return getEFactoryResource(eNotifier).getEFactoryNewObject(eNotifier);
	}

	protected @NonNull EFactoryResource getEFactoryResource(Notification msg) {
		final EObject eNotifier = (EObject) msg.getNotifier();
		return getEFactoryResource(eNotifier);
	}

	@SuppressWarnings("null") // assuming EObject eNotifier is ALWAYS in a EResource
	protected @NonNull EFactoryResource getEFactoryResource(EObject eNotifier) {
		final Resource eNotifierResource = eNotifier.eResource();
		return (EFactoryResource) eNotifierResource;
	}
	
}
