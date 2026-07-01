package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.controller.SpreadSheetController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ModelAdapter;
import edu.cs3500.spreadsheets.provider.view.VisualWorksheetView;
import edu.cs3500.spreadsheets.provider.view.WorksheetView;
import edu.cs3500.spreadsheets.view.ViewEventListener;
import java.io.IOException;

/**
 * Adapts our controller to theirs. Also implements features main so that the controller can handle
 * when the view calls for events to occur.
 */
public class ControllerAdapter implements WorksheetController, FeaturesMain {

  ModelAdapter model;
  WorksheetView view;
  ViewEventListener vel;

  /**
   * Default constructor that takes our implementations of the model and controller.
   *
   * @param model      to be adapted
   * @param controller to be adapted
   */
  public ControllerAdapter(ModelAdapter model, SpreadSheetController controller) {
    this.model = model;
    this.vel = controller;
    this.view = new VisualWorksheetView(model);
    view.addFeatures(this);
  }

  @Override
  public void goRender() throws IOException {
    view.render();
  }

  @Override
  public void selectCell(Coord c) {
    this.vel.setEditableCoord(c);
    this.view.highlightCell(c);
  }

  /**
   * Removes the selected cell - a features should have this in scope.
   */
  @Override
  public void removeCell() {
    this.vel.confirmEdits("");
    this.view.refresh();
  }


  @Override
  public void moveSelectedUp() {
    // not handled
  }

  @Override
  public void moveSelectedLeft() {
    // not handled
  }

  @Override
  public void moveSelectedRight() {
    // not handled
  }

  @Override
  public void moveSelectedDown() {
    // not handled
  }

  @Override
  public void save() {
    // not handled
  }

  @Override
  public void load() {
    // not handled
  }

  @Override
  public void putCell(String s) {
    this.vel.confirmEdits(s);
    this.view.refresh();
  }

  @Override
  public void discard() {
    this.vel.confirmEdits("");
    this.view.refresh();
  }
}
