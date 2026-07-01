package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Adapter from our model interface to their model interface. Includes overriding
 * our own methods so that it can be used in both cases.
 */
public class ModelAdapter implements Worksheet, SpreadsheetModel {

  private SpreadsheetModel model;

  public ModelAdapter() {
    this.model = new BasicSpreadsheetModel();
  }

  public ModelAdapter(SpreadsheetModel model) {
    this.model = model;
  }


  /**
   * Adds a cell to the WorkSheet implementation given some instructions from rawData.
   *
   * @param pos     the position of the cell to be added
   * @param rawData a string representing the value or formula of the cell being added
   */
  @Override
  public void addCell(Coord pos, String rawData) {

    this.model.editCell(pos, rawData);
  }

  /**
   * Removes a cell from the worksheet's board.
   *
   * @param pos the position of the cell to be removed.
   */
  @Override
  public void remove(Coord pos) {

    this.model.editCell(pos, "");
  }

  /**
   * evaluates a cell at a position having already visited some cells.
   *
   * @param c       the location of the cell to evaluate
   * @param visited The cells already traversed
   * @return the evaluated value of this cell
   */
  @Override
  public IVal evaluateCell(Coord c, Set<Coord> visited) {

    return new ValAdapter(model.getCellAt(c).getValue());
  }

  /**
   * Evaluates a single cell.
   *
   * @param c the coordinate of the cell
   */
  @Override
  public IVal evaluateCell(Coord c) {
    return new ValAdapter(model.getCellAt(c).getValue());
  }

  /**
   * Returns the raw value of the cell at the Coord given.
   *
   * @param pos the position of the cell to be evaluated
   * @return the raw string given to the cell at the given Coord when constructing
   */
  @Override
  public String getRawVal(Coord pos) {
    return model.getRawCellAt(pos);
  }

  /**
   * Returns a map from location to value of every value in the WorkSheet.
   *
   * @return a Map of every value in the cell
   */
  @Override
  public Map<Coord, IVal> getAllValues() {
    Map<Coord, ICell> map = model.getAllCells();
    Map<Coord, IVal> map2 = new HashMap<>();

    for (Entry<Coord, ICell> entry : map.entrySet()) {
      map2.put(entry.getKey(), new ValAdapter(entry.getValue().getValue()));
    }

    return map2;
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
    model.editCell(coord, string);
  }

  @Override
  public Map<Coord, ICell> getAllCells() {
    return model.getAllCells();
  }

  @Override
  public void clearSheet() {
    model.clearSheet();
  }
}
