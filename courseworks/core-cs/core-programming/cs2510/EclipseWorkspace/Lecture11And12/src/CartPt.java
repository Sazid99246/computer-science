class CartPt {
  double x;
  double y;
  
  CartPt(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  boolean samePoint(CartPt that) {
    return this.x == that.x && this.y == that.y;
  }
}