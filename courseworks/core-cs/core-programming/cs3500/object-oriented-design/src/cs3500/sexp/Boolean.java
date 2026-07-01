package cs3500.sexp;

import java.util.List;

import cs3500.husky.withvars.success.Constant;
import cs3500.husky.withvars.success.Expr;

/**
 * A boolean constant {@link Sexp}.
 */
public class Boolean implements Sexp {
  boolean val;

  Boolean(boolean val) {
    this.val = val;
  }

  @Override
  public Expr translate() {
    return new Constant(this.val);
  }

  @Override
  public Expr translate(List<Sexp> kids) {
    throw new IllegalStateException("We should not have gotten here");
  }
}
