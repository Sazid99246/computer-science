import java.awt.Color;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//Represents a single cell in the maze grid

class Cell {
  int x; // grid x-coordinate
  int y; // grid y-coordinate
  boolean isStart;
  boolean isEnd;

  Cell(int x, int y) {
    this.x = x;
    this.y = y;
    this.isStart = (x == 0 && y == 0);
  }

  // Visual representation helper for standard squares
  WorldImage draw(int size, Color color) {
    return new RectangleImage(size, size, OutlineMode.SOLID, color);
  }
}
