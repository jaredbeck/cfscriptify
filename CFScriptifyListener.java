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

	public void CFScriptifyListener() {
		depth = 0;
	}

	@Override public void enterTagBreak(CFMLParser.TagBreakContext ctx) {
		print("break");
	}

	@Override public void enterTagInclude(CFMLParser.TagIncludeContext ctx) {
		print("include " + ctx.STRING_LITERAL().getText());
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
		String from = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_FROM()), 6));
		String to = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_TO()), 4));
		String index = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_INDEX()), 7));
		String step = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_STEP()), 6));
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
		String array = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_ARRAY()), 7));
		String index = ctxSubstr(firstTextIn(ctx.ATR_INDEX()), 7);
		print ("for (" + index + " in " + array + ") {\n");
		entab();
	}

	@Override public void exitTagLoopArray(CFMLParser.TagLoopArrayContext ctx) {
		detab();
		print("}\n");
	}

	/* <cfloop list="" index=""> */
	@Override public void enterTagLoopList(CFMLParser.TagLoopListContext ctx) {
		String list = trimOctothorps(ctxSubstr(firstTextIn(ctx.ATR_LIST()), 6));
		String index = ctxSubstr(firstTextIn(ctx.ATR_INDEX()), 7);
		print("for (" + index + " in ListToArray(" + list + ")) {\n");
		entab();
	}

	@Override public void exitTagLoopList(CFMLParser.TagLoopListContext ctx) {
		detab();
		print("}\n");
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

	private String firstTextIn(List<TerminalNode> l) {
		if (l.size() == 0) {
			return "";
		}
		else {
			return l.get(0).getText();
		}
	}

	private String getCFCommentInnerText(String c) {
		return c.substring(6, c.length() - 5);
	}

	private void print(String s) {
		String indents = StringUtils.repeat("\t", depth);
		System.out.print(indents + s);
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
