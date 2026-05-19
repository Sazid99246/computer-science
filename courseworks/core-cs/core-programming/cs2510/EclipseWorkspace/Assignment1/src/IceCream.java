/*
                      +-----------+
                      |  IceCream |-------------------+
                      +-----------+                   |
                      +-----------+                   |
                            |                         |
                           / \                        |
                           ---                        |
                            |                         |
               -------------------------              |
               |                        |             |
       +--------------+        +---------------+      |
       | EmptyServing |        | Scooped       |      |
       +--------------+        +---------------+      |
       | Boolean cone |        | IceCream rest |-------
       +--------------+        | String flavor |
                               +---------------+
 */

// to represent an ice-ceram
interface IceCream { }

// to represent an empty serving ice-cream
class EmptyServing implements IceCream {
  boolean cone;
  
  // constructor
  EmptyServing(boolean cone) {
    this.cone = cone;
  }
}


// to represent a scooped ice cream
class Scooped implements IceCream {
  IceCream rest;
  String flavor;
  
  // constructor
  Scooped(IceCream rest, String flavor) {
    this.rest = rest;
    this.flavor = flavor;
  }
}

// for examples and tests
class ExamplesIceCream {
  IceCream order1 = 
      new Scooped(
          new Scooped(
              new Scooped(
                  new Scooped(
                      new EmptyServing(false), 
                      "mint chip"), 
                  "coffee"), 
              "black raspberry"), 
          "caramel swirl");
  IceCream order2 = 
      new Scooped(
          new Scooped(
              new Scooped(
                  new EmptyServing(true), 
                  "chocolate"), 
              "vanilla"), 
          "strawberry");
}