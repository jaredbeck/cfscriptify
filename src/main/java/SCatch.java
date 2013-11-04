import java.util.Map;

/*
`SCatch` - because `catch` is a reserved word in java.
http://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
*/
public class SCatch extends Scriptable {

  private CFMLParser.TagCatchContext ctx;

  public SCatch(CFMLParser.TagCatchContext ctx) {
    this.ctx = ctx;
  }

  @Override public String toString() {
    return "catch(" + type() + " cfcatch)";
  }

  private String type() {
    return (ctx.attribute() == null) ? "any"
      : CFScript.dequote(ctx.attribute().STRING_LITERAL().getText());
  }
}
