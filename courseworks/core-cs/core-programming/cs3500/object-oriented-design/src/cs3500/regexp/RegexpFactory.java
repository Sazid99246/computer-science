package cs3500.regexp;

/**
 * A generic interface for constructing {@link Regexp}s.  The parameter is generic
 * so that we can instantiate this with different families of {@link Regexp} implementations.
 */
public interface RegexpFactory<T extends Regexp> {
  /**
   * Constructs an alternation {@link Regexp} from the two provided alternatives.
   *
   * @param r1 The left side of the {@link Regexp}
   * @param r2 The right side of the {@link Regexp}
   * @return The constructed {@link Regexp}
   */
  T newAlternate(T r1, T r2);

  /**
   * Constructs a character-matching regexp.
   *
   * @param c The character to match
   * @return The constructed {@link Regexp}
   */
  T newChar(char c);

  /**
   * Constructs a {@link Regexp} matching an empty string.
   *
   * @return The constructed {@link Regexp}
   */
  T newEmpty();

  /**
   * Constructs a {@link Regexp} matching the given {@link Regexp} zero or more times.
   *
   * @param r The {@link Regexp} component
   * @return The constructed {@link Regexp}
   */
  T newRepeat(T r);

  /**
   * Constructs a sequencing {@link Regexp} from the two provided components.
   *
   * @param r1 The left side of the {@link Regexp}
   * @param r2 The right side of the {@link Regexp}
   * @return The constructed {@link Regexp}
   */
  T newSequence(T r1, T r2);

  /**
   * Constructs a {@link Regexp} matching any single character.
   *
   * @return The constructed {@link Regexp}
   */
  T newAny();
}
