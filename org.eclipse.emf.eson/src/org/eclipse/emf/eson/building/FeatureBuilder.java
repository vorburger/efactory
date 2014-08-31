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
package org.eclipse.emf.eson.building;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eson.eFactory.Feature;

public abstract class FeatureBuilder {

	private Feature feature;
	private EObject container;
	private ModelBuilder modelBuilder;

	Feature getFeature() {
		return feature;
	}

	EObject getContainer() {
		return container;
	}

	ModelBuilder getModelBuilder() {
		return modelBuilder;
	}

	public FeatureBuilder feature(Feature feature) {
		this.feature = feature;
		return this;
	}

	public FeatureBuilder container(EObject container) {
		this.container = container;
		return this;
	}

	public abstract void build() throws ModelBuilderException;

	public FeatureBuilder modelBuilder(ModelBuilder modelBuilder) {
		this.modelBuilder = modelBuilder;
		return this;
	}

}
