import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;

public class CFScript {

  public static String atrsToString(List<CFMLParser.AttributeContext> ctxs, String delimiter) {
    Iterator<CFMLParser.AttributeContext> i = ctxs.iterator();
    ArrayList strs = new ArrayList();
    while(i.hasNext()) { strs.add(atrToString(i.next())); }
    return StringUtils.join(strs.toArray(), delimiter);
  }

  public static String argumentsToString(List<CFMLParser.TagArgumentContext> ctxs) {
    Iterator<CFMLParser.TagArgumentContext> i = ctxs.iterator();
    ArrayList strs = new ArrayList();
    while(i.hasNext()) { strs.add(argumentToString(i.next())); }
    return StringUtils.join(strs.toArray(), ", ");
  }

  public static String assignmentToString(CFMLParser.AssignmentContext ctx) {
    String ref = referenceToString(ctx.reference());
    String expr = expressionToString(ctx.expression());
    return String.format("%s = %s", ref, expr);
  }

  public static String atrVal(String assignment) {
    return dequote(assignment.split("=")[1]);
  }

  public static String dequote(String str) {
    String q = str.substring(0, 1);
    String esc = StringUtils.repeat(q, 2);
    return StringUtils.strip(str, q).replace(esc, q);
  }

  public static String expressionToString(CFMLParser.ExpressionContext ctx) {
    String str = null;
    if (ctx.binaryOp() != null) {
      str = binaryOpToString(ctx.binaryOp());
    }
    if (ctx.operand() != null) {
      str = operandToString(ctx.operand());
    }
    if (ctx.parenthesis() != null) {
      str = parenthesisToString(ctx.parenthesis());
    }
    if (ctx.unaryOp() != null) {
      str = unaryOpToString(ctx.unaryOp());
    }
    if (str == null) { die("Unexpected input in expression"); }
    return str;
  }

  public static String firstTextIn(List<TerminalNode> l) {
    if (l.size() == 0) {
      return "";
    }
    else {
      return l.get(0).getText();
    }
  }

  // Private Methods
  // ---------------

  private static String argumentToString(CFMLParser.TagArgumentContext ctx) {
    ArrayList base = new ArrayList();

    String req = firstTextIn(ctx.ATR_REQUIRED());
    if (req.length() > 0 && truthyAttr(atrVal(req))) {
      base.add("required");
    }

    String typ = firstTextIn(ctx.ATR_TYPE());
    if (typ.length() > 0) {
      base.add(atrVal(typ));
    }

    String name = firstTextIn(ctx.ATR_NAME());
    if (name.length() > 0) {
      base.add(atrVal(name));
    }

    String result = StringUtils.join(base.toArray(), " ");

    String dflt = firstTextIn(ctx.ATR_DEFAULT());
    if (dflt.length() > 0) {
      result += String.format(" = \"%s\"", atrVal(dflt));
    }

    return result;
  }

  private static String atrToString(CFMLParser.AttributeContext ctx) {
    String key = StringUtils.removeEnd(ctx.ATTRIBUTE_EQ().getText(), "=");
    String value = ctx.STRING_LITERAL().getText();
    return String.format("%s=%s", key, value);
  }

  private static String binaryOpToString(CFMLParser.BinaryOpContext ctx) {
    String lhs = null;
    if (ctx.operand() != null) {
      lhs = operandToString(ctx.operand());
    }
    else {
      lhs = parenthesisToString(ctx.parenthesis());
    }
    String rhs = expressionToString(ctx.expression());
    String op = ctx.BINARY_OPERATOR().getText();
    return String.format("%s %s %s", lhs, op, rhs);
  }

  private static void die(String msg) {
    System.err.println("ERROR: " + msg);
    System.exit(1);
  }

  private static String literalToString(CFMLParser.LiteralContext ctx) {
    String str = null;
    if (ctx.STRING_LITERAL() != null
      || ctx.INT_LITERAL() != null
      || ctx.DECIMAL_LITERAL() != null)
    {
      str = ctx.getText();
    }
    else if (ctx.arrayLiteral() != null) {
      str = '[' + positionalArgumentsToString(ctx.arrayLiteral().positionalArguments()) + ']';
    }
    else if (ctx.structLiteral() != null) {
      str = '{' + namedArgumentsToString(ctx.structLiteral().namedArguments()) + '}';
    }
    else {
      die("Unexpected input in literal");
    }
    return str;
  }

  private static String operandToString(CFMLParser.OperandContext ctx) {
    String str = null;
    if (ctx.literal() != null) {
      str = literalToString(ctx.literal());
    }
    else if (ctx.reference() != null) {
      str = referenceToString(ctx.reference());
    }
    else if (ctx.funcInvoc() != null) {
      str = funcInvocToString(ctx.funcInvoc());
    }
    return str;
  }

  private static String referenceToString(CFMLParser.ReferenceContext ctx) {
    String str = null;
    if (ctx.dottedRef() != null) {
      str = ctx.dottedRef().getText();
    }
    else if (ctx.arrayIndex() != null) {
      str = arrayIndexToString(ctx.arrayIndex());
    }
    return str;
  }

  private static boolean truthyAttr(String s) {
    return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes");
  }

  private static String arrayIndexToString(CFMLParser.ArrayIndexContext ctx) {
    String ref = ctx.dottedRef().getText();
    String ix = expressionToString(ctx.expression());
    return String.format("%s[%s]", ref, ix);
  }

  private static String funcInvocToString(CFMLParser.FuncInvocContext ctx) {
    String ref = null;
    if (ctx.dottedRef() != null) {
      ref = ctx.dottedRef().getText();
    }
    else {
      ref = ctx.BUILTIN_FUNC().getText();
    }
    String args = "";
    if (ctx.positionalArguments() != null) {
      args = positionalArgumentsToString(ctx.positionalArguments());
    }
    else if (ctx.namedArguments() != null) {
      args = namedArgumentsToString(ctx.namedArguments());
    }
    return String.format("%s(%s)", ref, args);
  }

  private static String namedArgumentsToString(CFMLParser.NamedArgumentsContext ctx) {
    Iterator<CFMLParser.AssignmentContext> ai = ctx.assignment().iterator();
    ArrayList strs = new ArrayList();
    while(ai.hasNext()) { strs.add(assignmentToString(ai.next())); }
    return StringUtils.join(strs.toArray(), ", ");
  }

  private static String parenthesisToString(CFMLParser.ParenthesisContext ctx) {
    return '(' + expressionToString(ctx.expression()) + ')';
  }

  private static String positionalArgumentsToString(CFMLParser.PositionalArgumentsContext ctx) {
    Iterator<CFMLParser.ExpressionContext> ei = ctx.expression().iterator();
    ArrayList strs = new ArrayList();
    while(ei.hasNext()) { strs.add(expressionToString(ei.next())); }
    return StringUtils.join(strs.toArray(), ", ");
  }

  private static String unaryOpToString(CFMLParser.UnaryOpContext ctx) {
    String str = null;
    if (ctx.UNARY_POSTFIX_OPERATOR() != null) {
      die("Unary postfix operators not yet supported");
    }
    else {
      String op = ctx.UNARY_PREFIX_OPERATOR().getText();
      String expr = expressionToString(ctx.expression());
      str = String.format("%s %s", op, expr);
    }
    return str;
  }
}
