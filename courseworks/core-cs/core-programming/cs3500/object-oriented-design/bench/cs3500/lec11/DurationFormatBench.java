package cs3500.lec11;

import com.google.caliper.*;
import com.google.caliper.runner.CaliperMain;
import cs3500.hw01.duration.Duration;
import cs3500.hw01.duration.HmsDuration;

/**
 * Benchmarks for string accumulators.
 */
public final class DurationFormatBench {
  @Param({ "1000", "2000", "3000", "4000" }) int length;
  @Param({
    "DoublingArrayStringAccumulator",
    "ExactArrayStringAccumulator",
    "LinearArrayStringAccumulator",
    "PreallocArrayStringAccumulator",
    "StringStringAccumulator"
  }) String impl;

  private String template;
  private Duration duration;
  private Class<?> accClass;

  @BeforeExperiment
  public void setUp() throws ClassNotFoundException {
    char[] templateArray = new char[length];

    for (int i = 0; i < length; ++i) {
      if (i % 50 == 0 && i + 1 < length) {
        templateArray[i] = '%';
      } else {
        templateArray[i] = 's';
      }
    }

    template = String.valueOf(templateArray);
    duration = new HmsDuration(4, 5, 6);
    accClass = Class.forName("stringAccumulator." + impl);
  }

  @Benchmark
  public int timeFormat(int reps)
    throws IllegalAccessException,  InstantiationException
  {
    int result = 0;

    while (reps-- > 0) {
      StringAccumulator acc = (StringAccumulator) accClass.newInstance();
      duration.format(acc, template);
      result ^= acc.stringValue().length();
    }

    return result;
  }

  public static void main(String[] args) {
    CaliperMain.main(DurationFormatBench.class, args);
  }
}
