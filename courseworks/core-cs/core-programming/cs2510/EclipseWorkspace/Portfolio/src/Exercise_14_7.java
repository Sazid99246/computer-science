import Tester.*;

/**
                     +-----------------+
 *                   | IGroceryItem    |
 *                   +-----------------+
 *                   +-----------------+
 *                           |
 *                          / \
 *                          ---
 *                           |
 *             ------------------------------------------------------------------------------------------------
 *             |                                               |                                               |
 *   +-----------------------------------+     +-----------------------------------+     +-----------------------------------+
 *   | IceCream                          |     | Coffee                            |     | Juice                             |
 *   +-----------------------------------+     +-----------------------------------+     +-----------------------------------+
 *   | String brand                      |     | String brand                      |     | String brand                      |
 *   | int weight                        |     | int weight                        |     | int weight                        |
 *   | int price                         |     | int price                         |     | int price                         |
 *   | String flavor                     |     | boolean isDecaf                   |     | String flavor                     |
 *   +-----------------------------------+     +-----------------------------------+     | String packageType                |
 *   | double unitPrice()                |     | double unitPrice()                |     +-----------------------------------+
 *   | boolean lowerUnitPrice(double)    |     | boolean lowerUnitPrice(double)    |     | double unitPrice()                |
 *   | boolean cheaperThan(IGroceryItem) |     | boolean cheaperThan(IGroceryItem) |     | boolean lowerUnitPrice(double)    |
 *   +-----------------------------------+     +-----------------------------------+     | boolean cheaperThan(IGroceryItem) |
 *                                                                                       +-----------------------------------+
 */

// Represents an item in a grocery store
interface IGroceryItem {
  // computes the unit price (cents per gram) of this item
  double unitPrice();

  // is this item's unit price lower than the given amount?
  boolean lowerUnitPrice(double amount);

  // is this item's unit price lower than the given item's unit price?
  boolean cheaperThan(IGroceryItem that);
};


// to represent an ice-cream
class IceCream implements IGroceryItem {
  String brand;
  int weight; // in gram
  int price; // in cents
  String flavor;
  
  // constructor
  IceCream(String brand, int weight, int price, String flavor) {
    this.brand = brand;
    this.weight = weight;
    this.price = price;
    this.flavor = flavor;
  }

  // computes the unit price (cents per gram) of an ice-cream.
  public double unitPrice() {
    return (double) this.price / this.weight;
  }

  // determines whether amount is less than unit price of an ice-cream.
  public boolean lowerUnitPrice(double amount) {
    return this.unitPrice() < amount;
  }

  // determines whether a grocery item's unit price is cheaper than ice-cream's unit price
  public boolean cheaperThan(IGroceryItem that) {
    return this.unitPrice() < that.unitPrice();
  }
}

// to represent a coffee
class Coffee implements IGroceryItem {
  String brand;
  int weight; // in gram
  int price; // in cents
  String flavor;
  boolean isDecaf;
  
  // constructor
  Coffee(String brand, int weight, int price, boolean isDecaf) {
    this.brand = brand;
    this.weight = weight;
    this.price = price;
    this.isDecaf = isDecaf;
  }

//computes the unit price (cents per gram) of a coffee.
 public double unitPrice() {
   return (double) this.price / this.weight;
 }

 // determines whether amount is less than unit price of a coffee.
 public boolean lowerUnitPrice(double amount) {
   return this.unitPrice() < amount;
 }

 // determines whether a grocery item's unit price is cheaper than coffee's unit price.
 public boolean cheaperThan(IGroceryItem that) {
   return this.unitPrice() < that.unitPrice();
 }
}

// to represent a juice
class Juice implements IGroceryItem {
  String brand;
  int weight; // in gram
  int price; // in cents
  String flavor;
  String packageType;
  
  // constructor
  Juice(String brand, int weight, int price, String flavor, String packageType) {
    this.brand = brand;
    this.weight = weight;
    this.price = price;
    this.flavor = flavor;
    this.packageType = packageType;
  }

//computes the unit price (cents per gram) of a juice.
 public double unitPrice() {
   return (double) this.price / this.weight;
 }

 // determines whether amount is less than unit price of a juice.
 public boolean lowerUnitPrice(double amount) {
   return this.unitPrice() < amount;
 }

 // determines whether a grocery item's unit price is cheaper than juice's unit price.
 public boolean cheaperThan(IGroceryItem that) {
   return this.unitPrice() < that.unitPrice();
 }
}

// for tests and examples
class ExamplesIGroceryItem {
  IGroceryItem ice1 = new IceCream("BrandA", 100, 200, "vanilla");
  IGroceryItem ice2 = new IceCream("Brand2", 200, 300, "chocolate");
  
  IGroceryItem coffee1 = new Coffee("BrandC", 100, 150, true);
  IGroceryItem coffee2 = new Coffee("BrandD", 50, 200, false);
  
  IGroceryItem juice1 = new Juice("Brand5", 250, 500, "apple", "bottled");
  IGroceryItem juice2 = new Juice("Brand6", 100, 120, "orange", "fresh");
}