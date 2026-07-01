package cs3500.husky.withvars.failed1;

/**
 * Represents a variable, whose value is determined by some environment...but there is
 * none available.
 */
public class Variable implements Expr {
  String name;

  public Variable(String name) {
    this.name = name;
  }

  @Override
  public boolean evaluate() {
    /*
      There's no way to implement this method with this signature
     */
    throw new IllegalStateException("Can't be done!");
  }
}
