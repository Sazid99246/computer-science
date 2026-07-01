package edu.cs3500.spreadsheets.model;


/**
 * <p>Class representing a double cell value.</p>
 */
public class CVDouble extends CellValueImp {

  private final double contents;

  /**
   * Default constructor.
   *
   * @param d the double contents.
   */
  public CVDouble(double d) {
    this.contents = d;
  }

  @Override
  public String toString() {
    return String.format("%f", this.contents);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof CVDouble)) {
      return false;
    }

    CVDouble o = (CVDouble) other;

    return (Math.abs(this.contents - o.contents) < 0.0001);
  }

  @Override
  public int hashCode() {
    Double d = this.contents;
    return d.hashCode();
  }

  @Override
  public <R> R accept(CellValueVisitor<R> visitor) {
    return visitor.visitDouble(this.contents);
  }
}
