import java.util.List;

/* LineTag represents all CFML tags that do not take blocks. */
public abstract class LineTag extends Scriptable {

  /* `attrOutputOrder` defines an order, mostly to ensure deterministic
  output (remember, not all Maps guarantee order).  Also, more important
  attrs should come first to improve code-reading speed. */
  protected abstract List<String> attrOutputOrder();

}
