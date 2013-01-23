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
package com.googlecode.efactory.proposal.tests.mock;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitioningListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.xtext.concurrent.IUnitOfWork;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.IXtextDocumentContentObserver;
import org.eclipse.xtext.ui.editor.model.IXtextModelListener;
import org.eclipse.xtext.util.concurrent.IEObjectHandle;

public class MockableXtextDocument implements IXtextDocument {

	private String model;
	private XtextResource xtextResource;

	public MockableXtextDocument(XtextResource xtextResource, String model) {
		this.model = model;
		this.xtextResource = xtextResource;
	}

	public void addModelListener(IXtextModelListener listener) {
		throw new UnsupportedOperationException();

	}

	public void addXtextDocumentContentObserver(
			IXtextDocumentContentObserver listener) {
		throw new UnsupportedOperationException();

	}

	public <T extends EObject> IEObjectHandle<T> createHandle(T obj) {
		throw new UnsupportedOperationException();

	}

	public <T> T getAdapter(Class<T> adapterType) {
		throw new UnsupportedOperationException();

	}

	public void removeModelListener(IXtextModelListener listener) {
		throw new UnsupportedOperationException();

	}

	public void removeXtextDocumentContentObserver(
			IXtextDocumentContentObserver listener) {
		throw new UnsupportedOperationException();

	}

	public void addDocumentListener(IDocumentListener listener) {
		throw new UnsupportedOperationException();

	}

	public void addDocumentPartitioningListener(
			IDocumentPartitioningListener listener) {
		throw new UnsupportedOperationException();

	}

	public void addPosition(Position position) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public void addPosition(String category, Position position)
			throws BadLocationException, BadPositionCategoryException {
		throw new UnsupportedOperationException();

	}

	public void addPositionCategory(String category) {
		throw new UnsupportedOperationException();

	}

	public void addPositionUpdater(IPositionUpdater updater) {
		throw new UnsupportedOperationException();

	}

	public void addPrenotifiedDocumentListener(IDocumentListener documentAdapter) {
		throw new UnsupportedOperationException();

	}

	public int computeIndexInCategory(String category, int offset)
			throws BadLocationException, BadPositionCategoryException {
		throw new UnsupportedOperationException();

	}

	public int computeNumberOfLines(String text) {
		throw new UnsupportedOperationException();

	}

	public ITypedRegion[] computePartitioning(int offset, int length)
			throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public boolean containsPosition(String category, int offset, int length) {
		throw new UnsupportedOperationException();
	}

	public boolean containsPositionCategory(String category) {
		throw new UnsupportedOperationException();
	}

	public String get() {
		throw new UnsupportedOperationException();

	}

	public String get(int offset, int length) throws BadLocationException {
		return model.substring(offset, length - offset);
	}

	public char getChar(int offset) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public String getContentType(int offset) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public IDocumentPartitioner getDocumentPartitioner() {
		throw new UnsupportedOperationException();

	}

	public String[] getLegalContentTypes() {
		throw new UnsupportedOperationException();

	}

	public String[] getLegalLineDelimiters() {
		throw new UnsupportedOperationException();

	}

	public int getLength() {
		return Integer.MAX_VALUE;
	}

	public String getLineDelimiter(int line) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public IRegion getLineInformation(int line) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public IRegion getLineInformationOfOffset(int offset)
			throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public int getLineLength(int line) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public int getLineOfOffset(int offset) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public int getLineOffset(int line) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public int getNumberOfLines() {
		throw new UnsupportedOperationException();

	}

	public int getNumberOfLines(int offset, int length)
			throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public ITypedRegion getPartition(int offset) throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public String[] getPositionCategories() {
		throw new UnsupportedOperationException();

	}

	public IPositionUpdater[] getPositionUpdaters() {
		throw new UnsupportedOperationException();

	}

	public Position[] getPositions(String category)
			throws BadPositionCategoryException {
		throw new UnsupportedOperationException();

	}

	public void insertPositionUpdater(IPositionUpdater updater, int index) {
		throw new UnsupportedOperationException();

	}

	public void removeDocumentListener(IDocumentListener listener) {
		throw new UnsupportedOperationException();

	}

	public void removeDocumentPartitioningListener(
			IDocumentPartitioningListener listener) {
		throw new UnsupportedOperationException();

	}

	public void removePosition(Position position) {
		throw new UnsupportedOperationException();

	}

	public void removePosition(String category, Position position)
			throws BadPositionCategoryException {
		throw new UnsupportedOperationException();

	}

	public void removePositionCategory(String category)
			throws BadPositionCategoryException {
		throw new UnsupportedOperationException();

	}

	public void removePositionUpdater(IPositionUpdater updater) {
		throw new UnsupportedOperationException();

	}

	public void removePrenotifiedDocumentListener(
			IDocumentListener documentAdapter) {
		throw new UnsupportedOperationException();

	}

	public void replace(int offset, int length, String text)
			throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public int search(int startOffset, String findString,
			boolean forwardSearch, boolean caseSensitive, boolean wholeWord)
			throws BadLocationException {
		throw new UnsupportedOperationException();

	}

	public void set(String text) {
		throw new UnsupportedOperationException();

	}

	public void setDocumentPartitioner(IDocumentPartitioner partitioner) {
		throw new UnsupportedOperationException();

	}

	public <T> T modify(IUnitOfWork<T, XtextResource> work) {
		try {
			return work.exec(xtextResource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T readOnly(IUnitOfWork<T, XtextResource> work) {
		try {
			return work.exec(xtextResource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T readOnly(
			org.eclipse.xtext.util.concurrent.IUnitOfWork<T, XtextResource> work) {
		throw new UnsupportedOperationException();
	}

	public <T> T modify(
			org.eclipse.xtext.util.concurrent.IUnitOfWork<T, XtextResource> work) {
		throw new UnsupportedOperationException();
	}

}
