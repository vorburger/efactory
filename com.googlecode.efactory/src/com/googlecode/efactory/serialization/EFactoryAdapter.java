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
import com.googlecode.efactory.eFactory.MultiValue;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * EMF Adapter (Observer) which gets notified by the observed derived "real"
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
					setOrRemoveSingleValue(factoryFeature, msg);
					break;
				case Notification.ADD :
					addListValue(factoryFeature, msg);
					break;
				case Notification.REMOVE :
					removeListValue(factoryFeature, msg);
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

	protected void setOrRemoveSingleValue(final Feature factoryFeature, final Notification msg) throws NoNameFeatureMappingException {
		if (factoryFeature.getEFeature().isMany())
			// Notification.SET should never happen for lists
			throw new IllegalArgumentException(); 
		if (msg.getNewValue() != null)
			setSingleValue(factoryFeature, msg);
		else
			removeSingleValueFeature(factoryFeature, msg);
	}
	
	public void setRootNewObject(final EObject eObject) {
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource xResource) throws Exception {
				// final EFactoryResource resource = getEFactoryResource(eObject);
				EFactoryResource fResource = (EFactoryResource) xResource;
				final Factory factory = fResource.getEFactoryFactory();
				if (factory == null)
					throw new IllegalStateException();
				FactoryBuilder2 factoryBuilder = new FactoryBuilder2(fResource);
				NewObjectBuilder builder = NewObjectBuilder.context(factory, factoryBuilder);
				NewObject newObject = builder.build(eObject);
				factory.setRoot(newObject);
			}
		});
	}
	
	protected void setSingleValue(final Feature factoryFeature, final Notification msg) throws NoNameFeatureMappingException {
		// do NOT just: factoryFeature.setValue(value); // @see http://koehnlein.blogspot.ch/2010/06/semantic-model-access-in-xtext.html
		final String uriFragment = factoryFeature.eResource().getURIFragment(factoryFeature);
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final Value value = getNewValue(factoryFeature, msg);
				final Feature localFactoryFeature = (Feature) resource.getEObject(uriFragment);
				localFactoryFeature.setValue(value);
			}
		});
	}

	protected Value getNewValue(final Feature factoryFeature, final Notification msg) {
		final EFactoryResource resource = getEFactoryResource(msg);
		final IFactoryBuilder factoryBuilder = new FactoryBuilder2(resource);
		final EStructuralFeature eFeature = factoryFeature.getEFeature();
		final Value value = FeatureBuilderFactory.createValue(eFeature, factoryBuilder, msg.getNewValue());
		return value;
	}

	protected void addListValue(final Feature factoryFeature, final Notification msg) {
		final String uriFragment = factoryFeature.eResource().getURIFragment(factoryFeature);
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final Value value = getNewValue(factoryFeature, msg);
				final Feature localFactoryFeature = (Feature) resource.getEObject(uriFragment);
				final MultiValue multiValue = (MultiValue) localFactoryFeature.getValue();
				multiValue.getValues().add(value);
			}
		});
	}

	protected void removeListValue(Feature factoryFeature, final Notification msg) {
		final String uriFragment = factoryFeature.eResource().getURIFragment(factoryFeature);
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final Feature localFactoryFeature = (Feature) resource.getEObject(uriFragment);
				final MultiValue multiValue = (MultiValue) localFactoryFeature.getValue();
				final int index = msg.getPosition();
				multiValue.getValues().remove(index);
			}
		});
	}
	
	protected void removeSingleValueFeature(final Feature factoryFeature, final Notification msg) {
		final String uriFragment = factoryFeature.eResource().getURIFragment(factoryFeature);
		writeAccessProvider.get().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final Feature localFactoryFeature = (Feature) resource.getEObject(uriFragment);
				NewObject newObject = (NewObject) localFactoryFeature.eContainer();
				EList<Feature> newObjectFeatures = newObject.getFeatures();
				newObjectFeatures.remove(localFactoryFeature);
			}
		});
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
		newObject.getFeatures().add(newFeature);
		if (changedEFeature.isMany()) {
			MultiValue multiValue = EFactoryFactory.eINSTANCE.createMultiValue();
			newFeature.setValue(multiValue);
		}
		return newFeature;
	}

	protected @NonNull NewObject getChangedNewObject(EObject eNotifier) {
		return getEFactoryResource(eNotifier).getExistingEFactoryNewObject(eNotifier);
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
