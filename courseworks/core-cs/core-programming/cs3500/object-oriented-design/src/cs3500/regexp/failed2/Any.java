package cs3500.regexp.failed2;

/**
 * This regular expression matches any character at all.
 */
public class Any extends AbstractRegexp {
  @Override
  protected int matches(String target, int start) {
    if (start >= 0 && start < target.length()) {
      return start + 1;
    }
    return -1;
  }
}
