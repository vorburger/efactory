package com.googlecode.efactory.ui.ecoreedit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

public class EFactoryEcoreEditor extends EcoreEditor {

	// TODO we'll need an integration test for this..
	
	// TODO instead of a drop-down, we'll need to have a pop-up.. try re-using the Xtext "Open Model Element" (with EClass pre-set) ?
	
	private @Inject IScopeProvider scopeProvider;
	private @Inject IGlobalScopeProvider globalScopeProvider; 
	
//	private @Inject	IResourceDescriptions resourceDescriptions;

//	private @Inject IContainer.Manager containerManager;
	
	//private @Inject IQualifiedNameConverter qualifiedNameConverter;

	// TODO using this lower-level API seems wrong - why not just use IScope, which behind the scenes should do this? 
//	private List<IResourceDescription> listVisibleResources(Resource myResource) {
//		List<IResourceDescription> visibleResources = new ArrayList<IResourceDescription>();
//		URI uri = myResource.getURI();
//		IResourceDescription descr = resourceDescriptions.getResourceDescription(uri);
//		if (descr != null) { // null if nothing is exported
//			for (IContainer visibleContainer : containerManager.getVisibleContainers(descr, resourceDescriptions)) {
//				for (IResourceDescription visibleResourceDesc : visibleContainer.getResourceDescriptions()) {
//					visibleResources.add(visibleResourceDesc);
//				}
//			}
//		}
//		return visibleResources;
//	}

