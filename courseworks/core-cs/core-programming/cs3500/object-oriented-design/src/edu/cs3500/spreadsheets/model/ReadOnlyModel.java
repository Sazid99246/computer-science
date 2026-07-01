package edu.cs3500.spreadsheets.model;

import java.util.Map;

/**
 * Read-only implementation of the {@link SpreadsheetModel} interface.
 */
public class ReadOnlyModel implements SpreadsheetModel {

  private final SpreadsheetModel model;

  /**
   * Constructor for the read-only model.
   *
   * @param model the internal model through which all reads take place.
   */
  public ReadOnlyModel(SpreadsheetModel model) {
    this.model = model;
  }

  @Override
  public Cell getCellAt(Coord coord) {
    return model.getCellAt(coord);
  }

  @Override
  public String getRawCellAt(Coord coord) {
    return model.getRawCellAt(coord);
  }

  @Override
  public void editCell(Coord coord, String string) {

    // This is a read only model, it needs to prevent editing of the cells.
  }

  @Override
  public Map<Coord, ICell> getAllCells() {
    return model.getAllCells();
  }

  @Override
  public void clearSheet() {
    // This is a read only model, the cells should not be cleared.
  }

  @Override
  public String toString() {
    return this.model.toString();
  }
}
