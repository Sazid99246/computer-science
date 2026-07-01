package edu.cs3500.spreadsheets.model;

import java.util.Map;

/**
 * Interface for the model of spreadsheets. Has methods to get a param K, get the raw value,
 * edit a cell, get a copy of the whole model, and clear the sheet.
 */
public interface SpreadsheetModel {

  /**
   * Gets the cell value at the given coordinate.
   *
   * @param coord the coordinates of the desired cell
   * @return The cell at the given coordinate.
   */
  Cell getCellAt(Coord coord);

  /**
   * <p>Gets the raw contents of the Cell at the given coordinate.</p>
   *
   * @param coord the coordinates of the desired cell
   * @return the raw String value of the requested cell.
   */
  String getRawCellAt(Coord coord);

  /**
   * Updates or adds a cell at the desired coordinates.
   *
   * @param coord  spot to add or update the cell.
   * @param string new value of the cell.
   */
  void editCell(Coord coord, String string);

  /**
   * Gets the map of coordinates and their associated Cells.
   *
   * @return
   */
  Map<Coord, ICell> getAllCells();

  /**
   * Clears the sheet of all cell values.
   */
  void clearSheet();
}
