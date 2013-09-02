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

	@Override public void enterTagIf(CFMLParser.TagIfContext ctx) {
		String expr = ctxSubstr(ctx.condIf(), 2);
		print("if (" + expr + ") {\n");
		entab();
	}

	@Override public void exitTagIf(CFMLParser.TagIfContext ctx) {
		detab();
		print("}\n");
	}

	@Override public void enterTagElseIf(CFMLParser.TagElseIfContext ctx) {
		detab();
		String expr = ctxSubstr(ctx.condElseIf(), 6);
		print("}\n");
		print("else if (" + expr + ") {\n");
		entab();
	}

	@Override public void exitTagElseIf(CFMLParser.TagElseIfContext ctx) {
		/* nop */
	}

	@Override public void enterTagElse(CFMLParser.TagElseContext ctx) {
		detab();
		print("}\n");
		print("else {\n");
		entab();
	}

	@Override public void exitTagElse(CFMLParser.TagElseContext ctx) {
		/* nop */
	}

	@Override public void exitAssignment(CFMLParser.AssignmentContext ctx) {
		String assignment = StringUtils.chop(ctx.ASSIGNMENT().getText());
		print(ctx.IDENTIFIER().getText() + " " + assignment);
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

	@Override public void exitLiteral(CFMLParser.LiteralContext ctx) {
		if (ctx.BOOLEAN_LITERAL() != null) {
			print(ctx.BOOLEAN_LITERAL().getText());
		}
		else {
			print(ctx.STRING_LITERAL().getText());
		}
	}

	private void entab() {
		depth ++;
	}

	private void detab() {
		if (depth > 0) { depth --; }
	}

	private String ctxSubstr(ParserRuleContext ctx, int start) {
		return StringUtils.chop(ctx.getText().substring(start));
	}

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(String s) {
		String indents = StringUtils.repeat("\t", depth);
		System.out.print(indents + s);
	}
}
