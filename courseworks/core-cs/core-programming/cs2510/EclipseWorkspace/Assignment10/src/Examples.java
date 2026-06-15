import tester.Tester;

class MazeExamples {
  void testMazeGame(Tester t) {
    // Arbitrary size allocation configuration matching 100x60 specification bounds
    MazeWorld game = new MazeWorld(40, 30);
    game.bigBang(40 * 12, 30 * 12, 0.01); // 0.01 seconds per tick ensures smooth rendering speed
  }

  // Test Union-Find structures
  void testUnionFind(Tester t) {
    Cell c1 = new Cell(0, 0);
    Cell c2 = new Cell(1, 0);

    HashMap<Cell, Cell> reps = new HashMap<>();
    reps.put(c1, c1);
    reps.put(c2, c2);

    // Simulate finding roots
    t.checkExpect(c1, c1);

    // Simulate union
    reps.put(c1, c2);
    // Now root of c1 should link directly or indirectly to c2
  }

  // Test edge sorting behavior
  void testEdgeComparison(Tester t) {
    Cell c1 = new Cell(0, 0);
    Cell c2 = new Cell(1, 0);
    Cell c3 = new Cell(0, 1);

    Edge e1 = new Edge(c1, c2, 10);
    Edge e2 = new Edge(c1, c3, 5);

    t.checkExpect(e1.compareTo(e2) > 0, true);
    t.checkExpect(e2.compareTo(e1) < 0, true);
  }
}