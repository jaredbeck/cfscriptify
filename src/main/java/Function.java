import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class Function extends Scriptable {

  private CFMLParser.TagFunctionContext ctx;
  private Map<String, String> attrs;

  public Function(CFMLParser.TagFunctionContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  @Override public String toString() {
    ArrayList<String> strs = new ArrayList<String>();
    if (attrs.containsKey("access")) {
      strs.add(CFScript.dequote(attrs.get("access")));
    }
    if (attrs.containsKey("returntype")) {
      strs.add(CFScript.dequote(attrs.get("returntype")));
    }
    String name = CFScript.dequote(attrs.get("name"));
    String args = CFScript.argumentsToString(ctx.tagArgument());
    strs.add(String.format("function %s(%s)", name, args));
    if (attrs.containsKey("returnformat")) {
      String returnformat = "returnformat='";
      returnformat += CFScript.dequote(attrs.get("returnformat"));
      returnformat += "'";
      strs.add(returnformat);
    }
    return StringUtils.join(strs.toArray(), " ");
  }
}
