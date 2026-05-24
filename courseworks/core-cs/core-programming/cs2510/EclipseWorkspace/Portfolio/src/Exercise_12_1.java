import tester.*;

// ============================================================
// Represents all geometric shapes
// A shape is one of:
// - Dot
// - Square
// - Circle
// ============================================================

interface IShape {

  // Computes the area of this shape
  double area();

  // Computes the distance of this shape to the origin (0,0)
  double distTo0();

  // Determines whether the given point is inside this shape
  boolean in(CartPt2 p);

  // Computes the bounding box of this shape
  // The bounding box is represented as a Square
  Square bb();
}

// ============================================================
// Represents a Cartesian point
// ============================================================

class CartPt {
  int x;
  int y;

  // Constructor for CartPt
  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Computes the distance from this point to the origin
  double distTo0() {
    return Math.sqrt((this.x * this.x) + (this.y * this.y));
  }

  // Determines whether this point and the given point are the same
  boolean same(CartPt2 p) {
    return (this.x == p.x) && (this.y == p.y);
  }

  // Computes the distance between this point and the given point
  double distanceTo(CartPt2 p) {
    return Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
  }

  // Creates a new point that is delta pixels up and left
  // from this point
  CartPt2 translate(int delta) {
    return new CartPt2(this.x - delta, this.y - delta);
  }
}

// ============================================================
// Represents a square
// The location represents the northwest (top-left) corner
// ============================================================

class Square implements IShape {
  int size;
  CartPt2 loc;

  // Constructor for Square
  Square(CartPt2 loc, int size) {
    this.loc = loc;
    this.size = size;
  }

  // Computes the area of this square
  public double area() {
    return this.size * this.size;
  }

  // Computes the distance from this square's location
  // to the origin
  public double distTo0() {
    return this.loc.distTo0();
  }

  // Determines whether the given point is inside this square
  public boolean in(CartPt2 p) {
    return this.between(this.loc.x, p.x, this.size) && this.between(this.loc.y, p.y, this.size);
  }

  // Computes the bounding box of this square
  // A square's bounding box is itself
  public Square bb() {
    return this;
  }

  // Determines whether x is inside the interval
  // [lft, lft + wdth]
  boolean between(int lft, int x, int wdth) {
    return lft <= x && x <= lft + wdth;
  }
}

// ============================================================
// Represents a circle
// The location represents the center of the circle
// ============================================================

class Circle implements IShape {
  CartPt2 loc;
  int radius;

  // Constructor for Circle
  Circle(CartPt2 loc, int radius) {
    this.loc = loc;
    this.radius = radius;
  }

  // Computes the area of this circle
  public double area() {
    return Math.PI * this.radius * this.radius;
  }

  // Computes the distance from this circle's center
  // to the origin
  public double distTo0() {
    return this.loc.distTo0();
  }

  // Determines whether the given point is inside this circle
  public boolean in(CartPt2 p) {
    return this.loc.distanceTo(p) <= this.radius;
  }

  // Computes the bounding box of this circle
  public Square bb() {
    return new Square(this.loc.translate(this.radius), this.radius * 2);
  }
}

// ============================================================
// Represents a dot
// A dot has area 0
// ============================================================

class Dot implements IShape {
  CartPt2 loc;

  // Constructor for Dot
  Dot(CartPt2 loc) {
    this.loc = loc;
  }

  // Computes the area of this dot
  // A dot has area 0
  public double area() {
    return 0;
  }

  // Computes the distance from this dot to the origin
  public double distTo0() {
    return this.loc.distTo0();
  }

  // Determines whether the given point is the same
  // as this dot's location
  public boolean in(CartPt2 p) {
    return this.loc.same(p);
  }

  // Computes the bounding box of this dot
  // The bounding box is a square of size 0
  public Square bb() {
    return new Square(this.loc, 0);
  }
}

// ============================================================
// Examples and tests for shapes
// ============================================================

class ExamplesShapes {

