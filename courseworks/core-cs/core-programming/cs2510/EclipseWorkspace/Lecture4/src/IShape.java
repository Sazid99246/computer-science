import tester.*;
/**
 * HtDC Lectures
 * Lecture 5: Methods for unions of classes
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */


/*
 +----------------------------+                    
 | IShape                     |                    
 +----------------------------+                    
 | double area()              |                    
 | double distToOrigin()      |                    
 | IShape grow(int)           |                    
 | boolean biggerThan(IShape) |                    
 | boolean contains(CartPt)   |                    
 +----------------------------+                    
                    |                                    
                   / \                                   
                   ---                                   
                    |                                    
             ----------------------------------------------------------------------
             |                                   |                                |
   +----------------------------+   +----------------------------+     +-----------------------------+
   | Circle                     |   | Square                     |     | Combo                       |
   +----------------------------+   +----------------------------+     +-----------------------------+
 +-| IPoint center              | +-| IPoint nw                  |     | IShape i1                   |
 | | int radius                 | | | int size                   |     | IShape i2                   |
 | | String color               | | | String color               |     +-----------------------------+
 | +----------------------------+ | +----------------------------+     | double area()               |
 | | double area()              | | | double area()              |     | double distToOrigin()       |
 | | double distToOrigin()      | | | double distToOrigin()      |     | IShape grow(int)            |
 | | IShape grow(int)           | | | IShape grow(int)           |     | boolean biggerThan(IShape)  |
 | | boolean biggerThan(IShape) | | | boolean biggerThan(IShape) |     | boolean contains(IPoint)    |
 | | boolean contains(CartPt)   | | | boolean contains(CartPt)   |     +-----------------------------+
 | +----------------------------+ | +----------------------------+  
 +----+ +-------------------------+
      | |
      v v                                                                     
 +-----------------------+
 | IPoint                |
 +-----------------------+
 +-----------------------+
 | double distToOrigin() |
 | double distTo(IPoint) |
 | double xValue()       |
 | double yValue()       |
 +-----------------------+ 
           / \
           ---
            |
   -----------------------------------
   |                                 |
+-----------------------+    +-----------------------+
| CartPt                |    | PolarPt               |
+-----------------------+    +-----------------------+
| int x                 |    | double r              |
| int y                 |    | double theta          |
+-----------------------+    +-----------------------+
| double distToOrigin() |    | double distToOrigin() |
| double distTo(IPoint) |    | double distTo(IPoint) |
| double xValue()       |    | double xValue()       |
| double yValue()       |    | double yValue()       |
+-----------------------+    +-----------------------+
 */

//Represents a 2D point in the plane.
//A point can be represented in multiple ways (Cartesian or Polar),
//but must support conversion to Cartesian coordinates and distance operations.
interface IPoint {
 // distance from this point to the origin (0,0)
 double distToOrigin();

 // distance between this point and another point (Euclidean distance)
 double distTo(IPoint pt);

 // x-coordinate of this point in Cartesian form
 double xValue();

 // y-coordinate of this point in Cartesian form
 double yValue();
}

//Represents a geometric shape in 2D space.
//Shapes support area computation, scaling, comparison, and point containment.
interface IShape {

 // compute area of this shape
 double area();

 // distance from this shape to origin (0,0)
 double distToOrigin();

 // increase size of this shape by inc (returns new shape)
 IShape grow(int inc);

 // is this shape larger than the given shape (by area)?
 boolean biggerThan(IShape that);

 // does this shape contain the given point (including boundary)?
 boolean contains(IPoint pt);
}

//Represents a circle defined by a center point and a radius.
class Circle implements IShape {
  IPoint center;
  int radius;
  String color;

  Circle(IPoint center, int radius, String color) {
   this.center = center;
   this.radius = radius;
   this.color = color;
 }
  
  /* TEMPLATE
     FIELDS:
      ... this.center ...              -- IPoint
      ... this.radius ...              -- int
      ... this.color ...               -- String
      METHODS
      ... this.area() ...              -- double
      ... this.distToOrigin() ...      -- double
      ... this.grow(int) ...           -- IShape
      ... this.biggerThan(that) ...    -- boolean
      ... this.contains(IPoint) ...    -- boolean
  */

 // area = πr²
  public double area() {
    return Math.PI * this.radius * this.radius;
 }

 // approximate minimum distance from circle boundary to origin
  public double distToOrigin() {
    return this.center.distToOrigin() - this.radius;
 }

 // returns a new circle with increased radius
  public IShape grow(int inc) {
   return new Circle(this.center, this.radius + inc, this.color);
 }

 // compares shapes by area
  public boolean biggerThan(IShape that) {
    return this.area() >= that.area();
 }

 // checks whether point lies inside or on circle boundary
  public boolean contains(IPoint pt) {
    return this.center.distTo(pt) <= this.radius;
 }
}

//Represents an axis-aligned square defined by its northwest corner.
class Square implements IShape {
  IPoint nw;
  int size;
  String color;

  Square(IPoint nw, int size, String color) {
    this.nw = nw;
    this.size = size;
    this.color = color;
 }
  
