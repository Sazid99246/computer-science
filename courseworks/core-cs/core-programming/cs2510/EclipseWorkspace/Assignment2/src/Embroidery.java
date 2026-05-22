import tester.Tester;

/*
+----------------------------+
| EmbroideryPiece            |
+----------------------------+
| String name                |
| IMotif motif               |--------------+
+----------------------------+              |
| double averageDifficulty() |              |
| String embroideryInfo()    |              |
+----------------------------+              |
                                            v
                                   +--------------------------+
                                   | IMotif                   |
                                   +--------------------------+
                                   | int countStitches()      |
                                   | double totalDifficulty() |
                                   | String getStitchInfo()   |
                                   +--------------------------+
                                                |
               ---------------------------------------------------
               |                        |                        |
 +--------------------------+   +--------------------------+   +--------------------------+
 | CrossStitchMotif         |   | ChainStitchMotif         |   | GroupMotif               |
 +--------------------------+   +--------------------------+   +--------------------------+
 | String description       |   | String description       |   | String description       |
 | double difficulty        |   | double difficulty        |   | ILoMotif motifs          |-----+
 +--------------------------+   +--------------------------+   +--------------------------+     |
 | int countStitches()      |   | int countStitches()      |   | int countStitches()      |     |
 | double totalDifficulty() |   | double totalDifficulty() |   | double totalDifficulty() |     |
 | String getStitchInfo()   |   | String getStitchInfo()   |   | String getStitchInfo()   |     |
 +--------------------------+   +--------------------------+   +--------------------------+     |
                                                                                                |
                                                                                                v
                                                                                  +--------------------------+
                                                                                  | ILoMotif                 |
                                                                                  +--------------------------+
                                                                                  | int countStitches()      |
                                                                                  | double totalDifficulty() |
                                                                                  | String getStitchInfo()   |
                                                                                  | boolean isEmpty()        |
                                                                                  +--------------------------+
                                                                                                |
                                                                                    --------------------------
                                                                                    |                        |
                                                                      +--------------------------+   +--------------------------+
                                                                      | MtLoMotif                |   | ConsLoMotif              |
                                                                      +--------------------------+   +--------------------------+
                                                                      |                          |   | IMotif first             |
                                                                      +--------------------------+   | ILoMotif rest            |
                                                                      | int countStitches()      |   +--------------------------+
                                                                      | double totalDifficulty() |   | int countStitches()      |
                                                                      | String getStitchInfo()   |   | double totalDifficulty() |
                                                                      | boolean isEmpty()        |   | String getStitchInfo()   |
                                                                      +--------------------------+   | boolean isEmpty()        |
                                                                                                     +--------------------------+
*/

// represents an embroidary piece
class EmbroideryPiece {
  String name;
  IMotif motif;

  // constructor
  EmbroideryPiece(String name, IMotif motif) {
    this.name = name;
    this.motif = motif;
  }

  // Computes the average difficulty of all cross/chain stitch motifs
  double averageDifficulty() {
    int totalStitches = this.motif.countStitches();
    if (totalStitches == 0) {
      return 0.0; // Avoid division by zero if a piece has no stitches
    }
    else {
      return this.motif.totalDifficulty() / totalStitches;
    }
  }

  // Captures the complete structural string for the piece, dynamically prefixes
  // the
  // item name, cleans empty structures, and caps the result with a trailing
  // period.
  String embroideryInfo() {
    String insideInfo = this.motif.getStitchInfo();

    // If there are no stitches at all, just return the name and a period
    if (insideInfo.equals("")) {
      return this.name + ".";
    }

    // Otherwise, combine the name with the inner info and cap it with a period
    return this.name + ": " + insideInfo + ".";
  }
}

// to represent a motif
interface IMotif {
  // Counts how many single stitches (cross/chain) are in this motif
  int countStitches();

  // Sums up the difficulty values of all single stitches in this motif
  double totalDifficulty();

  // Traverses this individual motif tree branch using structural recursion
  // to build a plain text description of its localized identity or sub-elements.
  String getStitchInfo();
}

// to represent a cross stitch motif
class CrossStitchMotif implements IMotif {
  String description;
  double difficulty;

  // constructor
  CrossStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  // Counts this specific atomic unit as a single standalone stitch
  public int countStitches() {
    return 1;
  }

  // Returns this independent element's numeric difficulty factor
  public double totalDifficulty() {
    return this.difficulty;
  }

  // Formats this instance explicitly labeling its underlying cross stitch type
  // mapping
  public String getStitchInfo() {
    return this.description + " (cross stitch)";
  }
}

// to represent a chain stich motif
class ChainStitchMotif implements IMotif {
  String description;
  double difficulty;

  // constructor
  ChainStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }

  // Counts this specific atomic unit as a single standalone stitch
  public int countStitches() {
    return 1;
  }

  // Returns this independent element's numeric difficulty factor
  public double totalDifficulty() {
    return this.difficulty;
  }

  // Formats this instance explicitly labeling its underlying chain stitch type
  // mapping
  public String getStitchInfo() {
    return this.description + " (chain stitch)";
  }
}

// to represent a group of motifs
class GroupMotif implements IMotif {
  String description;
  ILoMotif motifs;

  // constructor
  GroupMotif(String description, ILoMotif motifs) {
    this.description = description;
    this.motifs = motifs;
  }

  // Defers counting processing downwards directly onto its nested list collection
  // variant
  public int countStitches() {
    return this.motifs.countStitches();
  }

