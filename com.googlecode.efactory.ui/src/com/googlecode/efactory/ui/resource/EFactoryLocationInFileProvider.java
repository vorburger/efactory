package com.googlecode.efactory.ui.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

import com.googlecode.efactory.resource.EFactoryResource;

public class EFactoryLocationInFileProvider extends
		DefaultLocationInFileProvider {

	@Override
	protected ITextRegion getTextRegion(EObject obj, boolean isSignificant) {
		if (obj.eResource() instanceof EFactoryResource) {
			EFactoryResource efactoryResource = (EFactoryResource) obj
					.eResource();
			EObject eFactoryElement = efactoryResource.getEFactoryElement(obj);
			if (eFactoryElement == null) {
				return new TextRegion(0, 0);
			}
			return super.getTextRegion(eFactoryElement, isSignificant);
		}
		return super.getTextRegion(obj, isSignificant);
	}

}
