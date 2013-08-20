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
package com.googlecode.efactory.building.resync;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.StringAttribute;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.resource.EFactoryResource;

/**
 * EMF Adapter (Observer) which get notified by the observed derived "real"
 * EObjects so that it can "re-sychronize" changes made to them to the source
 * EFactory model.
 * 
 * @author Michael Vorburger
 */
public class EFactoryAdapter extends EContentAdapter {

	@Override
	public void notifyChanged(Notification msg) {
		super.notifyChanged(msg); // MUST do first
		System.out.println(msg);

		if (msg.isTouch())
			return;
		
		final NewObject newObject = findChangedNewObject(msg);
		if (newObject == null)
			return;
		
		final Feature factoryFeature = findChangedFactoryFeature(msg, newObject);
		if (factoryFeature == null)
			return;
		
		switch (msg.getEventType()) {
			case Notification.SET:
				setValue(factoryFeature, msg);
				break;
		}
	}

	// setOrAddValue ?
	protected void setValue(Feature factoryFeature, Notification newValue) {
		Value value = factoryFeature.getValue();
		if (value instanceof StringAttribute) {
			StringAttribute string = (StringAttribute) value;
			string.setValue(newValue.getNewStringValue());
		}
	}
		
	protected Feature findChangedFactoryFeature(final Notification msg, final NewObject newObject) {
		final Object changedFeature = msg.getFeature();
		final EStructuralFeature changedEFeature = (EStructuralFeature) changedFeature;
		final EList<Feature> newObjectAllFeatures = newObject.getFeatures();
		// TODO could use org.eclipse.xtext.util.SimpleCache<Key, Value> to cache result of this search loop 
		for (Feature feature : newObjectAllFeatures) {
			if (changedEFeature.equals(feature.getEFeature())) {
				return feature;
			}
		}
		// TODO this is wrong actually - it has to work for features which are not yet in the Factory, too.. so create them on the fly, if needed @see com.googlecode.efactory.builder.resync.tests.BuilderResyncTest.testSetNewFeature()
		return null;
	}

	protected NewObject findChangedNewObject(Notification msg) {
		final Object notifier = msg.getNotifier();
		final EObject eNotifier = (EObject) notifier;
		final Resource eNotifierResource = eNotifier.eResource();
		final EFactoryResource eNotifierFactoryResource = (EFactoryResource) eNotifierResource;  
		final NewObject newObject = eNotifierFactoryResource.getEFactoryElement(eNotifier);
		return newObject;
	}
	
}
