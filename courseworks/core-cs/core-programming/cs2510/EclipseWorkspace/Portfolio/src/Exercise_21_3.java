class Factoring {
  int x, y;

  Factoring(int x, int y) {
    this.x = x;
    this.y = y;
  }

  int prod() {
    return this.x * this.y;
  }

  boolean sameFactoring(Factoring other) {
    return (this.x == other.x && this.y == other.y) || (this.x == other.y && this.y == other.y);
  }
  
  boolean sameProduct(Factoring other) {
    return this.prod() == other.prod();
  }
}