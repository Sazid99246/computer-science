package edu.cs3500.spreadsheets.view;

import javax.swing.JTextField;

/**
 * Wrapper class representing a textfield in a grid.
 */
public class CoordTextField {

  final int row;
  final int col;
  final JTextField field;

  /**
   * Default constructor.
   *
   * @param field of text fields.
   * @param row number of rows.
   * @param col number of cols.
   */
  public CoordTextField(JTextField field, int row, int col) {
    this.row = row;
    this.col = col;
    this.field = field;
  }

}
