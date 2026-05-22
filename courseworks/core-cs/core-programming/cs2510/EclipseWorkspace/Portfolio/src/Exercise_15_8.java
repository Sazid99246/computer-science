// ========================================================
// Complete Solution for Exercise 15.8 - River Systems
// One self-contained file
// ========================================================

// Location on the map
class Location {
  int x;
  int y;
  String name;

  Location(int x, int y, String name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }

  // Helper: check if two locations are the same (by coordinates)
  boolean same(Location other) {
    return this.x == other.x && this.y == other.y;
  }

  public String toString() {
    return name + "(" + x + "," + y + ")";
  }
}

// River system interface
interface IRiver {
  int maxLength();

  int confluences();

  ILocList locations();
}

// Source of a river (leaf)
class Source implements IRiver {
  Location loc;

  Source(Location loc) {
    this.loc = loc;
  }

  public int maxLength() {
    return 0; // No segment length in basic model, or you can add miles if needed
  }

  public int confluences() {
    return 0;
  }

  public ILocList locations() {
    return new ConsLocList(this.loc, new MTLocList());
  }
}

// Confluence of two rivers
class Confluence implements IRiver {
  Location loc;
  IRiver left;
  IRiver right;

  Confluence(Location loc, IRiver left, IRiver right) {
    this.loc = loc;
    this.left = left;
    this.right = right;
  }

  public int maxLength() {
    return Math.max(this.left.maxLength(), this.right.maxLength());
  }

  public int confluences() {
    return 1 + this.left.confluences() + this.right.confluences();
  }

  public ILocList locations() {
    return new ConsLocList(this.loc, this.left.locations().append(this.right.locations()));
  }
}

// Mouth of the entire river system
class Mouth {
  Location loc;
  IRiver river;

  Mouth(Location loc, IRiver river) {
    this.loc = loc;
    this.river = river;
  }

  // 1. Length of the longest path through the river system
  int maxLength() {
    return this.river.maxLength();
  }

  // 2. Number of confluences in the river system
  int confluences() {
    return this.river.confluences();
  }

  // 3. List of all locations (mouth + all on the river)
  ILocList locations() {
    return new ConsLocList(this.loc, this.river.locations());
  }
}

// ========================================================
// Simple List of Locations (needed for locations() method)
// ========================================================

interface ILocList {
  ILocList append(ILocList other);
}

class MTLocList implements ILocList {
  public ILocList append(ILocList other) {
    return other;
  }
}

class ConsLocList implements ILocList {
  Location first;
  ILocList rest;

  ConsLocList(Location first, ILocList rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILocList append(ILocList other) {
    return new ConsLocList(this.first, this.rest.append(other));
  }
}

// ========================================================
// Examples and Tests
// ========================================================

class RiverExamples {
  // Locations
  Location lm = new Location(0, 0, "Mouth");
  Location la = new Location(1, 1, "A");
  Location lb = new Location(2, 2, "B");
  Location ls = new Location(3, 3, "S");
  Location lt = new Location(4, 4, "T");
  Location lu = new Location(5, 5, "U");

  // Rivers
  IRiver s = new Source(ls);
  IRiver t = new Source(lt);
  IRiver u = new Source(lu);

  IRiver b = new Confluence(lb, s, t);
  IRiver a = new Confluence(la, b, u);

  RiverExamples() {
  }
}

// Run with: java -cp . RiverExamples (or use ProfessorJ / IDE)