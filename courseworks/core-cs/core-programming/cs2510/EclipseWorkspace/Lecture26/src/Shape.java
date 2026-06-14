// Structural layout for shapes
interface IShape {
  boolean sameShape(IShape that);

  boolean sameCircle(Circle that);

  boolean sameSquare(Square that);

  boolean sameRect(Rect that);
}

// 26.5.3 Hybrid top-level base class handling type dispatching redirects
abstract class AShape implements IShape {

  public boolean sameCircle(Circle that) {
    return false;
  }

  public boolean sameSquare(Square that) {
    return false;
  }

  public boolean sameRect(Rect that) {
    return false;
  }

  // Overriding standard Object equals down into our double-dispatch engine
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IShape)) {
      return false;
    }
    IShape that = (IShape) other;
    return this.sameShape(that);
  }

  // FIXING THE "DO NOW!": Overriding equals requires a matching override of
  // hashCode
  @Override
  public abstract int hashCode();
}

// Concrete class: Circle
class Circle extends AShape {
  int radius;

  Circle(int radius) {
    this.radius = radius;
  }

  public boolean sameShape(IShape that) {
    return that.sameCircle(this);
  }

  public boolean sameCircle(Circle that) {
    return this.radius == that.radius;
  }

  @Override
  public int hashCode() {
    return this.radius * 31;
  }
}

// Concrete class: Square
class Square extends AShape {
  int side;

  Square(int side) {
    this.side = side;
  }

  public boolean sameShape(IShape that) {
    return that.sameSquare(this);
  }

  public boolean sameSquare(Square that) {
    return this.side == that.side;
  }

  @Override
  public int hashCode() {
    return this.side * 37;
  }
}

// Concrete class: Rect (Rectangle)
class Rect extends AShape {
  int width;
  int height;

  Rect(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public boolean sameShape(IShape that) {
    return that.sameRect(this);
  }

  public boolean sameRect(Rect that) {
    return this.width == that.width && this.height == that.height;
  }

  @Override
  public int hashCode() {
    return this.width * 41 + this.height * 43;
  }
}