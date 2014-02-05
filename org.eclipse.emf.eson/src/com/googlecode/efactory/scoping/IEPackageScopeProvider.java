/*******************************************************************************
 * Copyright (c) 2013 Yann Andenmatten.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Yann Andenmatten - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.scoping.IScope;

public interface IEPackageScopeProvider {

	IScope createEClassScope(Resource resource, EClass type, IScope parent);

	IScope createEClassScope(Resource resource, IScope parent);

	IScope createEPackageScope(Resource eResource, IScope parent);

}