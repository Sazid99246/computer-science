/*
                               +-----------------+
                               | Housing         |
                               +-----------------+
                               +-----------------+
                                        |
                                       / \
                                        |
                  ---------------------------------------------------
                  |                     |                           |
         +----------------+     +----------------+     +----------------------+
         | Hut            |     | Inn            |     | Castle               |
         +----------------+     +----------------+     +----------------------+
         | int capacity   |     | String name    |     | String name          |
         | int population |     | int capacity   |     | String familyName    |
         +----------------+     | int population |     | int population       |
                                | int stable     |     | int carriageCapacity |
                                +----------------+     +----------------------+
   */

interface Housing { }

class Hut implements Housing {
  int capacity;
  int population;
  
  // constructor
  Hut(int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
  }
}

class Inn implements Housing {
  String name;
  int capacity;
  int population;
  int stable;
  
  // constructor
  Inn(String name, int capacity, int population, int stable) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stable = stable;
  }
}

// represents a castle
class Castle implements Housing {
  String name;
  String familyName;
  int population;
  int carriageCapacity;
  
  // constructor
  Castle(String name, String familyName, int population, int carriageCapacity) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageCapacity = carriageCapacity;
  }
}

/*
                    +----------------+
                    | Transportation |
                    +----------------+
                    +----------------+
                            |
                           / \
                            |
               -------------------------
               |                        |
      +---------------+         +---------------+
      | Horse         |         | Carriage      |
      +---------------+         +---------------+
      | String name   |         | int tonnage   |
      | String color  |         | Housing from  |
      | Housing from  |         | Housing to    |
      | Housing to    |         +---------------+
      +---------------+
 */

// represents a transportation
interface Transportation { }

// represents a horse
class Horse implements Transportation {
  String name;
  String color;
  Housing from;
  Housing to;
  
  // constructor
  Horse(String name, String color, Housing from, Housing to) {
    this.name = name;
    this.color = color;
    this.from = from;
    this.to = to;
  }
}

// represents a Carriage
class Carriage implements Transportation {
  int tonnage;
  Housing from;
  Housing to;
  
  // constructor
  Carriage(int tonnage, Housing from, Housing to) {
    this.tonnage = tonnage;
    this.from = from;
    this.to = to;
  }
}


// examples and tests
class ExamplesTravel {
  // examples of Housing
  Housing hut1 = new Hut(5, 1);
  Housing hut2 = new Hut(8, 2);

  Housing inn1 = new Inn("Inn At The Crossroads", 40, 20, 12);
  Housing inn2 = new Inn("Inn At The Wedding", 100, 80, 10);
  
  Housing castle1 = new Castle("Winterfell", "Stark", 500, 6);
  Housing castle2 = new Castle("Eilean Donan", "MacRae", 90, 9);
  
  Transportation horse1 = new Horse("Pony", "Palomino", inn1, castle1);
  Transportation horse2 = new Horse("AQHA Gelding", "Palomino", hut1, castle2);
  
  Transportation carriage1 = new Carriage(8, castle1, inn1);
  Transportation carriage2 = new Carriage(12, castle2, inn2);
}