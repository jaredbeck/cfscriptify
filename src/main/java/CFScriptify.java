import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CFScriptify {
  public static void main(String[] args) {
    checkUsage(args);
    CFMLLexer lexer = new CFMLLexer(stdinStream());
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CFMLParser parser = new CFMLParser(tokens);
    ParseTree tree = parser.block();
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(new CFScriptifyListener(), tree);
  }

  private static CharStream stdinStream() {
    CharStream s = null;
    try { s = new ANTLRInputStream(System.in); }
    catch (IOException e) { die(e); }
    return s;
  }

  private static void checkUsage(String[] args) {
    if (args.length != 0) {
      die(String.format("Usage: Expected zero arguments, found %d", args.length));
    }
  }

  private static void die(Exception e) {
    die(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
  }

  private static void die(String msg) {
    System.out.println(msg);
    System.exit(1);
  }
}
