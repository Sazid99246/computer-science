package cs3500.sexp;

import java.util.HashMap;

import cs3500.husky.withvars.success.Expr;

/**
 * Harness to run a simple {@link Sexp} and {@link Expr} example.
 */
public class SexpMain {
  /**
   * Runs the program.
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    //String sexp = "(+ 1 (- 3 2))";
    //Sexp s = Parser.parse(sexp);

    String sexp = "(and (or #true (not #false)) #true)";

    Sexp s = Parser.parse(sexp);
    Expr e = s.translate();

    System.out.println("e: " + e.evaluate(new HashMap<>()));

    return;
  }
}
