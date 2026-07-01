package cs3500.regexp.failed2;

import java.util.Objects;

import cs3500.regexp.Regexp;
import cs3500.regexp.RegexpFactory;

/**
 * The abstract base class for this failed implementation of regular expressions.
 * It provides a factory for this particular implementation, and also handles the basic behavior of
 * checking whether a regular expression matches at any particular starting character.
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


  @Override
  public boolean matches(String target) {
    Objects.requireNonNull(target);
    for (int i = 0; i <= target.length(); i++) {
      if (this.matches(target, i) >= 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Computes whether this {@link Regexp} matches the target string starting at the given index.
   *
   * @param target The string to be matched
   * @param start  The index to start matching from
   * @return If the match succeeds, returns the character at which the match ends, otherwise returns
   *          -1.
   */
  protected abstract int matches(String target, int start);
}
