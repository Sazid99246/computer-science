package cs3500.husky.novars.success;

/**
 * A logical 'not' expression.
 */
public class NotExp implements Expr {
  final Expr e;

  public NotExp(Expr e) {
    this.e = e;
  }

  @Override
  public boolean evaluate() {
    return !this.e.evaluate();
  }
}
