import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import tester.Tester;

class ExamplesFifteen {
  ArrayUtils utils = new ArrayUtils();

  ArrayList<Integer> numbers;
  Predicate<Integer> isEven = n -> n % 2 == 0;

  // Set up baseline data values
  void reset() {
    this.numbers = new ArrayList<Integer>(Arrays.asList(1, 3, 4, 5, 7, 8));
  }

  // Verifies that filter creates a separate, correct collection array instance
  void testFilter(Tester t) {
    reset();
    ArrayList<Integer> filteredResult = utils.filter(this.numbers, isEven);

    // Check that the output list contains only even values
    t.checkExpect(filteredResult, new ArrayList<Integer>(Arrays.asList(4, 8)));
    // Check that the original source array remains unmutated
    t.checkExpect(this.numbers, new ArrayList<Integer>(Arrays.asList(1, 3, 4, 5, 7, 8)));
  }

  // Verifies destructive removal logic side effects without sliding index bugs
  void testRemoveExcept(Tester t) {
    reset();

    // Mutate the original list in place
    utils.removeExcept(this.numbers, isEven);

    // Verify that the odd numbers are correctly gone, leaving only [4, 8]
    t.checkExpect(this.numbers, new ArrayList<Integer>(Arrays.asList(4, 8)));
  }

//Launches the game window inside bigBang
  void testGame(Tester t) {
    FifteenGame game = new FifteenGame();
    // 4 tiles * 60px = 240px window canvas resolution dimensions
    game.bigBang(240, 240, 0.1);
  }

  // Verification testing suite for mutative side-effects
  void testMovementAndUndoState(Tester t) {
    FifteenGame g = new FifteenGame(); // Hole initializes at (3,3)

    // Verify boundary edge hits return false (cannot slide a tile from below row 3)
    t.checkExpect(g.tryMove("up"), false);

    // Slide a tile to the right into the hole (Tile from index 3,2 slides to 3,3)
    t.checkExpect(g.holeCol, 3);
    g.onKeyEvent("right");
    t.checkExpect(g.holeCol, 2); // Hole shifts left as tile moves right
    t.checkExpect(g.history.get(0), "right"); // Verify event logging works

    // Fire undo transaction step
    g.onKeyEvent("u");
    t.checkExpect(g.holeCol, 3); // Hole returns to its baseline position
    t.checkExpect(g.history.isEmpty(), true); // Verify event log pops cleanly
  }
}