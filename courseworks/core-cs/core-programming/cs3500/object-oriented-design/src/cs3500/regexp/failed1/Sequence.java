package cs3500.regexp.failed1;

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
  public boolean matches(String target) {
    /*
      This method cannot be implemented with this signature,
      because we don't get enough information from r1 to find out *where* it matches,
      so that we can check whether r2 matches *from that point*.
     */
    throw new IllegalStateException("Can't be done!");
  }
}
