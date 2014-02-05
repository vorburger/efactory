
package com.googlecode.efactory;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class EFactoryStandaloneSetup extends EFactoryStandaloneSetupGenerated{

	// TODO move this helper class into new TBD *.standalone project
	
	public static void doSetup() {
		new EFactoryStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

