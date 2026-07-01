package cs3500.lec11;

import java.util.Arrays;

/**
 * A string accumulator that expands as needed according to a programmable
 * strategy. Extend this class and override the {@link #determineNewCapacity(int)}
 * method in order to define a new array expansion strategy.
 */
public abstract class AbstractArrayStringAccumulator
  implements StringAccumulator
{
  /**
   * The default initial capacity for a new, empty
   * {@code AbstractArrayStringAccumulator}. Override {@link #initialCapacity
   * ()} to specify a different initial capacity.
   */
  public static final int DEFAULT_CAPACITY = 10;

  // We represent a string accumulator using a character array as a buffer.
  // The array is not necessarily full, so we keep track of the length of the
  // accumulated string separately.
  //
  // We enforce two class invariants: that the buffer field not be null,
  // and that the buffer be at least as large as the string it has to hold.

  // Holds the characters in the accumulated string (may not be full):
  private char[] contents = new char[initialCapacity()];
  // The length of the characters accumulated thus far:
  private int length = 0;

  // CLASS INVARIANTS:
  //  - contents != null
  //  - length <= contents.length

  /**
   * Returns the initial capacity to use for a new,
   * empty {@code AbstractArrayStringAccumulator}.
   *
   * @return the capacity
   */
  protected int initialCapacity () {
    return DEFAULT_CAPACITY;
  }

  /**
   * Computes the new capacity to use for a requested minimum capacity.
   *
   * <p>
   * <b>For subclasses:</b>
   * This method is called by the {@code append} methods in order to make
   * room for the characters to append, only in case there is insufficient
   * capacity already. Override this method to change the growing strategy for
   * the underlying array. The return {@code int} <b>must</b> be no smaller
   * than the parameter {@code minCapacity}.
   *
   * @param minCapacity the minimum capacity to return
   * @return the new capacity, according to the strategy
   *         <p><b>POSTCONDITION:</b>
   *         result &gt;= minCapacity
   */
  protected abstract int determineNewCapacity(int minCapacity);

  @Override
  public final StringAccumulator append(char c) {
    ensureCapacity(length + 1);
    contents[length++] = c ;
    return this;
  }

  @Override
  public StringAccumulator append(CharSequence csq, int start, int end) {
    // We can do better than the default implementation of this method by
    // expanding at most once, even if the subsequence to append is long.
    ensureCapacity(length + end - start);

    // That same weird behavior from Appendable
    if (csq == null) {
      csq = "null";
    }

    while (start < end) {
      contents[length++] = csq.charAt(start++);
    }

    return this;
  }

  /**
   * Returns the current capacity of the StringAccumulator.
   *
   * @return the capacity
   */
  public final int capacity() {
    return contents.length;
  }

  /**
   * Resizes the underlying array, if necessary, to ensure that the capacity
   * exceeds the specified {@code minCapacity}.
   *
   * @param minCapacity (lower bound for) the new capacity
   */
  public final void ensureCapacity(int minCapacity) {
    if (capacity() < minCapacity) {
      resize(determineNewCapacity(minCapacity));
    }
  }

  /**
   * Resizes the underlying array to the given capacity.
   *
   * <p>UNCHECKED PRECONDITION: determineNewCapacity >= length
   *
   * @param newCapacity the new capacity of the array
   */
  private void resize(int newCapacity) {
    contents = Arrays.copyOf(contents, newCapacity);
  }

  @Override
  public final String toString() {
    return String.valueOf(contents, 0, length);
  }

  @Override
  public final String stringValue() {
    return toString();
  }

  @Override
  public final int length() {
    return length;
  }
}
