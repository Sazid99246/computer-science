package cs3500.lec11;

/**
 * Preallocates the underlying array to size {@link #INITIAL_CAPACITY}.
 */
public class PreallocArrayStringAccumulator
  extends AbstractArrayStringAccumulator
{
  /**
   * The initial capacity.
   */
  public static final int INITIAL_CAPACITY = 65_536;

  @Override
  protected int initialCapacity () {
    return INITIAL_CAPACITY;
  }

  @Override
  protected int determineNewCapacity(int minCapacity) {
    return 2 * minCapacity;
  }
}
