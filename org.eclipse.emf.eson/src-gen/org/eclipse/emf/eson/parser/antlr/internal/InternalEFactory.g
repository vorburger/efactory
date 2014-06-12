/*
* generated by Xtext
*/
grammar InternalEFactory;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package org.eclipse.emf.eson.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.emf.eson.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.emf.eson.services.EFactoryGrammarAccess;

}

@parser::members {

 	private EFactoryGrammarAccess grammarAccess;
 	
    public InternalEFactoryParser(TokenStream input, EFactoryGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "Factory";	
   	}
   	
   	@Override
   	protected EFactoryGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleFactory
entryRuleFactory returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getFactoryRule()); }
	 iv_ruleFactory=ruleFactory 
	 { $current=$iv_ruleFactory.current; } 
	 EOF 
;

// Rule Factory
ruleFactory returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{ 
	        newCompositeNode(grammarAccess.getFactoryAccess().getEpackagesPackageImportParserRuleCall_0_0()); 
	    }
		lv_epackages_0_0=rulePackageImport		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getFactoryRule());
	        }
       		add(
       			$current, 
       			"epackages",
        		lv_epackages_0_0, 
        		"PackageImport");
	        afterParserOrEnumRuleCall();
	    }

)
)*(
(
		{ 
	        newCompositeNode(grammarAccess.getFactoryAccess().getImportsImportParserRuleCall_1_0()); 
	    }
		lv_imports_1_0=ruleImport		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getFactoryRule());
	        }
       		add(
       			$current, 
       			"imports",
        		lv_imports_1_0, 
        		"Import");
	        afterParserOrEnumRuleCall();
	    }

)
)*(
(
		{ 
	        newCompositeNode(grammarAccess.getFactoryAccess().getAnnotationsAnnotationParserRuleCall_2_0()); 
	    }
		lv_annotations_2_0=ruleAnnotation		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getFactoryRule());
	        }
       		add(
       			$current, 
       			"annotations",
        		lv_annotations_2_0, 
        		"Annotation");
	        afterParserOrEnumRuleCall();
	    }

)
)*(
(
		{ 
	        newCompositeNode(grammarAccess.getFactoryAccess().getRootNewObjectParserRuleCall_3_0()); 
	    }
		lv_root_3_0=ruleNewObject		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getFactoryRule());
	        }
       		set(
       			$current, 
       			"root",
        		lv_root_3_0, 
        		"NewObject");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRulePackageImport
entryRulePackageImport returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getPackageImportRule()); }
	 iv_rulePackageImport=rulePackageImport 
	 { $current=$iv_rulePackageImport.current; } 
	 EOF 
;

// Rule PackageImport
rulePackageImport returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='use' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getPackageImportAccess().getUseKeyword_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getPackageImportRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getPackageImportAccess().getEPackageEPackageCrossReference_1_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_2='.*' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getPackageImportAccess().getFullStopAsteriskKeyword_2());
    }
)
;





// Entry rule entryRuleImport
entryRuleImport returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getImportRule()); }
	 iv_ruleImport=ruleImport 
	 { $current=$iv_ruleImport.current; } 
	 EOF 
;

// Rule Import
ruleImport returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='import' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
    }
(
(
		lv_importURI_1_0=RULE_STRING
		{
			newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getImportRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"importURI",
        		lv_importURI_1_0, 
        		"STRING");
	    }

)
))
;





// Entry rule entryRuleAnnotation
entryRuleAnnotation returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAnnotationRule()); }
	 iv_ruleAnnotation=ruleAnnotation 
	 { $current=$iv_ruleAnnotation.current; } 
	 EOF 
;

// Rule Annotation
ruleAnnotation returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:

    { 
        newCompositeNode(grammarAccess.getAnnotationAccess().getCustomNameMappingParserRuleCall()); 
    }
    this_CustomNameMapping_0=ruleCustomNameMapping
    { 
        $current = $this_CustomNameMapping_0.current; 
        afterParserOrEnumRuleCall();
    }

;





// Entry rule entryRuleCustomNameMapping
entryRuleCustomNameMapping returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getCustomNameMappingRule()); }
	 iv_ruleCustomNameMapping=ruleCustomNameMapping 
	 { $current=$iv_ruleCustomNameMapping.current; } 
	 EOF 
