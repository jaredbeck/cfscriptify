// Generated from CFML.g4 by ANTLR 4.0

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

import org.apache.commons.lang3.StringUtils;

public class CFScriptifyListener extends CFMLBaseListener {

	private int depth; // for indentation

	public void CFScriptifyListener() {
		depth = 0;
	}

	/* <cfif> */
	@Override public void enterTagIf(CFMLParser.TagIfContext ctx) {
		String expr = ctxSubstr(ctx.CFIF().getText(), 6);
		print("if (" + expr + ") {\n");
		entab();
	}

	@Override public void exitTagIf(CFMLParser.TagIfContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfelseif> */
	@Override public void enterTagElseIf(CFMLParser.TagElseIfContext ctx) {
		detab();
		String expr = ctxSubstr(ctx.CFELSEIF().getText(), 10);
		print("}\n");
		print("else if (" + expr + ") {\n");
		entab();
	}

	/* <cfelse> */
	@Override public void enterTagElse(CFMLParser.TagElseContext ctx) {
		detab();
		print("}\n");
		print("else {\n");
		entab();
	}

	/* <cfset> */
	@Override public void enterTagSet(CFMLParser.TagSetContext ctx) {
		print(ctxSubstr(ctx.getText(), 7));
	}

	@Override public void enterCfcomment(CFMLParser.CfcommentContext ctx) {
		print("/* " + getCFCommentInnerText(ctx.getText()) + " */\n");
	}

	@Override public void exitLine(CFMLParser.LineContext ctx) {
		System.out.print(";\n");
	}

	@Override public void exitTagAbort(CFMLParser.TagAbortContext ctx) {
		print("abort");
	}

	private void entab() {
		depth ++;
	}

	private void detab() {
		if (depth > 0) { depth --; }
	}

	private String ctxSubstr(String ctxText, int start) {
		return StringUtils.chop(ctxText.substring(start));
	}

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(String s) {
		String indents = StringUtils.repeat("\t", depth);
		System.out.print(indents + s);
	}
}
