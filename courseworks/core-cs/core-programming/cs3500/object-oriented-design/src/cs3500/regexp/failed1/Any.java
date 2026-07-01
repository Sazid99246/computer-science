package cs3500.regexp.failed1;

/**
 * This regular expression matches any character at all.
 */
public class Any extends AbstractRegexp {
  @Override
  public boolean matches(String target) {
    return target != null && target.length() > 0;
  }
}
