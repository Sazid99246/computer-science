package cs3500.lec07;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class TestController5 {
  @Test
  public void testGo() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 4");
    CalcController controller5 = new Controller5(in, out);
    controller5.go(new Calculator());
    assertEquals("7\n", out.toString());
  }
}
