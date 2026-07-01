package cs3500.husky.withvars.failed1;

/**
 * A bogus boolean expression, useful only for testing short circuiting.
 */
public class Boom implements Expr {
  @Override
  public boolean evaluate() {
    throw new RuntimeException("Shouldn't get here!");
  }
}
