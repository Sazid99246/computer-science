/* 
                                +-------+
                                | IMeal |
                                +-------+
                                +-------+
                                    |
                                   / \
                                   ---
                                    |
         --------------------------------------------------------           
         |                          |                           |           
  +----------------------+    +----------------------+    +-----------------+
  | Soup                 |    | Salad                |    | Sandwich        |
  +----------------------+    +----------------------+    +-----------------+
  | String name          |    | String name          |    | String name     |
  | int price            |    | int price            |    | int price       |
  | boolean isVegetarian |    | boolean isVegetarian |    | String bread    |
  +----------------------+    | String dressing      |    | String filling1 |
                              +----------------------+    | String filling2 |
                                                          +-----------------+
 */

// to represent a meal
interface IMeal { }

// to represent a soup
class Soup implements IMeal {
  String name;
  int price;
  boolean isVegetarian;
  
  //  constructor
  Soup(String name, int price, boolean isVegetarian) {
    this.name = name;
    this.price = price;
    this.isVegetarian = isVegetarian;
  }
}

// to represent a Salad
class Salad implements IMeal {
  String name;
  int price;
  boolean isVegetarian;
  String dressing;
  
  //  constructor
  Salad(String name, int price, boolean isVegetarian, String dressing) {
    this.name = name;
    this.price = price;
    this.isVegetarian = isVegetarian;
    this.dressing = dressing;
  }
}

// to represent a Sandwich
class Sandwich implements IMeal {
  String name;
  int price;
  String bread;
  String filling1;
  String filling2;
  
  // constructor
  Sandwich(String name, int price, String bread, String filling1, String filling2) {
    this.name = name;
    this.price = price;
    this.bread = bread;
    this.filling1 = filling1;
    this.filling2 = filling2;
  }
}

// examples and tests for Meal
class ExamplesMeal {
  ExamplesMeal() { }

  // examples for soup
  Soup s1 = new Soup("Tomato Soup", 250, true);
  Soup s2 = new Soup("Chicken Corn Soup", 300, false);
  
  //  examples for salad
  Salad sa1 = new Salad("Caesar Salad", 450, false, "Caesar Dressing");
  Salad sa2 = new Salad("Greek Salad", 400, true, "Olive Oil Dressing");
  
  // examples for sandwich
  Sandwich sand1 = new Sandwich("PBJ Sandwich", 300, "White Bread", "Peanut Butter", "Jelly");
  Sandwich sand2 = new Sandwich("Ham Cheese Sandwich", 450, "Wheat Bread", "Ham", "Cheese");
}