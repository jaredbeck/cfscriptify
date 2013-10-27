import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

import org.apache.commons.lang3.StringUtils;

public class CFScriptifyListener extends CFMLBaseListener {

	private int depth; // for indentation

	public CFScriptifyListener() {
		depth = 0;
	}

	@Override public void enterTagLog(CFMLParser.TagLogContext ctx) {
		print(new WriteLog(ctx).toString());
	}

	@Override public void enterTagComponent(CFMLParser.TagComponentContext ctx) {
		String atrs = CFScript.atrsToString(ctx.attribute(), " ");
		String line = "component ";
		if (atrs.length() > 0) { line += atrs + " "; }
		print(line + "{\n");
		entab();
	}

	@Override public void exitTagComponent(CFMLParser.TagComponentContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfreturn> */
	@Override public void enterTagReturn(CFMLParser.TagReturnContext ctx) {
		String expr = CFScript.expressionToString(ctx.expression());
		print(String.format("return %s;\n", expr));
	}

	/* <cffunction> */
	@Override public void enterTagFunction(CFMLParser.TagFunctionContext ctx) {
		String name 	= CFScript.atrVal(CFScript.firstTextIn(ctx.ATR_NAME()));
		String rtyp 	= CFScript.atrVal(CFScript.firstTextIn(ctx.ATR_RETURNTYPE()));
		String access = CFScript.atrVal(CFScript.firstTextIn(ctx.ATR_ACCESS()));
		String args = CFScript.argumentsToString(ctx.tagArgument());
		print(String.format("%s %s function %s(%s) {\n", access, rtyp, name, args));
		entab();
	}

	@Override public void exitTagFunction(CFMLParser.TagFunctionContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfswitch> */
	@Override public void enterTagSwitch(CFMLParser.TagSwitchContext ctx) {
		String exprAttrText = ctx.attribute().STRING_LITERAL().getText();
		String expression = CFScript.trimOctothorps(CFScript.dequote(exprAttrText));
		print("switch (" + expression + ") {\n");
		entab();
	}

	@Override public void exitTagSwitch(CFMLParser.TagSwitchContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfcase> */
	@Override public void enterTagCase(CFMLParser.TagCaseContext ctx) {
		String value = CFScript.trimOctothorps(ctx.attribute().STRING_LITERAL().getText());
		print("case " + value + ":\n");
		entab();
	}

	/* Add a break statement to every `case` to prevent fallthrough.
	".. if you omit [break], ColdFusion executes all the statements
	in the following case statement, even if that case is false. In
	nearly all circumstances, this is not what you want to do."
	[Using switch and case statements](http://adobe.ly/TQLp4t) */
	@Override public void exitTagCase(CFMLParser.TagCaseContext ctx) {
		print("break;\n");
		detab();
	}

	/* <cfdefaultcase> */
	@Override public void enterTagDefaultCase(CFMLParser.TagDefaultCaseContext ctx) {
		print("default:\n");
		entab();
	}

	@Override public void exitTagDefaultCase(CFMLParser.TagDefaultCaseContext ctx) {
		detab();
	}


	/* <cffinally> */
	@Override public void enterTagFinally(CFMLParser.TagFinallyContext ctx) {
		print("finally {\n");
		entab();
	}

	@Override public void exitTagFinally(CFMLParser.TagFinallyContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfthrow> */
	@Override public void enterTagThrow(CFMLParser.TagThrowContext ctx) {
		String args = CFScript.ctxSubstr(ctx.CFTHROW().getText(), 9);
		print("Throw(" + args + ")");
	}

	/* <cfrethrow> */
	@Override public void enterTagRethrow(CFMLParser.TagRethrowContext ctx) {
		print("rethrow");
	}

	/* <cfcatch type="foo"> */
	@Override public void enterTagCatch(CFMLParser.TagCatchContext ctx) {
		TerminalNode atrType = ctx.ATR_TYPE();
		String type = (atrType == null) ? "any" : CFScript.atrVal(atrType.getText());
		print("catch(" + type + " cfcatch) {\n");
		entab();
	}

	@Override public void exitTagCatch(CFMLParser.TagCatchContext ctx) {
		detab();
		print("}\n");
	}

	/* <cftry> */
	@Override public void enterTagTry(CFMLParser.TagTryContext ctx) {
		print("try {\n");
		entab();
	}

	@Override public void exitTagTry(CFMLParser.TagTryContext ctx) {
		detab();
		print("}\n");
	}

	@Override public void enterTagParam(CFMLParser.TagParamContext ctx) {
		print("param" + CFScript.ctxSubstr(ctx.CFPARAM().getText(), 8));
	}

	@Override public void enterTagBreak(CFMLParser.TagBreakContext ctx) {
		print("break");
	}

	@Override public void enterTagInclude(CFMLParser.TagIncludeContext ctx) {
		print("include " + ctx.attribute().STRING_LITERAL().getText());
	}

	/* <cfscript> */
	@Override public void enterTagScript(CFMLParser.TagScriptContext ctx) {
		String scr = StringUtils.substringBetween(ctx.CFSCRIPT().getText(), "<cfscript>", "</cfscript>");
		String[] lines = StringUtils.split(scr, "\r\n");
		int offset = firstLineOffset(lines);
		for (int i = 0; i < lines.length; i++) {
			print(lines[i].substring(offset) + "\n");
		}
	}

	/* <cfloop from="" to="" index="" step=""> */
	@Override public void enterTagLoopFrom(CFMLParser.TagLoopFromContext ctx) {
		ForLoop l = new ForLoop(ctx);
		print(l.toString());
		print(" {\n");
		entab();
		if (l.op() == "NEQ") { printWarning("is NEQ what you wanted?"); }
	}

	@Override public void exitTagLoopFrom(CFMLParser.TagLoopFromContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfloop array="" index=""> */
	@Override public void enterTagLoopArray(CFMLParser.TagLoopArrayContext ctx) {
		String array = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_ARRAY()), 7));
		String index = CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7);
		print ("for (" + index + " in " + array + ") {\n");
		entab();
	}

	@Override public void exitTagLoopArray(CFMLParser.TagLoopArrayContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfloop list="" index=""> */
	@Override public void enterTagLoopList(CFMLParser.TagLoopListContext ctx) {
		String list = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_LIST()), 6));
		String index = CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7);
		print("for (" + index + " in ListToArray(" + list + ")) {\n");
		entab();
	}

	@Override public void exitTagLoopList(CFMLParser.TagLoopListContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfif> */
	@Override public void enterTagIf(CFMLParser.TagIfContext ctx) {
		String expr = CFScript.expressionToString(ctx.expression());
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
		String expr = CFScript.expressionToString(ctx.expression());
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

	@Override public void enterTagSet(CFMLParser.TagSetContext ctx) {
		if (ctx.expression() != null) {
			print(CFScript.expressionToString(ctx.expression()));
		}
		else {
			print(CFScript.assignmentToString(ctx.assignment()));
		}
	}

	@Override public void enterCfcomment(CFMLParser.CfcommentContext ctx) {
		print("/* " + getCFCommentInnerText(ctx.getText()) + " */\n");
	}

	@Override public void exitLineTag(CFMLParser.LineTagContext ctx) {
		System.out.print(";\n");
	}

	@Override public void exitTagAbort(CFMLParser.TagAbortContext ctx) {
		print("abort");
	}

	private int countLeadingWS(String s) {
		Pattern pattern = Pattern.compile("^\\s*");
		Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.end() : 0;
	}

	private void detab() {
		if (depth > 0) { depth --; }
	}

	private void entab() {
		depth ++;
	}

	private int firstLineOffset(String[] lines) {
		return lines.length == 0 ? 0 : countLeadingWS(lines[0]);
	}

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(String s) {
		System.out.print(StringUtils.repeat("\t", depth) + s);
	}

	private void printWarning(String s) {
		print("/* cfscriptify warning: " + s + " */\n");
	}
}
