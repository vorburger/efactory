
package com.googlecode.efactory;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class EFactoryStandaloneSetup extends EFactoryStandaloneSetupGenerated{

	public static void doSetup() {
		new EFactoryStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

