import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Location extends LineTag {

  private CFMLParser.TagLocationContext ctx;
  private Map<String, String> attrs;

  public Location(CFMLParser.TagLocationContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  @Override public String toString() {
    String s = "location";
    for (String a : attrOutputOrder()) {
      if (attrs.containsKey(a)) {
        s += String.format(" %s=%s", a, attrs.get(a));
      }
    }
    return s;
  }

  protected List<String> attrOutputOrder() {
    return Arrays.asList("url", "statusCode", "addToken");
  }
}