;

// Rule CustomNameMapping
ruleCustomNameMapping returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='@Name' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getCustomNameMappingAccess().getNameKeyword_0());
    }
	otherlv_1='{' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getCustomNameMappingAccess().getLeftCurlyBracketKeyword_1());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getCustomNameMappingRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getCustomNameMappingAccess().getEClassEClassCrossReference_2_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_3='=' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getCustomNameMappingAccess().getEqualsSignKeyword_3());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getCustomNameMappingRule());
	        }
        }
	otherlv_4=RULE_ID
	{
		newLeafNode(otherlv_4, grammarAccess.getCustomNameMappingAccess().getNameFeatureEAttributeCrossReference_4_0()); 
	}

)
)	otherlv_5='}' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getCustomNameMappingAccess().getRightCurlyBracketKeyword_5());
    }
)
;





// Entry rule entryRuleFeature
entryRuleFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getFeatureRule()); }
	 iv_ruleFeature=ruleFeature 
	 { $current=$iv_ruleFeature.current; } 
	 EOF 
;

// Rule Feature
ruleFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getFeatureRule());
	        }
        }
	otherlv_0=RULE_ID
	{
		newLeafNode(otherlv_0, grammarAccess.getFeatureAccess().getEFeatureEStructuralFeatureCrossReference_0_0()); 
	}

)
)	otherlv_1=':' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getFeatureAccess().getColonKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getFeatureAccess().getValueValueParserRuleCall_2_0()); 
	    }
		lv_value_2_0=ruleValue		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getFeatureRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_2_0, 
        		"Value");
	        afterParserOrEnumRuleCall();
	    }

)
)?)
;





// Entry rule entryRuleNewObject
entryRuleNewObject returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getNewObjectRule()); }
	 iv_ruleNewObject=ruleNewObject 
	 { $current=$iv_ruleNewObject.current; } 
	 EOF 
;

// Rule NewObject
ruleNewObject returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getNewObjectRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getNewObjectAccess().getEClassEClassCrossReference_0_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getNewObjectAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getNewObjectRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)?	otherlv_2='{' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getNewObjectAccess().getLeftCurlyBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getNewObjectAccess().getFeaturesFeatureParserRuleCall_3_0()); 
	    }
		lv_features_3_0=ruleFeature		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getNewObjectRule());
	        }
       		add(
       			$current, 
       			"features",
        		lv_features_3_0, 
        		"Feature");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_4='}' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getNewObjectAccess().getRightCurlyBracketKeyword_4());
    }
)
;





// Entry rule entryRuleValue
entryRuleValue returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getValueRule()); }
	 iv_ruleValue=ruleValue 
	 { $current=$iv_ruleValue.current; } 
	 EOF 
;

// Rule Value
ruleValue returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getValueAccess().getMultiValueParserRuleCall_0()); 
    }
    this_MultiValue_0=ruleMultiValue
    { 
        $current = $this_MultiValue_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getValueAccess().getAttributeParserRuleCall_1()); 
    }
    this_Attribute_1=ruleAttribute
    { 
        $current = $this_Attribute_1.current; 
        afterParserOrEnumRuleCall();
    }

    |((	ruleContainment)=>
    { 
        newCompositeNode(grammarAccess.getValueAccess().getContainmentParserRuleCall_2()); 
    }
    this_Containment_2=ruleContainment
    { 
        $current = $this_Containment_2.current; 
        afterParserOrEnumRuleCall();
    }
)
    |((	ruleReference)=>
    { 
        newCompositeNode(grammarAccess.getValueAccess().getReferenceParserRuleCall_3()); 
    }
    this_Reference_3=ruleReference
    { 
        $current = $this_Reference_3.current; 
        afterParserOrEnumRuleCall();
    }
))
;





// Entry rule entryRuleMultiValue
entryRuleMultiValue returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getMultiValueRule()); }
	 iv_ruleMultiValue=ruleMultiValue 
	 { $current=$iv_ruleMultiValue.current; } 
	 EOF 
;

