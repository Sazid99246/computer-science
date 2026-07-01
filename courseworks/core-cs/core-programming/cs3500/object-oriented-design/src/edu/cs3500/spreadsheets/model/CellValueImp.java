package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Interface for CellContents that are solely values.
 */
public abstract class CellValueImp implements Formula, CellValue {

  /**
   * <p>Visitor pattern method.</p>
   *
   * @param visitor the given {@link CellValueVisitor}.
   * @param <R>     the return type of the {@link CellValueVisitor}.
   * @return the output of the visitor.
   */
  public abstract <R> R accept(CellValueVisitor<R> visitor);

  @Override
  public CellValue getValue() {
    return this;
  }

  @Override
  public boolean isFlat() {
    return true;
  }

  @Override
  public boolean hasCycle() {
    return false;
  }

  @Override
  public Set<Formula> getEdges() {
    return new HashSet<Formula>();
  }

  @Override
  public ArrayList<CellValue> flattenHelp() {
    return new ArrayList<CellValue>(Arrays.asList(getValue()));
  }

}
