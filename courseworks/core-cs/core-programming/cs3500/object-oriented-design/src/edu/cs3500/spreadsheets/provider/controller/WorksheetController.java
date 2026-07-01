package edu.cs3500.spreadsheets.provider.controller;

import java.io.IOException;

/**
 * Controller interaface with methods needed in all controllers.
 */
public interface WorksheetController {
  /**
   * Calles the render method in the view.
   */
  void goRender() throws IOException;
}
