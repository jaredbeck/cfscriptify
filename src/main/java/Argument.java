import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class Argument extends Scriptable {

  private CFMLParser.TagArgumentContext ctx;
  private Map<String, String> attrs;

  public Argument(CFMLParser.TagArgumentContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  public String toString() {
    ArrayList base = new ArrayList();
    if (isRequired()) { base.add("required"); }

    String typ = CFScript.firstTextIn(ctx.ATR_TYPE());
    if (typ.length() > 0) {
      base.add(CFScript.atrVal(typ));
    }

    String name = CFScript.firstTextIn(ctx.ATR_NAME());
    if (name.length() > 0) {
      base.add(CFScript.atrVal(name));
    }

    String result = StringUtils.join(base.toArray(), " ");

    if (attrs.containsKey("default")) {
      String dflt = CFScript.dequote(attrs.get("default"));
      result += String.format(" = \"%s\"", dflt);
    }

    return result;
  }

  private boolean isRequired() {
    return attrs.containsKey("required")
      && truthyAttr(CFScript.dequote(attrs.get("required")));
  }

  private boolean truthyAttr(String s) {
    return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes");
  }
}