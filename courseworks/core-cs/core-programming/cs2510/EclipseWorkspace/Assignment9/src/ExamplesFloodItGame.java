import java.awt.Color; // Fixes "Color cannot be resolved"
import javalib.worldimages.Posn; // Fixes "Posn cannot be resolved to a type"
import tester.Tester;

class ExamplesFloodItGame {

  // Runs and kicks off the active live simulation game frame window
  void testRunFloodIt(Tester t) {
    FloodItWorld world = new FloodItWorld();
    int size = FloodItWorld.BOARD_SIZE * FloodItWorld.CELL_SIZE;
    world.bigBang(size, size + 40, 0.05); // 0.05 tick speed provides smooth animation
  }

  // Asserts structural linkage mapping configurations
  void testGridConnectionsAndInitialization(Tester t) {
    FloodItWorld w = new FloodItWorld();

    Cell topLeft = w.getCellAt(0, 0);
    t.checkExpect(topLeft.flooded, true); // Origin must always be active
    t.checkExpect(topLeft.left, null);
    t.checkExpect(topLeft.top, null);
    t.checkExpect(topLeft.right, w.getCellAt(1, 0));
    t.checkExpect(topLeft.bottom, w.getCellAt(0, 1));

    Cell bottomRight = w.getCellAt(FloodItWorld.BOARD_SIZE - 1, FloodItWorld.BOARD_SIZE - 1);
    t.checkExpect(bottomRight.right, null);
    t.checkExpect(bottomRight.bottom, null);
  }

  // Tests mouse interaction mechanics and animation safety guards
  void testGameInputAndFloodingState(Tester t) {
    FloodItWorld w = new FloodItWorld();

    // Manually force coordinate (1,0) to be a distinct known color to isolate tests
    Cell testCell = w.getCellAt(1, 0);
    testCell.color = Color.BLUE;
    w.getCellAt(0, 0).color = Color.RED;

    // Click on our known custom setup cell tile position to expand
    t.checkExpect(w.clicksUsed, 0);
    w.onMouseClicked(new Posn(35, 10)); // Resolves to cell (1,0)

    t.checkExpect(w.clicksUsed, 1);
    t.checkExpect(w.isFlooding, true);
    t.checkExpect(w.targetColor, Color.BLUE);
  }

  // Verifies that pressing 'r' cleanly unrolls state variables to default
  // baselines
  void testResetMechanics(Tester t) {
    FloodItWorld w = new FloodItWorld();
    w.clicksUsed = 5;

    w.onKeyEvent("r");
    t.checkExpect(w.clicksUsed, 0);
    t.checkExpect(w.isFlooding, false);
  }
}