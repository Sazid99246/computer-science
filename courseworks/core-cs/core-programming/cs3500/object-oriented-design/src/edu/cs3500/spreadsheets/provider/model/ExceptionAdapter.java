package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.CVError;

/**
 * Adapts our cell value error to their IException in the event that a visitor needs
 * an adapted version or methods from their interface are called in their view.
 */
public class ExceptionAdapter implements IException {

  private CVError err;


  public ExceptionAdapter(CVError err) {
    this.err = err;
  }

  @Override
  public void raise() throws IllegalArgumentException, IllegalStateException {
    throw new IllegalArgumentException("this is an exception adapter");
  }

  @Override
  public <R> R accept(ValueVisitor<R> evalInstructions)
      throws IllegalStateException, IllegalArgumentException {
    return new VisitorAdapter<>(evalInstructions).visitError("error");
  }
}
