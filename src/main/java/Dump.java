import java.util.Arrays;
import java.util.List;

public class Dump extends LineTag {

  public Dump(CFMLParser.TagDumpContext ctx) {
    super(ctx.attribute());
  }

  @Override protected String name() {
    return "WriteDump";
  }

  @Override protected boolean taggish() {
    return false;
  }

  @Override protected List<String> attrOutputOrder() {
    return Arrays.asList("var", "expand", "format", "hide", "keys",
      "label", "metainfo", "output", "show", "showUDFs", "top", "abort");
  }
}
