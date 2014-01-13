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
package com.googlecode.efactory;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader.GenericUnloader;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.googlecode.efactory.conversion.DATEValueConverter;
import com.googlecode.efactory.conversion.TerminalConverters;
import com.googlecode.efactory.generator.EFactoryJSONGenerator;
import com.googlecode.efactory.resource.EFactoryDerivedStateComputer;
import com.googlecode.efactory.resource.EFactoryLocationInFileProvider;
import com.googlecode.efactory.resource.EFactoryStandaloneResourceFactory;
import com.googlecode.efactory.scoping.EFactoryImportedNamespaceAwareScopeProvider;
import com.googlecode.efactory.scoping.EFactoryResourceDescriptionManager;
import com.googlecode.efactory.scoping.EPackageScopeProvider;
import com.googlecode.efactory.scoping.IEPackageScopeProvider;
import com.googlecode.efactory.scoping.WarningErrorHandlerWithoutNoSuchMethodException;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EFactoryRuntimeModule extends com.googlecode.efactory.AbstractEFactoryRuntimeModule {

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
	
	@Override
	public void configureIScopeProviderDelegate(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.scoping.IScopeProvider.class).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(
				EFactoryImportedNamespaceAwareScopeProvider.class);
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
		// return org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager.class;
		return EFactoryResourceDescriptionManager.class;
	}
	
//	@SuppressWarnings("restriction")
//	public Class<? extends IResourceValidator> bindIResourceValidator() {
//		return org.eclipse.xtext.xbase.annotations.validation.DerivedStateAwareResourceValidator.class;
//	}
	
	// NO @Override
	public Class<? extends IGenerator> bindIGenerator() {
		return EFactoryJSONGenerator.class;
	}

}
