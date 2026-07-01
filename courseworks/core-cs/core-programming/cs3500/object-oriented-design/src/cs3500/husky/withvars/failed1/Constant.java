package cs3500.husky.withvars.failed1;

/**
 * A boolean constant (true or false).
 */
public class Constant implements Expr {
  final boolean value;

  public Constant(boolean value) {
    this.value = value;
  }

  @Override
  public boolean evaluate() {
    return this.value;
  }
}
