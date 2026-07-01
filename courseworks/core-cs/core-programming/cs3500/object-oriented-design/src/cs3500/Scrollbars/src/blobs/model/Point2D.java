package cs3500.Scrollbars.src.blobs.model;

/**
 * A class representing a simple 2D point
 */

public class Point2D {
  public final int x, y;

  public Point2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point2D(Point2D p) {
    this.x = p.x;
    this.y = p.y;
  }
}
