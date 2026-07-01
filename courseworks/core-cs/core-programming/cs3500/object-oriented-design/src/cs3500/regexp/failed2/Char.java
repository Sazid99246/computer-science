package cs3500.regexp.failed2;

/**
 * This regular expression matches one particular character.
 */
public class Char extends AbstractRegexp {
  char c;

  Char(char c) {
    this.c = c;
  }

  @Override
  protected int matches(String target, int start) {
    if (start >= 0 && start < target.length() && target.charAt(start) == this.c) {
      return start + 1;
    }
    return -1;
  }
}
