package org.eclipse.emf.eson.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eson.eFactory.BooleanAttribute;
import org.eclipse.emf.eson.eFactory.Containment;
import org.eclipse.emf.eson.eFactory.CustomNameMapping;
import org.eclipse.emf.eson.eFactory.DateAttribute;
import org.eclipse.emf.eson.eFactory.DoubleAttribute;
import org.eclipse.emf.eson.eFactory.EFactoryPackage;
import org.eclipse.emf.eson.eFactory.EnumAttribute;
import org.eclipse.emf.eson.eFactory.Factory;
import org.eclipse.emf.eson.eFactory.Feature;
import org.eclipse.emf.eson.eFactory.Import;
import org.eclipse.emf.eson.eFactory.IntegerAttribute;
import org.eclipse.emf.eson.eFactory.MultiValue;
import org.eclipse.emf.eson.eFactory.NewObject;
import org.eclipse.emf.eson.eFactory.NullAttribute;
import org.eclipse.emf.eson.eFactory.PackageImport;
import org.eclipse.emf.eson.eFactory.Reference;
import org.eclipse.emf.eson.eFactory.StringAttribute;
import org.eclipse.emf.eson.services.EFactoryGrammarAccess;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class EFactorySemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private EFactoryGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == EFactoryPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case EFactoryPackage.BOOLEAN_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getBooleanAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_BooleanAttribute(context, (BooleanAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.CONTAINMENT:
				if(context == grammarAccess.getContainmentRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_Containment(context, (Containment) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.CUSTOM_NAME_MAPPING:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getCustomNameMappingRule()) {
					sequence_CustomNameMapping(context, (CustomNameMapping) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.DATE_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getDateAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_DateAttribute(context, (DateAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.DOUBLE_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getDoubleAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_DoubleAttribute(context, (DoubleAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.ENUM_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getEnumAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_EnumAttribute(context, (EnumAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.FACTORY:
				if(context == grammarAccess.getFactoryRule()) {
					sequence_Factory(context, (Factory) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.FEATURE:
				if(context == grammarAccess.getFeatureRule()) {
					sequence_Feature(context, (Feature) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.IMPORT:
				if(context == grammarAccess.getImportRule()) {
					sequence_Import(context, (Import) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.INTEGER_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getIntegerAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_IntegerAttribute(context, (IntegerAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.MULTI_VALUE:
				if(context == grammarAccess.getMultiValueRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_MultiValue(context, (MultiValue) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.NEW_OBJECT:
				if(context == grammarAccess.getNewObjectRule()) {
					sequence_NewObject(context, (NewObject) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.NULL_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getNullAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_NullAttribute(context, (NullAttribute) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.PACKAGE_IMPORT:
				if(context == grammarAccess.getPackageImportRule()) {
					sequence_PackageImport(context, (PackageImport) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.REFERENCE:
				if(context == grammarAccess.getReferenceRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_Reference(context, (Reference) semanticObject); 
					return; 
				}
				else break;
			case EFactoryPackage.STRING_ATTRIBUTE:
				if(context == grammarAccess.getAttributeRule() ||
				   context == grammarAccess.getStringAttributeRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_StringAttribute(context, (StringAttribute) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     value=BOOLEAN
	 */
	protected void sequence_BooleanAttribute(EObject context, BooleanAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.BOOLEAN_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.BOOLEAN_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBooleanAttributeAccess().getValueBOOLEANTerminalRuleCall_0(), semanticObject.isValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=NewObject
	 */
	protected void sequence_Containment(EObject context, Containment semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.CONTAINMENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.CONTAINMENT__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getContainmentAccess().getValueNewObjectParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (eClass=[EClass|QualifiedName] nameFeature=[EAttribute|ID])
	 */
	protected void sequence_CustomNameMapping(EObject context, CustomNameMapping semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.CUSTOM_NAME_MAPPING__ECLASS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.CUSTOM_NAME_MAPPING__ECLASS));
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.CUSTOM_NAME_MAPPING__NAME_FEATURE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.CUSTOM_NAME_MAPPING__NAME_FEATURE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getCustomNameMappingAccess().getEClassEClassQualifiedNameParserRuleCall_2_0_1(), semanticObject.getEClass());
		feeder.accept(grammarAccess.getCustomNameMappingAccess().getNameFeatureEAttributeIDTerminalRuleCall_4_0_1(), semanticObject.getNameFeature());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=DATE
	 */
	protected void sequence_DateAttribute(EObject context, DateAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.DATE_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.DATE_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDateAttributeAccess().getValueDATETerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=DOUBLE
	 */
	protected void sequence_DoubleAttribute(EObject context, DoubleAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.DOUBLE_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.DOUBLE_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDoubleAttributeAccess().getValueDOUBLETerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=[EEnumLiteral|ID]
	 */
	protected void sequence_EnumAttribute(EObject context, EnumAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.ENUM_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.ENUM_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEnumAttributeAccess().getValueEEnumLiteralIDTerminalRuleCall_1_0_1(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (epackages+=PackageImport* imports+=Import* annotations+=Annotation* root=NewObject)
	 */
	protected void sequence_Factory(EObject context, Factory semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (eFeature=[EStructuralFeature|ID] value=Value?)
	 */
	protected void sequence_Feature(EObject context, Feature semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     importURI=STRING
	 */
	protected void sequence_Import(EObject context, Import semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.IMPORT__IMPORT_URI) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.IMPORT__IMPORT_URI));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0(), semanticObject.getImportURI());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=LONG
	 */
	protected void sequence_IntegerAttribute(EObject context, IntegerAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.INTEGER_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.INTEGER_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getIntegerAttributeAccess().getValueLONGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (values+=Value*)
	 */
	protected void sequence_MultiValue(EObject context, MultiValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (eClass=[EClass|QualifiedName] name=ID? features+=Feature*)
	 */
	protected void sequence_NewObject(EObject context, NewObject semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value='NULL'
	 */
	protected void sequence_NullAttribute(EObject context, NullAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.NULL_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.NULL_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNullAttributeAccess().getValueNULLKeyword_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ePackage=[EPackage|QualifiedName]
	 */
	protected void sequence_PackageImport(EObject context, PackageImport semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.PACKAGE_IMPORT__EPACKAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.PACKAGE_IMPORT__EPACKAGE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPackageImportAccess().getEPackageEPackageQualifiedNameParserRuleCall_1_0_1(), semanticObject.getEPackage());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=[EObject|QualifiedName]
	 */
	protected void sequence_Reference(EObject context, Reference semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.REFERENCE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.REFERENCE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getReferenceAccess().getValueEObjectQualifiedNameParserRuleCall_0_1(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_StringAttribute(EObject context, StringAttribute semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EFactoryPackage.Literals.STRING_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EFactoryPackage.Literals.STRING_ATTRIBUTE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getStringAttributeAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
}