  // Defers summation calculation downwards directly onto its nested list
  // collection variant
  public double totalDifficulty() {
    return this.motifs.totalDifficulty();
  }

  // Recursively extracts structural label data out of its contained list
  // composite elements
  public String getStitchInfo() {
    return this.motifs.getStitchInfo();
  }
}

// to represent a list of motifs
interface ILoMotif {
  // Counts the stitches across a whole list of motifs
  int countStitches();

  // Sums the difficulty across a whole list of motifs
  double totalDifficulty();

  // Collates formatting text recursively from each element embedded across the
  // collection
  String getStitchInfo();

  // Determines state properties checking if the targeted structural component
  // list is empty
  boolean isEmpty();
}

// to represent an empty list of motif
class MtLoMotif implements ILoMotif {
  // constructor
  MtLoMotif() {
  }

  // Evaluates an empty terminal sequence containing zero active stitches
  public int countStitches() {
    return 0;
  }

  // Evaluates an empty terminal sequence containing zero computational weight
  public double totalDifficulty() {
    return 0.0;
  }

  // Halts recursive generation parsing by matching a blank baseline signature
  // string
  public String getStitchInfo() {
    return "";
  }

  // Validates the identity status confirmation representing a functional base
  // case
  public boolean isEmpty() {
    return true;
  }
}

// to represent a non-empty list of motif
class ConsLoMotif implements ILoMotif {
  IMotif first;
  ILoMotif rest;

  // constructor
  ConsLoMotif(IMotif first, ILoMotif rest) {
    this.first = first;
    this.rest = rest;
  }

  // Aggregates matching tallies grouping current target items with ongoing tail
  // lists
  public int countStitches() {
    return this.first.countStitches() + this.rest.countStitches();
  }

  // Aggregates matching value limits pooling current target items with ongoing
  // tail lists
  public double totalDifficulty() {
    return this.first.totalDifficulty() + this.rest.totalDifficulty();
  }

  // Rejects identity validation checking flags on active items
  public boolean isEmpty() {
    return false;
  }

  // Processes compound serialization blocks by contextually ignoring empty nodes
  // or
  // formatting sequential contents using accurate comma delimiters dynamically.
  public String getStitchInfo() {
    String firstInfo = this.first.getStitchInfo();
    String restInfo = this.rest.getStitchInfo();

    if (firstInfo.equals("")) {
      return restInfo;
    }
    if (restInfo.equals("")) {
      return firstInfo;
    }
    return firstInfo + ", " + restInfo;
  }
}

// Examples and tests
class ExamplesEmbroidery {
  ExamplesEmbroidery() {
  }

  IMotif singleCross = new CrossStitchMotif("bird", 4.5);
  IMotif singleChain = new ChainStitchMotif("tree", 3.0);

  IMotif emptyGroup = new GroupMotif("empty", new MtLoMotif());
  EmbroideryPiece emptyPiece = new EmbroideryPiece("Blank Canvas", this.emptyGroup);

  EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover",
      new GroupMotif("nature",
          new ConsLoMotif(new CrossStitchMotif("bird", 4.5),
              new ConsLoMotif(new ChainStitchMotif("tree", 3.0),
                  new ConsLoMotif(
                      new GroupMotif("flowers",
                          new ConsLoMotif(new CrossStitchMotif("rose", 5.0),
                              new ConsLoMotif(new ChainStitchMotif("poppy", 4.75), new ConsLoMotif(
                                  new CrossStitchMotif("daisy", 3.2), new MtLoMotif())))),
                      new MtLoMotif())))));

  // Asserves that structural units calculate true element tallies appropriately
  boolean testCountStitches(Tester t) {
    return t.checkExpect(this.singleCross.countStitches(), 1)
        && t.checkExpect(this.singleChain.countStitches(), 1)
        && t.checkExpect(this.emptyGroup.countStitches(), 0)
        && t.checkExpect(this.pillowCover.motif.countStitches(), 5);
  }

  // Asserves math calculations compute accurate sum totals for composite trees
  boolean testTotalDifficulty(Tester t) {
    return t.checkInexact(this.singleCross.totalDifficulty(), 4.5, 0.001)
        && t.checkInexact(this.emptyGroup.totalDifficulty(), 0.0, 0.001)
        // 4.5 + 3.0 + 5.0 + 4.75 + 3.2 = 20.45
        && t.checkInexact(this.pillowCover.motif.totalDifficulty(), 20.45, 0.001);
  }

  // Asserves complete arithmetic tracking rules calculate balanced averages
  // safely
  boolean testAverageDifficulty(Tester t) {
    return t.checkInexact(this.emptyPiece.averageDifficulty(), 0.0, 0.001)
        && t.checkInexact(new EmbroideryPiece("Just Bird", this.singleCross).averageDifficulty(),
            4.5, 0.001)
        && t.checkInexact(this.pillowCover.averageDifficulty(), 4.09, 0.001);
  }

  // Asserves serialization engines print completely structured string files
  // accurately
  boolean testEmbroideryInfo(Tester t) {
    return t.checkExpect(this.emptyPiece.embroideryInfo(), "Blank Canvas.")
        && t.checkExpect(new EmbroideryPiece("Just Tree", this.singleChain).embroideryInfo(),
            "Just Tree: tree (chain stitch).")
        && t.checkExpect(this.pillowCover.embroideryInfo(),
            "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).");
  }
}