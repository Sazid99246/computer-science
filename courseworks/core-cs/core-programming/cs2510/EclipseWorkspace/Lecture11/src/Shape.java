import tester.Tester;

interface IShape {
  boolean sameShape(IShape that);

  // is this shape a Circle?
  boolean isCircle();

  // is this shape a Rect?
  boolean isRect();

  // is this shape a Square?
  boolean isSquare();
}

class Circle implements IShape {
  int x, y;
  int radius;

  Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }

  public boolean isCircle() {
    return true;
  }

  public boolean isRect() {
    return false;
  }

  public boolean isSquare() {
    return false;
  }

  public boolean sameCircle(Circle that) {
    return this.x == that.x && this.y == that.y && this.radius == that.radius;
  }

  public boolean sameShape(IShape that) {
    if (that instanceof Circle) {
      return this.sameCircle((Circle) that);
    }
    else {
      return false;
    }
  }
}

class Rect implements IShape {
  int x, y;
  int w, h;

  Rect(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public boolean isCircle() {
    return false;
  }

  public boolean isRect() {
    return true;
  }

  public boolean isSquare() {
    return false;
  }

  public boolean sameRect(Rect that) {
    return this.x == that.x && this.y == that.y && this.w == that.w && this.h == that.h;
  }

  public boolean sameShape(IShape that) {
    if (that.isRect()) {
      return this.sameRect((Rect) that);
    }
    else {
      return false;
    }
  }
}

class Square extends Rect {

  Square(int x, int y, int s) {
    super(x, y, s, s);
  }

  public boolean isCircle() {
    return false;
  }

  public boolean isRect() {
    return false;
  }

  public boolean isSquare() {
    return true;
  }

  public boolean sameSquare(Square that) {
    return this.x == that.x && this.y == that.y && this.w == that.w && this.h == that.h;
  }

  public boolean sameShape(IShape that) {
    if (that.isSquare()) {
      return this.sameSquare((Square) that);
    }
    else {
      return false;
    }
  }
}

class ExamplesShapes {
  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);
  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);
  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 6);
  Square s3 = new Square(3, 4, 5);

  boolean testSameCircle(Tester t) {
    return t.checkExpect(c1.sameCircle(c2), false) && t.checkExpect(c2.sameCircle(c1), false)
        && t.checkExpect(c1.sameCircle(c3), true) && t.checkExpect(c3.sameCircle(c1), true);
  }

  boolean testSameRectange(Tester t) {
    return t.checkExpect(r1.sameRect(r2), false) && t.checkExpect(r2.sameRect(r1), false)
        && t.checkExpect(r1.sameRect(r3), true) && t.checkExpect(r3.sameRect(r1), true);
  }

  boolean testSameShape(Tester t) {
    return t.checkExpect(c1.sameShape(c3), true) && t.checkExpect(c1.sameShape(c2), false)
        && t.checkExpect(r1.sameShape(r3), true) && t.checkExpect(r1.sameShape(r2), false)
        && t.checkExpect(c1.sameShape(r1), false) && t.checkExpect(r1.sameShape(c1), false)
        && t.checkExpect(s1.sameShape(r2), false) && t.checkExpect(r2.sameShape(s1), false)
        && t.checkExpect(s1.sameShape(r1), false) && t.checkExpect(r1.sameShape(s1), false);
  }

  boolean testSameSquare(Tester t) {
    return t.checkExpect(s1.sameShape(s2), false) && t.checkExpect(s2.sameShape(s1), false)
        && t.checkExpect(s1.sameShape(s3), true) && t.checkExpect(s3.sameShape(s1), true);
  }
}