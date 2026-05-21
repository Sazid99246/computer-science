import tester.Tester;

interface IAT {
  // To compute the number of known ancestors of this ancestor tree (excluding
  // this ancestor tree itself)
  int count();

  // To compute the number of known ancestors of this ancestor tree (*including*
  // this ancestor tree itself)
  int countHelp();

  // To compute how many ancestors of this ancestor tree (excluding this ancestor
  // tree itself)
  // are women older than 40 (in the current year).
  int femaleAncOver40();

  // To compute how many ancestors of this ancestor tree (*including* this
  // ancestor tree itself)
  // are women older than 40 (in the current year).
  int femaleAncOver40Help();

  // Counts how many generations are completely known
  int numTotalGens();

  // Counts how many generations are at least partially known
  int numPartialGens();

  // To determine if this ancestry tree is well-formed
  boolean wellFormed();

  // To determine if this ancestry tree is older than the given year of birth,
  // and its parents are well-formed
  boolean wellFormedHelp(int childYob);

  // To compute the names of all the known ancestors in this ancestor tree
  // (including this ancestor tree itself)
  ILoString ancNames();

  // To compute this ancestor tree's youngest grandparent
  IAT youngestGrandparent();
}

class Unknown implements IAT {
  Unknown() {
  }

  // To compute the number of known ancestors of this Unknown (excluding this
  // Unknown itself)
  public int count() {
    return 0;
  }

  // To compute the number of known ancestors of this Unknown (*including* this
  // Unknown itself)
  public int countHelp() {
    return 0;
  }

  // To compute how many ancestors of this Unknown (excluding this Unknown itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40() {
    return 0;
  }

  // To compute how many ancestors of this Unknown (*including* this Unknown
  // itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40Help() {
    return 0;
  }

  // Counts how many generations are completely known
  public int numTotalGens() {
    return 0;
  }

  // Counts how many generations are at least partially known
  public int numPartialGens() {
    return 0;
  }

  // To determine if this Unknown is well-formed
  public boolean wellFormed() {
    return true;
  }

  // To determine if this Unknown is older than the given year of birth,
  // and its parents are well-formed
  public boolean wellFormedHelp(int childYob) {
    return true;
  }
}

class Person implements IAT {
  String name;
  int yob;
  boolean isMale;
  IAT mom;
  IAT dad;

  Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
    this.name = name;
    this.yob = yob;
    this.isMale = isMale;
    this.mom = mom;
    this.dad = dad;
  }

  // To compute the number of known ancestors of this Person (excluding this
  // Person itself)
  public int count() {
    return this.mom.countHelp() + this.dad.countHelp();
  }

  // To compute the number of known ancestors of this Person (*including* this
  // Person itself)
  public int countHelp() {
    return 1 + this.mom.countHelp() + this.dad.countHelp();
  }

  // To compute how many ancestors of this Person (excluding this Person itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40() {
    return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
  }

  // To compute how many ancestors of this Person (*including* this Person itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40Help() {
    if (2015 - this.yob > 40 && !this.isMale) {
      return 1 + this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }
    else {
      return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }
  }

  // Counts how many generations are completely known
  public int numTotalGens() {
    return 1 + Math.min(this.mom.numTotalGens(), this.dad.numTotalGens());
  }

  // Counts how many generations are at least partially known
  public int numPartialGens() {
    return 1 + Math.max(this.mom.numPartialGens(), this.dad.numPartialGens());
  }

  // To determine if this Person is older than the given year of birth,
  // and its parents are well-formed
  public boolean wellFormedHelp(int childYob) {
    return this.yob <= childYob && this.mom.wellFormedHelp(this.yob)
        && this.dad.wellFormedHelp(this.yob);
  }

  // To determine if this Person is well-formed: is it younger than its parents,
  // and are its parents are well-formed
  public boolean wellFormed() {
    return this.mom.wellFormedHelp(this.yob) && this.dad.wellFormedHelp(this.yob);
  }
}

interface ILoString {

}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
}

class MtLoString implements ILoString {
  MtLoString() {
  }
}

class ExamplesIAT {
  IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
  IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
  IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
  IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());

  IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
  IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
  IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
  IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
  IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
  IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());

  IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
  IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
  IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
  IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());

  IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
  IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);

  IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);

  boolean testCount(Tester t) {
    return t.checkExpect(this.andrew.count(), 16) && t.checkExpect(this.david.count(), 1)
        && t.checkExpect(this.enid.count(), 0) && t.checkExpect(new Unknown().count(), 0);
  }

  boolean testFemaleAncOver40(Tester t) {
    return t.checkExpect(this.andrew.femaleAncOver40(), 7)
        && t.checkExpect(this.bree.femaleAncOver40(), 3)
        && t.checkExpect(this.darcy.femaleAncOver40(), 1)
        && t.checkExpect(this.enid.femaleAncOver40(), 0)
        && t.checkExpect(new Unknown().femaleAncOver40(), 0);
  }

  boolean testWellFormed(Tester t) {
    return t.checkExpect(this.andrew.wellFormed(), true)
        && t.checkExpect(new Unknown().wellFormed(), true) && t.checkExpect(
            new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(), false);
  }

  boolean testAncNames(Tester t) {
    return t.checkExpect(this.david.ancNames(),
        new ConsLoString("David", new ConsLoString("Edward", new MtLoString())))
        && t.checkExpect(this.eustace.ancNames(), new ConsLoString("Eustace", new MtLoString()))
        && t.checkExpect(new Unknown().ancNames(), new MtLoString());
  }

  boolean testYoungestGrandparent(Tester t) {
    return t.checkExpect(this.emma.youngestGrandparent(), new Unknown())
        && t.checkExpect(this.david.youngestGrandparent(), new Unknown())
        && t.checkExpect(this.claire.youngestGrandparent(), this.eustace)
        && t.checkExpect(this.bree.youngestGrandparent(), this.dixon)
        && t.checkExpect(this.andrew.youngestGrandparent(), this.candace)
        && t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
  }

  boolean testNumGens(Tester t) {
    return t.checkExpect(this.andrew.numTotalGens(), 3)
        && t.checkExpect(this.andrew.numPartialGens(), 5)
        && t.checkExpect(this.enid.numTotalGens(), 1)
        && t.checkExpect(this.enid.numPartialGens(), 1)
        && t.checkExpect(new Unknown().numTotalGens(), 0)
        && t.checkExpect(new Unknown().numPartialGens(), 0);
  }
}