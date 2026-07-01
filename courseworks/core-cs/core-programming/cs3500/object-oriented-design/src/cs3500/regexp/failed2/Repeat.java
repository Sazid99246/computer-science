package cs3500.regexp.failed2;

/**
 * This regular expression recognizes strings that contain zero or more consecutive repetitions
 * of its component regexp.
 */
public class Repeat extends AbstractRegexp {
  AbstractRegexp contents;

  Repeat(AbstractRegexp contents) {
    this.contents = contents;
  }

  @Override
  protected int matches(String target, int start) {
    /*
      This method can't be implemented with this signature.
      The whole point of a Repeat expression is that it could match its contained pattern
      an arbitrary number of times.  Consider the example regexp /(a)*ab/, and try to match the
      string "aaaaaab".  Somehow we need to determine that (a)* should match 5 times, stopping
      just before the last 'a', so that the trailing 'ab' can be matched by the rest of the regexp.
      But (a)* could match any number of times between 0 and 6, and we much choose exactly one to
      return...
     */
    throw new IllegalStateException("Can't be done correctly!");
  }
}