// Rule MultiValue
ruleMultiValue returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
    {
        $current = forceCreateModelElement(
            grammarAccess.getMultiValueAccess().getMultiValueAction_0(),
            $current);
    }
)	otherlv_1='[' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getMultiValueAccess().getLeftSquareBracketKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getMultiValueAccess().getValuesValueParserRuleCall_2_0()); 
	    }
		lv_values_2_0=ruleValue		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getMultiValueRule());
	        }
       		add(
       			$current, 
       			"values",
        		lv_values_2_0, 
        		"Value");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_3=']' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getMultiValueAccess().getRightSquareBracketKeyword_3());
    }
)
;





// Entry rule entryRuleReference
entryRuleReference returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getReferenceRule()); }
	 iv_ruleReference=ruleReference 
	 { $current=$iv_ruleReference.current; } 
	 EOF 
;

// Rule Reference
ruleReference returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getReferenceRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getReferenceAccess().getValueEObjectCrossReference_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)
;





// Entry rule entryRuleContainment
entryRuleContainment returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getContainmentRule()); }
	 iv_ruleContainment=ruleContainment 
	 { $current=$iv_ruleContainment.current; } 
	 EOF 
;

// Rule Containment
ruleContainment returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getContainmentAccess().getValueNewObjectParserRuleCall_0()); 
	    }
		lv_value_0_0=ruleNewObject		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getContainmentRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"NewObject");
	        afterParserOrEnumRuleCall();
	    }

)
)
;





// Entry rule entryRuleAttribute
entryRuleAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeRule()); }
	 iv_ruleAttribute=ruleAttribute 
	 { $current=$iv_ruleAttribute.current; } 
	 EOF 
;

// Rule Attribute
ruleAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getEnumAttributeParserRuleCall_0()); 
    }
    this_EnumAttribute_0=ruleEnumAttribute
    { 
        $current = $this_EnumAttribute_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getStringAttributeParserRuleCall_1()); 
    }
    this_StringAttribute_1=ruleStringAttribute
    { 
        $current = $this_StringAttribute_1.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getIntegerAttributeParserRuleCall_2()); 
    }
    this_IntegerAttribute_2=ruleIntegerAttribute
    { 
        $current = $this_IntegerAttribute_2.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getBooleanAttributeParserRuleCall_3()); 
    }
    this_BooleanAttribute_3=ruleBooleanAttribute
    { 
        $current = $this_BooleanAttribute_3.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getDoubleAttributeParserRuleCall_4()); 
    }
    this_DoubleAttribute_4=ruleDoubleAttribute
    { 
        $current = $this_DoubleAttribute_4.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getDateAttributeParserRuleCall_5()); 
    }
    this_DateAttribute_5=ruleDateAttribute
    { 
        $current = $this_DateAttribute_5.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getAttributeAccess().getNullAttributeParserRuleCall_6()); 
    }
    this_NullAttribute_6=ruleNullAttribute
    { 
        $current = $this_NullAttribute_6.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleEnumAttribute
entryRuleEnumAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEnumAttributeRule()); }
	 iv_ruleEnumAttribute=ruleEnumAttribute 
	 { $current=$iv_ruleEnumAttribute.current; } 
	 EOF 
;

// Rule EnumAttribute
ruleEnumAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0=':' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getEnumAttributeAccess().getColonKeyword_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getEnumAttributeRule());
	        }
        }
	otherlv_1=RULE_ID
	{
		newLeafNode(otherlv_1, grammarAccess.getEnumAttributeAccess().getValueEEnumLiteralCrossReference_1_0()); 
	}

)
))
;





// Entry rule entryRuleStringAttribute
entryRuleStringAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getStringAttributeRule()); }
	 iv_ruleStringAttribute=ruleStringAttribute 
	 { $current=$iv_ruleStringAttribute.current; } 
	 EOF 
;

// Rule StringAttribute
ruleStringAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_STRING
		{
			newLeafNode(lv_value_0_0, grammarAccess.getStringAttributeAccess().getValueSTRINGTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getStringAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"STRING");
	    }

)
)
;





