import java.util.Arrays;
import java.util.List;

public class Header extends LineTag {

  public Header(CFMLParser.TagHeaderContext ctx) {
    super(ctx.attribute());
  }

  @Override protected String name() {
    return "cfheader";
  }

  @Override protected boolean taggish() {
    return false;
  }

  @Override protected List<String> attrOutputOrder() {
    return Arrays.asList("charset", "name", "statuscode", "statustext", "value");
  }
}