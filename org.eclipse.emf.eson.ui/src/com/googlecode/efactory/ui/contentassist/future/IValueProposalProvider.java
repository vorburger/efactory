/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Michael Vorburger - initial API and implementation
 ******************************************************************************/
package com.googlecode.efactory.ui.contentassist.future;


/**
 * Proposal Provider for a specific EFactory Value Type.
 *
 * @param <Type> e.g. String, BigDecimal, Date, or a com.ibm.icu.util.Currency etc.
 * 
 * @author Michael Vorburger
 */
public interface IValueProposalProvider<Type> {

	Type[] getProposals(/* EAttribute eAttribute ? */);
	
}
