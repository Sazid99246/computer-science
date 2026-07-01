package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * Describes a local binding of a variable to a value.
 */
public class LetExp implements Expr {
  final String name;
  final Expr e;
  final Expr body;

  /**
   * Constructs an expression describing a let-bound variable.
   * @param name The name of the variable
   * @param e The expression to be bound
   * @param body The body in which it is in scope
   */
  public LetExp(String name, Expr e, Expr body) {
    this.name = name;
    this.e = e;
    this.body = body;
  }

  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    boolean eVal = e.evaluate(env);
    Boolean oldVal = env.getOrDefault(this.name, null);
    env.put(this.name, eVal);
    boolean ans = this.body.evaluate(env);
    if (oldVal == null) {
      env.remove(this.name);
    }
    else {
      env.put(this.name, oldVal);
    }
    return ans;
  }
}
