package cs3500.sexp;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A simpleminded parser for {@link Sexp}s.
 */
public class Parser {
  /**
   * Parses the given string as an s-expression.  The expected syntax is
   *
   * <ul>
   * <li><code>#true</code> or <code>#false</code> for Boolean values</li>
   * <li>Numeric literals</li>
   * <li><code>(<i>sexp</i> ...)</code> for nested {@link Sexp}s</li>
   * </ul>
   *
   * @param in The string to be parsed
   * @return The resulting s-expression
   */
  public static Sexp parse(String in) {
    try {
      Scanner s = new Scanner(in);
      s.useDelimiter(Pattern.compile("\\s+|(?=[()])|(?<=[()])"));
      return parse(s);
    }
    catch (IllegalArgumentException a) {
      throw new IllegalArgumentException("Badly formatted sexp: " + in);
    }
  }

  private static Sexp parse(Scanner scan) {
    java.util.List<Sexp> parts = new ArrayList<>();
    while (scan.hasNext()) {
      if (scan.hasNextInt()) {
        parts.add(new Number(scan.nextInt()));
      }
      else {
        String s = scan.next();
        switch (s) {
          case "(":
            parts.add(parse(scan));
            break;
          case ")":
            return new SList(parts);
          default:
            if (s.equals("#true")) {
              parts.add(new Boolean(true));
            }
            else if (s.equals("#false")) {
              parts.add(new Boolean(false));
            }
            else {
              parts.add(new Symbol(s));
            }
            break;
        }
      }
    }
    if (parts.size() == 1) {
      return parts.get(0);
    }
    throw new IllegalArgumentException();
  }
}
