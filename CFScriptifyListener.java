// Generated from CFML.g4 by ANTLR 4.0

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

public class CFScriptifyListener extends CFMLBaseListener {
	@Override public void enterExpression(CFMLParser.ExpressionContext ctx) { }
	@Override public void exitExpression(CFMLParser.ExpressionContext ctx) { }

	@Override public void enterTagSet(CFMLParser.TagSetContext ctx) {
    print(ctx.IDENTIFIER().getText() + " = ");
	}
	@Override public void exitTagSet(CFMLParser.TagSetContext ctx) { }

	@Override public void enterCfm(CFMLParser.CfmContext ctx) { }
	@Override public void exitCfm(CFMLParser.CfmContext ctx) { }

	@Override public void enterLine(CFMLParser.LineContext ctx) { }
	@Override public void exitLine(CFMLParser.LineContext ctx) {
		print(";\n");
	}

	@Override public void enterTagAbort(CFMLParser.TagAbortContext ctx) { }
	@Override public void exitTagAbort(CFMLParser.TagAbortContext ctx) {
		print("abort");
	}

	@Override public void enterLiteral(CFMLParser.LiteralContext ctx) { }
	@Override public void exitLiteral(CFMLParser.LiteralContext ctx) {
		if (ctx.BOOLEAN_LITERAL() != null) {
			print(ctx.BOOLEAN_LITERAL().getText());
		}
		else {
			print(ctx.STRING_LITERAL().getText());
		}
	}

	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }

	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }

	private void print(String s) {
		System.out.print(s);
	}
}
