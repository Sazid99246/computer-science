package cs3500.regexp.failed1;

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
  public boolean matches(String target) {
    /*
      This method cannot be implemented with this signature,
      because we don't get enough information from contents to find out *where* it matches,
      so that we can check whether it could match again *from that point*.
     */
    throw new IllegalStateException("Can't be done!");
  }
}
