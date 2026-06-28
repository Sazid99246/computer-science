package cs3500.lec07;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TestController4 {
  @Test
  public void testGo() throws Exception {
    InputStream in = new ByteArrayInputStream("3 4".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    CalcController controller4 = new Controller4(in, out);
    controller4.go(new Calculator());
    assertEquals("7", new String(bytes.toByteArray()));
  }
}
