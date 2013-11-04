import java.util.Map;

public class Function extends Scriptable {

  private CFMLParser.TagFunctionContext ctx;
  private Map<String, String> attrs;

  public Function(CFMLParser.TagFunctionContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  @Override public String toString() {
    String name   = CFScript.dequote(attrs.get("name"));
    String rtyp   = CFScript.dequote(attrs.get("returntype"));
    String access = CFScript.dequote(attrs.get("access"));
    String args   = CFScript.argumentsToString(ctx.tagArgument());
    return String.format("%s %s function %s(%s)", access, rtyp, name, args);
  }
}
