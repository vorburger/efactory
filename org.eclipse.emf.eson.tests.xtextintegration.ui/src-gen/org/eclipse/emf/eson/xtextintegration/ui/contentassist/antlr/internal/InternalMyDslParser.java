package org.eclipse.emf.eson.xtextintegration.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import org.eclipse.emf.eson.xtextintegration.services.MyDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMyDslParser extends AbstractInternalContentAssistParser {
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
    public String getGrammarFileName() { return "../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g"; }


     
     	private MyDslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(MyDslGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleModel"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:60:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:61:1: ( ruleModel EOF )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:62:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel61);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:69:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:73:2: ( ( ( rule__Model__Group__0 ) ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:74:1: ( ( rule__Model__Group__0 ) )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:74:1: ( ( rule__Model__Group__0 ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:75:1: ( rule__Model__Group__0 )
            {
             before(grammarAccess.getModelAccess().getGroup()); 
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:76:1: ( rule__Model__Group__0 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:76:2: rule__Model__Group__0
            {
            pushFollow(FOLLOW_rule__Model__Group__0_in_ruleModel94);
            rule__Model__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleGreeting"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:88:1: entryRuleGreeting : ruleGreeting EOF ;
    public final void entryRuleGreeting() throws RecognitionException {
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:89:1: ( ruleGreeting EOF )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:90:1: ruleGreeting EOF
            {
             before(grammarAccess.getGreetingRule()); 
            pushFollow(FOLLOW_ruleGreeting_in_entryRuleGreeting121);
            ruleGreeting();

            state._fsp--;

             after(grammarAccess.getGreetingRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGreeting128); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGreeting"


    // $ANTLR start "ruleGreeting"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:97:1: ruleGreeting : ( ( rule__Greeting__Group__0 ) ) ;
    public final void ruleGreeting() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:101:2: ( ( ( rule__Greeting__Group__0 ) ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:102:1: ( ( rule__Greeting__Group__0 ) )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:102:1: ( ( rule__Greeting__Group__0 ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:103:1: ( rule__Greeting__Group__0 )
            {
             before(grammarAccess.getGreetingAccess().getGroup()); 
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:104:1: ( rule__Greeting__Group__0 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:104:2: rule__Greeting__Group__0
            {
            pushFollow(FOLLOW_rule__Greeting__Group__0_in_ruleGreeting154);
            rule__Greeting__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGreetingAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGreeting"


    // $ANTLR start "rule__Model__Group__0"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:118:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:122:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:123:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__0188);
            rule__Model__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Model__Group__1_in_rule__Model__Group__0191);
            rule__Model__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:130:1: rule__Model__Group__0__Impl : ( 'Greetings' ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:134:1: ( ( 'Greetings' ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:135:1: ( 'Greetings' )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:135:1: ( 'Greetings' )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:136:1: 'Greetings'
            {
             before(grammarAccess.getModelAccess().getGreetingsKeyword_0()); 
            match(input,11,FOLLOW_11_in_rule__Model__Group__0__Impl219); 
             after(grammarAccess.getModelAccess().getGreetingsKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:149:1: rule__Model__Group__1 : rule__Model__Group__1__Impl rule__Model__Group__2 ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:153:1: ( rule__Model__Group__1__Impl rule__Model__Group__2 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:154:2: rule__Model__Group__1__Impl rule__Model__Group__2
            {
            pushFollow(FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__1250);
            rule__Model__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Model__Group__2_in_rule__Model__Group__1253);
            rule__Model__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:161:1: rule__Model__Group__1__Impl : ( ( rule__Model__NameAssignment_1 ) ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:165:1: ( ( ( rule__Model__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:166:1: ( ( rule__Model__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:166:1: ( ( rule__Model__NameAssignment_1 ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:167:1: ( rule__Model__NameAssignment_1 )
            {
             before(grammarAccess.getModelAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:168:1: ( rule__Model__NameAssignment_1 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:168:2: rule__Model__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Model__NameAssignment_1_in_rule__Model__Group__1__Impl280);
            rule__Model__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Model__Group__2"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:178:1: rule__Model__Group__2 : rule__Model__Group__2__Impl ;
    public final void rule__Model__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:182:1: ( rule__Model__Group__2__Impl )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:183:2: rule__Model__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Model__Group__2__Impl_in_rule__Model__Group__2310);
            rule__Model__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2"


    // $ANTLR start "rule__Model__Group__2__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:189:1: rule__Model__Group__2__Impl : ( ( rule__Model__GreetingsAssignment_2 )* ) ;
    public final void rule__Model__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:193:1: ( ( ( rule__Model__GreetingsAssignment_2 )* ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:194:1: ( ( rule__Model__GreetingsAssignment_2 )* )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:194:1: ( ( rule__Model__GreetingsAssignment_2 )* )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:195:1: ( rule__Model__GreetingsAssignment_2 )*
            {
             before(grammarAccess.getModelAccess().getGreetingsAssignment_2()); 
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:196:1: ( rule__Model__GreetingsAssignment_2 )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:196:2: rule__Model__GreetingsAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Model__GreetingsAssignment_2_in_rule__Model__Group__2__Impl337);
            	    rule__Model__GreetingsAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getGreetingsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2__Impl"


    // $ANTLR start "rule__Greeting__Group__0"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:212:1: rule__Greeting__Group__0 : rule__Greeting__Group__0__Impl rule__Greeting__Group__1 ;
    public final void rule__Greeting__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:216:1: ( rule__Greeting__Group__0__Impl rule__Greeting__Group__1 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:217:2: rule__Greeting__Group__0__Impl rule__Greeting__Group__1
            {
            pushFollow(FOLLOW_rule__Greeting__Group__0__Impl_in_rule__Greeting__Group__0374);
            rule__Greeting__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Greeting__Group__1_in_rule__Greeting__Group__0377);
            rule__Greeting__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__0"


    // $ANTLR start "rule__Greeting__Group__0__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:224:1: rule__Greeting__Group__0__Impl : ( 'Hello' ) ;
    public final void rule__Greeting__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:228:1: ( ( 'Hello' ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:229:1: ( 'Hello' )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:229:1: ( 'Hello' )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:230:1: 'Hello'
            {
             before(grammarAccess.getGreetingAccess().getHelloKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__Greeting__Group__0__Impl405); 
             after(grammarAccess.getGreetingAccess().getHelloKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__0__Impl"


    // $ANTLR start "rule__Greeting__Group__1"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:243:1: rule__Greeting__Group__1 : rule__Greeting__Group__1__Impl rule__Greeting__Group__2 ;
    public final void rule__Greeting__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:247:1: ( rule__Greeting__Group__1__Impl rule__Greeting__Group__2 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:248:2: rule__Greeting__Group__1__Impl rule__Greeting__Group__2
            {
            pushFollow(FOLLOW_rule__Greeting__Group__1__Impl_in_rule__Greeting__Group__1436);
            rule__Greeting__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Greeting__Group__2_in_rule__Greeting__Group__1439);
            rule__Greeting__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__1"


    // $ANTLR start "rule__Greeting__Group__1__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:255:1: rule__Greeting__Group__1__Impl : ( ( rule__Greeting__NameAssignment_1 ) ) ;
    public final void rule__Greeting__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:259:1: ( ( ( rule__Greeting__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:260:1: ( ( rule__Greeting__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:260:1: ( ( rule__Greeting__NameAssignment_1 ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:261:1: ( rule__Greeting__NameAssignment_1 )
            {
             before(grammarAccess.getGreetingAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:262:1: ( rule__Greeting__NameAssignment_1 )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:262:2: rule__Greeting__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Greeting__NameAssignment_1_in_rule__Greeting__Group__1__Impl466);
            rule__Greeting__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGreetingAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__1__Impl"


    // $ANTLR start "rule__Greeting__Group__2"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:272:1: rule__Greeting__Group__2 : rule__Greeting__Group__2__Impl ;
    public final void rule__Greeting__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:276:1: ( rule__Greeting__Group__2__Impl )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:277:2: rule__Greeting__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Greeting__Group__2__Impl_in_rule__Greeting__Group__2496);
            rule__Greeting__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__2"


    // $ANTLR start "rule__Greeting__Group__2__Impl"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:283:1: rule__Greeting__Group__2__Impl : ( '!' ) ;
    public final void rule__Greeting__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:287:1: ( ( '!' ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:288:1: ( '!' )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:288:1: ( '!' )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:289:1: '!'
            {
             before(grammarAccess.getGreetingAccess().getExclamationMarkKeyword_2()); 
            match(input,13,FOLLOW_13_in_rule__Greeting__Group__2__Impl524); 
             after(grammarAccess.getGreetingAccess().getExclamationMarkKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__Group__2__Impl"


    // $ANTLR start "rule__Model__NameAssignment_1"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:309:1: rule__Model__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Model__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:313:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:314:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:314:1: ( RULE_ID )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:315:1: RULE_ID
            {
             before(grammarAccess.getModelAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Model__NameAssignment_1566); 
             after(grammarAccess.getModelAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__NameAssignment_1"


    // $ANTLR start "rule__Model__GreetingsAssignment_2"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:324:1: rule__Model__GreetingsAssignment_2 : ( ruleGreeting ) ;
    public final void rule__Model__GreetingsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:328:1: ( ( ruleGreeting ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:329:1: ( ruleGreeting )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:329:1: ( ruleGreeting )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:330:1: ruleGreeting
            {
             before(grammarAccess.getModelAccess().getGreetingsGreetingParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleGreeting_in_rule__Model__GreetingsAssignment_2597);
            ruleGreeting();

            state._fsp--;

             after(grammarAccess.getModelAccess().getGreetingsGreetingParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__GreetingsAssignment_2"


    // $ANTLR start "rule__Greeting__NameAssignment_1"
    // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:339:1: rule__Greeting__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Greeting__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:343:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:344:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:344:1: ( RULE_ID )
            // ../org.eclipse.emf.eson.tests.xtextintegration.ui/src-gen/org/eclipse/emf/eson/xtextintegration/ui/contentassist/antlr/internal/InternalMyDsl.g:345:1: RULE_ID
            {
             before(grammarAccess.getGreetingAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Greeting__NameAssignment_1628); 
             after(grammarAccess.getGreetingAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Greeting__NameAssignment_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__0_in_ruleModel94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGreeting_in_entryRuleGreeting121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGreeting128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Greeting__Group__0_in_ruleGreeting154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__0188 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Model__Group__1_in_rule__Model__Group__0191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Model__Group__0__Impl219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__1250 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__Model__Group__2_in_rule__Model__Group__1253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__NameAssignment_1_in_rule__Model__Group__1__Impl280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__2__Impl_in_rule__Model__Group__2310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__GreetingsAssignment_2_in_rule__Model__Group__2__Impl337 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_rule__Greeting__Group__0__Impl_in_rule__Greeting__Group__0374 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Greeting__Group__1_in_rule__Greeting__Group__0377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Greeting__Group__0__Impl405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Greeting__Group__1__Impl_in_rule__Greeting__Group__1436 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__Greeting__Group__2_in_rule__Greeting__Group__1439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Greeting__NameAssignment_1_in_rule__Greeting__Group__1__Impl466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Greeting__Group__2__Impl_in_rule__Greeting__Group__2496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Greeting__Group__2__Impl524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Model__NameAssignment_1566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGreeting_in_rule__Model__GreetingsAssignment_2597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Greeting__NameAssignment_1628 = new BitSet(new long[]{0x0000000000000002L});

}