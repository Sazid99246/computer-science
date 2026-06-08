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
// 2. Function Object Interfaces
// =============================================================================

// Predicate interface for boolean tests
interface IPred<T> {
  boolean apply(T t);
}

// General function object interface (e.g., for actions yielding Void)
interface IFunc<Arg, Ret> {
  Ret apply(Arg arg);
}

// =============================================================================
// 3. Generic List Framework
// =============================================================================

interface IList<T> {
  // Finds and returns the item in this list matching the given predicate,
  // or null if no such item is found
  T find(IPred<T> whichOne);

  // EFFECT: Finds and modifies the item in this list matching the
  // given predicate, by using the given operation
  Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo);
}

class MtList<T> implements IList<T> {
  MtList() {
  }

  public T find(IPred<T> whichOne) {
    return null;
  }

  public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) {
    return null;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public T find(IPred<T> whichOne) {
    if (whichOne.apply(this.first)) {
      return this.first;
    }
    else {
      return this.rest.find(whichOne);
    }
  }

  public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) {
    if (whichOne.apply(this.first)) {
      whatToDo.apply(this.first);
    }
    else {
      this.rest.find(whichOne, whatToDo);
    }
    return null;
  }
}

// =============================================================================
// 4. Concrete Function Objects for Testing
// =============================================================================

// Predicate to find a person by their name
class FindByName implements IPred<Person> {
  String targetName;

  FindByName(String targetName) {
    this.targetName = targetName;
  }

  public boolean apply(Person p) {
    return p.name.equals(this.targetName);
  }
}

// Function object to change a person's phone number via side effect
class ChangePhoneAction implements IFunc<Person, Void> {
  int newNum;

  ChangePhoneAction(int newNum) {
    this.newNum = newNum;
  }

  public Void apply(Person p) {
    p.changeNum(this.newNum);
    return null; // Required to satisfy the Void return type
  }
}

// =============================================================================
// 5. Tests and Mock Data
// =============================================================================

class ExamplePhoneLists {

  Person anne;
  Person bob;
  Person clyde;
  Person dana;
  Person eric;
  Person frank;
  Person gail;
  Person henry;
  Person irene;
  Person jenny;

  IList<Person> friends;
  IList<Person> family;
  IList<Person> work;

  void initData() {
    this.anne = new Person("Anne", 1234);
    this.bob = new Person("Bob", 3456);
    this.clyde = new Person("Clyde", 6789);
    this.dana = new Person("Dana", 1357);
    this.eric = new Person("Eric", 12469);
    this.frank = new Person("Frank", 7294);
    this.gail = new Person("Gail", 9345);
    this.henry = new Person("Henry", 8602);
    this.irene = new Person("Irene", 91302);
    this.jenny = new Person("Jenny", 8675309);

    this.friends = new ConsList<Person>(this.anne,
        new ConsList<Person>(this.clyde,
            new ConsList<Person>(this.gail, new ConsList<Person>(this.frank,
                new ConsList<Person>(this.jenny, new MtList<Person>())))));

    this.family = new ConsList<Person>(this.anne,
        new ConsList<Person>(this.dana, new ConsList<Person>(this.frank, new MtList<Person>())));

    this.work = new ConsList<Person>(this.bob,
        new ConsList<Person>(this.clyde,
            new ConsList<Person>(this.dana,
                new ConsList<Person>(this.eric, new ConsList<Person>(this.henry,
                    new ConsList<Person>(this.irene, new MtList<Person>()))))));
  }

  void testFindAndModifyGeneric(Tester t) {
    this.initData();

    // Testing 'find' instead of old 'contains' and 'findPhoneNum'
    Person foundFrank = this.friends.find(new FindByName("Frank"));
    t.checkExpect(foundFrank != null, true);
    t.checkExpect(foundFrank.phone, 7294);

    Person foundZelda = this.work.find(new FindByName("Zelda"));
    t.checkExpect(foundZelda, null);

    // Cross-list verification
    Person friendsAnne = this.friends.find(new FindByName("Anne"));
    Person familyAnne = this.family.find(new FindByName("Anne"));
    t.checkExpect(friendsAnne.phone, familyAnne.phone);

    // Testing 'find' with mutation (the second 'Do Now!' task)
    this.family.find(new FindByName("Frank"), new ChangePhoneAction(9021));

    // Re-verify updates across shared reference structures
    Person updatedFriendsFrank = this.friends.find(new FindByName("Frank"));
    Person updatedFamilyFrank = this.family.find(new FindByName("Frank"));

    t.checkExpect(updatedFriendsFrank.phone, 9021);
    t.checkExpect(updatedFamilyFrank.phone, 9021);
    t.checkExpect(this.frank.phone, 9021);
  }

  void testChangeNum(Tester t) {
    this.initData();
    t.checkExpect(this.frank.phone, 7294);
    this.frank.changeNum(9021);
    t.checkExpect(this.frank.phone, 9021);
  }

  void testAliasing(Tester t) {
    Person johndoe1 = new Person("John Doe", 12345);
    Person johndoe2 = new Person("John Doe", 12345);
    Person johndoe3 = johndoe1;

    t.checkExpect(johndoe1.samePerson(johndoe2), true);
    t.checkExpect(johndoe1.samePerson(johndoe3), true);

    johndoe1.name = "Johnny Deere";

    // Note: johndoe1.samePerson(johndoe2) will now fail because names don't match
    t.checkExpect(johndoe1.samePerson(johndoe2), false);
    t.checkExpect(johndoe1.samePerson(johndoe3), true);
  }
}