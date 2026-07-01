package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * <p>Class representing Concat function. Appends items as strings. </p>
 */
public class ConcatObject implements Operation, CellValueVisitor<String> {


  @Override
  public String visitDouble(Double cv) {
    return cv.toString();
  }

  @Override
  public String visitError(String cv) {
    throw new IllegalStateException();
  }

  @Override
  public String visitBlank(String cv) {
    return "";
  }

  @Override
  public String visitBool(Boolean cv) {
    return cv.toString();
  }

  @Override
  public String visitString(String cv) {
    return cv.toString();
  }

  @Override
  public CellValue apply(List<CellValue> vals) {
    StringBuilder outpt = new StringBuilder();
    for (CellValue v : vals) {
      try {
        outpt.append(v.accept(this));
      } catch (IllegalStateException e) {
        return new CVError();
      }
    }
    return new CVString(outpt.toString());
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    return other instanceof ConcatObject;
  }

  @Override
  public int hashCode() {
    return 3;
  }
}
