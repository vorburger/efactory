/*
 * #%L
 * org.eclipse.emf.eson.tests.xtextintegration.tests
 * %%
 * Copyright (C) 2012 - 2014 Michael Vorburger
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipse.emf.eson.tests.xtextintegration.util;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;

/**
 * EMF DiagnosticException with a proper toString().
 * 
 * This is very useful (indispensable!) in Unit Tests.
 * 
 * @author Michael Vorburger
 */
// TODO move DiagnosticExceptionWithURIAndToString into upcoming *.standalone module, to be able to share it between core test fragment and this one
public class DiagnosticExceptionWithURIAndToString extends DiagnosticException {
	private static final long serialVersionUID = 1L;

	private final URI uri;

    public DiagnosticExceptionWithURIAndToString(Diagnostic diagnostic, URI uri) {
        super(diagnostic);
        this.uri = uri;
    }

    @Override
    public String toString() {
        Diagnostic diagnostic = getDiagnostic();
        return uri.toString() + ", " + super.toString() + ": " + diagnostic.toString();
    }

}
