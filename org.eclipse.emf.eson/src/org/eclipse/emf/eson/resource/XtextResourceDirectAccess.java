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
package org.eclipse.emf.eson.resource;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.util.concurrent.IWriteAccess;

/**
 * IWriteAccess<XtextResource> implementation for standalone (no-UI) scenario and tests.
 * 
 * @author Michael Vorburger
 */
public class XtextResourceDirectAccess implements IWriteAccess<XtextResource> {
	private static Logger logger = Logger.getLogger(XtextResourceDirectAccess.class);

	private final XtextResource resource;

	public XtextResourceDirectAccess(XtextResource resource) {
		this.resource = resource;
	}

	public <T> T modify(IUnitOfWork<T, XtextResource> work) {
		try {
			return work.exec(resource);
		} catch (Exception e) {
			logger.error("IUnitOfWork.exec() threw an Exception: " + e.getMessage(), e);
			throw new WrappedException(e);
		}
	}

}