  /* TEMPLATE 
     FIELDS:
     ... this.nw ...                         -- IPoint
     ... this.size ...                       -- int
     ... this.color ...                      -- String
     METHODS:
     ... this.area() ...                     -- double
     ... this.distToOrigin() ...             -- double
     ... this.grow(int) ...                  -- IShape
     ... this.biggerThan(IShape) ...         -- boolean
     ... this.contains(IPoint) ...           -- boolean
  */

  // area = size²
  public double area() {
    return this.size * this.size;
  }

  // distance from NW corner to origin
  public double distToOrigin() {
    return this.nw.distToOrigin();
  }

  // returns a new square with increased side length
  public IShape grow(int inc) {
    return new Square(this.nw, this.size + inc, this.color);
  }

  // compares shapes by area
  public boolean biggerThan(IShape that) {
    return this.area() >= that.area();
  }

  // checks whether point lies inside square boundary
  public boolean contains(IPoint pt) {
    return (this.nw.xValue() <= pt.xValue()) &&
        (pt.xValue() <= this.nw.xValue() + this.size) &&
        (this.nw.yValue() <= pt.yValue()) &&
        (pt.yValue() <= this.nw.yValue() + this.size);
  }
 }

/*
 +--------+
 | CartPt |
 +--------+
 | int x  |
 | int y  |
 +--------+
 
 */

//Represents a point in Cartesian coordinates (x, y)
class CartPt implements IPoint {
 int x;
 int y;

 CartPt(int x, int y) {
     this.x = x;
     this.y = y;
 }

 // computes Euclidean distance from this point to the origin (0,0)
 public double distToOrigin() {
     return Math.sqrt(this.x * this.x + this.y * this.y);
 }

 // computes Euclidean distance between this point and another point
 public double distTo(IPoint pt) {
     return Math.sqrt(
         (this.x - pt.xValue()) * (this.x - pt.xValue()) +
         (this.y - pt.yValue()) * (this.y - pt.yValue()));
 }

 public double xValue() {
     return this.x;
 }

 public double yValue() {
     return this.y;
 }
}

//Represents a point in polar coordinates (r, θ)
//r is the distance from origin, θ is the angle in radians
class PolarPt implements IPoint {
  double r;
  double theta;
 
  PolarPt(double r, double theta) {
    this.r = r;
    this.theta = theta;
  }

  // x-coordinate conversion from polar to Cartesian
  public double xValue() {
    return this.r * Math.cos(this.theta);
  }

  // y-coordinate conversion from polar to Cartesian
  public double yValue() {
    return this.r * Math.sin(this.theta);
  }

  // distance from origin is simply r in polar form
  public double distToOrigin() {
    return this.r;
  }

  // Euclidean distance using Cartesian conversion
  public double distTo(IPoint pt) {
    double dx = this.xValue() - pt.xValue();
    double dy = this.yValue() - pt.yValue();
    return Math.sqrt(dx * dx + dy * dy);
  }
}

class Combo implements IShape {
  IShape i1;
  IShape i2;
  
  // constructor
  Combo(IShape i1, IShape i2) {
    this.i1 = i1;
    this.i2 = i2;
  }

  // to get the combination of the area of the two IShapes
  public double area() {
    return this.i1.area() + this.i2.area();
  }

  // returns the minimum of the distance between i1 and i2
  public double distToOrigin() {
    return Math.min(this.i1.distToOrigin(), this.i2.distToOrigin());
  }

  public IShape grow(int inc) {
    return new Combo(this.i1.grow(inc), this.i2.grow(inc));
  }

  public boolean biggerThan(IShape that) {
    return this.area() >= that.area();
  }

  public boolean contains(IPoint pt) {
    return this.i1.contains(pt) || this.i2.contains(pt);
  }
}

class ExamplesShapes {
  ExamplesShapes() {}
  
  IPoint pt1 = new CartPt(0, 0);
  IPoint pt2 = new CartPt(3, 4);
  IPoint pt3 = new CartPt(7, 1);
  
  IPoint p1 = new PolarPt(5, 0);
  IPoint p2 = new PolarPt(5, Math.PI / 2);
  IPoint p3 = new PolarPt(10, Math.PI / 4);
    
  IShape c1 = new Circle(new CartPt(50, 50), 10, "red");
  IShape c2 = new Circle(new CartPt(50, 50), 30, "red");
  IShape c3 = new Circle(new CartPt(30, 100), 30, "blue");
    
  IShape s1 = new Square(new CartPt(50, 50), 30, "red");
  IShape s2 = new Square(new CartPt(50, 50), 50, "red");
  IShape s3 = new Square(new CartPt(20, 40), 10, "green");
        
  // test the method distToOrigin in the class CartPt
  boolean testDistToOrigin(Tester t) { 
    return 
        t.checkInexact(this.pt1.distToOrigin(), 0.0, 0.001) &&
        t.checkInexact(this.pt2.distToOrigin(), 5.0, 0.001);
  }
    
  // test the method distTo in the class CartPt
  boolean testDistTo(Tester t) { 
    return
        t.checkInexact(this.pt1.distTo(this.pt2), 5.0, 0.001) &&
        t.checkInexact(this.pt2.distTo(this.pt3), 5.0, 0.001);
  }
    