	@Override
	public IPropertySheetPage getPropertySheetPage() {
		PropertySheetPage propertySheetPage = (PropertySheetPage) super.getPropertySheetPage();
		propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory) {
					@Override
					protected IPropertySource createPropertySource(Object object,IItemPropertySource itemPropertySource) {
						return new PropertySource(object, itemPropertySource) {
							@Override
							protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
								return new PropertyDescriptor(object,itemPropertyDescriptor) {

									List<IEObjectDescription> choiceOfValues = new ArrayList<IEObjectDescription>();

									// TODO probably move this method up/out.. and make it return List<IEObjectDescription> ?
									private void buildChoiceOfValues() {
										choiceOfValues = new ArrayList<IEObjectDescription>();
										Object genericFeature = itemPropertyDescriptor.getFeature(this.object);
										if (genericFeature instanceof EReference) {
											EReference eReference = (EReference) genericFeature;
											
											EObject eObject = (EObject) this.object;
											IScope scope = scopeProvider.getScope(eObject, eReference);
											//Predicate<IEObjectDescription> filter = null;
											//IScope scope = globalScopeProvider.getScope(eObject.eResource(), eReference, filter);
											for (IEObjectDescription objectDescription : scope.getAllElements()) {
//											for (IEObjectDescription objectDescription : scope.getElements(eObject)) {
												choiceOfValues.add(objectDescription);
											}
											
//											EClassifier eType = eReference.getEType();
//											if (eType instanceof EClass) {
//												EClass eClass = (EClass) eType;
//												Resource resource = eObject.eResource();
//												for (IResourceDescription resourceDescription : listVisibleResources(resource)) {
//													for (IEObjectDescription objectDescription : resourceDescription.getExportedObjectsByType(eClass)) {
//														choiceOfValues.add(objectDescription);
//													}
//												}
//											}
										}
									}

									private IEObjectDescription lookupEObjectDescription(EObject eObject) {
										URI uri = EcoreUtil.getURI(eObject);
										if (choiceOfValues.isEmpty())
											buildChoiceOfValues();
										for (IEObjectDescription objectDescription : choiceOfValues) {
											if (uri.equals(objectDescription.getEObjectURI())) {
												return objectDescription;
											}
										}
										return null;
									}

									@Override
									public ILabelProvider getLabelProvider() {
										final ILabelProvider superLabelProvider = super.getLabelProvider();
										return new LabelProvider() {
											@Override
											public String getText(Object element) {
												if (element instanceof EObject) {
													IEObjectDescription objectDescription = lookupEObjectDescription((EObject) element);
													if (objectDescription != null) {
														return objectDescription.getQualifiedName().toString();
													}
												}
												return superLabelProvider.getText(element);
											}

											@Override
											public Image getImage(Object element) {
												return superLabelProvider.getImage(element);
											}
										};
									}

									@Override
									public CellEditor createPropertyEditor(Composite composite) {
										if (!itemPropertyDescriptor.canSetProperty(object)) {
											return null;
										}

										Shell shell = getSite().getShell();
										Cursor oldCursor = shell.getCursor();
										
										try {
											Cursor waitCursor = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
											shell.setCursor(waitCursor);

											CellEditor result = null;
											Object genericFeature = itemPropertyDescriptor.getFeature(this.object);
											if (genericFeature instanceof EStructuralFeature) {

												final EStructuralFeature feature = (EStructuralFeature) genericFeature;
												final EClassifier eType = feature.getEType();
												buildChoiceOfValues();
												
												if (!choiceOfValues.isEmpty()) {
													if (itemPropertyDescriptor.isMany(object)) {
														boolean valid = true;
														for (Object choice : choiceOfValues) {
															if (!eType.isInstance(choice)) {
																valid = false;
																break;
															}
														}
														if (valid) {
															final ILabelProvider editLabelProvider = getEditLabelProvider();
															result = new ExtendedDialogCellEditor(
																	composite,
																	editLabelProvider) {
																@Override
																protected Object openDialogBox(
																		Control cellEditorWindow) {
																	FeatureEditorDialog dialog = new FeatureEditorDialog(
																			cellEditorWindow.getShell(),
																			editLabelProvider,
																			object,
																			feature.getEType(),
																			(List<?>) doGetValue(),
																			getDisplayName(),
																			new ArrayList<Object>(choiceOfValues),
																			false,
																			itemPropertyDescriptor.isSortChoices(object),
																			feature.isUnique());
																	dialog.open();
																	return dialog.getResult();
																}
															};
														}
													}
													if (result == null) {
														List<Object> choices = new ArrayList<Object>();
														for (IEObjectDescription description : choiceOfValues) {
															choices.add(description.getEObjectOrProxy());
														}
														result = new ExtendedComboBoxCellEditor(
																composite,
																choices,
																getLabelProvider(),
																itemPropertyDescriptor.isSortChoices(object));
													}
												} else if (eType instanceof EDataType) {
													EDataType eDataType = (EDataType) eType;
													if (eDataType.isSerializable()) {
														if (itemPropertyDescriptor.isMany(object)) {
															final ILabelProvider editLabelProvider = getEditLabelProvider();
															result = new ExtendedDialogCellEditor(composite,editLabelProvider) {
																@Override
																protected Object openDialogBox(Control cellEditorWindow) {
																	FeatureEditorDialog dialog = new FeatureEditorDialog(
																			cellEditorWindow.getShell(),
																			editLabelProvider,
																			object,
																			feature.getEType(),
																			(List<?>) doGetValue(),
																			getDisplayName(),
																			null,
																			itemPropertyDescriptor.isMultiLine(object),
																			false,
																			feature.isUnique());
																	dialog.open();
																	return dialog.getResult();
																}
															};
														} else if (eDataType.getInstanceClass() == Boolean.class) {
															result = new ExtendedComboBoxCellEditor(
																	composite,
																	Arrays.asList(new Object[] { null, Boolean.FALSE, Boolean.TRUE }),
																	getEditLabelProvider(),
																	itemPropertyDescriptor.isSortChoices(object));
														} else if (eDataType.getInstanceClass() == Boolean.TYPE) {
															result = new ExtendedComboBoxCellEditor(
																	composite,
																	Arrays.asList(new Object[] {Boolean.FALSE, Boolean.TRUE }),
																	getEditLabelProvider(),
																	itemPropertyDescriptor.isSortChoices(object));
														} else {
															result = createEDataTypeCellEditor(eDataType,composite);
														}
													}
												}
											} else {
												result = super.createPropertyEditor(composite);
											}
											return result;

										} finally {
											shell.setCursor(oldCursor);
										}

									}
								};
							}
						};
					}
				});
		return propertySheetPage;
	}

}
