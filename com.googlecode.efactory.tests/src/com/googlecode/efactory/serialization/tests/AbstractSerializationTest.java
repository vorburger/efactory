package com.googlecode.efactory.serialization.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.ISerializer;

import com.google.inject.Inject;
import com.googlecode.efactory.eFactory.Factory;
import com.googlecode.efactory.resource.EFactoryResource;
import com.googlecode.efactory.serialization.FactoryBuilder;
import com.googlecode.efactory.tests.util.AbstractEFactoryTest;

public abstract class AbstractSerializationTest extends AbstractEFactoryTest {

	public AbstractSerializationTest() {
		super();
		getInjector().injectMembers(this);
	}

	public AbstractSerializationTest(String name) {
		super(name);
		getInjector().injectMembers(this);
	}

	private File temp;
	static final String ROOT_FOLDER = "res/SerializationTests/";

	@Inject
	private ISerializer serializer;

	protected void printActual() throws IOException {
		System.out.println(readFile(temp));
	}

	private static String readFile(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

	protected URI createTempUri() throws IOException {
		temp = File.createTempFile("testModel", ".efactory");
		temp.deleteOnExit();
		return URI.createFileURI(temp.getAbsolutePath());
	}

	public void compare(Factory expected, Factory actual)
			throws InterruptedException, IOException {
		
		// Following is needed to remove the TestModel(Impl) object which we
		// added in the EFactoryDerivedStateComputer. It's a bit of a hack, but
		// how to do this better with EMF Compare? Why is it even looking at
		// another element in content the resource - if you're asking it to
		// match from the Factory only?!
		final EList<EObject> contents = expected.eResource().getContents();
		if (contents.size() == 2) {
			contents.remove(1);
		} 
		if (contents.size() != 1) {
			throw new IllegalStateException("why is there MORE contents in the resource? EMF Compare diff test will not work..");
		}
		
		// @see http://wiki.eclipse.org/EMF_Compare/Developer_Guide
		IComparisonScope scope = EMFCompare.createDefaultScope(expected, actual);
		EMFCompare comparator = EMFCompare.builder().build();
		Comparison comparison = comparator.compare(scope);

		// Matching model elements
		EList<Diff> differences = comparison.getDifferences();
		// TODO not sure if differences.toString() is useful?
		assertTrue("Models differ: " + differences.toString(), differences.isEmpty());
	}

	private String toString(Factory object) {
		return serializer.serialize(object);
	}

	protected void performSerializationTest(String name) throws Exception {
		EObject testModel = loadTestModel(name);
		Factory expected = ((EFactoryResource) testModel.eResource()).getFactory();
		FactoryBuilder builder = new FactoryBuilder();
		Factory actual = builder.buildFactory(testModel);

		assertModelsEquals(expected, actual);
	}

	private void assertModelsEquals(Factory expected, Factory actual) throws InterruptedException, IOException {
		XtextResourceSet rs = new XtextResourceSet();
		Resource r = rs.createResource(createTempUri());
		r.getContents().add(actual);
		System.out.println(toString(actual));
		compare(expected, actual);
	}

	private EObject loadTestModel(String name) throws IOException, DiagnosticException {
		String path = ROOT_FOLDER + name;
		EObject model = resourceProvider.loadModel(path);
		removeAdapters(model);
		return model;
	}

	private void removeAdapters(EObject model) {
		model.eAdapters().clear();
		Iterator<EObject> iterator = model.eAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			object.eAdapters().clear();
		}
	}

}
