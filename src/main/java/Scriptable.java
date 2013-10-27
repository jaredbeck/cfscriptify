import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Scriptable {

  protected Map attrMap(List<CFMLParser.AttributeContext> ctxs) {
    Map attrs = new HashMap();
    Iterator<CFMLParser.AttributeContext> i = ctxs.iterator();
    while(i.hasNext()) {
      CFMLParser.AttributeContext c = i.next();
      String key = StringUtils.removeEnd(c.ATTRIBUTE_EQ().getText(), "=");
      String value = c.STRING_LITERAL().getText();
      attrs.put(key, value);
    }
    return attrs;
  }

}
