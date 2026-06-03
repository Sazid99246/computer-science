import tester.*;

// some grocery items
interface IItem {
  boolean same(IItem x);

  boolean isCoffee();

  Coffee toCoffee();

  boolean isTea();

  Tea toTea();

  // Added for Exercise 21.6
  boolean isChocolate();

  Chocolate toChocolate();
}

abstract class AItem implements IItem {
  int price;

  AItem(int price) {
    this.price = price;
  }

  // Default implementations to reduce code in subclasses
  public boolean isCoffee() {
    return false;
  }

  public boolean isTea() {
    return false;
  }

  public boolean isChocolate() {
    return false;
  }

  public Coffee toCoffee() {
    return Util.error("Not Coffee");
  }

  public Tea toTea() {
    return Util.error("Not Tea");
  }

  public Chocolate toChocolate() {
    return Util.error("Not Chocolate");
  }
}

//Updated Coffee using the Abstract class
class Coffee extends AItem {
  private String origin;

  Coffee(String origin, int price) {
    super(price); // Pass price to the abstract parent
    this.origin = origin;
  }

  @Override
  public boolean isCoffee() {
    return true;
  }

  @Override
  public Coffee toCoffee() {
    return this;
  }

  public boolean same(IItem other) {
    return other.isCoffee() && other.toCoffee().sameCoffee(this);
  }

// Helper for double dispatch
  private boolean sameCoffee(Coffee other) {
    return this.origin.equals(other.origin) && this.price == other.price;
  }
}

class Tea extends AItem {
  private String kind;

  Tea(String kind, int price) {
    super(price); // Use AItem's price field
    this.kind = kind;
  }

  @Override
  public boolean isTea() {
    return true;
  }

  @Override
  public Tea toTea() {
    return this;
  }

  public boolean same(IItem other) {
    return other.isTea() && other.toTea().sameTea(this);
  }

  private boolean sameTea(Tea other) {
    return this.kind.equals(other.kind) && this.price == other.price;
  }
}

class Chocolate extends AItem {
  private String sweetness;

  Chocolate(String sweetness, int price) {
    super(price);
    this.sweetness = sweetness;
  }

  @Override
  public boolean isChocolate() {
    return true;
  }

  @Override
  public Chocolate toChocolate() {
    return this;
  }

  public boolean same(IItem other) {
    return other.isChocolate() && other.toChocolate().sameChocolate(this);
  }

  private boolean sameChocolate(Chocolate other) {
    return this.sweetness.equals(other.sweetness) && this.price == other.price;
  }
}

interface ILoI {
  boolean same(ILoI other);

  boolean isMt();

  boolean isCons();

  Cons getCons();
}

class Mt implements ILoI {
  public boolean isMt() {
    return true;
  }

  public boolean isCons() {
    return false;
  }

  public Cons getCons() {
    return Util.error("Empty");
  }

  public boolean same(ILoI other) {
    return other.isMt(); // Two empty lists are the same
  }
}

class Cons implements ILoI {
  int first;
  ILoI rest;

  Cons(int first, ILoI rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isMt() {
    return false;
  }

  public boolean isCons() {
    return true;
  }

  public Cons getCons() {
    return this;
  }

  public boolean same(ILoI other) {
    if (other.isCons()) {
      Cons that = other.getCons();
      // Structural recursion: check the head AND the rest of the list
      return this.first == that.first && this.rest.same(that.rest);
    }
    else {
      return false; // Cannot be the same as an empty list (Mt)
    }
  }
}

class ExamplesItems {
  ExamplesItems() {
  }

  IItem c1 = new Coffee("Colombia", 10);
  IItem c2 = new Coffee("Colombia", 10);
  IItem c3 = new Coffee("Sumatra", 10);
  IItem t1 = new Tea("Green", 5);
  IItem t2 = new Tea("Green", 5);
  IItem t3 = new Tea("Black", 5);coo

  boolean testSame(Tester t) {
    return
    t.checkExpect(c1.same(c1), true) && t.checkExpect(c1.same(c2), true)
        && t.checkExpect(t1.same(t2), true) &&
        t.checkExpect(c1.same(c3), false) && t.checkExpect(t1.same(t3), false) &&
        t.checkExpect(c1.same(t1), false) && t.checkExpect(t1.same(c1), false);
  }

  ILoI mt = new Mt();
  ILoI list12 = new Cons(1, new Cons(2, new Mt()));
  ILoI list12_copy = new Cons(1, new Cons(2, new Mt()));
  ILoI list123 = new Cons(1, new Cons(2, new Cons(3, new Mt())));
  ILoI list21 = new Cons(2, new Cons(1, new Mt()));

  boolean testListSame(Tester t) {
    return t.checkExpect(mt.same(new Mt()), true) && t.checkExpect(list12.same(list12_copy), true)
        && t.checkExpect(list12.same(mt), false) && // Different lengths
        t.checkExpect(list12.same(list123), false) && // Different lengths
        t.checkExpect(list12.same(list21), false); // Different order
  }
}

class Util {
// Signals an error with the given message
  static <T> T error(String msg) {
    throw new RuntimeException(msg);
  }
}