package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the format method of {@link Duration}s.
 * Add your tests to this class to assure that your format
 * method works properly
 */
public abstract class AbstractDurationFormatTest {
  @Test
  public void formatExample1() {
    assertEquals("4 days, 0 weeks, and 9 hours",
            wdh(0, 4, 9)
                    .format("%d days, %w weeks, and %h hours"));
  }

  @Test
  public void formatExample2() {
    assertEquals("5:05:17",
            wdh(5, 5, 17).format("%w:%D:%H"));
  }

  // ADD MORE TESTS HERE
  // Your tests must only use wdh(...) and hours(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new WdhDuration(...)"

  // --- Valid Format Token Tests ---

  @Test
  public void testFormatAllSpecifiers() {
    // 1 week (168h) + 2 days (48h) + 5 hours = 221 hours
    Duration d = wdh(1, 2, 5);
    assertEquals("221-1-01-2-02-5-05-%",
            d.format("%t-%w-%W-%d-%D-%h-%H-%%"));
  }

  @Test
  public void testFormatZeroDurationPadding() {
    Duration zero = wdh(0, 0, 0);
    assertEquals("0 00 0 00 0 00", zero.format("%w %W %d %D %h %H"));
  }

  @Test
  public void testFormatLargeDurationHours() {
    Duration large = hours(5000); // 5000 hours = 29 weeks, 5 days, 8 hours
    assertEquals("5000 total hours across 29 weeks", large.format("%t total hours across %w weeks"));
  }

  @Test
  public void testLeftToRightPrecedence() {
    Duration d = wdh(1, 2, 3);
    // "%%t" -> should process "%%" into "%" first, then leave "t" literal. Result: "%t"
    assertEquals("%t", d.format("%%t"));
    // "%%%w" -> "%%" becomes "%", then "%w" becomes "1". Result: "%1"
    assertEquals("%1", d.format("%%%w"));
  }

  @Test
  public void testPlainLiteralTextNoSpecifiers() {
    Duration d = wdh(5, 3, 2);
    assertEquals("Hello World 123!", d.format("Hello World 123!"));
    assertEquals("", d.format(""));
  }

  // --- Exception / Malformed Template Tests ---

  @Test(expected = IllegalArgumentException.class)
  public void testFormatThrowsOnNullTemplate() {
    wdh(1, 1, 1).format(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFormatThrowsOnTrailingPercent() {
    // Single trailing percent character at the end of the string
    wdh(1, 1, 1).format("Days: %d, Percent: %");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFormatThrowsOnInvalidSpecifierLetter() {
    // '%x' is not a valid predefined specifier
    wdh(1, 1, 1).format("%w weeks and %x unknown");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFormatThrowsOnLonePercent() {
    wdh(0, 0, 0).format("%");
  }


  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within
    your tests.
   */

  /**
   * Constructs an instance of the class under test representing the duration
   * given in weeks, days and hours
   *
   * @param weeks the weeks in the duration
   * @param days  the days in the duration
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
  public static final class WdhDurationTest extends AbstractDurationFormatTest {
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
  public static final class CompactDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration wdh(int weeks, int days, int hours) {
      return new CompactDuration(weeks, days, hours);
    }

    @Override
    protected Duration hours(long inHours) {
      return new CompactDuration(inHours);
    }
  }
}