package cs3500.Scrollbars.src.blobs.view;

import java.awt.event.MouseListener;

/**
 * Interface for the view.
 */
public interface IBlobView {
  /**
   * This method will be called when the drawing area has changed
   */
  void setCanvasSize(int width, int height);

  /**
   * This method will be called to set a mouse listener
   */
  void setMouseListener(MouseListener listener);

}
