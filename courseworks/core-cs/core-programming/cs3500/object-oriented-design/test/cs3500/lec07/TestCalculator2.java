package cs3500.lec07;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCalculator2 {
  @Test
  public void testAdd() {
    assertEquals(7, new Calculator().add(3, 4));
  }
}
