// Generated from CFML.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface CFMLListener extends ParseTreeListener {
	void enterExpression(CFMLParser.ExpressionContext ctx);
	void exitExpression(CFMLParser.ExpressionContext ctx);

	void enterAssignment(CFMLParser.AssignmentContext ctx);
	void exitAssignment(CFMLParser.AssignmentContext ctx);

	void enterTagSet(CFMLParser.TagSetContext ctx);
	void exitTagSet(CFMLParser.TagSetContext ctx);

	void enterCfm(CFMLParser.CfmContext ctx);
	void exitCfm(CFMLParser.CfmContext ctx);

	void enterCfcomment(CFMLParser.CfcommentContext ctx);
	void exitCfcomment(CFMLParser.CfcommentContext ctx);

	void enterLine(CFMLParser.LineContext ctx);
	void exitLine(CFMLParser.LineContext ctx);

	void enterTagAbort(CFMLParser.TagAbortContext ctx);
	void exitTagAbort(CFMLParser.TagAbortContext ctx);

	void enterLiteral(CFMLParser.LiteralContext ctx);
	void exitLiteral(CFMLParser.LiteralContext ctx);
}