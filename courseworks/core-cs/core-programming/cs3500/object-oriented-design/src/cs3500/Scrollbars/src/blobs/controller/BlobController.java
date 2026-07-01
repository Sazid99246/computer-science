package cs3500.Scrollbars.src.blobs.controller;

import blobs.model.*;
import blobs.view.IBlobView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

/**
 * This controller updates the model and view according to user input.
 *
 * In order to ease drawing of the blobs in the view, this controller enforces the following
 * constraint: no part of any blob will be in the -ve X and -ve Y space. That is, the smallest
 * region that contains all of the blobs has a lower left corner of (0,0) at the least.
 */
public class BlobController implements IBlobController {
  private IBlobModel model;
  private IBlobView view;

  public BlobController(IBlobModel model) {
    this.model = model;
  }

  /**
   * This resets the size of the canvas, or the "thing" on which the view draws the blobs Resetting
   * the size is important so that the view can adjust, for example, realign the scrollbars around
   * the "thing"
   */
  private void setCanvasSize() {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    Point2D max = model.getMax();
    view.setCanvasSize(max.x, max.y);

  }

  @Override
  public void setView(IBlobView view) {
    this.view = view;
    //set the mouse listener
    setMouseListener();
    setCanvasSize();
  }


  @Override
  public List<Blob> getBlobs() {
    return model.getBlobs();
  }


  @Override
  public Point2D getCanvasMax() {
    return model.getMax();
  }

  @Override
  public void addBlob(int x, int y, int radius) {
    //ensure that no part of the blob is in -ve x and -ve y space
    radius = Math.min(Math.min(radius, x), y);
    model.add(x, y, radius);
  }


  private void setMouseListener() {
    view.setMouseListener(new BlobAdder());
  }

  /**
   * When the user left-clicks at a point on the screen, a new blob is generated at random. In order
   * to enforce the above constraint, the blob must be restricted to the +x and +y space.
   *
   * For a mouse listener, we have two choice:
   *
   * 1. Write a class that implements the MouseListener interface. This mandates that this class
   * implements all the methods specified in the interface. This approach is good if most of these
   * methods are useful (i.e. mouse press, mouse release, etc.)
   *
   * 2. Write a class that extends MouseAdapter. MouseAdapter is an existing class that implements
   * MouseListener. Thus all the methods are implemented already, and we can only override the ones
   * we choose. This approach is good if our program is using only 1-2 methods of the MouseListener
   * interface, and if this class is not extending anything else.
   */

  class BlobAdder extends MouseAdapter {

    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) { //for right-handed people, if the left button was clicked
        int x = e.getX();
        int y = e.getY();

        int radius = 10 + (int) (100 * Math.random());
        BlobController.this.addBlob(x, y, radius);
        BlobController.this.setCanvasSize();
      }
    }
  }

}
