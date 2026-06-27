package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/** Tests for {@link Duration}s. */
public abstract class AbstractDurationTest {
  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */
  /**
   * Constructs an instance of the class under test representing the duration
   * given in weeks, days and hours.
   *
   * @param weeks the weeks in the duration
   * @param days the days in the duration
   * @param hours the hours in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration wdh(int weeks, int days, int hours);

  /**
   * Constructs an instance of the class under test representing the duration
   * given in hours.
   *
   * @param inHours the total hours in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hours(long inHours);

  /**
   * A nested testing factory class, that uses {@link WdhDuration} for
   * all of its test cases.
   */
  public static final class WdhDurationTest extends AbstractDurationTest {
    @Override
    protected Duration wdh(int weeks, int days, int hours) {
      return new WdhDuration(weeks, days, hours);
    }

    @Override
    protected Duration hours(long inHours) {
      return new WdhDuration(inHours);
    }
  }

  /**
   * A nested testing factory class, that uses {@link CompactDuration} for
   * all of its test cases.
   */
  public static final class CompactDurationTest extends AbstractDurationTest {
    @Override
    protected Duration wdh(int weeks, int days, int hours) {
      return new CompactDuration(weeks, days, hours);
    }

    @Override
    protected Duration hours(long inHours) {
      return new CompactDuration(inHours);
    }
  }




  // The interesting tests for Durations begin here.
  // Note how all the tests are defined in terms of wdh(...) and hours(...),
  // rather than being defined using "new CompactDuration(...)"
  // or "new WdhDuration(...)".  In this way, they're *abstracted* over
  // which kind of Duration they're testing -- because it shouldn't matter!
  // When JUnit runs, it will find the two classes above, and instantiate them.
  // They in turn specialize the wdh(...) and hours(...) methods to produce the
  // two kinds of Durations, but they inherit all the test methods below.
  // That means JUnit will run all the test methods below twice: once for
  // CompactDurations and once for HmsDurations.
  // It's almost like AbstractDurationTest is a "factory" for making tests,
  // and in fact, this is a variation on a pattern we'll see again later
  // in the course.



  static long s1 = 328375982;
  static long s2 = 299699;

  Duration d1_23_45 = wdh(1, 23, 45);
  Duration d2_03_00 = wdh(2, 3, 0);
  Duration d457_03_00 = wdh(457, 3, 0);

  @Test
  public void zeroIsZero() {
    assertEquals(0, wdh(0, 0, 0).inHours());
  }

  @Test
  public void asWdhWorks() {
    assertEquals("4:3:21", d1_23_45.asWdh());
    assertEquals("2:3:00", d2_03_00.asWdh());
    assertEquals("457:3:00", d457_03_00.asWdh());
  }

  @Test
  public void equalsWorks() {
    assertEquals(wdh(1, 23, 45), d1_23_45);
    assertEquals(d1_23_45, d1_23_45);
    assertEquals(hours(s1), hours(s1));
    assertEquals(hours(s2), hours(s2));

    assertNotEquals(hours(s1), hours(s2));
    assertNotEquals(hours(s2), hours(s1));
    assertNotEquals(d1_23_45, d2_03_00);
    assertNotEquals(d2_03_00, d1_23_45);
    assertNotEquals(wdh(1, 23, 44), wdh(1, 23, 45));
  }

  @Test
  public void hashCodeIsHashOfHours() {
    assertEquals(Long.hashCode(s1), hours(s1).hashCode());
    assertEquals(Long.hashCode(s2), hours(s2).hashCode());
    assertEquals(Long.hashCode(d1_23_45.inHours()), d1_23_45.hashCode());
    assertEquals(Long.hashCode(d457_03_00.inHours()), d457_03_00.hashCode());
  }

  @Test
  public void wdhConstructorCarriesDaysAndHours() {
    assertEquals("14:2:23", wdh(1, 93, 23).asWdh());
    assertEquals("7:0:20", wdh(2, 33, 68).asWdh());
    assertEquals("50:0:22", wdh(2, 33, 7294).asWdh());
  }

  @Test(expected = IllegalArgumentException.class)
  public void wdhConstructorDisallowsNegative() {
    wdh(2, 4, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void hoursConstructorDisallowsNegative() {
    hours(-1);
  }

  @Test
  public void inHoursIsInverseOfHoursConstructor() {
    assertEquals(s1, hours(s1).inHours());
    assertEquals(s2, hours(s2).inHours());

    assertEquals(hours(s1),
                  hours(hours(s1).inHours()));
    assertEquals(hours(s2),
                  hours(hours(s2).inHours()));

    assertEquals(d1_23_45,
                  hours(d1_23_45.inHours()));
    assertEquals(d2_03_00,
                  hours(d2_03_00.inHours()));
    assertEquals(d457_03_00,
                  hours(d457_03_00.inHours()));
  }

  @Test
  public void _1_23_03_plus_2_14_45_is_3_37_48() {
    assertEquals(wdh(3, 37, 48),
                 wdh(1, 23, 3).plus(wdh(2, 14, 45)));
  }

  @Test
  public void plusCarries() {
    assertEquals(wdh(8, 5, 6),
                 wdh(1, 23, 33).plus(wdh(2, 14, 45)));
    assertEquals(wdh(14, 4, 0),
                 wdh(1, 23, 3).plus(wdh(2, 56, 45)));
    assertEquals(wdh(14, 5, 6),
                 wdh(1, 23, 33).plus(wdh(2, 56, 45)));
  }

  @Test
  public void addWorks() {
    assertEquals(s1 + s2, hours(s1).plus(hours(s2)).inHours());
    assertEquals(2 * s1 + s2, hours(2 * s1).plus(hours(s2)).inHours());
  }
}
