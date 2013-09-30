package com.googlecode.efactory.tests.xtextintegration.tests;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.xtext.junit4.GlobalRegistries;
import org.eclipse.xtext.junit4.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.junit4.IInjectorProvider;
import org.eclipse.xtext.junit4.IRegistryConfigurator;

import testModelWithXtextReference.TestModelWithXtextReferencePackage;

import com.google.inject.Injector;
import com.googlecode.efactory.EFactoryStandaloneSetup;
import com.googlecode.efactory.xtextintegration.MyDslStandaloneSetup;

public class MyDslWithEFactoryInjectorProvider implements IInjectorProvider, IRegistryConfigurator {

    protected GlobalStateMemento stateBeforeInjectorCreation;
	protected GlobalStateMemento stateAfterInjectorCreation;
	protected Injector myDslInjector;
	protected Injector eFactoryInjector;

	static {
		GlobalRegistries.initializeDefaults();
	}

	@Override
	public Injector getInjector() {
		if (myDslInjector == null) {
			if (!Platform.isRunning()) {
				EcorePackage.eINSTANCE.toString();
				TestModelWithXtextReferencePackage.eINSTANCE.toString();
			}
			stateBeforeInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
			this.myDslInjector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
			this.eFactoryInjector = new EFactoryStandaloneSetup().createInjectorAndDoEMFRegistration();
			stateAfterInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
		}
		return myDslInjector;
	}

	@Override
	public void setupRegistry() {
		getInjector();
		stateAfterInjectorCreation.restoreGlobalState();
	}

	@Override
	public void restoreRegistry() {
		stateBeforeInjectorCreation.restoreGlobalState();
	}

}
