import java.util.Arrays;
import java.util.List;

public class Lock extends LineTag {

  public Lock(CFMLParser.TagLockContext ctx) {
    super(ctx.attribute());
  }

  @Override protected String name() {
    return "lock";
  }

  @Override protected List<String> attrOutputOrder() {
    return Arrays.asList("name", "scope", "type", "timeout", "throwOnTimeout");
  }
}
