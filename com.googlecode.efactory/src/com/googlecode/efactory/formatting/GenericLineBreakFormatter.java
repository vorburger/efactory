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

package com.googlecode.efactory.formatting;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

import com.googlecode.efactory.eFactory.EFactoryPackage;

public class GenericLineBreakFormatter {

	private static final String EQUALS = "="; //$NON-NLS-1$
	private static final String COLON = "."; //$NON-NLS-1$

	public void apply(FormattingConfig c, List<ParserRule> parserRules) {
		for (ParserRule parserRule : parserRules) {
			if (isComplexRule(parserRule) && isValid(parserRule)) {
				Keyword keyword = getFirstKeyword(parserRule);
				if (canGenericRuleBeApplied(keyword)) {
					c.setLinewrap().before(keyword);
				} else {
					if (checkIllegalType(parserRule,
							EFactoryPackage.Literals.FEATURE)) {
						c.setLinewrap().after(parserRule);
					}
				}
			}
		}
	}

	private boolean isValid(ParserRule parserRule) {

		EClass expectedType = EFactoryPackage.eINSTANCE.getAttribute();
		return checkIllegalType(parserRule, expectedType);
	}

	private boolean checkIllegalType(ParserRule parserRule, EClass expectedType) {
		EClassifier type = parserRule.getType().getClassifier();
		if (type instanceof EClass) {
			EClass eClass = (EClass) type;
			if (eClass.getEAllSuperTypes().contains(expectedType)) {
				return false;
			}

		}
		return true;
	}

	private boolean canGenericRuleBeApplied(Keyword keyword) {
		return keyword != null && !COLON.equals(keyword.getValue())
				&& !EQUALS.equals(keyword.getValue());
		// && !"true".equals(keyword.getValue())
		// && !"false".equals(keyword.getValue());
	}

	private Keyword getFirstKeyword(ParserRule parserRule) {
		for (Iterator<EObject> iterator = parserRule.getAlternatives()
				.eAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if (eObject instanceof Keyword) {
				return (Keyword) eObject;
			}
		}
		return null;
	}

	private boolean isComplexRule(ParserRule parserRule) {
		return parserRule.getAlternatives() instanceof Group;
	}

}