  // ==========================================================
  // Examples of points
  // ==========================================================

  CartPt2 origin = new CartPt2(0, 0);

  CartPt2 p1 = new CartPt2(3, 4);

  CartPt2 p2 = new CartPt2(10, 10);

  CartPt2 p3 = new CartPt2(5, 5);

  CartPt2 p4 = new CartPt2(20, 20);

  // ==========================================================
  // Examples of shapes
  // ==========================================================

  IShape d1 = new Dot(origin);

  IShape d2 = new Dot(p1);

  IShape s1 = new Square(origin, 10);

  IShape s2 = new Square(p3, 5);

  IShape c1 = new Circle(origin, 5);

  IShape c2 = new Circle(p2, 10);

  // ==========================================================
  // Tests for CartPt methods
  // ==========================================================

  // Tests distTo0 method for CartPt
  boolean testPointDistTo0(Tester t) {
    return t.checkInexact(origin.distTo0(), 0.0, 0.001) && t.checkInexact(p1.distTo0(), 5.0, 0.001);
  }

  // Tests same method for CartPt
  boolean testSame(Tester t) {
    return t.checkExpect(origin.same(new CartPt2(0, 0)), true)
        && t.checkExpect(origin.same(p1), false);
  }

  // Tests distanceTo method for CartPt
  boolean testDistanceTo(Tester t) {
    return t.checkInexact(origin.distanceTo(p1), 5.0, 0.001)
        && t.checkInexact(p1.distanceTo(p3), Math.sqrt(5), 0.001);
  }

  // Tests translate method for CartPt
  boolean testTranslate(Tester t) {
    return t.checkExpect(p3.translate(2), new CartPt2(3, 3));
  }

  // ==========================================================
  // Tests for area method
  // ==========================================================

  // Tests area for all kinds of shapes
  boolean testArea(Tester t) {
    return t.checkInexact(d1.area(), 0.0, 0.001) && t.checkInexact(s1.area(), 100.0, 0.001)
        && t.checkInexact(c1.area(), Math.PI * 25, 0.001);
  }

  // ==========================================================
  // Tests for distTo0 method
  // ==========================================================

  // Tests distTo0 for all kinds of shapes
  boolean testShapeDistTo0(Tester t) {
    return t.checkInexact(d2.distTo0(), 5.0, 0.001)
        && t.checkInexact(s2.distTo0(), Math.sqrt(50), 0.001)
        && t.checkInexact(c2.distTo0(), Math.sqrt(200), 0.001);
  }

  // ==========================================================
  // Tests for in method
  // ==========================================================

  // Tests whether points are inside shapes
  boolean testIn(Tester t) {
    return

    // Tests for dots
    t.checkExpect(d1.in(new CartPt2(0, 0)), true) && t.checkExpect(d1.in(p1), false)

    // Tests for squares
        && t.checkExpect(s1.in(new CartPt2(5, 5)), true)
        && t.checkExpect(s1.in(new CartPt2(11, 11)), false)

        // Tests for circles
        && t.checkExpect(c1.in(new CartPt2(3, 4)), true)
        && t.checkExpect(c1.in(new CartPt2(6, 0)), false);
  }

  // ==========================================================
  // Tests for bb method
  // ==========================================================

  // Tests bounding box computations
  boolean testBB(Tester t) {
    return

    // Bounding box of a dot
    t.checkExpect(d1.bb(), new Square(new CartPt2(0, 0), 0))

        // Bounding box of a square
        && t.checkExpect(s1.bb(), s1)

        // Bounding box of a circle
        && t.checkExpect(c1.bb(), new Square(new CartPt2(-5, -5), 10));
  }

  // ==========================================================
  // Tests for between helper method
  // ==========================================================

  // Tests the between helper method in Square
  boolean testBetween(Tester t) {
    return t.checkExpect(((Square) s1).between(0, 5, 10), true)
        && t.checkExpect(((Square) s1).between(0, 11, 10), false);
  }
}