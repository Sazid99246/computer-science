package edu.cs3500.spreadsheets.controller;

import java.io.IOException;

/**
 * Basic interface for controllers. Includes only a method to start the program.
 */
public interface IController {


  /**
   * Starts the program.
   *
   * @throws IOException if rendering fails.
   */
  void start() throws IOException;

}
