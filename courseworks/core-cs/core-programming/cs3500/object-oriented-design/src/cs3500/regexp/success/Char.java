package cs3500.regexp.success;

import java.util.HashSet;
import java.util.Set;

/**
 * This regular expression matches one particular character.
 */
public class Char extends AbstractRegexp {
  char c;

  Char(char c) {
    this.c = c;
  }

  @Override
  protected Set<Integer> matches(String target, int start) {
    HashSet<Integer> ans = new HashSet<>();
    if (start >= 0 && start < target.length() && target.charAt(start) == this.c) {
      ans.add(start + 1);
    }
    return ans;
  }
}
