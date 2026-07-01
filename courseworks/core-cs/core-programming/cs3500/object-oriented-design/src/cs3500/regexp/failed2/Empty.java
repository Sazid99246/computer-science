package cs3500.regexp.failed2;

/**
 * This regular expression matches any string at all.
 */
public class Empty extends AbstractRegexp {
  @Override
  protected int matches(String target, int start) {
    if (start >= 0 && start <= target.length()) {
      return start;
    }
    return -1;
  }
}
