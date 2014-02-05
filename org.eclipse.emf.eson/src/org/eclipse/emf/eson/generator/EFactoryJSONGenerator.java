/*******************************************************************************
 * Copyright (c) 2013 Michael Vorburger and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Michael Vorburger - Initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.eson.generator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

import org.eclipse.emf.eson.eFactory.Attribute;
import org.eclipse.emf.eson.eFactory.BooleanAttribute;
import org.eclipse.emf.eson.eFactory.Containment;
import org.eclipse.emf.eson.eFactory.DateAttribute;
import org.eclipse.emf.eson.eFactory.DoubleAttribute;
import org.eclipse.emf.eson.eFactory.EnumAttribute;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.Feature;
import org.eclipse.emf.eson.eFactory.IntegerAttribute;
import org.eclipse.emf.eson.eFactory.MultiValue;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.eclipse.emf.eson.eFactory.NullAttribute;
import org.eclipse.emf.eson.eFactory.Reference;
import org.eclipse.emf.eson.eFactory.StringAttribute;
import org.eclipse.emf.eson.eFactory.Value;

/**
 * JSON Generator (from ESON).
 * 
 * Currently this is implemented by working directly at the NewObject level. If
 * there are any advantages, this could later be changed to instead work at the
 * level of the "real" EObject which EFactory builds for each NewObject, and
 * serialize that to JSON using e.g. http://ghillairet.github.io/emfjson/.
 * 
 * @author Michael Vorburger
 */
public class EFactoryJSONGenerator implements IGenerator {

	public void doGenerate(Resource resource, IFileSystemAccess fsa) {
		if (isJSONGenerationActive()) {
			EFactoryResource efResource = getEFactoryResource(resource);
			if (isReadyToGenerateJSON(efResource)) {
				Factory factory = efResource.getEFactoryFactory();
				if (factory == null)
					return;
				NewObject rootNewObject = factory.getRoot();
				if (rootNewObject == null)
					return;
				String jsonFileName = getJSONFileName(resource.getURI() /* , fsa */);
				StringBuilder sb = new StringBuilder();
				generateJSON(sb, rootNewObject);
				fsa.generateFile(jsonFileName, sb);
			}
		}
	}

	protected void generateJSON(StringBuilder sb, NewObject newObject) {
		// see JavaDoc comment above at the top class level re. this impl:
		sb.append('{');
		boolean first = true;
		// TODO handle this: if (newObject.getName() != null)
		for (Feature feature : newObject.getFeatures()) {
			if (!first) {
				sb.append(',');
			} else {
				first = false;
			}
			String name = feature.getEFeature().getName();
			generateJSON(sb, name);
			sb.append(':');
			Value value = feature.getValue();
			generateJSON(sb, value);
		}
		sb.append('}');
	}

	protected void generateJSON(StringBuilder sb, Value value) {
		if (value instanceof Attribute) {
			if (value instanceof StringAttribute) {
				StringAttribute stringAttribute = (StringAttribute) value;
				generateJSON(sb, stringAttribute.getValue());
			} else if (value instanceof BooleanAttribute) {
				BooleanAttribute booleanAttribute = (BooleanAttribute) value;
				sb.append(booleanAttribute.isValue());
			} else if (value instanceof IntegerAttribute) {
				IntegerAttribute intAttribute = (IntegerAttribute) value;
				sb.append(intAttribute.getValue());
			} else if (value instanceof EnumAttribute) {
				EnumAttribute enumAttribute = (EnumAttribute) value;
				// TODO not 100% sure if one would want the literal or the name here?
				generateJSON(sb, enumAttribute.getValue().getLiteral());
			} else if (value instanceof DoubleAttribute) {
				DoubleAttribute doubleAttribute = (DoubleAttribute) value;
				sb.append(doubleAttribute.getValue());
			} else if (value instanceof NullAttribute) {
				// Don't this this is relevant, will probably be removed
				sb.append("null");
			} else if (value instanceof DateAttribute) {
				// I know this may not be right.. there is no standard Date format for JSON..
				// My idea is that, later, this can be handled by custom EDataType with custom convert() methods
				DateAttribute dateAttribute = (DateAttribute) value;
				generateJSON(sb, dateAttribute.getValue().toString());
			}
		} else if (value instanceof Containment) {
			Containment containment = (Containment) value;
			generateJSON(sb, containment.getValue());
		} else if (value instanceof MultiValue) {
			MultiValue mv = (MultiValue) value;
			sb.append('[');
			boolean first = true;
			for (Value value2 : mv.getValues()) {
				if (!first) {
					sb.append(',');
				} else {
					first = false;
				}
				generateJSON(sb, value2);
			}
			sb.append(']');
		} else if (value instanceof Reference) {
			// TODO handle this... ;-)
		}
	}

	protected void generateJSON(StringBuilder sb, String value) {
		sb.append('\"');
		sb.append(value);
		sb.append('\"');
	}
	
	protected String getJSONFileName(URI uri /*, IFileSystemAccess fsa */) {
		String relFileName = getProjectRelativeFileName(uri /*, fsa */);
		// return FilenameUtils.removeExtension(relFileName) + ".json";
		int extensionPos = relFileName.lastIndexOf('.');
		return relFileName.substring(0, extensionPos) + ".json";
	}
	
	protected String getProjectRelativeFileName(URI resourceURI /*, IFileSystemAccess fsa */) {
		// @see http://www.eclipse.org/forums/index.php/m/1230878/#msg_1230878
		if (!resourceURI.isPlatformResource())
			throw new IllegalArgumentException("Not a Platform Resource URI: " + resourceURI.toString());
		// This is  bit of a hack, but it works...
		String sURI = resourceURI.toPlatformString(true);
		String withoutProject = sURI.substring(sURI.indexOf('/', 1) + 1);
		return withoutProject;
		// Something like this may be a better use of the API, but is much more difficult to unit test in EFactoryJSONGeneratorTest, so not pursued: 
		// URI projectRootURI = ((IFileSystemAccessExtension2)fsa).getURI(".");
		// URI resourceWithoutProjectURI = resourceURI.deresolve(projectRootURI);
		// return resourceWithoutProjectURI.toString();
	}

	protected EFactoryResource getEFactoryResource(Resource resource) {
		if (!resource.isLoaded())
			return null;
		if (!(resource instanceof EFactoryResource))
			return null;
		return (EFactoryResource) resource;
	}
	
	protected boolean isReadyToGenerateJSON(EFactoryResource resource) {
		return resource != null ? resource.isBuilt() : false;
	}

	protected boolean isJSONGenerationActive() {
		String sysProp = System.getProperty("eson2json");
		if (sysProp != null && sysProp.toLowerCase().equals("true"))
			return true;
		else
			return false;
	}
}
