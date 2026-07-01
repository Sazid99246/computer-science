package cs3500.sexp;

import java.util.List;

import cs3500.husky.withvars.success.Expr;

/**
 * A numeric constant {@link Sexp}.
 */
public class Number implements Sexp {
  int num;

  public Number(int num) {
    this.num = num;
  }

  @Override
  public Expr translate() {
    throw new IllegalStateException("How did we get here?");
  }

  @Override
  public Expr translate(List<Sexp> kids) {
    throw new IllegalStateException("We should not have gotten here");
  }

}
