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
package com.googlecode.efactory.building;

import org.eclipse.emf.ecore.EObject;

import com.googlecode.efactory.eFactory.Containment;
import com.googlecode.efactory.util.EcoreUtil3;

public class ContainmentBuilder extends FeatureBuilder {

	private Containment containment;

	public ContainmentBuilder(Containment containment) {
		this.containment = containment;
	}

	@Override
	public void build() throws ModelBuilderException {
		EObject newValue = getModelBuilder().build(containment.getValue());
		EcoreUtil3.setOrAddValue(getContainer(), getFeature().getEFeature(),
				newValue);

	}

}
