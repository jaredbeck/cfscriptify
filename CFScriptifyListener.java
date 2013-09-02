// Generated from CFML.g4 by ANTLR 4.0

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

import org.apache.commons.lang3.StringUtils;

public class CFScriptifyListener extends CFMLBaseListener {

	@Override public void exitAssignment(CFMLParser.AssignmentContext ctx) {
		String assignment = StringUtils.chop(ctx.ASSIGNMENT().getText());
		print(ctx.IDENTIFIER().getText() + " " + assignment);
	}

	@Override public void enterCfcomment(CFMLParser.CfcommentContext ctx) {
		print("/* " + getCFCommentInnerText(ctx.getText()) + " */\n");
	}

	@Override public void exitLine(CFMLParser.LineContext ctx) {
		print(";\n");
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

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(String s) {
		System.out.print(s);
	}
}
