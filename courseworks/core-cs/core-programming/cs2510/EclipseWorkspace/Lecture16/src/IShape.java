interface IShape {
  <R> R accept(IShapeVisitor<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;

  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }

  public <R> R accept(IShapeVisitor<R> visitor) {
    return visitor.visitCircle(this); // Java knows 'this' is a Circle!
  }
}

class Rect implements IShape {
  int x, y, w, h;
  String color;

  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }
}

interface IShapeVisitor<R> {
  R visitCircle(Circle c);

  R visitRect(Rect r);

  R visitSquare(Square s);
}