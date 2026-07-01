package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * For java style.
 */
public interface FeaturesMain {
  void selectCell(Coord c);

  /**Removes the selected cell - a features should have this in scope.
   *
   */
  void removeCell();

  void moveSelectedUp();

  void moveSelectedLeft();

  void moveSelectedRight();

  void moveSelectedDown();

  void save();

  void load();

  void putCell(String s);

  void discard();
}
