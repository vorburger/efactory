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
package org.eclipse.emf.eson.ui.contentassist;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.xtext.ui.editor.contentassist.DefaultContentAssistantFactory;

public class EFactoryContentAssistantFactory extends DefaultContentAssistantFactory {

	@Override
	protected void setAutoInsert(ContentAssistant assistant) {
		super.setAutoInsert(assistant);
//		assistant.setAutoActivationDelay(0);
//		assistant.enableAutoInsert(true);
//		assistant.enablePrefixCompletion(true);
		assistant.enableAutoActivation(true);
	}
}
