package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.CVBlank;
import edu.cs3500.spreadsheets.model.CVBool;
import edu.cs3500.spreadsheets.model.CVDouble;
import edu.cs3500.spreadsheets.model.CVError;
import edu.cs3500.spreadsheets.model.CVString;
import edu.cs3500.spreadsheets.model.CellValueVisitor;

/**
 * Adapts their VisitorAdapter to our CellValueVisitor to handle visiting our implementation of
 * cell values instead of their IVal.
 *
 * @param <R> the return type for the methods.
 */
public class VisitorAdapter<R> implements CellValueVisitor {

  private ValueVisitor<R> visitor;

  /**
   * Default constructor. Takes a ValueVisitor of this param.
   *
   * @param visitor to be adapted.
   */
  public VisitorAdapter(ValueVisitor<R> visitor) {
    this.visitor = visitor;
  }

  /**
   * <p>Visit method for a {@link CVDouble}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  @Override
  public R visitDouble(Double cv) {
    return visitor.visitValueDouble(Double.valueOf(cv.toString()));
  }

  /**
   * <p>Visit method for a {@link CVError}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  @Override
  public R visitError(String cv) {
    return visitor.visitErr(null);
  }

  /**
   * <p>Visit method for a {@link CVBlank}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  @Override
  public R visitBlank(String cv) {
    return visitor.visitBlankValue();
  }

  /**
   * <p>Visit method for a {@link CVBool}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  @Override
  public R visitBool(Boolean cv) {
    return visitor.visitValueBoolean(Boolean.valueOf(cv.toString()));
  }

  /**
   * <p>Visit method for a {@link CVString}.</p>
   *
   * @param cv the value being visited.
   * @return the output, parameterized by the class.
   */
  @Override
  public R visitString(String cv) {
    return visitor.visitValueString(cv.toString());
  }
}
