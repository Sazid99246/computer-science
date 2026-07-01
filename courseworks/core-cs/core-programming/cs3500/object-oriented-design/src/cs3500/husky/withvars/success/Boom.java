package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * A bogus boolean expression, useful only for testing short circuiting.
 */
public class Boom implements Expr {
  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    throw new RuntimeException("Shouldn't get here!");
  }
}
