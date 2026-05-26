import tester.Tester;

interface IShape {
  boolean sameShape(IShape that);

  boolean sameCircle(Circle that);

  boolean sameRect(Rect that);

  boolean sameSquare(Square that);

  boolean sameCombo(Combo that);
}

abstract class AShape implements IShape {
  int x, y;

  AShape(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean sameCircle(Circle that) {
    return false;
  }

  public boolean sameRect(Rect that) {
    return false;
  }

  public boolean sameSquare(Square that) {
    return false;
  }

  public boolean sameCombo(Combo that) {
    return false;
  }
}

class Circle extends AShape {
  int radius;

  Circle(int x, int y, int radius) {
    super(x, y);
    this.radius = radius;
  }

  public boolean sameShape(IShape that) {
    return that.sameCircle(this);
  }

  @Override
  public boolean sameCircle(Circle that) {
    return this.x == that.x && this.y == that.y && this.radius == that.radius;
  }
}

class Rect extends AShape {
  int w, h;

  Rect(int x, int y, int w, int h) {
    super(x, y);
    this.w = w;
    this.h = h;
  }

  public boolean sameShape(IShape that) {
    return that.sameRect(this);
  }

  @Override
  public boolean sameRect(Rect that) {
    return this.x == that.x && this.y == that.y && this.w == that.w && this.h == that.h;
  }
}

class Square extends Rect {
  Square(int x, int y, int s) {
    super(x, y, s, s);
  }

  // CRITICAL: We must override sameShape so the handshake says "Square"
  @Override
  public boolean sameShape(IShape that) {
    return that.sameSquare(this);
  }

  @Override
  public boolean sameSquare(Square that) {
    return this.x == that.x && this.y == that.y && this.w == that.w && this.h == that.h;
  }

  // CRITICAL: Since we inherited sameRect from Rect, we must override it
  // to return false, otherwise a Square could be "sameRect" as a Rect.
  @Override
  public boolean sameRect(Rect that) {
    return false;
  }
}

class Combo implements IShape {
  IShape left;
  IShape right;

  Combo(IShape left, IShape right) {
    this.left = left;
    this.right = right;
  }

  public boolean sameShape(IShape that) {
    return that.sameCombo(this);
  }

  public boolean sameCombo(Combo that) {
    return this.left.sameShape(that.left) && this.right.sameShape(that.right);
  }

  public boolean sameCircle(Circle that) {
    return false;
  }

  public boolean sameRect(Rect that) {
    return false;
  }

  public boolean sameSquare(Square that) {
    return false;
  }
}

class ExamplesShapes {
  // Existing Simple Shapes
  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);
  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);
  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 6);
  Square s3 = new Square(3, 4, 5);

  Combo combo1 = new Combo(this.c1, this.r1);
  Combo combo2 = new Combo(new Circle(3, 4, 5), new Rect(3, 4, 5, 5));
  Combo combo3 = new Combo(new Circle(3, 4, 10), this.r1);

  Combo nestedCombo1 = new Combo(this.combo1, this.s1);
  Combo nestedCombo2 = new Combo(this.combo2, new Square(3, 4, 5));

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

  // --- New Combo Tests ---

  boolean testSameCombo(Tester t) {
    return t.checkExpect(this.combo1.sameShape(this.combo2), true)
        && t.checkExpect(this.combo2.sameShape(this.combo1), true)
        && t.checkExpect(this.combo1.sameShape(this.combo3), false)
        && t.checkExpect(this.combo1.sameShape(this.c1), false)
        && t.checkExpect(this.c1.sameShape(this.combo1), false)
        && t.checkExpect(this.nestedCombo1.sameShape(this.nestedCombo2), true)
        && t.checkExpect(this.nestedCombo1.sameShape(this.combo1), false);
  }
}