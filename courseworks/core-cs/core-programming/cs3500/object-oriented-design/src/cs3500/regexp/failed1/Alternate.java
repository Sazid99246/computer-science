package cs3500.regexp.failed1;

/**
 * Describes the alternation of two regular expressions in sequence: this whole expression
 * matches a string if either of the components match the string.
 */
public class Alternate extends AbstractRegexp {
  AbstractRegexp r1;
  AbstractRegexp r2;

  Alternate(AbstractRegexp r1, AbstractRegexp r2) {
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override
  public boolean matches(String target) {
    return this.r1.matches(target) || this.r2.matches(target);
  }
}
