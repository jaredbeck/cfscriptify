import java.util.List;
import java.util.Map;

/* LineTag represents all CFML tags that do not take blocks. */
public abstract class LineTag extends Scriptable {

  private Map<String, String> attrs;

  public LineTag(List<CFMLParser.AttributeContext> attributes) {
    attrs = attrMap(attributes);
  }

  @Override public String toString() {
    String s = name();
    for (String a : attrOutputOrder()) {
      if (attrs.containsKey(a)) {
        s += String.format(" %s=%s", a, attrs.get(a));
      }
    }
    return s;
  }

  /* `attrOutputOrder` defines an order, mostly to ensure deterministic
  output (remember, not all Maps guarantee order).  Also, more important
  attrs should come first to improve code-reading speed. */
  protected abstract List<String> attrOutputOrder();

  /* `name` returns tag name, minus "cf" prefix, eg. lock for cflock */
  protected abstract String name();

}
