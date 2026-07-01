package cs3500.Scrollbars.src.blobs.model;

/**
 * A blob is a circle with a center and radius.
 */
public class Blob {
  private Point2D center;
  private int radius;

  public Blob(Point2D center, int radius) {
    this.center = new Point2D(center.x, center.y);
    this.radius = radius;
  }

  public Point2D getCenter() {
    return new Point2D(center);
  }

  public int getRadius() {
    return radius;
  }
}