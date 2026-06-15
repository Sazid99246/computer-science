import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

public class MazeWorld extends World {
  // Board Configuration
  final int width;
  final int height;
  final int cellSize = 12;

  // Grid Data
  ArrayList<Cell> cells;
  ArrayList<Edge> mstEdges;
  ArrayList<Edge> blacklistedWalls; // Walls kept on the board

  // Graph Adjacency representation for quick path lookup
  HashMap<Cell, ArrayList<Cell>> adjacencyList;

  // Animation / Search States
  boolean searching;
  boolean isBfs; // true if BFS, false if DFS
  ArrayList<Cell> worklist;
  HashSet<Cell> visited;
  HashMap<Cell, Cell> cameFromCell;
  ArrayList<Cell> finalPath;

  public MazeWorld(int width, int height) {
    this.width = width;
    this.height = height;
    this.initializeMaze();
  }

  // --- Maze Generation Sequence ---
  private void initializeMaze() {
    this.searching = false;
    this.cells = new ArrayList<>();
    this.mstEdges = new ArrayList<>();
    this.blacklistedWalls = new ArrayList<>();
    this.adjacencyList = new HashMap<>();
    this.visited = new HashSet<>();
    this.cameFromCell = new HashMap<>();
    this.finalPath = new ArrayList<>();
    this.worklist = new ArrayList<>();

    // 1. Build the Grid nodes
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Cell c = new Cell(x, y);
        this.cells.add(c);
        this.adjacencyList.put(c, new ArrayList<>());
      }
    }

    // 2. Build all possible raw horizontal and vertical edges
    ArrayList<Edge> allEdges = new ArrayList<>();
    Random rand = new Random();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Cell current = this.getCellAt(x, y);
        if (x < width - 1) { // East Wall
          allEdges.add(new Edge(current, this.getCellAt(x + 1, y), rand.nextInt(10000)));
        }
        if (y < height - 1) { // South Wall
          allEdges.add(new Edge(current, this.getCellAt(x, y + 1), rand.nextInt(10000)));
        }
      }
    }

    // 3. Sort edges to satisfy Kruskal's Algorithm
    Collections.sort(allEdges);

    // 4. Initialize Union-Find Map
    HashMap<Cell, Cell> representatives = new HashMap<>();
    for (Cell c : this.cells) {
      representatives.put(c, c);
    }

    // 5. Build Spanning Tree
    for (Edge edge : allEdges) {
      Cell rootFrom = this.find(representatives, edge.from);
      Cell rootTo = this.find(representatives, edge.to);

      if (rootFrom != rootTo) {
        this.mstEdges.add(edge);
        // Link the tree paths bi-directionally
        this.adjacencyList.get(edge.from).add(edge.to);
        this.adjacencyList.get(edge.to).add(edge.from);
        // Union the roots
        representatives.put(rootFrom, rootTo);
      }
      else {
        // Discarded MST edges must remain as solid physical walls
        this.blacklistedWalls.add(edge);
      }
    }
  }

  // Tail-safe loop helper for finding roots without deep recursive stacks
  private Cell find(HashMap<Cell, Cell> representatives, Cell node) {
    Cell curr = node;
    while (curr != representatives.get(curr)) {
      curr = representatives.get(curr);
    }
    return curr;
  }

  private Cell getCellAt(int x, int y) {
    return this.cells.get(x + (y * this.width));
  }

  // --- Solving Setup ---
  private void startSolver(boolean useBfs) {
    this.searching = true;
    this.isBfs = useBfs;
    this.visited.clear();
    this.cameFromCell.clear();
    this.finalPath.clear();

    this.worklist = new ArrayList<>();
    this.worklist.add(this.getCellAt(0, 0)); // Start in top-left
  }

  // --- Core Game Loop (Animation Engine) ---
  @Override
  public void onTick() {
    if (!this.searching || this.worklist.isEmpty()) {
      return;
    }

    // BFS vs DFS behavior switch
    // BFS handles list as Queue (FIFO -> Index 0)
    // DFS handles list as Stack (LIFO -> Tail index)
    Cell next = this.isBfs ? this.worklist.remove(0)
        : this.worklist.remove(this.worklist.size() - 1);

    if (next.x == this.width - 1 && next.y == this.height - 1) {
      this.searching = false;
      this.reconstructFinalPath(next);
      return;
    }

    if (!this.visited.contains(next)) {
      this.visited.add(next);
      for (Cell neighbor : this.adjacencyList.get(next)) {
        if (!this.visited.contains(neighbor)) {
          this.worklist.add(neighbor);
          this.cameFromCell.put(neighbor, next);
        }
      }
    }
  }

  private void reconstructFinalPath(Cell endCell) {
    Cell curr = endCell;
    while (curr != null) {
      this.finalPath.add(0, curr); // Keep layout organized start-to-finish
      curr = this.cameFromCell.get(curr);
    }
  }

  // --- Keyboard Controller Intercept ---
  @Override
  public void onKeyEvent(String key) {
    if (key.equalsIgnoreCase("b")) {
      this.startSolver(true);
    }
    else if (key.equalsIgnoreCase("d")) {
      this.startSolver(false);
    }
    else if (key.equalsIgnoreCase("r")) {
      this.initializeMaze(); // Extra credit hook: instant map reset
    }
  }

  // --- Graphical Renderer Pipeline ---
  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.width * this.cellSize, this.height * this.cellSize);

    // 1. Draw cell base surfaces
    for (Cell c : this.cells) {
      Color renderColor = Color.LIGHT_GRAY;

      if (c.x == 0 && c.y == 0) {
        renderColor = Color.GREEN; // Entrance
      }
      else if (c.x == this.width - 1 && c.y == this.height - 1) {
        renderColor = Color.MAGENTA; // Exit
      }
      else if (this.finalPath.contains(c)) {
        renderColor = new Color(143, 0, 255); // Violet solution trail
      }
      else if (this.visited.contains(c)) {
        renderColor = Color.CYAN; // Active exploration tracking
      }

      int renderX = (c.x * this.cellSize) + (this.cellSize / 2);
      int renderY = (c.y * this.cellSize) + (this.cellSize / 2);
      scene.placeImageXY(c.draw(this.cellSize, renderColor), renderX, renderY);
    }

    // 2. Draw remaining walls overlay
    for (Edge wall : this.blacklistedWalls) {
      int x1 = wall.from.x * this.cellSize;
      int y1 = wall.from.y * this.cellSize;
      int x2 = wall.to.x * this.cellSize;
      int y2 = wall.to.y * this.cellSize;

      WorldImage wallLine;
      int midX, midY;

      if (x1 == x2) { // Horizontal separator boundary
        wallLine = new LineImage(new Posn(this.cellSize, 0), Color.BLACK);
        midX = x1 + (this.cellSize / 2);
        midY = Math.max(y1, y2);
      }
      else { // Vertical separator boundary
        wallLine = new LineImage(new Posn(0, this.cellSize), Color.BLACK);
        midX = Math.max(x1, x2);
        midY = y1 + (this.cellSize / 2);
      }
      scene.placeImageXY(wallLine, midX, midY);
    }

    return scene;
  }
}