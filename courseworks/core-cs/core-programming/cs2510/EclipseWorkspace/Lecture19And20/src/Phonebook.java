import tester.*;

// =============================================================================
// 1. Core Data Definitions
// =============================================================================

class Person {
  String name;
  int phone;

  Person(String name, int phone) {
    this.name = name;
    this.phone = phone;
  }

  boolean samePerson(Person that) {
    return this.name.equals(that.name) && this.phone == that.phone;
  }

  void changeNum(int newNum) {
    this.phone = newNum;
  }
}

// =============================================================================
// 2. Function Object Interfaces (From Lecture 19)
// =============================================================================

// Predicate interface for boolean tests
interface IPred<T> {
  boolean apply(T t);
}

// General function object interface
interface IFunc<Arg, Ret> {
  Ret apply(Arg arg);
}

// Concrete predicate to find a person by their name
class FindByName implements IPred<Person> {
  String targetName;

  FindByName(String targetName) {
    this.targetName = targetName;
  }

  public boolean apply(Person p) {
    return p.name.equals(this.targetName);
  }
}

// =============================================================================
// 3. The Generic Mutable Node Framework (Internal Structural Hierarchy)
// =============================================================================

// Represents a sentinel node, a data node, or the empty end of a list
abstract class ANode<T> {
  ANode<T> rest;

  ANode(ANode<T> rest) {
    this.rest = rest;
  }

  // EFFECT: Modifies the list structural pointers to bypass the node passing the
  // predicate
  abstract void removeHelp(IPred<T> choice, ANode<T> prev);
}

// Represents the structural "dummy" node sitting at the front of the list
class SentinelNode<T> extends ANode<T> {
  SentinelNode(ANode<T> rest) {
    super(rest);
  }

  void removeHelp(IPred<T> choice, ANode<T> prev) {
    // A Sentinel contains no data; this method should never realistically be
    // executed on it
    throw new RuntimeException("Can't attempt structural data removal directly on a Sentinel!");
  }
}

// Represents the empty terminal end of the list structure
class MtNode<T> extends ANode<T> {
  MtNode() {
    super(null); // The terminal end points to nothing
  }

  void removeHelp(IPred<T> choice, ANode<T> prev) {
    return; // Base Case: We reached the end of the list without matching; do nothing
  }
}

// Represents a data-carrying element node in the structural chain
class ConsNode<T> extends ANode<T> {
  T data;

  ConsNode(T data, ANode<T> rest) {
    super(rest);
    this.data = data;
  }

  void removeHelp(IPred<T> choice, ANode<T> prev) {
    if (choice.apply(this.data)) {
      // Re-route the previous node's link to point directly to our tail 'rest' link
      prev.rest = this.rest;
    }
    else {
      // Move to the next node, passing 'this' as the preceding tracking reference
      // node
      this.rest.removeHelp(choice, this);
    }
  }
}

// =============================================================================
// 4. The Generic Wrapper Container Class (Hides Sentinels/Nodes from User)
// =============================================================================

interface IMutableList<T> {
  void addToFront(T t);

  void remove(IPred<T> choice);
}

class MutableList<T> implements IMutableList<T> {
  SentinelNode<T> sentinel;

  MutableList() {
    // Initializes an empty list structure protected behind a wrapper indirection
    // sentinel
    this.sentinel = new SentinelNode<T>(new MtNode<T>());
  }

  // Solves Lecture 20 Exercise: Inserts an element onto the front of the list
  public void addToFront(T t) {
    // The sentinel's current rest path is passed down to act as the new node's
    // trail sequence
    this.sentinel.rest = new ConsNode<T>(t, this.sentinel.rest);
  }

  // Core Lecture 20 Goal: Safely delegates pointer removal manipulation to nodes
  public void remove(IPred<T> choice) {
    this.sentinel.rest.removeHelp(choice, this.sentinel);
  }

  // Utility helper for validation checks during testing assertions
  public boolean contains(IPred<T> choice) {
    ANode<T> curr = this.sentinel.rest;
    while (!(curr instanceof MtNode)) {
      ConsNode<T> cons = (ConsNode<T>) curr;
      if (choice.apply(cons.data)) {
        return true;
      }
      curr = curr.rest;
    }
    return false;
  }
}

// =============================================================================
// 5. Verification Testing Suites
// =============================================================================

class ExampleMutableListTests {
  Person anne;
  Person bob;
  Person clyde;
  Person dana;
  Person eric;
  Person frank;

  MutableList<Person> work;
  MutableList<Person> friends;

  void initData() {
    this.anne = new Person("Anne", 1234);
    this.bob = new Person("Bob", 3456);
    this.clyde = new Person("Clyde", 6789);
    this.dana = new Person("Dana", 1357);
    this.eric = new Person("Eric", 12469);
    this.frank = new Person("Frank", 7294);

    this.work = new MutableList<Person>();
    this.work.addToFront(this.frank);
    this.work.addToFront(this.eric);
    this.work.addToFront(this.dana);
    this.work.addToFront(this.clyde);
    this.work.addToFront(this.bob); // Structure: Sentinel -> Bob -> Clyde -> Dana -> Eric -> Frank

    // Replicating the Aliasing Hazard structure from Section 20.3 manually:
    this.friends = new MutableList<Person>();
    this.friends.sentinel.rest = new ConsNode<Person>(this.anne,
        new ConsNode<Person>(this.bob, this.work.sentinel.rest));
  }

  void testMutableRemovals(Tester t) {
    this.initData();

    // 1. Remove Middle Item (Dana)
    t.checkExpect(this.work.contains(new FindByName("Dana")), true);
    this.work.remove(new FindByName("Dana"));
    t.checkExpect(this.work.contains(new FindByName("Dana")), false);

    // 2. Remove First Data Item (Bob) via Sentinel protection
    t.checkExpect(this.work.contains(new FindByName("Bob")), true);
    this.work.remove(new FindByName("Bob"));
    t.checkExpect(this.work.contains(new FindByName("Bob")), false);
  }

  void testAliasingSideEffects(Tester t) {
    this.initData();

    // Verifying Eric is shared across lists initially
    t.checkExpect(this.work.contains(new FindByName("Eric")), true);
    t.checkExpect(this.friends.contains(new FindByName("Eric")), true);

    // Remove Eric from work list
    this.work.remove(new FindByName("Eric"));

    // Demonstrating the implicit deletion side effect from text Section 20.3
    t.checkExpect(this.work.contains(new FindByName("Eric")), false);
    t.checkExpect(this.friends.contains(new FindByName("Eric")), false); // Fails original user
                                                                         // expectation
  }
}