/**
 * This interface declares all methods that should be supported by the controller
 */

package cs3500.Scrollbars.src.blobs.controller;

import blobs.model.Blob;
import blobs.model.Point2D;
import blobs.view.IBlobView;

import java.util.List;

public interface IBlobController {
  /**
   * Return the maximum extent of the smallest rectangle that contains all blobs
   */
  Point2D getCanvasMax();

  /**
   * Returns the blobs maintained by the model
   */
  List<Blob> getBlobs();

  /**
   * Store a reference to the view
   */
  void setView(IBlobView view);

  /**
   * Add a blob to the model
   */
  void addBlob(int x, int y, int radius);
}
