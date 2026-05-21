import tester.*;

// Represents a mode of transportation
interface IMOT {

  // Returns true if this mode of transportation is at least
  // as fuel efficient as the given mode of transportation
  boolean isMoreFuelEfficientThan(IMOT mot);

  // Returns the fuel efficiency (miles per gallon)
  // of this mode of transportation
  int getMPG();
}

// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
  String brand;

  // Constructor
  Bicycle(String brand) {
    this.brand = brand;
  }

  // Returns a very large mpg value because bicycles
  // do not use fuel and are considered more efficient
  // than cars
  public int getMPG() {
    return 1000;
  }

  // Compares this bicycle's fuel efficiency with
  // another mode of transportation
  public boolean isMoreFuelEfficientThan(IMOT mot) {
    return this.getMPG() >= mot.getMPG();
  }
}

// Represents a car as a mode of transportation
class Car implements IMOT {
  String make;
  int mpg;

  // Constructor
  Car(String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }

  // Returns this car's fuel efficiency
  public int getMPG() {
    return this.mpg;
  }

  // Compares this car's fuel efficiency with
  // another mode of transportation
  public boolean isMoreFuelEfficientThan(IMOT mot) {
    return this.getMPG() >= mot.getMPG();
  }
}

// Represents a person and their mode of transportation
class Person {
  String name;
  IMOT mot;

  // Constructor
  Person(String name, IMOT mot) {
    this.name = name;
    this.mot = mot;
  }

  // Returns true if this person's mode of transportation
  // is at least as fuel efficient as the given mode of transportation
  boolean motIsMoreFuelEfficientThan(IMOT mot) {
    return this.mot.isMoreFuelEfficientThan(mot);
  }
}

// Examples and tests
class ExamplesPerson {

  // Example modes of transportation
  IMOT diamondback = new Bicycle("Diamondback");
  IMOT toyota = new Car("Toyota", 30);
  IMOT lamborghini = new Car("Lamborghini", 17);

  // Example people
  Person bob = new Person("Bob", diamondback);
  Person ben = new Person("Ben", toyota);
  Person becca = new Person("Becca", lamborghini);

  // Tests for comparing transportation fuel efficiency
  boolean testMotComparison(Tester t) {

    return t.checkExpect(bob.motIsMoreFuelEfficientThan(toyota), true)
        && t.checkExpect(ben.motIsMoreFuelEfficientThan(lamborghini), true)
        && t.checkExpect(becca.motIsMoreFuelEfficientThan(toyota), false);
  }
}