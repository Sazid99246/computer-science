package edu.cs3500.spreadsheets.provider.view;

//import java.awt.event.ActionListener;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.provider.controller.FeaturesMain;
import edu.cs3500.spreadsheets.model.Coord;

import edu.cs3500.spreadsheets.provider.model.ViewWorksheet;

/**
 * For java style.
 */
public interface WorksheetPane {

  Coord getTopLeft();

  void moveX(int x);

  void moveY(int y);

  void highlightCell(Coord c);

  void addFeatures(FeaturesMain f);

  Coord selectCell(int x, int y);


  void setModel(ViewWorksheet model);

}
