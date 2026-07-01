package edu.cs3500.spreadsheets.model;


/**
 * <p>Class representing String Cell Value.</p>
 */
public class CVString extends CellValueImp {

  private final String contents;

  /**
   * Default constructor.
   *
   * @param s the string contents.
   */
  public CVString(String s) {
    this.contents = s;
  }

  @Override
  public String toString() {
    return this.contents;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof CVString)) {
      return false;
    }

    CVString o = (CVString) other;

    return this.contents.equals(o.contents);
  }

  @Override
  public int hashCode() {
    return this.contents.hashCode();
  }

  @Override
  public <R> R accept(CellValueVisitor<R> visitor) {
    return visitor.visitString(this.contents);
  }
}
