package cs3500.lec11;

/**
 * Encapsulates the task of accumulating or building strings. The main idea
 * is to use the {@code append} methods repeatedly on a {@code
 * StringAccumulator} object to add characters to it,
 * and then method {@code stringValue} to get the resulting {@link String}
 * value when done.
 *
 * <p>Different implementations may have different performance
 * characteristics.
 */
public interface StringAccumulator extends Appendable {
  /**
   * Returns the accumulated string value. Should be the same as
   * {@code toString()}.
   *
   * @return the accumulated string value
   */
  String stringValue();

  /**
   * Returns the current length of the accumulated string.
   *
   * @return the length
   */
  int length();

  /**
   * Appends the given character to the end of the accumulated string.
   *
   * <p>Overridden from {@code Appendable} to remove {@code throws} clause.
   *
   * <p><b>Inheritance note:</b> Is called by the default method for
   * {@link #append(CharSequence, int, int)}.
   *
   * @param c the character to append
   * @return A reference to this {@code StringAccumulator}
   */
  @Override
  StringAccumulator append(char c);

  /**
   * Appends the given character sequence to the end of the accumulated string.
   * If {@code csq} is {@code null}, behaves as if {@code csq} is the
   * string {@code "null"} instead. (This strange behavior is inherited from
   * {@link Appendable#append(CharSequence)}.
   *
   * <p>Overridden from {@link Appendable} to remove {@code throws} clause.
   *
   * <p><b>Inheritance note:</b> The default method calls
   * {@link #append(CharSequence, int, int)}, which in turn calls
   * {@link #append(char)} once for each character in {@code csq}.
   *
   * @param csq the character sequence to append
   * @return this
   */
  @Override
  default StringAccumulator append(CharSequence csq) {
    if (csq == null) {
      csq = "null";
    }

    return append(csq, 0, csq.length());
  }

  /**
   * Appends a subsequence of the given character sequence to the end of the
   * accumulated string. If {@code csq} is {@code null}, behaves as if
   * {@code csq} is the string {@code "null"} instead. (This strange
   * behavior is inherited from
   * {@link Appendable#append(CharSequence, int, int)}.
   *
   * <p>Overridden from {@link Appendable} to remove {@code throws} clause.
   *
   * <p><b>Inheritance note:</b> The default method implementation calls {@link
   * #append(char)} once for each character in the subsequence.
   *
   * @param csq the character sequence to get characters from
   * @param start the index to start copying from
   * @param end   one past the index to stop copying from
   * @return this
   */
  @Override
  default StringAccumulator append(CharSequence csq, int start, int end) {
    if (csq == null) {
      csq = "null";
    }

    for (int i = start; i < end; ++i) {
      append(csq.charAt(i));
    }

    return this;
  }
}
