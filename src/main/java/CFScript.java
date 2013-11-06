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

  /* assignment : operand '=' expression ; */
  public static String assignmentToString(CFMLParser.AssignmentContext ctx) {
    String opnd = "";
    if (ctx.operand() != null) {
      opnd = operandToString(ctx.operand());
    }
    else {
      opnd = StringUtils.removeEnd(ctx.ATTRIBUTE_EQ().getText(), "=");
    }
    String expr = expressionToString(ctx.expression());
    return String.format("%s = %s", opnd, expr);
  }

  public static String atrVal(String assignment) {
    return dequote(assignment.split("=")[1]);
  }

  public static String ctxSubstr(String ctxText, int start) {
    if (ctxText.length() <= start) { return ""; }
    return StringUtils.chop(ctxText.substring(start));
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

  public static String trimOctothorps(String s) {
    return StringUtils.strip(s, "#");
  }

  // Private Methods
  // ---------------

  private static String argumentToString(CFMLParser.TagArgumentContext ctx) {
    return new Argument(ctx).toString();
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

  /* operand : chainable ( '.' chainable )* ; */
  private static String operandToString(CFMLParser.OperandContext ctx) {
    Iterator<CFMLParser.ChainableContext> i = ctx.chainable().iterator();
    ArrayList strs = new ArrayList();
    while(i.hasNext()) { strs.add(chainableToString(i.next())); }
    return StringUtils.join(strs.toArray(), '.');
  }

  /* chainable : atom ( message )* ; */
  private static String chainableToString(CFMLParser.ChainableContext ctx) {
    String str = "";
    if (ctx.atom() != null) {
      str = atomToString(ctx.atom());
    }
    if (ctx.message() != null) {
      str += messagesToString(ctx.message());
    }
    return str;
  }

  private static String messagesToString(List<CFMLParser.MessageContext> ctxs) {
    Iterator<CFMLParser.MessageContext> i = ctxs.iterator();
    ArrayList strs = new ArrayList();
    while(i.hasNext()) { strs.add(messageToString(i.next())); }
    return StringUtils.join(strs.toArray(), "");
  }

  /* message : arrayIndex | funcInvoc ; */
  private static String messageToString(CFMLParser.MessageContext ctx) {
    String str = "";
    if (ctx.arrayIndex() != null) {
      str = arrayIndexToString(ctx.arrayIndex());
    }
    else if (ctx.funcInvoc() != null) {
      str = funcInvocToString(ctx.funcInvoc());
    }
    else {
      die("Unexpected input in message");
    }
    return str;
  }

  /* atom : VARIABLE_NAME | BUILTIN_FUNC | literal ; */
  private static String atomToString(CFMLParser.AtomContext ctx) {
    String str = "";
    if (ctx.VARIABLE_NAME() != null) {
      str = ctx.VARIABLE_NAME().getText();
    }
    else if (ctx.BUILTIN_FUNC() != null) {
      str = ctx.BUILTIN_FUNC().getText();
    }
    else if (ctx.literal() != null) {
      str = literalToString(ctx.literal());
    }
    else {
      die("Unexpected input in atom");
    }
    return str;
  }

  /* arrayIndex : '[' expression ']' ; */
  private static String arrayIndexToString(CFMLParser.ArrayIndexContext ctx) {
    return String.format("[%s]", expressionToString(ctx.expression()));
  }

  /*
  funcInvoc :
    '('
    ( positionalArguments | namedArguments )?
    ')'
    ;
  */
  private static String funcInvocToString(CFMLParser.FuncInvocContext ctx) {
    String args = "";
    if (ctx.positionalArguments() != null) {
      args = positionalArgumentsToString(ctx.positionalArguments());
    }
    else if (ctx.namedArguments() != null) {
      args = namedArgumentsToString(ctx.namedArguments());
    }
    return String.format("(%s)", args);
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
