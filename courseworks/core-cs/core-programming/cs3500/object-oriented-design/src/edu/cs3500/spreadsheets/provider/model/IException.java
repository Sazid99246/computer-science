package edu.cs3500.spreadsheets.provider.model;

/**
 * interface for exceptions.
 */
public interface IException extends IVal {
  void raise() throws IllegalArgumentException, IllegalStateException;
}
