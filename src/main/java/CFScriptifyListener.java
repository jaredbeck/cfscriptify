import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.ErrorNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
		String expression = trimOctothorps(CFScript.dequote(exprAttrText));
		print("switch (" + expression + ") {\n");
		entab();
	}

	@Override public void exitTagSwitch(CFMLParser.TagSwitchContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfcase> */
	@Override public void enterTagCase(CFMLParser.TagCaseContext ctx) {
		String value = trimOctothorps(ctx.attribute().STRING_LITERAL().getText());
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
		String args = ctxSubstr(ctx.CFTHROW().getText(), 9);
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
		print("param" + ctxSubstr(ctx.CFPARAM().getText(), 8));
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
		String from 	= trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_FROM()), 6));
		String to 		= trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_TO()), 4));
		String index 	= trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7));
		String step 	= trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_STEP()), 6));
		if (step.length() == 0) { step = "1"; }
		String op = loopComparison(from, to, step);

		String begin = String.format("%s = %s", index, from);
		String middle = String.format("%s %s %s", descope(index), op, to);
		String end = stepStmt(descope(index), step);

		print(String.format("for (%s; %s; %s) {\n", begin, middle, end));
		entab();
		if (op == "NEQ") { printWarning("is NEQ what you wanted?"); }
	}

	@Override public void exitTagLoopFrom(CFMLParser.TagLoopFromContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfloop array="" index=""> */
	@Override public void enterTagLoopArray(CFMLParser.TagLoopArrayContext ctx) {
		String array = trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_ARRAY()), 7));
		String index = ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7);
		print ("for (" + index + " in " + array + ") {\n");
		entab();
	}

	@Override public void exitTagLoopArray(CFMLParser.TagLoopArrayContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfloop list="" index=""> */
	@Override public void enterTagLoopList(CFMLParser.TagLoopListContext ctx) {
		String list = trimOctothorps(ctxSubstr(CFScript.firstTextIn(ctx.ATR_LIST()), 6));
		String index = ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7);
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

	private String ctxSubstr(String ctxText, int start) {
		if (ctxText.length() <= start) { return ""; }
		return StringUtils.chop(ctxText.substring(start));
	}

	private String descope(String dotScoped) {
		String[] parts = dotScoped.split("\\.");
		return parts[parts.length - 1];
	}

	private void detab() {
		if (depth > 0) { depth --; }
	}

	private void entab() {
		depth ++;
	}

	private String loopComparison(String from, String to, String step) {
		boolean direction;
		if (NumberUtils.isNumber(step)) {
			direction = Double.parseDouble(step) > 0; // true means ascending
		}
		else if (NumberUtils.isNumber(from) && NumberUtils.isNumber(to)) {
			direction = Double.parseDouble(from) < Double.parseDouble(to);
		}
		else {
			/* If none of the attributes are numeric, we can't determine
			the direction.  The best possible guess is NEQ.  Thankfully,
			a non-numeric step is quite uncommon. */
			return "NEQ";
		}
		return direction ? "LTE" : "GTE";
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

	private String stepStmt(String index, String step) {
		try {
			int s = Integer.parseInt(step);
			if (s == 1) { return index + "++"; }
			else if (s == -1) { return index + "--"; }
			else { return index + " += " + String.valueOf(s); }
		}
		catch (NumberFormatException e) {
			return index + " += " + step;
		}
	}

	private String trimOctothorps(String s) {
		return StringUtils.strip(s, "#");
	}
}
