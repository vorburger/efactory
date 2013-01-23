package com.googlecode.efactory.formatting;

import java.util.Stack;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

public class IntendationFormatter implements KeywordFormatter {

	private String stopKeyword;
	private String startKeyword;
	private Stack<Keyword> openBraceStack;

	public IntendationFormatter(String startKeyword, String stopKeyword) {
		this.startKeyword = startKeyword;
		this.stopKeyword = stopKeyword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.efactory.formatting.KeywordFormatter#apply(org.eclipse
	 * .xtext.formatting.impl.FormattingConfig, java.lang.Iterable)
	 */
	public void apply(FormattingConfig c, Iterable<Keyword> keywords) {
		openBraceStack = new Stack<Keyword>();
		for (Keyword currentKeyword : keywords) {
			if (isStartKeyword(currentKeyword)) {
				saveStartKeyword(currentKeyword);
				applyLeftBraceFormatting(c, currentKeyword);
			} else if (isStopKeyword(currentKeyword)) {
				applyRightBraceFormatting(c, currentKeyword);
				applyIntendation(c, currentKeyword);
			}
		}
	}

	private void applyIntendation(FormattingConfig c, Keyword currentKeyword) {
		if (!openBraceStack.isEmpty()) {
			c.setIndentation(openBraceStack.pop(), currentKeyword);
		}
	}

	private void saveStartKeyword(Keyword currentKeyword) {
		openBraceStack.add(currentKeyword);
	}

	private boolean isStopKeyword(Keyword currentKeyword) {
		return stopKeyword.equals(currentKeyword.getValue());
	}

	private boolean isStartKeyword(Keyword currentKeyword) {
		return startKeyword.equals(currentKeyword.getValue());
	}

	private void applyRightBraceFormatting(FormattingConfig c,
			Keyword currentKeyword) {
		c.setLinewrap(2).after(currentKeyword);
		c.setLinewrap().before(currentKeyword);
	}

	private void applyLeftBraceFormatting(FormattingConfig c,
			Keyword currentKeyword) {
		c.setLinewrap().after(currentKeyword);
	}

}
