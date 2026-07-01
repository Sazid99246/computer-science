package cs3500.lec11;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@code StringAccumulator}s. Extend this class and override
 * method {@code make()} to return empty instances of the string accumulator
 * class to be tested.
 */
abstract class StringAccumulatorTest {
  protected abstract StringAccumulator make();

  StringAccumulator acc = make();

  @Test
  public void testEmpty() {
    assertEquals("", acc.stringValue());
  }

  @Test
  public void testAppend_oneChar() {
    acc.append('a');
    assertEquals("a", acc.stringValue());
  }

  @Test
  public void testAppend_1Char_chained() {
    assertEquals("a", acc.append('a').stringValue());
  }

  @Test
  public void testAppend_3Chars_chained() {
    assertEquals("abc",
                 acc.append('a').append('b').append('c').stringValue());
  }

  @Test
  public void testAppend_Strings() {
    acc.append("Hello");
    acc.append(", ");
    acc.append("world").append("!");

    assertEquals("Hello, world!", acc.stringValue());
  }

  @Test
  public void testAppend_Mixed() {
    acc.append("Hello");
    acc.append(',');
    acc.append(' ');
    acc.append("world").append('!');

    assertEquals("Hello, world!", acc.stringValue());
  }

  @Test
  public void testLarge() {
    for (int i = 0; i < 10000; ++i) {
      acc.append((char) i);
    }

    String result = acc.stringValue();

    for (int i = 0; i < 10000; ++i) {
      assertEquals((char) i, result.charAt(i));
    }
  }
}
