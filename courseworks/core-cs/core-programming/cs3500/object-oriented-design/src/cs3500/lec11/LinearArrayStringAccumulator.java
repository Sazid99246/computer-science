package cs3500.lec11;

/**
 * An extension of {@code ArrayStringAccumulator} that grows the underlying
 * array in linear steps rather than by doubling. This can save memory but
 * may result in quadratic time for sequences of appends.
 */
public final class LinearArrayStringAccumulator
  extends AbstractArrayStringAccumulator
{
  /**
   * The default step size for new {@code LinearArrayStringAccumulator}s
   * created by the nullary constructor.
   */
  public static int DEFAULT_STEP = 16;

  /**
   * The step size for this {@code LinearArrayStringAccumulator}. The size of
   * the underlying array will always be the smallest integer multiple of
   * {@code step} that fits the accumulated string.
   */
  public final int step;

  /**
   * Constructs a new, empty {@code LinearArrayStringAccumulator} with the
   * given step size.
   *
   * @param step
   * <p><b>Precondition:</b> step &gt;= 1
   *
   * @throws IllegalArgumentException step &lt; 1
   */
  public LinearArrayStringAccumulator(int step) {
    if (step < 1) {
      throw new IllegalArgumentException("step cannot be less than 1");
    }
    this.step = step;
  }

  /**
   * Constructs a new, empty {@code LinearArrayStringAccumulator} with the
   * default step size of {@code DEFAULT_STEP}.
   */
  public LinearArrayStringAccumulator() {
    step = DEFAULT_STEP;
  }

  @Override
  protected int determineNewCapacity(int minCapacity) {
    // (a + b - 1) / b computes ceil((double) a / b),
    // so this finds the next multiple of step that is at least minCapacity:
    return ((minCapacity + step - 1) / step) * step;
  }
}
