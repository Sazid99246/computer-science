package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.ContentsBuilder;
import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * <p>Wrapper class for a cell that implements ICell. Contains the raw and calculated contents
 * as well as the coord. </p>
 */
public class Cell implements ICell {

  private String rawContents;
  private Coord coord;
  private Formula contents;

  /**
   * Row and col constructor.
   *
   * @param row      The desired row.
   * @param col      The desired col.
   * @param contents The desired raw contents.
   */
  public Cell(int row, int col, String contents, BasicSpreadsheetModel model) {
    this.rawContents = contents;
    this.coord = new Coord(col, row);
    this.contents = createContents(model);
  }

  /**
   * Coord constructor.
   *
   * @param coord    The desired coord.
   * @param contents The desired raw contents.
   */
  public Cell(Coord coord, String contents, BasicSpreadsheetModel model) {
    this.rawContents = contents;
    this.coord = coord;
    this.contents = createContents(model);
  }

  /**
   * Default only coord constructor, value is automatically blank.
   *
   * @param coord The desired coord.
   */
  public Cell(Coord coord) {
    this.contents = new CVBlank();
    this.rawContents = "";
    this.coord = coord;
  }

  /**
   * Gets the contents of this cell.
   *
   * @return the evaluated contents.
   */
  public Formula getContents() {
    return contents;
  }

  @Override
  public CellValue getValue() {
    if (this.contents == null) {
      return new CVString("");
    }
    return contents.getValue();
  }

  @Override
  public String getRawValue() {
    return rawContents;
  }

  @Override
  public Coord getCoord() {
    return new Coord(this.coord.col, this.coord.row);
  }

  // for debugging
  @Override
  public String toString() {
    return this.getValue().toString();
  }


  @Override
  public void update(String contents, SpreadsheetModel model) {
    this.rawContents = contents;
    this.contents = createContents(model);
  }

  /**
   * Method used to parse the cell raw contents.
   *
   * @param model the model being used.
   * @return The correct cell value.
   */
  private Formula createContents(SpreadsheetModel model) {
    Parser p = new Parser();
    if (rawContents.length() == 0) {
      return new CVBlank();
    }

    if (rawContents.charAt(0) == '=') {
      Formula f = p.parse(this.rawContents.substring(1)).accept(new ContentsBuilder(coord, model));
      if (this.rawContents.contains(this.coord.toString())) {
        return new CVError();
      }
      return f;
    } else {
      Formula f = p.parse(this.rawContents).accept(new ContentsBuilder(coord, model));
      return f;
    }
  }


  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Cell)) {
      return false;
    }

    Cell o = (Cell) other;

    return (this.rawContents.equals(o.rawContents) && this.coord.equals(o.coord));
  }

  @Override
  public int hashCode() {
    return this.rawContents.hashCode() * this.coord.hashCode() * 31;
  }

}
