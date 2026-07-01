package edu.cs3500.spreadsheets.model;


/**
 * <p>Class representing a boolean Cell Value.</p>
 */
public class CVBool extends CellValueImp {

  private final boolean content;

  /**
   * Default constructor.
   *
   * @param b the boolean content.
   */
  public CVBool(boolean b) {
    this.content = b;
  }


  @Override
  public String toString() {
    return String.valueOf(this.content);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof CVBool)) {
      return false;
    }

    CVBool o = (CVBool) other;

    return (this.content == o.content);
  }

  @Override
  public int hashCode() {
    if (content) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public <R> R accept(CellValueVisitor<R> visitor) {
    return visitor.visitBool(this.content);
  }
}
