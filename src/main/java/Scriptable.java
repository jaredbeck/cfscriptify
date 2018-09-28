import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class Scriptable {

  public static boolean hasKey(List<CFMLParser.AttributeContext> ctxs, String k) {
    boolean result = false;
    Iterator<CFMLParser.AttributeContext> i = ctxs.iterator();
    while(!result && i.hasNext()) {
      result = key(i.next()).equals(k);
    }
    return result;
  }

  public abstract String toString();

  protected Map attrMap(List<CFMLParser.AttributeContext> ctxs) {
    Map attrs = new HashMap();
    Iterator<CFMLParser.AttributeContext> i = ctxs.iterator();
    while(i.hasNext()) {
      CFMLParser.AttributeContext c = i.next();
      attrs.put(key(c).toLowerCase(), value(c));
    }
    return attrs;
  }

  private static String key(CFMLParser.AttributeContext c) {
    return StringUtils.removeEnd(c.ATTRIBUTE_EQ().getText(), "=");
  }

  private static String value(CFMLParser.AttributeContext c) {
    return c.STRING_LITERAL().getText();
  }
}
