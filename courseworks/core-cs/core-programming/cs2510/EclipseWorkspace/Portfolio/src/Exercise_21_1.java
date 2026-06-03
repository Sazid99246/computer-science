import tester.*;

// represents a set of two numbers
class Set2 {
  private int one;
  private int two;

  public Set2(int one, int two) {
    this.one = one;
    this.two = two;
  }

  // does this set contain x?
  public boolean contains(int x) {
    return (x == this.one) || (x == this.two);
  }

  // is this the same Set2 as other?
  public boolean same(Set2 other) {
    return other.contains(this.one) && other.contains(this.two) && this.contains(other.one)
        && this.contains(other.two);
  }
}

class ExamplesSet2 {
  Set2 s12 = new Set2(1, 2);
  Set2 s21 = new Set2(2, 1);
  Set2 s13 = new Set2(1, 3);
  Set2 s44 = new Set2(4, 4);
  Set2 s14 = new Set2(1, 4);

  // Testing contains
  boolean testContains(Tester t) {
    return t.checkExpect(this.s12.contains(1), true) && t.checkExpect(this.s12.contains(3), false)
        && t.checkExpect(this.s44.contains(4), true);
  }

  // Testing same
  boolean testSame(Tester t) {
    return t.checkExpect(this.s12.same(this.s21), true) // Order doesn't matter
        && t.checkExpect(this.s12.same(this.s13), false) // One element differs
        && t.checkExpect(this.s12.same(this.s14), false) // Another difference
        && t.checkExpect(this.s44.same(new Set2(4, 4)), true);
  }
}