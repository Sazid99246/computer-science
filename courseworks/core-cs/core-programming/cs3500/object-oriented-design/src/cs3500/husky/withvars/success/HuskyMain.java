package cs3500.husky.withvars.success;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple harness for Expressions with local bindings and variables.
 */
public class HuskyMain {
  /**
   * Run the program.
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    Map<String, Boolean> emptyEnv = new HashMap<>();
    Expr e = new LetExp("x", new Constant(true),
            new LetExp("y", new Constant(false),
                    new AndExp(new Variable("x"), new Variable("y"))));
    System.out.println("e: " + e.evaluate(emptyEnv));

    Expr e2 =
            new LetExp("x", new Constant(true),
              new AndExp(new LetExp("x", new Constant(false),
                      new NotExp(new Variable("x"))),
                      new Variable("x")));
    System.out.println("e2: " + e2.evaluate(emptyEnv));


    Expr myTrue = new AndExp(new Constant(true),
            new NotExp(new Constant(false)));
    System.out.println("myTrue: " + myTrue.evaluate(emptyEnv));
    return;
  }
}
