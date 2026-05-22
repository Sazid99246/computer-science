/*
  +-----------------+
  | EmbroideryPiece |
  +-----------------+
  | String name     |
  | IMotif motif    |----------------------
  +-----------------+                     |
                                          v
                                 +---------------+
                                 | IMotif        |
                                 +---------------+
                                          |
                 ---------------------------------------------------
                 |                        |                        |
       +--------------------+   +--------------------+   +--------------------+
       | CrossStitchMotif   |   | ChainStitchMotif   |   | GroupMotif         |
       +--------------------+   +--------------------+   +--------------------+
       | String description |   | String description |   | String description |
       | double difficulty  |   | double difficulty  |   | ILoMotif motifs    |----------------
       +--------------------+   +--------------------+   +--------------------+               |
                                                                                              v
                                                                                      +-------------+
                                                                                      | ILoMotif    |
                                                                                      +-------------+
                                                                                             |
                                                                                      ---------------
                                                                                      |             |
                                                                                +-----------+  +---------------+
                                                                                | MtLoMotif |  | ConsLoMotif   |
                                                                                +-----------+  +---------------+
                                                                                |           |  | IMotif first  |
                                                                                +-----------+  | ILoMotif rest |
                                                                                               +---------------+
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
}

// to represent a motif
interface IMotif {
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
}

// to represent a list of motifs
interface ILoMotif {
}

// to represent an empty list of motif
class MtLoMotif implements ILoMotif {
  // constructor
  MtLoMotif() {
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
}

class ExamplesEmbroidery {
  ExamplesEmbroidery() {
  }

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
}