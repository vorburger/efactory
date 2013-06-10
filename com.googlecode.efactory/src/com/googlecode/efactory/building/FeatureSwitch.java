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
package com.googlecode.efactory.building;

import com.googlecode.efactory.eFactory.Attribute;
import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.eFactory.Value;
import com.googlecode.efactory.eFactory.util.EFactorySwitch;

public class FeatureSwitch extends EFactorySwitch<FeatureBuilder> {
	@Override
	public FeatureBuilder caseContainment(Containment object) {
		if (object.getValue() != null)
			return new ContainmentBuilder(object);
		else
			// This can happen during typing in editor - user might not have typed {} yet
			return null;
	}

	@Override
	public FeatureBuilder caseReference(Reference object) {
		if (object.getValue() != null)
			return new ReferenceBuilder(object);
		else
			// dito, see above
			return null;
	}

	@Override
	public FeatureBuilder caseAttribute(Attribute object) {
		return new AttributeBuilder(object);
	}

	@Override
	public FeatureBuilder caseFeature(Feature object) {
		final Value value = object.getValue();
		if (value == null) {
			return null;
		}
		return doSwitch(value);
	}
}
