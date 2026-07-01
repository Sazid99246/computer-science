package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * <p>Class representing the Add function, adds any CVDouble together. If
 * cell val is not a CVDouble then treats its value as 0. </p>
 */
public class AddObject implements Operation, CellValueVisitor<Double> {


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
    return 0.0;
  }

  @Override
  public Double visitBool(Boolean cv) {
    return 0.0;
  }

  @Override
  public Double visitString(String cv) {
    return 0.0;
  }

  @Override
  public CellValue apply(List<CellValue> vals) {
    Double outpt = 0.0;
    for (CellValue v : vals) {
      try {
        outpt += v.accept(this);
      } catch (IllegalStateException e) {
        return new CVError();
      }
    }
    return new CVDouble(outpt);
  }
}
