package cs3500.lec11;

/**
 * Tests for {@link stringAccumulator.LinearArrayStringAccumulator}.
 */
public class PreallocArrayStringAccumulatorTest extends StringAccumulatorTest {
  @Override
  protected StringAccumulator make() {
    return new PreallocArrayStringAccumulator();
  }
}
