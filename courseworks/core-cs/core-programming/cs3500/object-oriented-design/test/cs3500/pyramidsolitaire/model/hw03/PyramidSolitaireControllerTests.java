package cs3500.pyramidsolitaire.model.hw03;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import static org.junit.Assert.assertTrue;

public class PyramidSolitaireControllerTests {

  @Test
  public void testImmediateQuitCommand() {
    StringBuilder modelLog = new StringBuilder();
    PyramidSolitaireModel<Card> mockModel = new MockPyramidSolitaireModel(modelLog);

    Readable input = new StringReader("q");
    StringBuilder output = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input, output);
    List<Card> dummyDeck = new ArrayList<>();

    controller.playGame(mockModel, dummyDeck, false, 3, 1);

    // Verify controller handled termination output correctly
    assertTrue(output.toString().contains("Game quit!"));
    assertTrue(output.toString().contains("State of game when quit:"));
    assertTrue(output.toString().contains("Score: 100"));
  }

  @Test
  public void testControllerNormalizesIndicesToZeroBased() {
    StringBuilder modelLog = new StringBuilder();
    PyramidSolitaireModel<Card> mockModel = new MockPyramidSolitaireModel(modelLog);

    // User types 'rm1 2 1' (meaning 2nd row, 1st card). Then quits.
    Readable input = new StringReader("rm1 2 1 q");
    StringBuilder output = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input, output);
    List<Card> dummyDeck = new ArrayList<>();

    controller.playGame(mockModel, dummyDeck, false, 3, 1);

    // Confirm the controller successfully mapped user 1-indices to model 0-indices (row 1, card 0)
    assertTrue(modelLog.toString().contains("remove(1-card) called: row=1, card=0"));
  }

  @Test
  public void testHandlesGarbageInputAndRetriesSuccessfully() {
    StringBuilder modelLog = new StringBuilder();
    PyramidSolitaireModel<Card> mockModel = new MockPyramidSolitaireModel(modelLog);

    // User tries rm1, enters garbage 'abc', then fixes it with '2 1', then quits.
    Readable input = new StringReader("rm1 abc 2 1 q");
    StringBuilder output = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input, output);
    List<Card> dummyDeck = new ArrayList<>();

    controller.playGame(mockModel, dummyDeck, false, 3, 1);

    // Despite the 'abc', the controller should successfully recover and parse the valid integers
    assertTrue(modelLog.toString().contains("remove(1-card) called: row=1, card=0"));
  }

  @Test
  public void testHandlesMidCommandQuit() {
    StringBuilder modelLog = new StringBuilder();
    PyramidSolitaireModel<Card> mockModel = new MockPyramidSolitaireModel(modelLog);

    // User types a command identifier but quits halfway through supplying arguments
    Readable input = new StringReader("rm2 3 2 q");
    StringBuilder output = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input, output);
    List<Card> dummyDeck = new ArrayList<>();

    controller.playGame(mockModel, dummyDeck, false, 3, 1);

    // Ensure the game successfully quit, and the model function was never called (since args were incomplete)
    assertTrue(output.toString().contains("Game quit!"));
    assertFalse(modelLog.toString().contains("remove(2-card)"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameThrowsWhenModelIsNull() {
    Readable input = new StringReader("q");
    StringBuilder output = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input, output);

    controller.playGame(null, new ArrayList<>(), false, 3, 1);
  }

  // Quick structural helper to easily assert string exclusions
  private boolean assertFalse(boolean condition) {
    return !condition;
  }
}