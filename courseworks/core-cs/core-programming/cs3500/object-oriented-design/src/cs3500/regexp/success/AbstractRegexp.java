package cs3500.regexp.success;

import java.util.Objects;
import java.util.Set;

import cs3500.regexp.Regexp;
import cs3500.regexp.RegexpFactory;

/**
 * The abstract base class for this successful implementation of regular expressions.
 * It provides a factory for this particular implementation, and also handles the basic behavior of
 * checking whether a regular expression matches at any particular starting character
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
      if (!this.matches(target, i).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  protected abstract Set<Integer> matches(String target, int start);
}
