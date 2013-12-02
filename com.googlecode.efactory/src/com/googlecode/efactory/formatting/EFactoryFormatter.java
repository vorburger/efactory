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
package com.googlecode.efactory.formatting;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

import com.googlecode.efactory.services.EFactoryGrammarAccess;

/**
 * Xtext Formatter.
 * @author Michael Vorburger
 */
public class EFactoryFormatter extends AbstractDeclarativeFormatter {

	@Override
	protected void configureFormatting(FormattingConfig c) {
		EFactoryGrammarAccess f = (EFactoryGrammarAccess) getGrammarAccess();
		
		// NO c.setAutoLinewrap(...);
		
		c.setLinewrap(2).after(f.getPackageImportRule());
		c.setNoSpace().after(f.getPackageImportAccess().getEPackageAssignment_1());
		
		setIndentationIncrementAndDecrementAndLinewrapAfter(c,
				f.getNewObjectAccess().getLeftCurlyBracketKeyword_2(),
				f.getNewObjectAccess().getRightCurlyBracketKeyword_4());
		
		c.setNoSpace().after(f.getFeatureAccess().getEFeatureAssignment_0());
		c.setLinewrap().after(f.getFeatureRule()); // or better? c.setLinewrap().before(f.getFeatureAccess().getEFeatureEStructuralFeatureCrossReference_0_0());
		
		setIndentationIncrementAndDecrementAndLinewrapAfter(c,
				f.getMultiValueAccess().getLeftSquareBracketKeyword_1(),
				f.getMultiValueAccess().getRightSquareBracketKeyword_3());
		c.setLinewrap().after(f.getMultiValueAccess().getValuesAssignment_2());
		
		c.setNoSpace().after(f.getEnumAttributeAccess().getColonKeyword_0());
	}

	protected void setIndentationIncrementAndDecrementAndLinewrapAfter(FormattingConfig c, EObject increment, EObject decrement) {
		c.setIndentationIncrement().after(increment);
		c.setLinewrap().after(increment);
		
		c.setIndentationDecrement().before(decrement);
		c.setLinewrap().after(decrement);
	}
	
}
