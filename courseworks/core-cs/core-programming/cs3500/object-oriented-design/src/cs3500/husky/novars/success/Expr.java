package cs3500.husky.novars.success;

/**
 * Represents a boolean expression using only constants and boolean operators.
 */
public interface Expr {
  /**
   * Evaluates the current expression.
   * @return The computed boolean result
   */
  boolean evaluate();
}
