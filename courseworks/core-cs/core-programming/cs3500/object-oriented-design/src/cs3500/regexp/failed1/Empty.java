package cs3500.regexp.failed1;

/**
 * This regular expression matches any string at all.
 */
public class Empty extends AbstractRegexp {
  @Override
  public boolean matches(String target) {
    return target != null;
  }
}
