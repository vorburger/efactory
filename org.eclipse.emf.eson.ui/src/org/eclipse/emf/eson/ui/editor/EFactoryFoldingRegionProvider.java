package org.eclipse.emf.eson.ui.editor;

import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;

public class EFactoryFoldingRegionProvider extends DefaultFoldingRegionProvider {

	// @Override
	// protected List<IFoldingRegion> doGetFoldingRegions(
	// IXtextDocument xtextDocument, XtextResource xtextResource) {
	// List<IFoldingRegion> foldingRegions = Lists.newArrayList();
	// if (xtextResource instanceof EFactoryResource) {
	// EFactoryResource eFactoryResource = (EFactoryResource) xtextResource;
	// Factory factory = eFactoryResource.getFactory();
	// if (factory != null) {
	// xtextResource = (XtextResource) factory.eResource();
	// }
	// }
	// Iterator<EObject> allContents = xtextResource.getAllContents();
	// while (allContents.hasNext()) {
	// EObject eObject = allContents.next();
	// if (isHandled(eObject)) {
	// addFoldingRegions(xtextDocument, eObject, foldingRegions);
	// }
	// }
	// return foldingRegions;
	// }

}
