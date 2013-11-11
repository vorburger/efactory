/*******************************************************************************
 * Copyright (c) 2009 Sebastian Benz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Benz - initial API and implementation
 *     Michael Vorburger - fixed OutOfMemoryError
 ******************************************************************************/
package com.googlecode.efactory.ui.highlighting;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.googlecode.efactory.eFactory.Annotation;
import com.googlecode.efactory.eFactory.EFactoryPackage;
import com.googlecode.efactory.eFactory.Feature;
import com.googlecode.efactory.eFactory.NewObject;

/**
 * Highlight Annotation with SemanticHighlightingConfiguration.ANNOTATION_ID,
 * and NewObject eClass EClass + Feature eFeature(EStructuralFeature) as
 * DefaultHighlightingConfiguration.KEYWORD_ID.
 */
public class EFactoryHighlightingCalculator implements ISemanticHighlightingCalculator {

	public void provideHighlightingFor(XtextResource resource, final IHighlightedPositionAcceptor acceptor) {
		if (resource == null)
			return;

		final IParseResult parseResult = resource.getParseResult();
		if (parseResult == null)
			return;

		Iterator<EObject> iter = EcoreUtil.getAllContents(resource, true);
		while (iter.hasNext()) {
			EObject current = iter.next();
			if (current instanceof Annotation) {
				ICompositeNode node = NodeModelUtils.findActualNodeFor(current);
				highlightNode(node, SemanticHighlightingConfiguration.ANNOTATION_ID, acceptor);
			} else if (current instanceof NewObject) {
				EReference expectedFeature = EFactoryPackage.eINSTANCE.getNewObject_EClass();
				highlightLeafnode(acceptor, current, expectedFeature, DefaultHighlightingConfiguration.KEYWORD_ID);
			} else if (current instanceof Feature) {
				EReference expectedFeature = EFactoryPackage.eINSTANCE.getFeature_EFeature();
				highlightLeafnode(acceptor, current, expectedFeature, DefaultHighlightingConfiguration.KEYWORD_ID);
			}
		}
	}

	private void highlightLeafnode(final IHighlightedPositionAcceptor acceptor, final EObject semanticElement, EReference expectedFeature,
			String id) {
		List<INode> nodes = NodeModelUtils.findNodesForFeature(semanticElement, expectedFeature);
		for (INode node : nodes) {
			highlightNode(node, id, acceptor);
		}
	};

	/**
	 * Highlights the non-hidden parts of {@code node} with the style that is
	 * associated with {@code id}.
	 * 
	 * This method is shamelessly copy/pasted from
	 * org.eclipse.xtext.ui.codetemplates
	 * .ui.highlighting.SemanticHighlighter.highlightNode(INode, String,
	 * IHighlightedPositionAcceptor).
	 */
	protected void highlightNode(INode node, String id, IHighlightedPositionAcceptor acceptor) {
		if (node == null)
			return;
		if (node instanceof ILeafNode) {
			acceptor.addPosition(node.getOffset(), node.getLength(), id);
		} else {
			for (ILeafNode leaf : node.getLeafNodes()) {
				if (!leaf.isHidden()) {
					acceptor.addPosition(leaf.getOffset(), leaf.getLength(), id);
				}
			}
		}
	}
}
