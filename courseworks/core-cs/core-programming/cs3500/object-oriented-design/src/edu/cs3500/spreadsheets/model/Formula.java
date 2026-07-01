package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Set;

/**
 * General Interface for all Cell values, Functions and References.
 */
public interface Formula {

  /**
   * <p>Returns the final Cell Value of a Formula.</p>
   *
   * @return the final Cell Value.
   */
  CellValue getValue();

  /**
   * <p>Helper function for the {@link Function} flatten method.</p>
   *
   * @return a list of the cell values from this formula
   */
  ArrayList<CellValue> flattenHelp();

  /**
   * <p>Checks if the given formula has a cycle in it's contents.</p>
   *
   * @return true if the Formula does contain a cycle.
   */
  boolean hasCycle();

  /**
   * <p>Gets all the Formula's that are directly referenced or nested in the given Formula.</p>
   *
   * @return the {@link Set} of directly referenced or nested Formulas.
   */
  Set<Formula> getEdges();

  /**
   * <p>Determines if the given Formula is flat, or has nothing nested in it.</p>
   *
   * @return true if the Formula is flat.
   */
  boolean isFlat();

}
