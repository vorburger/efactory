package org.eclipse.emf.eson.xtextintegration.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider; 
 
import com.google.inject.Inject;

public class MyDslLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public MyDslLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}
}
