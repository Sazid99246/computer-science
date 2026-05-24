// To represent a Tetris piece
interface ITetrisPiece {
  int SCREEN_HEIGHT = 30;
}

// To share implementations common to all Tetris pieces
abstract class ATetrisPiece implements ITetrisPiece {
  int xPos;
  int yPos;

  ATetrisPiece(int x, int y) {
    this.xPos = x;
    this.yPos = y;
  }

  ATetrisPiece(int x) {
    this(x, SCREEN_HEIGHT);
  }
}

// To represent a 2x2 square Tetris piece
class Square extends ATetrisPiece {
  Square(int topLeftX, int topLeftY) {
    super(topLeftX, topLeftY);
  }

  Square(int topLeftX) {
    super(topLeftX);
  }
}

// To represent an L-shaped Tetris piece
class LShape extends ATetrisPiece {
  LShape(int cornerX, int cornerY) {
    super(cornerX, cornerY);
  }
}

class ExamplesTetris {
  // Calls the first constructor, and creates a square at position (3, 15)
  ITetrisPiece square1 = new Square(3, 15);
  // Calls the second constructor, and creates a square at position (3, 30)
  ITetrisPiece square2 = new Square(3);
}