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
import com.googlecode.efactory.eFactory.util.EFactorySwitch;

public class FeatureSwitch extends EFactorySwitch<FeatureBuilder> {
	@Override
	public FeatureBuilder caseContainment(Containment object) {
		return new ContainmentBuilder(object);
	}

	@Override
	public FeatureBuilder caseReference(Reference object) {
		return new ReferenceBuilder(object);
	}

	@Override
	public FeatureBuilder caseAttribute(Attribute object) {
		return new AttributeBuilder(object);
	}

	@Override
	public FeatureBuilder caseFeature(Feature object) {
		if (object.getValue() == null) {
			throw new IllegalStateException("Feature '"
					+ object.getEFeature().getName()
					+ "' has no value. This may result from a parsing error.");
		}
		return doSwitch(object.getValue());
	}
}
