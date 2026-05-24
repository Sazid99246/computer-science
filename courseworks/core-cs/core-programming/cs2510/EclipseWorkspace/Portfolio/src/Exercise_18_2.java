/**
                             +------------+
                             | IZooAnimal |
                             +------------+
                                    |
                                   / \
                                   ---
                                    |
                             +-------------+
                             |   AAnimal   |
                             +-------------+
                             | String name |
                             | int weight  |
                             +-------------+
                                   |
                                  / \
                                  ---
                                   |
            -----------------------------------------------
            |                      |                      |
      +-----------+         +------------+         +-------------+
      | Lion      |         | Snake      |         | Monkey      |
      +-----------+         +------------+         +-------------+
      | int meat  |         | int length |         | String food |
      +-----------+         +------------+         +-------------+
*/

interface IZooAnimal { }

abstract class AAnimal implements IZooAnimal {
  String name;
  int weight;
  
  AAnimal(String name, int weight) {
    this.name = name;
    this.weight = weight;
  }
}

class Lion extends AAnimal {
  int meat;
  
  Lion(String name, int weight, int meat) {
    super(name, weight);
    
    this.meat = meat;
  }
}

class Snake extends AAnimal {
  int length;
  
  Snake(String name, int weight, int length) {
    super(name, weight);
    
    this.length = length;
  }
}

class Monkey extends AAnimal {
  String food;

  Monkey(String name, int weight, String food) {
    super(name, weight);
    
    this.food = food;
  }
  
}