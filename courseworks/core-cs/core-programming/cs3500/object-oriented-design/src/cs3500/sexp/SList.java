package cs3500.sexp;

import java.util.List;

import cs3500.husky.withvars.success.Expr;

/**
 * Represents a list s-expression (i.e. <code>({@link Sexp} ...)</code>).
 */
public class SList implements Sexp {
  List<Sexp> contents;

  SList(List<Sexp> contents) {
    this.contents = contents;
  }

  @Override
  public Expr translate() {
    return this.contents.get(0).translate(this.contents);
  }

  @Override
  public Expr translate(List<Sexp> kids) {
    throw new IllegalStateException("We should not have gotten here");
  }
}
