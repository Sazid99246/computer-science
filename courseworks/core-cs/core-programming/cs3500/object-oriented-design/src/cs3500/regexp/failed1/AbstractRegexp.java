package cs3500.regexp.failed1;

import cs3500.regexp.Regexp;
import cs3500.regexp.RegexpFactory;

/**
 * The abstract base class for this failed implementation of regular expressions.
 * All it does is provide a factory for this particular implementation; it doesn't
 * supply any shared implementation.
 */
public abstract class AbstractRegexp implements Regexp {
  public static class Factory implements RegexpFactory<AbstractRegexp> {

    @Override
    public AbstractRegexp newAlternate(AbstractRegexp r1, AbstractRegexp r2) {
      return new Alternate(r1, r2);
    }

    @Override
    public AbstractRegexp newChar(char c) {
      return new Char(c);
    }

    @Override
    public AbstractRegexp newEmpty() {
      return new Empty();
    }

    @Override
    public AbstractRegexp newRepeat(AbstractRegexp r) {
      return new Repeat(r);
    }

    @Override
    public AbstractRegexp newSequence(AbstractRegexp r1, AbstractRegexp r2) {
      return new Sequence(r1, r2);
    }

    @Override
    public AbstractRegexp newAny() {
      return new Any();
    }
  }
}
