package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * A logical 'and' expression: evaluation should short-circuit if the first argument is false.
 */
public class AndExp implements Expr {
  final Expr e1;
  final Expr e2;

  public AndExp(Expr e1, Expr e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    return this.e1.evaluate(env) && this.e2.evaluate(env);
  }
}
