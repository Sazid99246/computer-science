package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * A logical 'not' expression.
 */
public class NotExp implements Expr {
  final Expr e;

  public NotExp(Expr e) {
    this.e = e;
  }

  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    return !this.e.evaluate(env);
  }
}
