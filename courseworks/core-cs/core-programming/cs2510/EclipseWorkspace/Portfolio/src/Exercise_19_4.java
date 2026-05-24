import tester.Tester;

// To represent a list of integers
interface ILin {
  // Count how many times the given integer appears in this list
  int howMany(int i);

  // Remove the first occurrence of the given integer from this list
  ILin removeFirst(int i);

  // Determine the total structural size of this list
  int length();
}

// To represent an empty list of integers
class MTin implements ILin {
  MTin() {
  }

  public int howMany(int i) {
    return 0;
  }

  public ILin removeFirst(int i) {
    return this;
  }

  public int length() {
    return 0;
  }
}

// To represent a non-empty list of integers
class Cin implements ILin {
  int first;
  ILin rest;

  Cin(int first, ILin rest) {
    this.first = first;
    this.rest = rest;
  }

  public int howMany(int i) {
    if (this.first == i) {
      return 1 + this.rest.howMany(i);
    }
    else {
      return this.rest.howMany(i);
    }
  }

  public ILin removeFirst(int i) {
    if (this.first == i) {
      return this.rest;
    }
    else {
      return new Cin(this.first, this.rest.removeFirst(i));
    }
  }

  public int length() {
    return 1 + this.rest.length();
  }
}

//A union representing a collection of integers
interface ICollection {
  ICollection add(int i);

  boolean in(int i);

  int size();

  ICollection rem(int i);
}

//Abstract superclass containing shared fields and method definitions
abstract class ACollection implements ICollection {
  ILin elements;

  ACollection(ILin elements) {
    this.elements = elements;
  }

  // Is i a member of this collection? (Identical for Set and Bag)
  public boolean in(int i) {
    return this.elements.howMany(i) > 0;
  }

  // Part 5: Total physical count of items inside the underlying list structure
  public int size() {
    return this.elements.length();
  }
}

//Concrete Variant: Set (Duplicates filtered on insertion)
class Set extends ACollection {
  Set(ILin elements) {
    super(elements);
  }

  // Add i to this set unless it's already present
  public ICollection add(int i) {
    if (this.in(i)) {
      return this;
    }
    else {
      return new Set(new Cin(i, this.elements));
    }
  }

  // Part 6: Completely removes the element from a Set (since it exists at most
  // once)
  public ICollection rem(int i) {
    return new Set(this.elements.removeFirst(i));
  }
}

//Concrete Variant: Bag (Duplicates allowed)
class Bag extends ACollection {
  Bag(ILin elements) {
    super(elements);
  }

  // Add i directly to this bag
  public ICollection add(int i) {
    return new Bag(new Cin(i, this.elements));
  }

  // Specific behavior: how often is i in this bag?
  public int howMany(int i) {
    return this.elements.howMany(i);
  }

  // Part 6: Drops exactly one occurrence of the item from the bag
  public ICollection rem(int i) {
    return new Bag(this.elements.removeFirst(i));
  }
}

class ExamplesCollections {
  // Initial lists
  ILin mt = new MTin();
  ILin list1 = new Cin(1, new Cin(2, new Cin(2, new MTin())));

  // Examples of Sets
  ICollection emptySet = new Set(mt);
  ICollection set1 = new Set(new Cin(1, new Cin(2, new MTin())));

  // Examples of Bags
  ICollection emptyBag = new Bag(mt);
  Bag bag1 = new Bag(list1); // contains [1, 2, 2]

  // Test fundamental insertion and inclusion rules
  boolean testAddAndIn(Tester t) {
    return t.checkExpect(this.emptySet.in(5), false)
        && t.checkExpect(this.emptySet.add(5).in(5), true)
        // Sets should reject multiple copies of the same value
        && t.checkExpect(this.set1.add(2), this.set1)
        // Bags should accept multiple copies
        && t.checkExpect(this.bag1.howMany(2), 2)
        && t.checkExpect(((Bag) this.bag1.add(2)).howMany(2), 3);
  }

  // Test structural size calculations
  boolean testSize(Tester t) {
    return t.checkExpect(this.emptySet.size(), 0) && t.checkExpect(this.set1.size(), 2)
        && t.checkExpect(this.bag1.size(), 3) // 1 and two 2s = 3 total items
        && t.checkExpect(this.bag1.add(5).size(), 4);
  }

  // Test subtraction element removals
  boolean testRem(Tester t) {
    ICollection baseSet = new Set(new Cin(1, new Cin(2, new MTin())));
    ICollection expectedSet = new Set(new Cin(2, new MTin()));

    Bag baseBag = new Bag(new Cin(2, new Cin(1, new Cin(2, new MTin()))));
    Bag expectedBag = new Bag(new Cin(1, new Cin(2, new MTin())));

    return t.checkExpect(baseSet.rem(1), expectedSet) && t.checkExpect(baseSet.rem(3), baseSet) // item
                                                                                                // not
                                                                                                // found
    // Bag must drop only ONE copy of the value 2
        && t.checkExpect(baseBag.rem(2), expectedBag)
        && t.checkExpect(((Bag) baseBag.rem(2)).howMany(2), 1);
  }
}