// Entry rule entryRuleIntegerAttribute
entryRuleIntegerAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getIntegerAttributeRule()); }
	 iv_ruleIntegerAttribute=ruleIntegerAttribute 
	 { $current=$iv_ruleIntegerAttribute.current; } 
	 EOF 
;

// Rule IntegerAttribute
ruleIntegerAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_LONG
		{
			newLeafNode(lv_value_0_0, grammarAccess.getIntegerAttributeAccess().getValueLONGTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getIntegerAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"LONG");
	    }

)
)
;





// Entry rule entryRuleDoubleAttribute
entryRuleDoubleAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDoubleAttributeRule()); }
	 iv_ruleDoubleAttribute=ruleDoubleAttribute 
	 { $current=$iv_ruleDoubleAttribute.current; } 
	 EOF 
;

// Rule DoubleAttribute
ruleDoubleAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_DOUBLE
		{
			newLeafNode(lv_value_0_0, grammarAccess.getDoubleAttributeAccess().getValueDOUBLETerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getDoubleAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"DOUBLE");
	    }

)
)
;





// Entry rule entryRuleDateAttribute
entryRuleDateAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDateAttributeRule()); }
	 iv_ruleDateAttribute=ruleDateAttribute 
	 { $current=$iv_ruleDateAttribute.current; } 
	 EOF 
;

// Rule DateAttribute
ruleDateAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_DATE
		{
			newLeafNode(lv_value_0_0, grammarAccess.getDateAttributeAccess().getValueDATETerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getDateAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"DATE");
	    }

)
)
;





// Entry rule entryRuleNullAttribute
entryRuleNullAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getNullAttributeRule()); }
	 iv_ruleNullAttribute=ruleNullAttribute 
	 { $current=$iv_ruleNullAttribute.current; } 
	 EOF 
;

// Rule NullAttribute
ruleNullAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=	'NULL' 
    {
        newLeafNode(lv_value_0_0, grammarAccess.getNullAttributeAccess().getValueNULLKeyword_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getNullAttributeRule());
	        }
       		setWithLastConsumed($current, "value", lv_value_0_0, "NULL");
	    }

)
)
;





// Entry rule entryRuleBooleanAttribute
entryRuleBooleanAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getBooleanAttributeRule()); }
	 iv_ruleBooleanAttribute=ruleBooleanAttribute 
	 { $current=$iv_ruleBooleanAttribute.current; } 
	 EOF 
;

// Rule BooleanAttribute
ruleBooleanAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_BOOLEAN
		{
			newLeafNode(lv_value_0_0, grammarAccess.getBooleanAttributeAccess().getValueBOOLEANTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getBooleanAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"BOOLEAN");
	    }

)
)
;





// Entry rule entryRuleQualifiedName
entryRuleQualifiedName returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getQualifiedNameRule()); } 
	 iv_ruleQualifiedName=ruleQualifiedName 
	 { $current=$iv_ruleQualifiedName.current.getText(); }  
	 EOF 
;

// Rule QualifiedName
ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_0()); 
    }
    this_ValidID_0=ruleValidID    {
		$current.merge(this_ValidID_0);
    }

    { 
        afterParserOrEnumRuleCall();
    }
(((
	'.' 
)=>
	kw='.' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
    }
)
    { 
        newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_1_1()); 
    }
    this_ValidID_2=ruleValidID    {
		$current.merge(this_ValidID_2);
    }

    { 
        afterParserOrEnumRuleCall();
    }
)*)
    ;





// Entry rule entryRuleValidID
entryRuleValidID returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getValidIDRule()); } 
	 iv_ruleValidID=ruleValidID 
	 { $current=$iv_ruleValidID.current.getText(); }  
	 EOF 
;

// Rule ValidID
ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
    this_ID_0=RULE_ID    {
		$current.merge(this_ID_0);
    }

    { 
    newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall()); 
    }

    ;





RULE_BOOLEAN : ('true'|'false');

RULE_LONG : '-'? ('0'..'9')+;

RULE_DOUBLE : '-'? ('0'..'9')+ '.' ('0'..'9')+;

RULE_DATE : ('0'..'9')+ '.' ('0'..'9')+ '.' ('0'..'9')+;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_'|',') ('a'..'z'|'A'..'Z'|'_'|','|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;


