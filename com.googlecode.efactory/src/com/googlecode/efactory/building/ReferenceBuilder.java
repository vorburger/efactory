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

import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.Reference;
import com.googlecode.efactory.util.EcoreUtil3;

public class ReferenceBuilder extends FeatureBuilder {

	private Reference reference;

	public ReferenceBuilder(Reference reference) {
		this.reference = reference;
	}

	@Override
	public void build() {
		EObject newValue = getReferencedObject();
		EcoreUtil3.setOrAddValue(getContainer(), getFeature().getEFeature(),
				newValue);
	}

	private EObject getReferencedObject() {
		EObject referencedObject = reference.getValue();
		if (referencedObject instanceof NewObject) {
			NewObject referenceToNewObject = (NewObject) referencedObject;
			return getModelBuilder().getCreatedObject(referenceToNewObject);
		} else {
			return referencedObject;
		}
	}

}
