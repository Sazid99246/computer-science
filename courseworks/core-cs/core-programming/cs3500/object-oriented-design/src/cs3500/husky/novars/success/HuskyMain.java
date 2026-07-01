package cs3500.husky.novars.success;

/**
 * A simple harness for testing Expressions.
 */
public class HuskyMain {
  /**
   * Run the program.
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    Expr myTrue = new AndExp(new Constant(true),
            new NotExp(new Constant(false)));
    System.out.println("myTrue: " + myTrue.evaluate());
    return;
  }
}
