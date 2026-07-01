package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * A boolean constant (true or false).
 */
public class Constant implements Expr {
  final boolean value;

  public Constant(boolean value) {
    this.value = value;
  }

  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    return this.value;
  }
}
