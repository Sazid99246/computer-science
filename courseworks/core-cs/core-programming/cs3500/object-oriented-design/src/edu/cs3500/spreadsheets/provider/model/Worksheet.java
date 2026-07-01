package edu.cs3500.spreadsheets.provider.model;


import edu.cs3500.spreadsheets.model.Coord;
import java.util.Set;
//import edu.cs3500.spreadsheets.model.datastructures.values.IVal;

/**
 * Represents a Excel style spreadsheets - The Worksheet contains some number of cells that may or
 * may not depend on other cells. The cells should all be evaluated after they are built.
 */
public interface Worksheet extends ViewWorksheet {
  /**
   * Adds a cell to the WorkSheet implementation given some instructions from rawData.
   *
   * @param pos     the position of the cell to be added
   * @param rawData a string representing the value or formula of the cell being added
   */
  void addCell(Coord pos, String rawData);


  /**
   * Removes a cell from the worksheet's board.
   *
   * @param pos the position of the cell to be removed.
   */
  void remove(Coord pos);


  /**
   * evaluates a cell at a position having already visited some cells.
   *
   * @param c       the location of the cell to evaluate
   * @param visited The cells already traversed
   * @return the evaluated value of this cell
   */
  IVal evaluateCell(Coord c, Set<Coord> visited);

  //removed the redundant method from the interface after a conversation about design w/
  //prof. Lerner
  //  /**return the value of the cell at the given position.
  //   *
  //   * @param pos the position of the cell to be evaluated
  //   * @return a String representing the value of the given cell
  //   */
  //  String getCellValue(Coord pos);

}
