package org.eclipse.emf.eson.xtextintegration.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.emf.eson.xtextintegration.services.MyDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMyDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Greetings'", "'Hello'", "'!'"
    };
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_STRING=6;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_INT=5;
    public static final int T__11=11;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int EOF=-1;

    // delegates
    // delegators


        public InternalMyDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMyDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMyDslParser.tokenNames; }
    public String getGrammarFileName() { return "../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g"; }



     	private MyDslGrammarAccess grammarAccess;
     	
        public InternalMyDslParser(TokenStream input, MyDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Model";	
       	}
       	
       	@Override
       	protected MyDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModel"
    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:67:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:68:2: (iv_ruleModel= ruleModel EOF )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:69:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel75);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:76:1: ruleModel returns [EObject current=null] : (otherlv_0= 'Greetings' ( (lv_name_1_0= RULE_ID ) ) ( (lv_greetings_2_0= ruleGreeting ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        EObject lv_greetings_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:79:28: ( (otherlv_0= 'Greetings' ( (lv_name_1_0= RULE_ID ) ) ( (lv_greetings_2_0= ruleGreeting ) )* ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:80:1: (otherlv_0= 'Greetings' ( (lv_name_1_0= RULE_ID ) ) ( (lv_greetings_2_0= ruleGreeting ) )* )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:80:1: (otherlv_0= 'Greetings' ( (lv_name_1_0= RULE_ID ) ) ( (lv_greetings_2_0= ruleGreeting ) )* )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:80:3: otherlv_0= 'Greetings' ( (lv_name_1_0= RULE_ID ) ) ( (lv_greetings_2_0= ruleGreeting ) )*
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleModel122); 

                	newLeafNode(otherlv_0, grammarAccess.getModelAccess().getGreetingsKeyword_0());
                
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:84:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:85:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:85:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:86:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModel139); 

            			newLeafNode(lv_name_1_0, grammarAccess.getModelAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getModelRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:102:2: ( (lv_greetings_2_0= ruleGreeting ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:103:1: (lv_greetings_2_0= ruleGreeting )
            	    {
            	    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:103:1: (lv_greetings_2_0= ruleGreeting )
            	    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:104:3: lv_greetings_2_0= ruleGreeting
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModelAccess().getGreetingsGreetingParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGreeting_in_ruleModel165);
            	    lv_greetings_2_0=ruleGreeting();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"greetings",
            	            		lv_greetings_2_0, 
            	            		"Greeting");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleGreeting"
    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:128:1: entryRuleGreeting returns [EObject current=null] : iv_ruleGreeting= ruleGreeting EOF ;
    public final EObject entryRuleGreeting() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGreeting = null;


        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:129:2: (iv_ruleGreeting= ruleGreeting EOF )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:130:2: iv_ruleGreeting= ruleGreeting EOF
            {
             newCompositeNode(grammarAccess.getGreetingRule()); 
            pushFollow(FOLLOW_ruleGreeting_in_entryRuleGreeting202);
            iv_ruleGreeting=ruleGreeting();

            state._fsp--;

             current =iv_ruleGreeting; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGreeting212); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGreeting"


    // $ANTLR start "ruleGreeting"
    // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:137:1: ruleGreeting returns [EObject current=null] : (otherlv_0= 'Hello' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '!' ) ;
    public final EObject ruleGreeting() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:140:28: ( (otherlv_0= 'Hello' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '!' ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:141:1: (otherlv_0= 'Hello' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '!' )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:141:1: (otherlv_0= 'Hello' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '!' )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:141:3: otherlv_0= 'Hello' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '!'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_12_in_ruleGreeting249); 

                	newLeafNode(otherlv_0, grammarAccess.getGreetingAccess().getHelloKeyword_0());
                
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:145:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:146:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:146:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.eson.tests.xtextintegration/src-gen/org/eclipse/emf/eson/xtextintegration/parser/antlr/internal/InternalMyDsl.g:147:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGreeting266); 

            			newLeafNode(lv_name_1_0, grammarAccess.getGreetingAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGreetingRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_13_in_ruleGreeting283); 

                	newLeafNode(otherlv_2, grammarAccess.getGreetingAccess().getExclamationMarkKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGreeting"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleModel122 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModel139 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ruleGreeting_in_ruleModel165 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ruleGreeting_in_entryRuleGreeting202 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGreeting212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleGreeting249 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGreeting266 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleGreeting283 = new BitSet(new long[]{0x0000000000000002L});

}