package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import java.util.Map;
//import edu.cs3500.spreadsheets.model.datastructures.values.IVal;

/**
 * Represents the worksheet model used in the visual views.
 */
public interface ViewWorksheet {
  
  /**
   * Returns the raw value of the cell at the Coord given.
   *
   * @param pos the position of the cell to be evaluated
   * @return the raw string given to the cell at the given Coord when constructing
   */
  String getRawVal(Coord pos);
  
  /**
   * Returns a map from location to value of every value in the WorkSheet.
   *
   * @return a Map of every value in the cell
   */
  Map<Coord, IVal> getAllValues();
  
  /**
   * Evaluates a single cell.
   *
   * @param c the coordinate of the cell
   */
  IVal evaluateCell(Coord c);
}
