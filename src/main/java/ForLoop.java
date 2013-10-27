import org.apache.commons.lang3.math.NumberUtils;

public class ForLoop {

  private CFMLParser.TagLoopFromContext ctx;
  private String op;

  public ForLoop(CFMLParser.TagLoopFromContext ctx) {
    this.ctx = ctx;
  }

  public String op() {
    return this.op;
  }

  public String toString() {
    String from   = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_FROM()), 6));
    String to     = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_TO()), 4));
    String index  = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_INDEX()), 7));
    String step   = CFScript.trimOctothorps(CFScript.ctxSubstr(CFScript.firstTextIn(ctx.ATR_STEP()), 6));
    if (step.length() == 0) { step = "1"; }
    this.op = loopComparison(from, to, step);

    String begin = String.format("%s = %s", index, from);
    String middle = String.format("%s %s %s", descope(index), op, to);
    String end = stepStmt(descope(index), step);

    return String.format("for (%s; %s; %s)", begin, middle, end);
  }

  private String descope(String dotScoped) {
    String[] parts = dotScoped.split("\\.");
    return parts[parts.length - 1];
  }

  private String loopComparison(String from, String to, String step) {
    boolean direction;
    if (NumberUtils.isNumber(step)) {
      direction = Double.parseDouble(step) > 0; // true means ascending
    }
    else if (NumberUtils.isNumber(from) && NumberUtils.isNumber(to)) {
      direction = Double.parseDouble(from) < Double.parseDouble(to);
    }
    else {
      /* If none of the attributes are numeric, we can't determine
      the direction.  The best possible guess is NEQ.  Thankfully,
      a non-numeric step is quite uncommon. */
      return "NEQ";
    }
    return direction ? "LTE" : "GTE";
  }

  private String stepStmt(String index, String step) {
    try {
      int s = Integer.parseInt(step);
      if (s == 1) { return index + "++"; }
      else if (s == -1) { return index + "--"; }
      else { return index + " += " + String.valueOf(s); }
    }
    catch (NumberFormatException e) {
      return index + " += " + step;
    }
  }

}
