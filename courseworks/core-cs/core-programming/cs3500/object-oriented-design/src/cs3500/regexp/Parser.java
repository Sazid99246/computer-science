package cs3500.regexp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simpleminded parser for Regexps.  See {@link Parser#parse} for the expected syntax.
 */
public class Parser {
  private static int curIdx;

  /**
   * <p>Parses an input string into a regular expression.  This method is parameterized over
   * a factory that is guaranteed to produce some subtype of {@link Regexp}.  (We use this
   * flexibility to have multiple implementations of Regexps, to see where they fail.)</p>
   *
   * <p>The syntax is</p>
   *
   * <ul>
   *   <li><code>c</code> for an arbitrary character c (except periods)</li>
   *   <li><code>.</code> to match any character at all</li>
   *   <li><code>(<i>r</i>)*</code> to repeat a {@link Regexp} an arbitrary number of times</li>
   *   <li><code>[<i>r1</i> | <i>r2</i> | ...]*</code> to choose among a set of {@link Regexp}s</li>
   *   <li><code><i>r1</i><i>r2</i></code> to match two {@link Regexp}s in a row</li>
   * </ul>
   *
   * <p>An empty string input will produce the empty {@link Regexp}, which matches
   * any non-null string</p>
   *
   * @param in The string to be parsed
   * @param factory A factory for constructing objects of (a subclass of) type {@link Regexp}
   * @param <T> The type of {@link Regexp} being constructed
   * @return The newly constructed {@link Regexp}
   */
  public static <T extends Regexp> T parse(String in, RegexpFactory<T> factory) {
    curIdx = 0;
    return parseHelp(in, factory, curIdx);
  }

  private static <T extends Regexp> T parseHelp(String in, RegexpFactory<T> factory, int startIdx) {
    curIdx = startIdx;
    List<T> seqs = new ArrayList<>();
    boolean done = false;
    while (!done && curIdx < in.length()) {
      switch (in.charAt(curIdx)) {
        case '(':
          T inner = parseHelp(in, factory, startIdx + 1);
          if (curIdx < in.length() && in.charAt(curIdx) != ')') {
            throw new IllegalArgumentException("Badly formatted regexp: " + in);
          }
          else {
            curIdx++;
          }
          if (curIdx < in.length() && in.charAt(curIdx) == '*') {
            curIdx++;
            seqs.add(factory.newRepeat(inner));
          }
          else if (curIdx < in.length() && in.charAt(curIdx) == '?') {
            curIdx++;
            seqs.add(factory.newAlternate(inner, factory.newEmpty()));
          }
          else {
            seqs.add(inner);
          }
          break;
        case ')':
          done = true;
          break;
        case '[':
          List<T> alterns = new ArrayList<>();
          curIdx++;
          while (curIdx < in.length() && in.charAt(curIdx) != ']') {
            alterns.add(parseHelp(in, factory, curIdx));
            if (curIdx < in.length()) {
              if (in.charAt(curIdx) == '|') {
                curIdx++;
              }
              else if (in.charAt(curIdx) == ']') {
                curIdx++;
                break;
              }
            }
            else {
              throw new IllegalArgumentException("Badly formatted regexp:" + in);
            }
          }
          if (alterns.size() == 0) {
            return factory.newEmpty();
          }
          else if (alterns.size() == 1) {
            return alterns.get(0);
          }
          else {
            T ans = alterns.get(0);
            for (int i = 1; i < alterns.size(); i++) {
              ans = factory.newAlternate(ans, alterns.get(i));
            }
            return ans;
          }
        case ']':
          done = true;
          break;
        default:
          if (in.charAt(curIdx) == '.') {
            seqs.add(factory.newAny());
          }
          else {
            seqs.add(factory.newChar(in.charAt(curIdx)));
          }
          curIdx++;
          break;
      }
    }
    if (seqs.size() == 0) {
      return factory.newEmpty();
    }
    else if (seqs.size() == 1) {
      return seqs.get(0);
    }
    else {
      T ans = seqs.get(0);
      for (int i = 1; i < seqs.size(); i++) {
        ans = factory.newSequence(ans, seqs.get(i));
      }
      return ans;
    }
  }
}
