package cs3500.husky.novars.success;

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
  public boolean evaluate() {
    return this.e1.evaluate() && this.e2.evaluate();
  }
}
