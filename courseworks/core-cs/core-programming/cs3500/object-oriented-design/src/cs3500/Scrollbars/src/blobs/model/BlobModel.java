package cs3500.Scrollbars.src.blobs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This model stores all the circular blobs drawn by the user by clicking on the screen
 */
public class BlobModel implements IBlobModel {

  private List<Blob> blobs;
  //the lower left corner of the smallest rectangle that contains all blobs
  private Point2D min;
  //the upper right corner of the smallest rectangle that contains all blobs
  private Point2D max;

  public BlobModel(int dims) {
    blobs = new ArrayList<Blob>();
    //initialization invariant on what min and max are
    max = new Point2D(0, 0);
    min = new Point2D(0, 0);
  }

  public void add(int x, int y, int radius) {
    Point2D center = new Point2D(x, y);
    blobs.add(new Blob(center, radius));

    //update the area covered by all the blobs: update invariant
    updateBoundingBox(center, radius);
  }

  public Point2D getMax() {
    return new Point2D(max);
  }

  public Point2D getMin() {
    return new Point2D(min);
  }

  public List<Blob> getBlobs() {
    return new ArrayList<Blob>(blobs);
  }

  /**
   * This method updates the extent of the rectangular region that contains all the blobs. Before
   * this method is called, this extent is maintained in rectangle with lower left corner at "min"
   * and upper right corner at "max".
   *
   * Adding a new blob creates a rectangle with lower left corner at (center-radius) and upper right
   * corner at (center+radius). We must now update "min" and "max" so that it contains this
   * rectangle. This is a standard union operation of two rectangles.
   *
   * Given two rectangles A(axmin,aymin,axmax,aymax) and B(bxmin,bymin,bxmax,bymax), their union is
   * given by C(min(axmin,bxmin),min(aymin,bymin), max(axmax,bxmax), max(aymax,bymax).
   *
   * This method implements this logic.
   */
  private void updateBoundingBox(Point2D center, int radius) {
    min = new Point2D(Math.min(min.x, center.x - radius), Math.min(min.y, center.y - radius));
    max = new Point2D(Math.max(max.x, center.x + radius), Math.max(max.y, center.y + radius));
  }
}
