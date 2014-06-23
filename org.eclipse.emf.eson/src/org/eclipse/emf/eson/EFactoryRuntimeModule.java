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
package org.eclipse.emf.eson;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.eclipse.emf.eson.conversion.DATEValueConverter;
import org.eclipse.emf.eson.conversion.TerminalConverters;
import org.eclipse.emf.eson.generator.EFactoryJSONGenerator;
import org.eclipse.emf.eson.resource.EFactoryDerivedStateComputer;
import org.eclipse.emf.eson.resource.EFactoryLocationInFileProvider;
import org.eclipse.emf.eson.resource.EFactoryResourceServiceProvider;
import org.eclipse.emf.eson.resource.EFactoryStandaloneResourceFactory;
import org.eclipse.emf.eson.scoping.EFactoryQualifiedNameProvider;
import org.eclipse.emf.eson.scoping.EPackageScopeProvider;
import org.eclipse.emf.eson.scoping.IEPackageScopeProvider;
import org.eclipse.emf.eson.scoping.WarningErrorHandlerWithoutNoSuchMethodException;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader.GenericUnloader;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EFactoryRuntimeModule extends AbstractEFactoryRuntimeModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(SimpleDateFormat.class)
				.annotatedWith(Names.named(DATEValueConverter.DATE_FORMAT_KEY))
				.toInstance(new SimpleDateFormat("MM.dd.yyyy"));
		binder.bind(IReferableElementsUnloader.class).to(GenericUnloader.class);
	}
	
	@Override
	public Class<? extends org.eclipse.xtext.conversion.IValueConverterService> bindIValueConverterService() {
		return TerminalConverters.class;
	}

	@Override
	public Class<? extends IResourceFactory> bindIResourceFactory() {
		return EFactoryStandaloneResourceFactory.class;
	}
	
	// NO @Override
	public Class<? extends IDerivedStateComputer> bindIDerivedStateComputer() {
		return EFactoryDerivedStateComputer.class;
	}
	
	// http://www.eclipse.org/forums/index.php/m/759778/
	 public void configureErrorHandler(Binder binder) {
		 Logger logger = Logger.getLogger(PolymorphicDispatcher.class);
	     PolymorphicDispatcher.ErrorHandler<IScope> handler = new WarningErrorHandlerWithoutNoSuchMethodException<IScope>(logger);
	        
	     binder.bind(new TypeLiteral<PolymorphicDispatcher.ErrorHandler<IScope>>(){})
	        .annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_ERROR_HANDLER))
	        .toInstance(handler);	        
	 }
	 
	@Override
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return EFactoryLocationInFileProvider.class;
	}
	
	public Class<? extends IEPackageScopeProvider> bindIEPackageScopeProvider() {
		return EPackageScopeProvider.class;
	}

	public Class<? extends org.eclipse.xtext.resource.IResourceDescription.Manager> bindIResourceDescription$Manager() {
		return org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager.class;
	}
	
	@SuppressWarnings("restriction")
	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return org.eclipse.xtext.xbase.annotations.validation.DerivedStateAwareResourceValidator.class;
	}
	
	// NO @Override
	public Class<? extends IGenerator> bindIGenerator() {
		return EFactoryJSONGenerator.class;
	}

	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return EFactoryResourceServiceProvider.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return EFactoryQualifiedNameProvider.class;
	}
}
