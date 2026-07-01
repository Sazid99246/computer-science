package cs3500.regexp.success;

import java.util.Set;

/**
 * Describes the alternation of two regular expressions in sequence: this whole expression
 * matches a string if either of the components match the string.
 */
public class Alternate extends AbstractRegexp {
  AbstractRegexp r1;
  AbstractRegexp r2;

  Alternate(AbstractRegexp r1, AbstractRegexp r2) {
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override
  protected Set<Integer> matches(String target, int start) {
    Set<Integer> ans = this.r1.matches(target, start);
    ans.addAll(this.r2.matches(target, start));
    return ans;
  }
}
