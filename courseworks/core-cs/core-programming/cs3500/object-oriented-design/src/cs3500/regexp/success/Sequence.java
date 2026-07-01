package cs3500.regexp.success;

import java.util.HashSet;
import java.util.Set;

/**
 * Describes the concatenation of two regular expressions in sequence: this whole expression
 * matches a string if the string first matches the first component, and then immediately
 * matches the second component starting at the next character.
 */
public class Sequence extends AbstractRegexp {
  AbstractRegexp r1;
  AbstractRegexp r2;

  Sequence(AbstractRegexp r1, AbstractRegexp r2) {
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override
  protected Set<Integer> matches(String target, int start) {
    Set<Integer> r1matches = this.r1.matches(target, start);

    HashSet<Integer> ans = new HashSet<Integer>();

    for (Integer r1match : r1matches) {
      ans.addAll(this.r2.matches(target, r1match));
    }

    return ans;
  }
}
