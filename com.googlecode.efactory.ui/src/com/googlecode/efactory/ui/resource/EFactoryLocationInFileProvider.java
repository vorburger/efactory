package com.googlecode.efactory.ui.resource;

import org.eclipse.xtext.resource.DefaultLocationInFileProvider;

public class EFactoryLocationInFileProvider extends DefaultLocationInFileProvider {

	// This doesn't seem to work - clicking on Reference to jump within file, or to other file,
	// does NOT work with this as is.  Commenting out - to revisit when working on Outline.
	
/*	
	@Override
	protected ITextRegion getTextRegion(EObject obj, boolean isSignificant) {
		if (obj.eResource() instanceof EFactoryResource) {
			EFactoryResource efactoryResource = (EFactoryResource) obj.eResource();
			EObject eFactoryElement = efactoryResource.getEFactoryElement(obj);
			if (eFactoryElement == null) {
				return new TextRegion(0, 0);
			}
			return super.getTextRegion(eFactoryElement, isSignificant);
		}
		return super.getTextRegion(obj, isSignificant);
	}
*/
}
