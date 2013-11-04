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

  @Override public String toString() {
    ArrayList base = new ArrayList();
    if (isRequired()) { base.add("required"); }

    if (attrs.containsKey("type")) {
      base.add(CFScript.dequote(attrs.get("type")));
    }

    if (attrs.containsKey("name")) {
      base.add(CFScript.dequote(attrs.get("name")));
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