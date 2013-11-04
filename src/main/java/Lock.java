import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Lock extends LineTag {

  private CFMLParser.TagLockContext ctx;
  private Map<String, String> attrs;

  public Lock(CFMLParser.TagLockContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  @Override public String toString() {
    String s = "lock";
    for (String a : attrOutputOrder()) {
      if (attrs.containsKey(a)) {
        s += String.format(" %s=%s", a, attrs.get(a));
      }
    }
    return s;
  }

  protected List<String> attrOutputOrder() {
    return Arrays.asList("name", "scope", "type", "timeout", "throwOnTimeout");
  }
}
