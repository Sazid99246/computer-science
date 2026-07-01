package cs3500.sexp;

import java.util.List;

import cs3500.husky.withvars.success.Expr;

/**
 * Represents an s-expression.
 */
public interface Sexp {
  /**
   * Translates this {@link Sexp} to an {@link Expr}.
   * @return The translated expression
   */
  Expr translate();

  /**
   * Translates the given list of {@link Sexp}s into an {@link Expr}, assuming
   * this {@link Sexp} the first element of the given list.  This is useful for translating
   * expressions based on their leading symbol
   *
   * @param kids The contents of a list {@link Sexp}
   * @return The translated expression
   */
  Expr translate(List<Sexp> kids);
}
