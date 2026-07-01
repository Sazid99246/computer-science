package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.io.IOException;

/**
 * Interface for all spreadsheets views.
 */
public interface SpreadsheetView {

  /**
   * <p>Renders the spreadsheets view.</p>
   */
  void render() throws IOException;

  /**
   * <p>Refreshes the view.</p>
   *
   * @throws IOException if fails to refresh.
   */
  void refresh() throws IOException;

  /**
   * For loading a file. Updates this view to contain the cells from the loaded model.
   *
   * @param model the model to use.
   */
  void updateModel(SpreadsheetModel model);

}
