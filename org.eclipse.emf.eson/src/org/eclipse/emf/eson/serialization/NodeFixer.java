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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.impl.NodeModelBuilder;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import org.eclipse.emf.eson.eFactory.EFactoryPackage;
import org.eclipse.emf.eson.services.EFactoryGrammarAccess;

/**
 * Adds missing INode to new AST objects created in EFactoryAdapter.
 * 
 * This is tested by org.eclipse.emf.eson.builder.resync.tests.BuilderResyncTest.testAddNewContainerFeature().
 * This is used internally by EFactoryAdapter only, and is only a separate class for better readability. 
 * 
 * @author Michael Vorburger
 */
@Singleton
public class NodeFixer {

	protected @Inject EFactoryGrammarAccess grammarAccess;

	/**
	 * Adds missing INode/s to astElement and any of its parents.
	 */
	public void addMissingNodes(EObject leafAstElement) {
		// Get a list of all the AST Elements which we need to fix (from argument "upwards")
		List<EObject> astElements = getASTElementsWithoutNode(leafAstElement);
		
		for (EObject astElement : astElements) {
			if (     astElement.eClass().equals(EFactoryPackage.Literals.FEATURE))
				associateNode(astElement, grammarAccess.getFeatureRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.MULTI_VALUE))
				associateNode(astElement, grammarAccess.getMultiValueRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.CONTAINMENT))
				associateNode(astElement, grammarAccess.getContainmentRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.NEW_OBJECT))
				associateNode(astElement, grammarAccess.getNewObjectRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.STRING_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getStringAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.REFERENCE))
				associateNode(astElement, grammarAccess.getNullAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.ENUM_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getEnumAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.INTEGER_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getIntegerAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.BOOLEAN_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getBooleanAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.DATE_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getDateAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.DOUBLE_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getDoubleAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.NULL_ATTRIBUTE))
				associateNode(astElement, grammarAccess.getNullAttributeRule());
			else if (astElement.eClass().equals(EFactoryPackage.Literals.FACTORY))
				associateNode(astElement, grammarAccess.getFactoryRule());
			else
				throw new IllegalStateException("Need to also handle: " + astElement.eClass().getName());
		}
	}

	protected List<EObject> getASTElementsWithoutNode(EObject leafAstElement) {
		Preconditions.checkNotNull(leafAstElement);
		List<EObject> astElementsWithoutNode = new ArrayList<EObject>();
		for (EObject astElement = leafAstElement; astElement != null;) {
			if (NodeModelUtils.getNode(astElement) != null) // NOT findActualNodeFor()
				break;
			astElementsWithoutNode.add(astElement);
			astElement = astElement.eContainer();
		}
		
		// We have to reverse the list, because we need to add the nodes "top-down"...
		// .. if you try "bottom-up", then associateNode() will fail, as it cannot find
		// the parent which we are about to create next yet. 
		Collections.reverse(astElementsWithoutNode);
		
		return astElementsWithoutNode;
	}
	
	protected void associateNode(EObject astElement, EObject grammarElement) {
		Preconditions.checkNotNull(astElement);
		Preconditions.checkNotNull(grammarElement);
		NodeModelBuilder newNodeModelBuilder = new NodeModelBuilder();
		int lookahead = 0;
		EObject valueContainer = astElement.eContainer();
		if ( valueContainer == null )
			throw new IllegalStateException("value.eContainer() == null");
		ICompositeNode parent = NodeModelUtils.findActualNodeFor(valueContainer); // TODO or just NodeModelUtils.getNode() ?
		if ( parent == null )
			throw new IllegalStateException("NodeModelUtils.findActualNodeFor() ICompositeNode parent == null");
		ICompositeNode node = newNodeModelBuilder.newCompositeNode(astElement, lookahead, parent);// TODO or, sometimes, newLeafNode() ?
		if (astElement.eClass().equals(EFactoryPackage.Literals.FEATURE)){
			newNodeModelBuilder.newLeafNode(parent.getTotalEndOffset()-1,parent.getLength(), astElement, false, null, parent);			
		}
		newNodeModelBuilder.associateWithSemanticElement(node, astElement);
	}

}