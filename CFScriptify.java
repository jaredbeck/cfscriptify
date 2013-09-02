import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class CFScriptify {
  public static void main(String[] args) {
    checkUsage(args);

    try {
      String filename = args[0];
      CharStream charStream = new ANTLRFileStream(filename);
      CFMLLexer lexer = new CFMLLexer(charStream);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CFMLParser parser = new CFMLParser(tokens);
      ParseTree tree = parser.block();
      ParseTreeWalker walker = new ParseTreeWalker();
      walker.walk(new CFScriptifyListener(), tree);
    }
    catch (IOException e) {
      die("IOException: " + e.getMessage());
    }
  }

  private static void checkUsage(String[] args) {
    if (args.length != 1) {
      die("Usage: java CFScriptify filename.cfm");
    }
  }

  private static void die(String msg) {
    System.out.println(msg);
    System.exit(1);
  }
}
