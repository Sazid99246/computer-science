package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * Represents a boolean expression using constants and boolean operators,
 * and local variable bindings.
 */
public interface Expr {
  /**
   * Evaluates this {@link Expr} in the given environment.
   *
   * @param env A mapping from variable names to values
   * @return The boolean result of this expression
   */
  boolean evaluate(Map<String, Boolean> env);
}
