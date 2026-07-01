package cs3500.lec11;

/**
 * Tests for {@link LinearArrayStringAccumulator}.
 */
public class LinearArrayStringAccumulatorTest extends StringAccumulatorTest {
  @Override
  protected StringAccumulator make() {
    return new LinearArrayStringAccumulator();
  }
}
