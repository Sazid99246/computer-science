package cs3500.lec11;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.io.IOException;

/**
 * Benchmarks for string accumulators.
 */

@VmOptions("-XX:-TieredCompilation")
public final class StringAccumulatorBench {
  @Param({ "1000", "2000", "3000", "4000" }) int length;
  @Param({
    "StringBuilder",
    "StringBuffer",
    "DoublingArrayStringAccumulator",
    "ExactArrayStringAccumulator",
    "LinearArrayStringAccumulator",
    "StringStringAccumulator"
  }) String impl;

  private Class<Appendable> klass;

  @SuppressWarnings("unchecked")
  @BeforeExperiment
  public void setUp() throws ClassNotFoundException {
    try {
      klass = (Class<Appendable>) Class.forName("cs3500.lec11." + impl);
    } catch (ClassNotFoundException e) {
      klass = (Class<Appendable>) Class.forName("java.lang." + impl);
    }
  }

  @Benchmark
  public int timeAccumulation(int reps)
    throws IllegalAccessException, InstantiationException, IOException
  {
    int result = 0;

    while (reps-- > 0) {
      Appendable acc = klass.newInstance();

      for (int j = 0; j < length; ++j) {
        acc.append((char) j);
      }

      result ^= acc.toString().length();
    }

    return result;
  }

  public static void main(String[] args) {
    CaliperMain.main(StringAccumulatorBench.class, args);
  }
}
