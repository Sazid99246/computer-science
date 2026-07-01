package cs3500.lec11;

/**
 * Tests for {@link ExactArrayStringAccumulator}.
 */
public class ExactArrayStringAccumulatorTest extends StringAccumulatorTest {
  @Override
  protected StringAccumulator make() {
    return new ExactArrayStringAccumulator();
  }
}
