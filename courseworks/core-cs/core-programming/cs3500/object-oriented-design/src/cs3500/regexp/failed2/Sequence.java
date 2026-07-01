package cs3500.regexp.failed2;

/**
 * Describes the concatenation of two regular expressions in sequence: this whole expression
 * matches a string if the string first matches the first component, and then immediately
 * matches the second component starting at the next character.
 */
public class Sequence extends AbstractRegexp {
  AbstractRegexp r1;
  AbstractRegexp r2;

  Sequence(AbstractRegexp r1, AbstractRegexp r2) {
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override
  protected int matches(String target, int start) {
    int r1match = this.r1.matches(target, start);

    if (r1match >= start) {
      return this.r2.matches(target, r1match);
    }

    return -1;
  }
}
