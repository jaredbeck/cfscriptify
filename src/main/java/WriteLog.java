public class WriteLog extends Scriptable {

  private CFMLParser.TagLogContext ctx;

  public WriteLog(CFMLParser.TagLogContext ctx) {
    this.ctx = ctx;
  }

  @Override public String toString() {
    return String.format("WriteLog(%s)", atrs());
  }

  private String atrs() {
    return CFScript.atrsToString(ctx.attribute(), ", ");
  }
}
