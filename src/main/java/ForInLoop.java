import java.util.Map;

public class ForInLoop extends Loop {

  private CFMLParser.TagLoopContext ctx;
  private Map<String, String> attrs;

  public ForInLoop(CFMLParser.TagLoopContext ctx) {
    this.ctx = ctx;
    attrs = attrMap(ctx.attribute());
  }

  @Override public String toString() {
    String index = CFScript.dequote(attrs.get("index"));
    return String.format("for (%s in %s)", index, iterable());
  }

  private String iterable() {
    String result = null;
    if (attrs.containsKey("array")) {
      result = CFScript.trimOctothorps(CFScript.dequote(attrs.get("array")));
    }
    else if (attrs.containsKey("list")) {
      String lst = CFScript.trimOctothorps(CFScript.dequote(attrs.get("list")));
      result = String.format("ListToArray(%s)", lst);
    }
    return result;
  }
}
