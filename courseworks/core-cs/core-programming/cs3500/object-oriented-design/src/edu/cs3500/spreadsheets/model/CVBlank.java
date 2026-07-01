package edu.cs3500.spreadsheets.model;


/**
 * <p>Class representing a blank CellValue. A cell with this value is empty.</p>
 */
public class CVBlank extends CellValueImp {


  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    return other instanceof CVBlank;
  }

  @Override
  public int hashCode() {
    return 6;
  }


  @Override
  public <R> R accept(CellValueVisitor<R> visitor) {
    return visitor.visitBlank("");
  }
}
