package edu.cs3500.spreadsheets.model;

/**
 * Interface for cell values, includes mainly the basic datatypes displayable in a cell
 * (no references or functions).
 */
public interface CellValue {

  /**
   * For using {@link CellValueVisitor}.
   *
   * @param visitor to accept
   * @param <R> the parameterization for the visitor
   * @return the output for the specific cell value.
   */
  <R> R accept(CellValueVisitor<R> visitor);

}
