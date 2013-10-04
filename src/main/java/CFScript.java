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
    return str;
  }

  // Private Methods
  // ---------------

  private static String binaryOpToString(CFMLParser.BinaryOpContext ctx) {
    String lhs = operandToString(ctx.operand());
    String rhs = expressionToString(ctx.expression());
    String op = ctx.BINARY_OPERATOR().getText();
    return String.format("%s %s %s", lhs, op, rhs);
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

  private static String positionalArgumentsToString(CFMLParser.PositionalArgumentsContext ctx) {
    Iterator<CFMLParser.ExpressionContext> ei = ctx.expression().iterator();
    ArrayList strs = new ArrayList();
    while(ei.hasNext()) { strs.add(expressionToString(ei.next())); }
    return StringUtils.join(strs.toArray(), ", ");
  }
}
