import tester.Tester;
import java.awt.Color;
import javalib.worldimages.*;
import javalib.funworld.*;

// To represent a 2D Cartesian point
class CartPt2 {
  int x;
  int y;

  CartPt2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Compute the distance from this point to the origin (0,0)
  double distTo0() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  // Compute the distance from this point to another Cartesian point
  double distTo(CartPt other) {
    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }
}

//To represent a rectangular bounding box
class BoundingBox {
  int minX;
  int minY;
  int maxX;
  int maxY;

  BoundingBox(int minX, int minY, int maxX, int maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
  }

  // Compute total area inside this bounding box
  double area() {
    return (this.maxX - this.minX) * (this.maxY - this.minY);
  }
}

interface IComposite {
  double area();

  double distTo0();

  boolean in(CartPt pt);

  BoundingBox bb();

  // LIFTED METHODS: Moved to interface defaults since they rely solely
  // on public geometric calculations exposed by any type of composite shape.
  default boolean same(IComposite other, double delta) {
    return Math.abs(this.area() - other.area()) <= delta;
  }

  default boolean closerTo(IComposite other) {
    return this.distTo0() < other.distTo0();
  }

  default WorldImage drawBoundary() {
    BoundingBox box = this.bb();
    return new RectangleImage(box.maxX - box.minX, box.maxY - box.minY, OutlineMode.OUTLINE,
        Color.RED);
  }
}

// LIFTED SUPERCLASS: Eliminates the field duplication between Square and Circle
abstract class AShape implements IComposite {
  CartPt2 loc;

  AShape(CartPt2 loc) {
    this.loc = loc;
  }
}

class Square2 extends AShape {
  int size;

  Square2(CartPt2 loc, int size) {
    super(loc);
    this.size = size;
  }

  public double area() {
    return this.size * this.size;
  }

  public double distTo0() {
    return this.loc.distTo0();
  }

  public boolean in(CartPt pt) {
    return pt.x >= this.loc.x && pt.x <= this.loc.x + this.size && pt.y >= this.loc.y
        && pt.y <= this.loc.y + this.size;
  }

  public BoundingBox bb() {
    return new BoundingBox(this.loc.x, this.loc.y, this.loc.x + this.size, this.loc.y + this.size);
  }
}

class Circle2 extends AShape {
  int radius;

  Circle2(CartPt2 loc, int radius) {
    super(loc);
    this.radius = radius;
  }

  public double area() {
    return Math.PI * this.radius * this.radius;
  }

  public double distTo0() {
    return Math.max(0, this.loc.distTo0() - this.radius);
  }

  public boolean in(CartPt pt) {
    return this.loc.distTo(pt) <= this.radius;
  }

  public BoundingBox bb() {
    return new BoundingBox(this.loc.x - this.radius, this.loc.y - this.radius,
        this.loc.x + this.radius, this.loc.y + this.radius);
  }
}

class SuperImp implements IComposite {
  IComposite bot;
  IComposite top;

  SuperImp(IComposite bot, IComposite top) {
    this.bot = bot;
    this.top = top;
  }

  public double area() {
    return this.bot.area() + this.top.area();
  }

  public double distTo0() {
    return Math.min(this.bot.distTo0(), this.top.distTo0());
  }

  public boolean in(CartPt pt) {
    return this.bot.in(pt) || this.top.in(pt);
  }

  public BoundingBox bb() {
    BoundingBox b1 = this.bot.bb();
    BoundingBox b2 = this.top.bb();
    return new BoundingBox(Math.min(b1.minX, b2.minX), Math.min(b1.minY, b2.minY),
        Math.max(b1.maxX, b2.maxX), Math.max(b1.maxY, b2.maxY));
  }
}

class ExamplesShape {
  CartPt2 p0 = new CartPt2(0, 0);
  CartPt2 p34 = new CartPt2(3, 4); // distTo0 = 5
  CartPt2 p10 = new CartPt2(10, 10);

  IComposite s1 = new Square2(p34, 10); // Area = 100, distTo0 = 5, BB: (3,4) to (13,14)
  IComposite c1 = new Circle2(p10, 5); // Area ~ 78.54, distTo0 = 14.14 - 5 = 9.14
  IComposite super1 = new SuperImp(s1, c1);

  // Test Area logic
  boolean testArea(Tester t) {
    return t.checkInexact(this.s1.area(), 100.0, 0.001)
        && t.checkInexact(this.c1.area(), 78.5398, 0.001)
        && t.checkInexact(this.super1.area(), 178.5398, 0.001);
  }

  // Test Distance calculations
  boolean testDistTo0(Tester t) {
    return t.checkInexact(this.s1.distTo0(), 5.0, 0.001)
        && t.checkInexact(this.c1.distTo0(), 9.1421, 0.001)
        && t.checkInexact(this.super1.distTo0(), 5.0, 0.001);
  }

  // Test Coordinate Point Containment
  boolean testInMethod(Tester t) {
    return t.checkExpect(this.s1.in(new CartPt(5, 5)), true)
        && t.checkExpect(this.s1.in(new CartPt(2, 2)), false)
        && t.checkExpect(this.super1.in(new CartPt(10, 11)), true);
  }

  // Test Bounding Box Dimensions
  boolean testBoundingBox(Tester t) {
    BoundingBox b = this.super1.bb();
    return t.checkExpect(b.minX, 3) && t.checkExpect(b.minY, 4) && t.checkExpect(b.maxX, 15)
        && t.checkExpect(b.maxY, 15);
  }

  // Test Lifted Comparison Methods
  boolean testLiftedMethods(Tester t) {
    IComposite s2 = new Square2(p0, 10); // Area = 100
    return t.checkExpect(this.s1.same(s2, 0.001), true)
        && t.checkExpect(this.s1.same(this.c1, 0.001), false)
        && t.checkExpect(this.s1.closerTo(this.c1), true)
        && t.checkExpect(this.c1.closerTo(this.super1), false);
  }

  // Test Bounding Box Image Rendering Engine Configurations
  boolean testDrawBoundary(Tester t) {
    WorldImage expectedSquareImg = new RectangleImage(10, 10, OutlineMode.OUTLINE, Color.RED);
    return t.checkExpect(this.s1.drawBoundary(), expectedSquareImg);
  }
}