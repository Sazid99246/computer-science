package cs3500.lec11;

/**
 * Tests for {@link StringStringAccumulator}.
 */
public class StringStringAccumulatorTest extends StringAccumulatorTest {
  @Override
  protected StringAccumulator make() {
    return new StringStringAccumulator();
  }
}
