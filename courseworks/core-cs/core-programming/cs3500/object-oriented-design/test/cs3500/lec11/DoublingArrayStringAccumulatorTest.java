package cs3500.lec11;

/**
 * Tests for {@link AbstractArrayStringAccumulator}.
 */
public class DoublingArrayStringAccumulatorTest
  extends StringAccumulatorTest
{
  @Override
  protected StringAccumulator make() {
    return new DoublingArrayStringAccumulator();
  }
}
