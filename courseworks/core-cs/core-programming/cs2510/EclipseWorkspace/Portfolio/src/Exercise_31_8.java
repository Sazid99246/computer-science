// Common comparison interface
interface IComp {
  boolean lessThan(Object other);
}

class Apple implements IComp {
  int weight; // in grams
  int ripeness; // scale 1-10

  Apple(int weight, int ripeness) {
    this.weight = weight;
    this.ripeness = ripeness;
  }

  public boolean lessThan(Object other) {
    if (!(other instanceof Apple)) {
      return false; // Returns false if it's the wrong kind of class
    }
    Apple that = (Apple) other;
    return this.weight < that.weight; // Primary comparison strategy
  }
}

class Orange implements IComp {
  int weight;
  int ripeness;

  Orange(int weight, int ripeness) {
    this.weight = weight;
    this.ripeness = ripeness;
  }

  public boolean lessThan(Object other) {
    if (!(other instanceof Orange)) {
      return false;
    }
    Orange that = (Orange) other;
    return this.weight < that.weight;
  }
}