public class WriteLog extends Scriptable {

  private CFMLParser.TagLogContext ctx;

  public WriteLog(CFMLParser.TagLogContext ctx) {
    this.ctx = ctx;
  }

  public String toString() {
    return String.format("WriteLog(%s)", atrs());
  }

  private String atrs() {
    String atrs = CFScript.atrsToString(ctx.attribute(), ", ");
    if (ctx.ATR_TYPE().size() > 0) {
      String type = CFScript.atrVal(CFScript.firstTextIn(ctx.ATR_TYPE()));
      atrs += String.format(", type=\"%s\"", type);
    }
    return atrs;
  }
}
