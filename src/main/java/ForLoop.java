import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ForLoop extends Loop {

  private CFMLParser.TagLoopContext ctx;
  private Map<String, String> attrs;
  private String op;
  private String index;
  private String from;
  private String to;
  private String step;

  public ForLoop(CFMLParser.TagLoopContext ctx) {
    this.ctx = ctx;
    attrs  = attrMap(ctx.attribute());
    index  = getIndex();
    from   = getFrom();
    to     = getTo();
    step   = getStep();
    op     = loopComparison(from, to, step);
  }

  @Override public boolean hasWarning() {
    return this.op.equals("NEQ");
  }

  public String toString() {
    String begin = String.format("%s = %s", index, from);
    String middle = String.format("%s %s %s", descope(index), op, to);
    String end = stepStmt(descope(index), step);
    return String.format("for (%s; %s; %s)", begin, middle, end);
  }

  @Override public String warning() {
    return "is NEQ what you wanted?";
  }

  private String getIndex() {
    return CFScript.trimOctothorps(CFScript.dequote(attrs.get("index")));
  }

  private String getFrom() {
    return CFScript.trimOctothorps(CFScript.dequote(attrs.get("from")));
  }

  private String getTo() {
    return CFScript.trimOctothorps(CFScript.dequote(attrs.get("to")));
  }

  private String getStep() {
    String result = "";
    if (attrs.containsKey("step")) {
      result = CFScript.trimOctothorps(CFScript.dequote(attrs.get("step")));
    } else {
      result = "1";
    }
    return result;
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
