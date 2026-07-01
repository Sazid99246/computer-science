package edu.cs3500.spreadsheets.provider.model;

/**
 * For Java Style.
 * @param <R> parameterization
 */
public interface ValueVisitor<R> {

  /**
   * returns double as a string.
   *
   * @param d the double value
   */
  R visitValueDouble(Double d);

  /**
   * returns boolean as a string.
   *
   * @param b the boolean value
   */
  R visitValueBoolean(Boolean b);

  /**
   * returns string.
   *
   * @param s the string value
   */
  R visitValueString(String s);

  /**
   * returns blank value as a string.
   */
  R visitBlankValue();

  /**
   * returns error as a string.
   *
   * @param e the error value
   */
  R visitErr(IException e);
}

