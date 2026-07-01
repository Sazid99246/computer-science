package cs3500.regexp.failed2;

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
  protected int matches(String target, int start) {
    int ans = this.r1.matches(target, start);
    if (ans >= start) {
      return ans;
    }
    return this.r2.matches(target, start);
  }
}
