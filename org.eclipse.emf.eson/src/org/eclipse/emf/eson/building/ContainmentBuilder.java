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
package org.eclipse.emf.eson.building;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eson.util.EcoreUtil3;

import org.eclipse.emf.eson.eFactory.Containment;

public class ContainmentBuilder extends FeatureBuilder {

	private Containment containment;

	public ContainmentBuilder(Containment containment) {
		this.containment = containment;
	}

	@Override
	public void build() throws ModelBuilderException {
		EObject newValue = getModelBuilder().build(containment.getValue());
		final EStructuralFeature eFeature = getFeature().getEFeature();
		if (eFeature.eIsProxy())
			return;
		EcoreUtil3.setOrAddValue(getContainer(), eFeature, newValue);
	}

}
