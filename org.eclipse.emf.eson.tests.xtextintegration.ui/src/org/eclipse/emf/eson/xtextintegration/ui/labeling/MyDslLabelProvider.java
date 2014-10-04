/*
 * #%L
 * org.eclipse.emf.eson.tests.xtextintegration.ui
 * %%
 * Copyright (C) 2009 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.xtextintegration.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider; 
 
import com.google.inject.Inject;

public class MyDslLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public MyDslLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}
}
