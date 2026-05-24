import tester.*;

// to represent any game piece in the 2048 grid matrix
interface IGamePiece {
  // returns the numeric score value of this game piece
  int getValue();

  // combines this game piece with another game piece to form a new merge tile
  IGamePiece merge(IGamePiece that);

  // determines if this piece was built following the strict 2048 rule:
  // tiles can only merge if their component sub-tiles hold identical values.
  boolean isValid();
}

// to represent a single, unmerged starter tile
class BaseTile implements IGamePiece {
  int value; // always a positive integer (e.g., 2, 4)

  BaseTile(int value) {
    this.value = value;
  }

  // returns the raw base value directly
  public int getValue() {
    return this.value;
  }

  // creates a brand new merge tile combining this tile and 'that' tile
  public IGamePiece merge(IGamePiece that) {
    return new MergeTile(this, that);
  }

  // a single base tile is always valid on its own vacuously
  public boolean isValid() {
    return true;
  }
}

// to represent a tile created by combining two smaller tiles together
class MergeTile implements IGamePiece {
  IGamePiece piece1; // the left component tile
  IGamePiece piece2; // the right component tile

  MergeTile(IGamePiece piece1, IGamePiece piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }

  // the value of a merged tile is recursively calculated as the sum of its pieces
  public int getValue() {
    return this.piece1.getValue() + this.piece2.getValue();
  }

  // creates a brand new merge tile using this merged configuration as one side
  public IGamePiece merge(IGamePiece that) {
    return new MergeTile(this, that);
  }

  // checks if this merge is valid (piece1 value == piece2 value)
  // AND recursively checks if all underlying component tiles are also valid
  public boolean isValid() {
    return this.piece1.getValue() == this.piece2.getValue() && this.piece1.isValid()
        && this.piece2.isValid();
  }
}

// to represent test suites and sample game states
class Examples2048 {
  // Base tiles
  IGamePiece tile2 = new BaseTile(2);
  IGamePiece tile2b = new BaseTile(2);
  IGamePiece tile4 = new BaseTile(4);
  IGamePiece tile8 = new BaseTile(8);

  // Valid merged tiles
  // 2 + 2 = 4
  IGamePiece valid4 = new MergeTile(this.tile2, this.tile2b);
  // 4 + 4 = 8
  IGamePiece valid8 = new MergeTile(this.valid4, this.tile4);

  // Invalid merged tiles
  // Merging a 2 with a 4 directly violates 2048 rules
  IGamePiece invalidMerge = new MergeTile(this.tile2, this.tile4);
  // A tile where the top level matches (4 == 4), but a nested component down
  // below was illegal
  IGamePiece invalidNested = new MergeTile(this.invalidMerge, this.valid4);

  // test the getValue method across different structures
  boolean testGetValue(Tester t) {
    return t.checkExpect(this.tile2.getValue(), 2) && t.checkExpect(this.valid4.getValue(), 4)
        && t.checkExpect(this.valid8.getValue(), 8)
        && t.checkExpect(this.invalidMerge.getValue(), 6);
  }

  // test the merge method operation mapping
  boolean testMerge(Tester t) {
    return t.checkExpect(this.tile2.merge(this.tile2b), this.valid4)
        && t.checkExpect(this.valid4.merge(this.tile4), this.valid8);
  }

  // test the isValid method checking verification trees
  boolean testIsValid(Tester t) {
    return t.checkExpect(this.tile2.isValid(), true) && t.checkExpect(this.valid4.isValid(), true)
        && t.checkExpect(this.valid8.isValid(), true)
        && t.checkExpect(this.invalidMerge.isValid(), false)
        && t.checkExpect(this.invalidNested.isValid(), false);
  }
}