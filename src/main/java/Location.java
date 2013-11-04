import java.util.Arrays;
import java.util.List;

public class Location extends LineTag {

  public Location(CFMLParser.TagLocationContext ctx) {
    super(ctx.attribute());
  }

  @Override protected String name() {
    return "location";
  }

  @Override protected List<String> attrOutputOrder() {
    return Arrays.asList("url", "statusCode", "addToken");
  }
}