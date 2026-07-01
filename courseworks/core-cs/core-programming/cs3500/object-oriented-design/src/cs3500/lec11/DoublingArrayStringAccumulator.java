package cs3500.lec11;

/**
 * A string accumulator that expands by doubling the size of the underlying
 * array to achieve linear time in the number of characters appended.
 * Equivalently, this achieves amortized constant time for each character
 * appended.
 */
public final class DoublingArrayStringAccumulator
  extends AbstractArrayStringAccumulator
{
  /**
   * Computes the new capacity, which must meet or exceed the requested
   * minimum capacity. The strategy for {@code DoublingArrayStringAccumulator}
   * is to double if that is sufficient to reach {@code minCapacity}, and
   * otherwise to grow to {@code minCapacity} exactly.
   *
   * @param minCapacity the minimum capacity to return
   * @return minCapacity
   */
  @Override
  protected int determineNewCapacity(int minCapacity) {
    return Integer.max(minCapacity, 2 * capacity());
  }
}
