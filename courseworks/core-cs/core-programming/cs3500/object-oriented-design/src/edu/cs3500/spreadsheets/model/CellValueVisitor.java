package edu.cs3500.spreadsheets.model;

/**
 * <p>Interface for Visitors of the {@link CellValue} abstract class.</p>
 *
 * @param <R> the return type for the implementation.
 */
public interface CellValueVisitor<R> {

  /**
   * <p>Visit method for a {@link CVDouble}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  R visitDouble(Double cv);

  /**
   * <p>Visit method for a {@link CVError}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  R visitError(String s);

  /**
   * <p>Visit method for a {@link CVBlank}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  R visitBlank(String s);

  /**
   * <p>Visit method for a {@link CVBool}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  R visitBool(Boolean cv);

  /**
   * <p>Visit method for a {@link CVString}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  R visitString(String cv);

}
