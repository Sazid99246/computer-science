package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * <p>Class representing the Compare function. Throws errors if not a CVDouble.</p>
 */
public class CompareObject implements Operation, CellValueVisitor<Double> {

  @Override
  public Double visitDouble(Double cv) {
    return Double.valueOf(cv.toString());
  }

  @Override
  public Double visitError(String cv) {
    throw new IllegalStateException();
  }

  @Override
  public Double visitBlank(String cv) {
    throw new IllegalStateException();
  }

  @Override
  public Double visitBool(Boolean cv) {
    throw new IllegalStateException();
  }

  @Override
  public Double visitString(String cv) {
    throw new IllegalStateException();
  }

  @Override
  public CellValue apply(List<CellValue> vals) {
    if (vals.size() != 2) {
      return new CVError();
    }
    try {
      boolean outpt = vals.get(0).accept(this) < vals.get(1).accept(this);
      return new CVBool(outpt);
    } catch (IllegalStateException e) {
      return new CVError();
    }
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    return other instanceof CompareObject;
  }

  @Override
  public int hashCode() {
    return 2;
  }
}
