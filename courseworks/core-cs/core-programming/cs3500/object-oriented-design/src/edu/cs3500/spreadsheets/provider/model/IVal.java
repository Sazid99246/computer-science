package edu.cs3500.spreadsheets.provider.model;


/**
 * A value is one of a Boolean, Double, String, or blank value.
 */
public interface IVal {
  <R> R accept(ValueVisitor<R> evalInstructions) throws IllegalStateException,
          IllegalArgumentException;
}
