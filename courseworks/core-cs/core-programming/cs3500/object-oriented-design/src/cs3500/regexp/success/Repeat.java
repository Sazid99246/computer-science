package cs3500.regexp.success;

import java.util.HashSet;
import java.util.Set;

/**
 * This regular expression recognizes strings that contain zero or more consecutive repetitions
 * of its component regexp.
 */
public class Repeat extends AbstractRegexp {
  AbstractRegexp contents;

  Repeat(AbstractRegexp contents) {
    this.contents = contents;
  }

  @Override
  protected Set<Integer> matches(String target, int start) {
    HashSet<Integer> ans = new HashSet<>();

    if (start >= 0 && start <= target.length()) {
      ans.add(start);
    }

    HashSet<Integer> changes = new HashSet<>();
    boolean changed = changes.addAll(ans);

    while (changed) {
      changed = false;
      for (Integer i : changes) {
        changed = changed || ans.addAll(this.contents.matches(target, i));
      }
      HashSet<Integer> temp = new HashSet<>(ans);
      temp.removeAll(changes);
      changes = temp;
    }
    return ans;
  }
}
