package org.eclipse.emf.eson.xtextintegration.formatting;

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

public class MyDslFormatter extends AbstractDeclarativeFormatter {
	
	@Override
	protected void configureFormatting(FormattingConfig c) {
// It's usually a good idea to activate the following three statements.
// They will add and preserve newlines around comments
//		c.setLinewrap(0, 1, 2).before(getGrammarAccess().getSL_COMMENTRule());
//		c.setLinewrap(0, 1, 2).before(getGrammarAccess().getML_COMMENTRule());
//		c.setLinewrap(0, 1, 1).after(getGrammarAccess().getML_COMMENTRule());
	}
}
