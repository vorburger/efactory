/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - made more robust, handling inconsistent state during editing
 ******************************************************************************/
package com.googlecode.efactory.building;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.EcoreUtil2;

import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.MultiValue;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.eFactory.util.EFactorySwitch;
import com.googlecode.efactory.util.EcoreUtil3;

/**
 * Switch creating appropriate FeatureBuilder.
 * 
 * This checks if the requested Value actually makes sense for its Feature, and
 * returns null if it doesn't. This can happen during typing in editor - user
 * might not have typed {} yet, or user might have forgotten a [ ] or is
 * missing the = and so the Parser cannot create the correct Value; we
 * want the ModelBuider to ignore all these cases.
 */
public class FeatureSwitch extends EFactorySwitch<FeatureBuilder> {
	
	@Override
	public FeatureBuilder caseContainment(Containment object) {
		if (!EcoreUtil3.isEContainment(getEFeature(object)))
			return null;
		
		if (object.getValue() == null)
			return null;
		
		return new ContainmentBuilder(object);
	}

	@Override
	public FeatureBuilder caseReference(Reference object) {
		if (!EcoreUtil3.isEReference(getEFeature(object)))
			return null;
		
		if (object.getValue() == null)
			return null;
		
		return new ReferenceBuilder(object);
	}

	@Override
	public FeatureBuilder caseAttribute(Attribute object) {
		if (!EcoreUtil3.isEAttribute(getEFeature(object)))
			return null;

		return new AttributeBuilder(object);
	}

	@Override
	public FeatureBuilder caseMultiValue(MultiValue object) {
		EStructuralFeature eFeature = getEFeature(object);
		if (eFeature == null || !eFeature.isMany())
			return null; 

		return new MultiValueBuilder(object);
	}

	@Override
	public FeatureBuilder caseFeature(Feature object) {
		final Value value = object.getValue();
		if (value == null) {
			return null;
		}
		return doSwitch(value);
	}
	
	private @Nullable EStructuralFeature getEFeature(Value value) {
		final Feature containingFeature = EcoreUtil2.getContainerOfType(value, Feature.class);
		if ( containingFeature != null )
			return containingFeature.getEFeature();
		return null;
	}
}
