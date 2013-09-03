import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
		String from = ctxSubstr(firstTextIn(ctx.ATR_FROM()), 6);
		String to = ctxSubstr(firstTextIn(ctx.ATR_TO()), 4);
		String index = ctxSubstr(firstTextIn(ctx.ATR_INDEX()), 7);
		String step = ctxSubstr(firstTextIn(ctx.ATR_STEP()), 6);
		String begin = String.format("%s = %s", index, from);
		String middle = String.format("%s %s %s", descope(index), loopComparison(from, to), to);
		String end = descope(index) + loopCrementor(from, to);
		print(String.format("for (%s; %s; %s) {\n", begin, middle, end));
		entab();
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

	private boolean loopCompDirection(String a, String b) {
		try {
			int ia = Integer.parseInt(a);
			int ib = Integer.parseInt(b);
			return ia < ib;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	private String loopComparison(String from, String to) {
		return loopCompDirection(from, to) ? "LTE" : "GTE";
	}

	private String loopCrementor(String from, String to) {
		return loopCompDirection(from, to) ? "++" : "--";
	}

	private void print(String s) {
		String indents = StringUtils.repeat("\t", depth);
		System.out.print(indents + s);
	}

	private String trimOctothorps(String s) {
		return StringUtils.strip(s, "#");
	}
}
