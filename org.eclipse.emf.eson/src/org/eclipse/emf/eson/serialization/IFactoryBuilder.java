/*
 * #%L
 * org.eclipse.emf.eson
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
package org.eclipse.emf.eson.serialization;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eson.eFactory.NewObject;

/**
 * FactoryBuilder can turn an EObject into a NewObject.
 * 
 * Maybe the NewObject already existed and is just being found and returned,
 * or maybe an entirely new one is being created.
 * 
 * @author Michael Vorburger
 */
public interface IFactoryBuilder {

	NewObject getOrBuildNewObject(EObject eObject);

}