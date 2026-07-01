package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a basic version of a spreadsheets model. Uses a map of Coord->Cell.
 */
public class BasicSpreadsheetModel implements SpreadsheetModel {

  protected HashMap<Coord, Cell> coordMap = new HashMap<>();

  @Override
  public Cell getCellAt(Coord coord) {
    return coordMap.getOrDefault(coord, new Cell(coord));
  }

  @Override
  public String getRawCellAt(Coord coord) {
    try {
      return coordMap.get(coord).getRawValue();
    } catch (NullPointerException e) {
      return new Cell(coord).getRawValue();
    }
  }

  @Override
  public void editCell(Coord coord, String string) {
    if (!coordMap.containsKey(coord)) {
      coordMap.put(coord, new Cell(coord, string, this));
    } else {
      coordMap.get(coord).update(string, this);
    }
  }


  @Override
  public Map<Coord, ICell> getAllCells() {
    return Map.copyOf(coordMap);
  }

  @Override
  public void clearSheet() {
    coordMap = new HashMap<>();
  }
}
