import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

public class CFScript {

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

  // Private Methods
  // ---------------

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

  private static String operandToString(CFMLParser.OperandContext ctx) {
    String str = null;
    if (ctx.literal() != null) {
      str = ctx.literal().getText();
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
    return String.format("%s(%s)", ref, args);
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
