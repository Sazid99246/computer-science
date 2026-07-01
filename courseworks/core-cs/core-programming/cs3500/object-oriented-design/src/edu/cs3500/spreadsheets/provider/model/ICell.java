package edu.cs3500.spreadsheets.provider.model;


import edu.cs3500.spreadsheets.model.Coord;
import java.util.Set;

//import edu.cs3500.spreadsheets.model.datastructures.values.IVal;

/**
 * A class representing some implementation of a cell that lives in a Worksheet. It is assumed to be
 * able to evaluate to some value, contain some raw data that represents its formula, and be able to
 * decide if it references a given ICell.
 */
public interface ICell {
  /**
   * Evaluates a Cell while checking that the cell does not contain a cyclic reference.
   *
   * @param visited The list of cells that have already been traversed
   * @return the evaluated version of this Cell
   */
  IVal evaluate(Set<Coord> visited);

  /**
   * Removes the cache, then re-evaluates the cell, while checking for cycles.
   *
   * @param visited The list of cells that have already been traversed
   * @param clean   a flag indicating that the cache should be cleaned and the cell should be
   *                re-evaluated.
   * @return the re-evaluated version of the cell
   */
  IVal evaluate(Set<Coord> visited, boolean clean);

  /**
   * Checks if this cell references a cell at the given coord.
   *
   * @param c the coord of the cell being compared to
   * @return a boolean represting whether or not this cell references the other
   */
  boolean references(Coord c);

  /**
   * Adds the Coord c to this cells accumulation of cells that it references.
   *
   * @param c the coord being added
   */
  void addReferences(Coord c);

  /**
   * Gets the evaluated value of this cell.
   *
   * @return the evaluated value of this cell
   */
  IVal getVal();

  /**
   * Gets the raw data of this cell.
   *
   * @return the input given when creating this cell
   */
  String getRawVal();

  /**Sets the value field of the cell to null.
   *
   */
  void clear();

}
