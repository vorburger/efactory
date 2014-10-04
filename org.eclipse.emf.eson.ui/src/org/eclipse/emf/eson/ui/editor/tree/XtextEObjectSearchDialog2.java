/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2013 - 2014 Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.ui.editor.tree;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;
import org.eclipse.xtext.ui.search.XtextEObjectSearchDialog;

/**
 * XtextEObjectSearchDialog with setInitialTypePattern (like the
 * setInitialPattern which is already there), and optionally make that input
 * control (the "Eclass name prefix or pattern", 2nd field) non-editable, for a
 * kind of "picker" UI.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=422083
 */
public class XtextEObjectSearchDialog2 extends XtextEObjectSearchDialog {

	private String initialTypeText;
	private boolean typePatternEditable = true;

	public XtextEObjectSearchDialog2(Shell parent, IXtextEObjectSearch searchEngine, ILabelProvider labelProvider) {
		super(parent, searchEngine, labelProvider);
	}

	public XtextEObjectSearchDialog2(Shell parent, IXtextEObjectSearch searchEngine, ILabelProvider labelProvider, boolean enableStyledLabels) {
		super(parent, searchEngine, labelProvider, enableStyledLabels);
	}

	public void setInitialTypePattern(String text) {
		setInitialTypePattern(text, true);
	}

	public void setInitialTypePattern(String text, boolean editable) {
		this.initialTypeText = text;
		this.typePatternEditable = editable;
	}
	
	@Override
	protected Label createMessageArea(Composite composite) {
		Label label = super.createMessageArea(composite);
		typeSearchControl.setText(initialTypeText);
		typeSearchControl.setEditable(typePatternEditable);
		return label;
	}
}
