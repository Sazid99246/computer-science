package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * Interface for all operations.
 */
public interface Operation {

  /**
   * Runs the operation.
   *
   * @param vals the list of values being used
   * @return the result
   */
  CellValue apply(List<CellValue> vals);


}
