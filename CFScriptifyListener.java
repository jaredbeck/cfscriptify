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
		print(depth, "if (" + expr + ") {\n");
		depth++;
	}

	@Override public void exitTagIf(CFMLParser.TagIfContext ctx) {
		depth--;
		printBlockClose();
	}

	@Override public void enterTagElseIf(CFMLParser.TagElseIfContext ctx) {
		String expr = ctxSubstr(ctx.condElseIf(), 6);
		print(depth - 1, "}\nelse if (" + expr + ") {\n");
	}

	@Override public void exitTagElseIf(CFMLParser.TagElseIfContext ctx) {
		/* nop */
	}

	@Override public void enterTagElse(CFMLParser.TagElseContext ctx) {
		print(depth - 1, "}\nelse {\n");
	}

	@Override public void exitTagElse(CFMLParser.TagElseContext ctx) {
		depth--;
	}

	@Override public void exitAssignment(CFMLParser.AssignmentContext ctx) {
		String assignment = StringUtils.chop(ctx.ASSIGNMENT().getText());
		print(depth, ctx.IDENTIFIER().getText() + " " + assignment);
	}

	@Override public void enterCfcomment(CFMLParser.CfcommentContext ctx) {
		print(depth, "/* " + getCFCommentInnerText(ctx.getText()) + " */\n");
	}

	@Override public void exitLine(CFMLParser.LineContext ctx) {
		print(0, ";\n");
	}

	@Override public void exitTagAbort(CFMLParser.TagAbortContext ctx) {
		print(depth, "abort");
	}

	@Override public void exitLiteral(CFMLParser.LiteralContext ctx) {
		if (ctx.BOOLEAN_LITERAL() != null) {
			print(depth, ctx.BOOLEAN_LITERAL().getText());
		}
		else {
			print(depth, ctx.STRING_LITERAL().getText());
		}
	}

	private String ctxSubstr(ParserRuleContext ctx, int start) {
		return StringUtils.chop(ctx.getText().substring(start));
	}

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(int indentDepth, String s) {
		String indents = StringUtils.repeat("\t", indentDepth);
		System.out.print(indents + s);
	}

	private void printBlockClose() {
		print(depth, "}\n");
	}
}
