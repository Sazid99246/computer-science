import tester.*;

// represents bulk coffee for sale
class Coffee {
  private String origin;
  private int price;

  public Coffee(String origin, int price) {
    this.origin = origin;
    this.price = price;
  }

// is this the same Coffee as other?
  public boolean same(Coffee other) {
    return this.origin.equals(other.origin) && this.price == other.price;
  }
}

class Decaf extends Coffee {
  private int quality; // between 97 and 99

  Decaf(String origin, int price, int quality) {
    super(origin, price);
    this.quality = quality;
  }

  public boolean same(Decaf other) {
    return this.origin.equals(other.origin) && this.price == other.price
        && this.quality == other.quality;
  }

}

class ExamplesCoffee {
  // Regular Coffee
  Coffee c1 = new Coffee("Colombia", 10);
  Coffee c2 = new Coffee("Colombia", 10);
  Coffee c3 = new Coffee("Ethiopia", 12);

  // Decaf Coffee
  Decaf d1 = new Decaf("Colombia", 10, 98);
  Decaf d2 = new Decaf("Colombia", 10, 98);
  Decaf d3 = new Decaf("Colombia", 10, 99); // Same origin/price, different quality

  boolean testSame(Tester t) {
    return t.checkExpect(c1.same(c2), true) && t.checkExpect(d1.same(d2), true)
        && t.checkExpect(d1.same(d3), false) && t.checkExpect(d1.same((Coffee) d3), true);
  }
}