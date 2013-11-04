import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Lock extends Scriptable {

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

  /* Defining an order, mostly to ensure deterministic output (remember,
  not all Maps guarantee order).  Also, more important attrs should
  come first to improve code-reading speed. */
  private List<String> attrOutputOrder() {
    return Arrays.asList("name", "scope", "type", "timeout", "throwOnTimeout");
  }
}
