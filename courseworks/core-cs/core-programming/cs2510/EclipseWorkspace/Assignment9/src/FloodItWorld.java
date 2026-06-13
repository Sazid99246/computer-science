import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.*;
import tester.Tester;

// Represents a single square of the game area
class Cell {
  int x; // Logical column index (0 to BOARD_SIZE - 1)
  int y; // Logical row index (0 to BOARD_SIZE - 1)
  Color color;
  boolean flooded;

  // The four adjacent neighbor references
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  Cell(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = false;
  }

  // Renders this individual cell tile
  WorldImage drawCell(int cellSize) {
    return new RectangleImage(cellSize, cellSize, OutlineMode.SOLID, this.color);
  }
}

// Manages the mutable world state and gameplay engine
class FloodItWorld extends World {
  // Configurable single-point-of-control constants
  static final int BOARD_SIZE = 14; 
  static final int CELL_SIZE = 30;  
  
  // Color palette pool
  ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(
      Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.MAGENTA
  ));

  ArrayList<Cell> board;
  Random rand = new Random();

  // Gameplay tracking states
  int clicksAllowed;
  int clicksUsed;
  Color targetColor; // The new color filling the flooded zone
  boolean isFlooding; // True if the waterfall animation is processing steps

  FloodItWorld() {
    this.initGame();
  }

  // EFFECT: Resets and constructs a fresh game board with connected pointers
  void initGame() {
    this.board = new ArrayList<Cell>();
    this.clicksUsed = 0;
    this.clicksAllowed = (BOARD_SIZE * 2) + 4;
    this.isFlooding = false;

    // 1. Instantiate the grid matrix with random colors
    for (int r = 0; r < BOARD_SIZE; r++) {
      for (int c = 0; c < BOARD_SIZE; c++) {
        Color randomColor = this.colors.get(this.rand.nextInt(this.colors.size()));
        this.board.add(new Cell(c, r, randomColor));
      }
    }

    // 2. Link neighboring node references together
    for (int r = 0; r < BOARD_SIZE; r++) {
      for (int c = 0; c < BOARD_SIZE; c++) {
        Cell current = this.getCellAt(c, r);
        if (r > 0) { current.top = this.getCellAt(c, r - 1); }
        if (r < BOARD_SIZE - 1) { current.bottom = this.getCellAt(c, r + 1); }
        if (c > 0) { current.left = this.getCellAt(c - 1, r); }
        if (c < BOARD_SIZE - 1) { current.right = this.getCellAt(c + 1, r); }
      }
    }

    // Top-left origin element initializes as flooded baseline zone
    Cell origin = this.board.get(0);
    origin.flooded = true;
    this.targetColor = origin.color;
  }

  // Converts 2D column and row indices to a 1D flat array position index
  Cell getCellAt(int col, int row) {
    return this.board.get((row * BOARD_SIZE) + col);
  }

  // Renders the board canvas interface along with UI information metrics
  public WorldScene makeScene() {
    int boardPixels = BOARD_SIZE * CELL_SIZE;
    int uiHeight = 40;
    WorldScene scene = new WorldScene(boardPixels, boardPixels + uiHeight);

    // Render cells
    for (Cell c : this.board) {
      int pixelX = (c.x * CELL_SIZE) + (CELL_SIZE / 2);
      int pixelY = (c.y * CELL_SIZE) + (CELL_SIZE / 2);
      scene.placeImageXY(c.drawCell(CELL_SIZE), pixelX, pixelY);
    }

    // Display Text Info Panels
    String scoreText = "Clicks: " + this.clicksUsed + " / " + this.clicksAllowed;
    WorldImage textImage = new TextImage(scoreText, 16, FontStyle.BOLD, Color.BLACK);
    scene.placeImageXY(textImage, boardPixels / 2, boardPixels + (uiHeight / 2));

    // Handle game-over screens overlays
    if (this.checkWinCondition()) {
      scene.placeImageXY(new TextImage("YOU WIN!", 32, FontStyle.BOLD, Color.GREEN.darker()), boardPixels / 2, boardPixels / 2);
    } else if (this.clicksUsed >= this.clicksAllowed && !this.isFlooding) {
      scene.placeImageXY(new TextImage("GAME OVER", 32, FontStyle.BOLD, Color.RED), boardPixels / 2, boardPixels / 2);
    }

    return scene;
  }

  // EFFECT: Animates the step-by-step waterfall spreading on every tick edge
  public void onTick() {
    if (!this.isFlooding) {
      return;
    }

    boolean expandedThisTick = false;
    
    // Create a temporary clone to safely analyze current state mutations
    ArrayList<Cell> Snapshot = new ArrayList<Cell>(this.board);

    for (Cell c : Snapshot) {
      if (c.flooded && c.color != this.targetColor) {
        c.color = this.targetColor;
        expandedThisTick = true;
      }

      // Look outward from flooded zones to annex matching adjacent cells
      if (c.flooded) {
        expandedThisTick |= this.tryAnnexNeighbor(c.top);
        expandedThisTick |= this.tryAnnexNeighbor(c.bottom);
        expandedThisTick |= this.tryAnnexNeighbor(c.left);
        expandedThisTick |= this.tryAnnexNeighbor(c.right);
      }
    }

    // If no cells changed colors this tick, the waterfall ripple loop finishes
    if (!expandedThisTick) {
      this.isFlooding = false;
    }
  }

  // EFFECT: Annexes a neighbor cell if it matches the current flooded zone color
  private boolean tryAnnexNeighbor(Cell neighbor) {
    if (neighbor != null && !neighbor.flooded && neighbor.color == this.targetColor) {
      neighbor.flooded = true;
      return true;
    }
    return false;
  }

  // EFFECT: Listens to mouse clicks to trigger a color change transaction
  public void onMouseClicked(Posn pos) {
    // Lock inputs if game over or if an animation wave sequence is processing
    if (this.isFlooding || this.checkWinCondition() || this.clicksUsed >= this.clicksAllowed) {
      return;
    }

    int clickedCol = pos.x / CELL_SIZE;
    int clickedRow = pos.y / CELL_SIZE;

    // Out of bounds check safety guard
    if (clickedCol >= BOARD_SIZE || clickedRow >= BOARD_SIZE || pos.y >= BOARD_SIZE * CELL_SIZE) {
      return;
    }

    Cell clickedCell = this.getCellAt(clickedCol, clickedRow);
    Color newColor = clickedCell.color;

    // Ignore clicking the same color the flooded region already possesses
    if (newColor == this.board.get(0).color) {
      return;
    }

    // Commit turn transaction step
    this.clicksUsed++;
    this.targetColor = newColor;
    this.isFlooding = true;
  }

  // EFFECT: Triggers an instant board reset when the 'r' key is pressed
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.initGame();
    }
  }

  // Helper validation predicate to evaluate full game completion status
  boolean checkWinCondition() {
    for (Cell c : this.board) {
      if (!c.flooded) {
        return false;
      }
    }
    return true;
  }
}