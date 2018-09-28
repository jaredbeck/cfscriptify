import java.util.Arrays;
import java.util.List;

public class Content extends LineTag {

  public Content(CFMLParser.TagContentContext ctx) {
    super(ctx.attribute());
  }

  @Override protected String name() {
    return "cfcontent";
  }

  @Override protected boolean taggish() {
    return false;
  }

  @Override protected List<String> attrOutputOrder() {
    return Arrays.asList("type", "file", "variable", "deleteFile", "reset");
  }
}
