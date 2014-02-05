package org.eclipse.emf.eson.ui.wizard;

import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.eson.util.EPackageResolver;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.google.common.collect.Lists;
import org.eclipse.emf.eson.ui.EFactoryLog;

public class EFactoryNewFileWizardPage extends WizardPage {

	private Text containerText;

	private Text fileText;

	private ISelection selection;

	private Combo metamodelCombo;

	public EFactoryNewFileWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("EFactory File");
		setDescription("This wizard creates a new file with *.efactory extension.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&EPackage URI:");

		metamodelCombo = new Combo(container, SWT.NONE);
		List<EPackage> allRegisteredEPackages = Lists
				.newArrayList(new EPackageResolver()
						.getAllRegisteredEPackages());
		sort(allRegisteredEPackages, new Comparator<EPackage>() {

			public int compare(EPackage o1, EPackage o2) {
				return o1.getNsURI().compareTo(o2.getNsURI());
			}
		});
		for (EPackage ePackage : allRegisteredEPackages) {
			metamodelCombo.add(ePackage.getNsURI());
		}
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		metamodelCombo.setLayoutData(gd);
		metamodelCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				setContainerText((IResource) obj);
			} else if (obj instanceof IPackageFragment) {
				IPackageFragment packageFragment = (IPackageFragment) obj;
				try {
					setContainerText(packageFragment.getCorrespondingResource());
				} catch (JavaModelException e) {
					EFactoryLog.logError(e);
				}
			}
		}
		fileText.setText("model.efactory");
	}

	private void setContainerText(IResource resource) {
		IContainer container;
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			initPackageUri(file);
		}
		if (resource instanceof IContainer) {
			container = (IContainer) resource;
		} else {

			container = resource.getParent();
		}
		containerText.setText(container.getFullPath().toString());
	}

	private void initPackageUri(IFile file) {
		String fileExtension = file.getFileExtension().toLowerCase();
		if (fileExtension.equals("ecore") || fileExtension.equals("efactory")) {
			Resource resource = loadResource(file);
			if (resource != null && !resource.getContents().isEmpty()) {
				initPackageUri(resource.getContents().get(0));
			}
		}

	}

	private void initPackageUri(EObject eObject) {
		if (eObject instanceof EPackage) {
			EPackage ePackage = (EPackage) eObject;
			initPackageUri(ePackage);
		} else {
			initPackageUri(eObject.eClass().getEPackage());
		}

	}

	private void initPackageUri(EPackage ePackage) {
		metamodelCombo.setText(ePackage.getNsURI());
	}

	private Resource loadResource(IFile file) {
		ResourceSet rs = new ResourceSetImpl();
		return rs.getResource(URI.createPlatformResourceURI(file.getFullPath()
				.toString(), true), true);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select new file container");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}

		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("efactory") == false) {
				updateStatus("File extension must be \"efactory\"");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public String getPackageUri() {
		return metamodelCombo.getText();
	}
}