    // test the method area in the class Circle
  boolean testCircleArea(Tester t) { 
    return
        t.checkInexact(this.c1.area(), 314.15, 0.01);
  }
    
  // test the method grow in the class Circle
  boolean testSquareArea(Tester t) { 
    return
        t.checkInexact(this.s1.area(), 900.0, 0.01);
  }
  
  // test the method distToOrigin in the class Circle
  boolean testCircleDistToOrigin(Tester t) { 
    return
        t.checkInexact(this.c1.distToOrigin(), 60.71, 0.01) &&
        t.checkInexact(this.c3.distToOrigin(), 74.40, 0.01);
  }
    
  // test the method distTo in the class Circle
  boolean testSquareDistToOrigin(Tester t) { 
    return
        t.checkInexact(this.s1.distToOrigin(), 70.71, 0.01) &&
        t.checkInexact(this.s3.distToOrigin(), 44.72, 0.01);
  }
    
  // test the method grow in the class Circle
  boolean testCircleGrow(Tester t) { 
    return
        t.checkExpect(this.c1.grow(20), this.c2);
  }
    
  // test the method grow in the class Circle
  boolean testSquareGrow(Tester t) { 
    return
        t.checkExpect(this.s1.grow(20), this.s2);
  }
    
  // test the method biggerThan in the class Circle
  boolean testCircleBiggerThan(Tester t) { 
    return
        t.checkExpect(this.c1.biggerThan(this.c2), false) && 
        t.checkExpect(this.c2.biggerThan(this.c1), true) && 
        t.checkExpect(this.c1.biggerThan(this.s1), false) && 
        t.checkExpect(this.c1.biggerThan(this.s3), true);
  }
    
  // test the method biggerThan in the class Square
  boolean testSquareBiggerThan(Tester t) { 
    return
        t.checkExpect(this.s1.biggerThan(this.s2), false) && 
        t.checkExpect(this.s2.biggerThan(this.s1), true) && 
        t.checkExpect(this.s1.biggerThan(this.c1), true) && 
        t.checkExpect(this.s3.biggerThan(this.c1), false);
  }
    
  // test the method contains in the class Circle
  boolean testCircleContains(Tester t) { 
    return
        t.checkExpect(this.c1.contains(new CartPt(100, 100)), false) && 
        t.checkExpect(this.c2.contains(new CartPt(40, 60)), true);
  }
    
    
  // test the method contains in the class Square
  boolean testSquareContains(Tester t) { 
    return
        t.checkExpect(this.s1.contains(new CartPt(100, 100)), false) && 
        t.checkExpect(this.s2.contains(new CartPt(55, 60)), true);
  }
    
  boolean testPolarCoordinates(Tester t) {
    return
        t.checkInexact(this.p1.xValue(), 5.0, 0.001) &&
        t.checkInexact(this.p1.yValue(), 0.0, 0.001) &&
        t.checkInexact(this.p2.xValue(), 0.0, 0.001) &&
        t.checkInexact(this.p2.yValue(), 5.0, 0.001);
  }
  
  boolean testPolarDistToOrigin(Tester t) {
    return
        t.checkInexact(this.p1.distToOrigin(), 5.0, 0.001) &&
        t.checkInexact(this.p2.distToOrigin(), 5.0, 0.001) &&
        t.checkInexact(this.p3.distToOrigin(), 10.0, 0.001);
  }
  
  boolean testCrossRepresentationDistance(Tester t) {
    CartPt origin = new CartPt(0, 0);
    
    return
        t.checkInexact(this.p1.distTo(origin), 5.0, 0.001) &&
        t.checkInexact(origin.distTo(this.p2), 5.0, 0.001);
  }
  
  boolean testShapeWithPolarPoint(Tester t) {
     IShape c = new Circle(this.p1, 5, "red");
    
    return t.checkInexact(c.distToOrigin(), 0.0, 0.001);
  }
  
  IShape combo1 = new Combo(this.c1, this.s1);
  IShape combo2 = new Combo(this.c2, this.s2);
  IShape combo3 = new Combo(this.c1, this.c3);
  
  boolean testComboArea(Tester t) {
    return t.checkInexact(
        this.combo1.area(),
        this.c1.area() + this.s1.area(),
        0.01);
  }
  
  boolean testComboGrow(Tester t) {
    IShape grown = this.combo1.grow(10);

    return t.checkExpect(
        grown,
        new Combo(this.c1.grow(10), this.s1.grow(10)));
  }
  
  boolean testComboContainsTrue(Tester t) {
    return t.checkExpect(
        this.combo1.contains(new CartPt(50, 50)),
        true);
  }
  
  boolean testComboContainsFalse(Tester t) {
    return t.checkExpect(
        this.combo1.contains(new CartPt(1000, 1000)),
        false);
  }
  
  boolean testComboBigger(Tester t) {
    return t.checkExpect(
        this.combo2.biggerThan(this.combo1),
        this.combo2.area() >= this.combo1.area());
  }
}
