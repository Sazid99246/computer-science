package cs3500.regexp.success;

import java.util.HashSet;
import java.util.Set;

/**
 * This regular expression matches any character at all.
 */
public class Any extends AbstractRegexp {
  @Override
  protected Set<Integer> matches(String target, int start) {
    HashSet<Integer> ans = new HashSet<>();
    if (start >= 0 && start < target.length()) {
      ans.add(start + 1);
    }
    return ans;
  }
}
