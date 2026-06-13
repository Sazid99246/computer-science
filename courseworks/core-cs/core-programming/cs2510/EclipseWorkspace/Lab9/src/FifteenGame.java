import java.util.ArrayList;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.function.Predicate;

class ArrayUtils {
  // Produces a new ArrayList containing only items that satisfy the predicate
  // Using a For-Each Loop (Cleanest for non-mutative collection reads)
  <T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> result = new ArrayList<T>();
    for (T item : arr) {
      if (pred.test(item)) {
        result.add(item);
      }
    }
    return result;
  }

//Alternative Approach: Counted-For Loop
  <T> ArrayList<T> filterCounted(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> result = new ArrayList<T>();
    for (int i = 0; i < arr.size(); i++) {
      if (pred.test(arr.get(i))) {
        result.add(arr.get(i));
      }
    }
    return result;
  }

  // EFFECT: Modifies the given list to remove elements that fail the predicate
  <T> void removeExcept(ArrayList<T> arr, Predicate<T> pred) {
    ArrayList<T> itemsToKeep = new ArrayList<T>();

    // 1. Gather all items that pass the predicate into a temporary holding
    // container
    for (T item : arr) {
      if (pred.test(item)) {
        itemsToKeep.add(item);
      }
    }

    // 2. Clear the original array structure safely out-of-loop
    arr.clear();

    // 3. Re-populate the original array structure with the saved items
    arr.addAll(itemsToKeep);
  }
}

// Represents an individual tile in the grid
class Tile {
  int value; // 0 represents the empty hole

  Tile(int value) {
    this.value = value;
  }

  // Draws this tile at its specific column and row onto the provided background
  // scene
  WorldScene drawAt(int col, int row, WorldScene scene) {
    int tileSize = 60;
    // Calculate center pinning coordinates
    int posX = (col * tileSize) + (tileSize / 2);
    int posY = (row * tileSize) + (tileSize / 2);

    WorldImage tileImage;
    if (this.value == 0) {
      tileImage = new FrameImage(
          new RectangleImage(tileSize, tileSize, OutlineMode.SOLID, Color.DARK_GRAY), Color.BLACK);
    }
    else {
      WorldImage background = new RectangleImage(tileSize, tileSize, OutlineMode.SOLID,
          Color.LIGHT_GRAY);
      WorldImage border = new RectangleImage(tileSize, tileSize, OutlineMode.OUTLINE, Color.BLACK);
      WorldImage text = new TextImage(Integer.toString(this.value), 20, FontStyle.BOLD,
          Color.BLACK);
      tileImage = new OverlayImage(text, new OverlayImage(border, background));
    }

    scene.placeImageXY(tileImage, posX, posY);
    return scene;
  }
}

class FifteenGame extends World {
  ArrayList<ArrayList<Tile>> tiles;

  // Coordinates of the blank space (0 tile) tracked dynamically
  int holeRow;
  int holeCol;

  // History stack to support the undo mechanism
  ArrayList<String> history;

  FifteenGame() {
    this.initGame();
  }

  // EFFECT: Initializes a standard ordered board state
  void initGame() {
    this.tiles = new ArrayList<ArrayList<Tile>>();
    this.history = new ArrayList<String>();

    int count = 1;
    for (int r = 0; r < 4; r++) {
      ArrayList<String> rowList = new ArrayList<String>();
      ArrayList<Tile> row = new ArrayList<Tile>();
      for (int c = 0; c < 4; c++) {
        if (r == 3 && c == 3) {
          row.add(new Tile(0));
          this.holeRow = 3;
          this.holeCol = 3;
        }
        else {
          row.add(new Tile(count));
          count++;
        }
      }
      this.tiles.add(row);
    }
  }

  // Draws the current grid state frame
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(240, 240);
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 4; c++) {
        this.tiles.get(r).get(c).drawAt(c, r, scene);
      }
    }
    return scene;
  }

  // EFFECT: Swaps the positions of two tiles inside the nested board matrix
  void swapTiles(int r1, int c1, int r2, int c2) {
    Tile temp = this.tiles.get(r1).get(c1);
    this.tiles.get(r1).set(c1, this.tiles.get(r2).get(c2));
    this.tiles.get(r2).set(c2, temp);
  }

  // EFFECT: Updates grid states by processing moves or reverting history records
  public void onKeyEvent(String k) {
    if (k.equals("u")) {
      this.undoMove();
    }
    else if (this.tryMove(k)) {
      this.history.add(0, k); // Save successful moves to history head
    }
  }

  // EFFECT: Validates boundaries and slides tile into the hole if valid
  boolean tryMove(String direction) {
    int targetRow = this.holeRow;
    int targetCol = this.holeCol;

    // Arrow keys interpreted as moving a tile INTO the blank hole space
    if (direction.equals("up") && this.holeRow < 3) {
      targetRow++;
    }
    else if (direction.equals("down") && this.holeRow > 0) {
      targetRow--;
    }
    else if (direction.equals("left") && this.holeCol < 3) {
      targetCol++;
    }
    else if (direction.equals("right") && this.holeCol > 0) {
      targetCol--;
    }
    else {
      return false;
    } // Invalid move or boundary wall collision

    this.swapTiles(this.holeRow, this.holeCol, targetRow, targetCol);
    this.holeRow = targetRow;
    this.holeCol = targetCol;
    return true;
  }

  // EFFECT: Pops the latest transaction and performs the mirror inverse operation
  void undoMove() {
    if (!this.history.isEmpty()) {
      String lastMove = this.history.remove(0);
      if (lastMove.equals("up")) {
        this.tryMove("down");
      }
      else if (lastMove.equals("down")) {
        this.tryMove("up");
      }
      else if (lastMove.equals("left")) {
        this.tryMove("right");
      }
      else if (lastMove.equals("right")) {
        this.tryMove("left");
      }
    }
  }
}