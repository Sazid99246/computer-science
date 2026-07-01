package cs3500.husky.withvars.failed1;

/**
 * Describes a local binding of a variable to a value.
 */
public class LetExp implements Expr {
  final String name;
  final Expr e;
  final Expr body;

  LetExp(String name, Expr e, Expr body) {
    this.name = name;
    this.e = e;
    this.body = body;
  }

  @Override
  public boolean evaluate() {
    /*
      There's no way to implement this method with this signature
     */
    throw new IllegalStateException("Can't be done!");
  }
}
