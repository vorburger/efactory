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
package com.googlecode.efactory.ui.highlighting;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.googlecode.efactory.eFactory.Annotation;
import com.googlecode.efactory.eFactory.EFactoryPackage;
import com.googlecode.efactory.eFactory.NewObject;
import com.googlecode.efactory.eFactory.util.EFactorySwitch;

/**
 * Highlight Annotation with SemanticHighlightingConfiguration.ANNOTATION_ID,
 * and NewObject eClass EClass + Feature eFeature(EStructuralFeature)
 * as DefaultHighlightingConfiguration.KEYWORD_ID.
 */
public class EFactoryHighlightingCalculator implements ISemanticHighlightingCalculator {

	// TODO OutOfMemoryError!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	// TODO NewObject name with a different colour.
	
	private Object dummy = new Object();

	public void provideHighlightingFor(XtextResource resource, final IHighlightedPositionAcceptor acceptor) {
		if (resource == null)
			return;

		final IParseResult parseResult = resource.getParseResult();
		if (parseResult == null)
			return;
		
		Iterable<INode> allNodes = parseResult.getRootNode().getAsTreeIterable();
		for (final INode abstractNode : allNodes) {
			final EObject semanticElement = abstractNode.getSemanticElement();
			if (semanticElement != null) {
				new EFactorySwitch<Object>() {

					@Override
					public Object caseAnnotation(Annotation object) {
						acceptor.addPosition(abstractNode.getOffset(),
								abstractNode.getLength(),
								SemanticHighlightingConfiguration.ANNOTATION_ID);
						return dummy;
					}

					@Override
					public Object caseNewObject(NewObject object) {
						EReference expectedFeature = EFactoryPackage.eINSTANCE.getNewObject_EClass();
						highlightLeafnode(acceptor, abstractNode, expectedFeature);
						return dummy;
					};

					@Override
					public Object caseFeature(
							com.googlecode.efactory.eFactory.Feature object) {
						EReference expectedFeature = EFactoryPackage.eINSTANCE.getFeature_EFeature();
						highlightLeafnode(acceptor, abstractNode, expectedFeature);
						return dummy;
					}

					private void highlightLeafnode(
							final IHighlightedPositionAcceptor acceptor,
							final INode abstractNode, EReference expectedFeature) {
						List<INode> nodes = NodeModelUtils.findNodesForFeature(
								abstractNode.getSemanticElement(),
								expectedFeature);
						if (nodes.isEmpty()) {
							return;
						}
						int offset = nodes.get(0).getOffset();
						int length = 0;
						for (INode node : nodes) {
							length += node.getLength();
						}
						acceptor.addPosition(offset, length,
								DefaultHighlightingConfiguration.KEYWORD_ID);
					};

				}.doSwitch(semanticElement);
			}

		}
	}
}
