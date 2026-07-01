package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Textual View for a {@link SpreadsheetModel}. Render appends to the readable, can be
 * used for saving to a file with a FileWriter.</p>
 */
public class SpreadsheetTextualView implements SpreadsheetView {

  private Appendable ap;
  private SpreadsheetModel model;

  /**
   * <p>Constructor for the TextualView.</p>
   *
   * @param ap    the {@link Appendable} where the view is written.
   * @param model the {@link SpreadsheetModel}.
   */
  public SpreadsheetTextualView(Appendable ap, SpreadsheetModel model) {
    this.ap = ap;
    this.model = model;
  }


  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();

    Map<Coord, ICell> cells = model.getAllCells();

    for (Map.Entry<Coord, ICell> entry : cells.entrySet()) {
      s.append(entry.getKey().toString()).append(" ").append(entry.getValue().getRawValue())
          .append("\n");

    }

    return s.toString();
  }

  @Override
  public void render() throws IOException {

    ap.append(this.toString());

  }

  @Override
  public void refresh() throws IOException {
    ap.append(this.toString());
  }

  /**
   * For loading a file. Updates this view to contain the cells from the loaded model.
   *
   * @param model the model to use.
   */
  @Override
  public void updateModel(SpreadsheetModel model) {
    this.model = model;
  }
}
