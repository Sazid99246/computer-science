package cs3500.lec11;

/**
 * A string accumulator with a naive growing strategy: it maintains the
 * underlying array at the exact size needed to store the accumulated string.
 * This has poor time complexity for sequences of appends,  but avoids
 * using more memory than necessary.
 */
public final class ExactArrayStringAccumulator
  extends AbstractArrayStringAccumulator
{
  /**
   * Computes the new capacity, which is exactly the requested minimum capacity.
   *
   * @param minCapacity the minimum capacity to return
   * @return minCapacity
   */
  @Override
  protected int determineNewCapacity(int minCapacity) {
    return minCapacity;
  }
}
