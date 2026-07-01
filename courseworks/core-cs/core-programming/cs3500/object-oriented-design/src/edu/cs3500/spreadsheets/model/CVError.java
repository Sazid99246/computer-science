package edu.cs3500.spreadsheets.model;


/**
 * <p>Class representing an error cell value.</p>
 */
public class CVError extends CellValueImp {


  @Override
  public String toString() {
    return "#ERROR";
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    return other instanceof CVError;
  }

  @Override
  public int hashCode() {
    return 9;
  }

  @Override
  public <R> R accept(CellValueVisitor<R> visitor) {
    return visitor.visitError(this.toString());
  }
}
