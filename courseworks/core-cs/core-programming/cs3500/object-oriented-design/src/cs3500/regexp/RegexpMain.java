package cs3500.regexp;

import cs3500.regexp.success.AbstractRegexp;

/**
 * A simple harness for regular expressions.
 */
public class RegexpMain {
  /**
   * Runs the program.
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    String r1src = "([(abc)*|de])?";
    Regexp r1 = Parser.parse(r1src, new AbstractRegexp.Factory());
    String r2src = "(a)*abb";
    Regexp r2 = Parser.parse(r2src, new AbstractRegexp.Factory());
    boolean matched = r1.matches("aaaab");
    return;
  }
}
