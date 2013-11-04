import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* LineTag represents all CFML tags that do not take blocks. */
public abstract class LineTag extends Scriptable {

  private Map<String, String> attrs;

  public LineTag(List<CFMLParser.AttributeContext> attributes) {
    attrs = attrMap(attributes);
  }

  @Override public String toString() {
    ArrayList pairs = new ArrayList();
    for (String a : attrOutputOrder()) {
      if (attrs.containsKey(a)) {
        pairs.add(String.format("%s=%s", a, attrs.get(a)));
      }
    }
    String formattedAttrs = StringUtils.join(pairs.toArray(), attrDelimiter());
    return String.format(formatString(), name(), formattedAttrs);
  }

  /* `attrOutputOrder` defines an order, mostly to ensure deterministic
  output (remember, not all Maps guarantee order).  Also, more important
  attrs should come first to improve code-reading speed. */
  protected abstract List<String> attrOutputOrder();

  /* `name` returns tag name, minus "cf" prefix, eg. lock for cflock */
  protected abstract String name();

  /* `taggish` returns true if the cfscript equivalent is "tag-like"
  eg. cflock and cflocation.  It returns false if the tag has a
  function equivalent, like cfdump/WriteDump. */
  protected abstract boolean taggish();

  private String attrDelimiter() {
    return taggish() ? " " : ", ";
  }

  private String formatString() {
    return taggish() ? "%s %s" : "%s(%s)";
  }

}
