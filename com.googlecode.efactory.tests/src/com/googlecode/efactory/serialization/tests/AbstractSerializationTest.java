package com.googlecode.efactory.serialization.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
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

	protected Factory loadFactory(URI uri) throws IOException {
		Factory eFactory = resourceProvider.loadFactory(uri);
		boolean hasNoErrors = eFactory.eResource().getErrors().isEmpty();
		boolean hasNoWarnings = eFactory.eResource().getWarnings().isEmpty();
		boolean isFailed = !(hasNoWarnings && hasNoErrors);
		if (isFailed) {
			printActual();
			fail("Serialization produced errors");
		}
		return eFactory;
	}

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
		// Matching model elements
		MatchModel match = MatchService.doMatch(expected, actual,
				Collections.<String, Object> emptyMap());
		DiffModel diff = DiffService.doDiff(match, false);
		List<DiffElement> differences = new ArrayList<DiffElement>(
				diff.getOwnedElements());

		if (differences.get(0).getSubDiffElements().size() > 0) {
			StringBuilder descriptionOfDifferences = new StringBuilder();
			for (DiffElement d : differences) {
				descriptionOfDifferences.append(getDifferences(d));
			}
			assertEquals(descriptionOfDifferences.toString(),
					toString(expected), toString(actual));
		}
	}

	private String toString(Factory object) {
		return serializer.serialize(object);
	}

	private String getDifferences(DiffElement diff) {
		StringBuilder builder = new StringBuilder();
		for (DiffElement s : diff.getSubDiffElements()) {
			builder.append(s.toString());
			builder.append('\n');
			if (s.getSubDiffElements().size() > 0) {
				builder.append(getDifferences(s));
			}
		}
		return builder.toString();
	}

	protected void performSerializationTest(String name) throws IOException,
			InterruptedException {
		EObject testModel = loadTestModel(name);
		Factory expected = ((EFactoryResource) testModel.eResource())
				.getFactory();
		FactoryBuilder builder = new FactoryBuilder();
		Factory actual = builder.build(testModel);

		assertModelsEquals(expected, actual);
	}

	private void assertModelsEquals(Factory expected, Factory actual)
			throws InterruptedException, IOException {
		XtextResourceSet rs = new XtextResourceSet();
		Resource r = rs.createResource(createTempUri());
		r.getContents().add(actual);
		System.out.println(toString(actual));
		compare(expected, actual);
	}

	private EObject loadTestModel(String name) throws IOException {
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
