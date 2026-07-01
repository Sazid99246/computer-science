package edu.cs3500.spreadsheets.model;

/**
 * Represents an interface for all cells.
 */
public interface ICell {

  /**
   * Gets the value of this cell.
   *
   * @return the cell value
   */
  CellValue getValue();

  /**
   * Gets the raw value of this cell.
   *
   * @return the raw string value
   */
  String getRawValue();

  /**
   * Gets the coordinates of this cell.
   *
   * @return the coordinates.
   */
  Coord getCoord();

  /**
   * Updates this cell with new contents..
   *
   * @param contents the new raw contents of this cell.
   */
  void update(String contents, SpreadsheetModel model);

}
