package cs3500.pyramidsolitaire.model.hw03;

import java.util.ArrayList;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * A mock model used to verify that the controller passes inputs correctly.
 */
public class MockPyramidSolitaireModel implements PyramidSolitaireModel<Card> {
  final StringBuilder log;

  public MockPyramidSolitaireModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    // Return a dummy deck just to satisfy compilation/setup if checked
    return new ArrayList<>();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    log.append(String.format("startGame called: numRows=%d, numDraw=%d, shuffle=%b\n",
            numRows, numDraw, shuffle));
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    log.append(String.format("remove(2-card) called: row1=%d, card1=%d, row2=%d, card2=%d\n",
            row1, card1, row2, card2));
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    log.append(String.format("remove(1-card) called: row=%d, card=%d\n", row, card));
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    log.append(String.format("removeUsingDraw called: drawIndex=%d, row=%d, card=%d\n",
            drawIndex, row, card));
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    log.append(String.format("discardDraw called: drawIndex=%d\n", drawIndex));
  }

  @Override
  public int getNumRows() {
    return 3; // Fixed dummy value for rendering structure safely
  }

  @Override
  public int getNumDraw() {
    return 1;
  }

  @Override
  public int getRowWidth(int row) {
    return row + 1;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false; // Remain false so the input loop doesn't instantly stop
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 100; // Dummy fixed score
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return null; // Renders blank spots safely
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return new ArrayList<>();
  }
}