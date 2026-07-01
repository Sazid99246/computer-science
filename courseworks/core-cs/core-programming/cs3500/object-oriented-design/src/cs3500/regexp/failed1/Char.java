package cs3500.regexp.failed1;

/**
 * This regular expression matches one particular character.
 */
public class Char extends AbstractRegexp {
  char c;

  Char(char c) {
    this.c = c;
  }

  @Override
  public boolean matches(String target) {
    return target != null && target.indexOf(this.c) != -1;
  }
}
