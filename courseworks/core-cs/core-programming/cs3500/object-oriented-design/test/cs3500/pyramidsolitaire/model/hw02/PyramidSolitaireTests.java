package cs3500.pyramidsolitaire.model.hw02;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Test suite verifying the correctness of the BasicPyramidSolitaire model
 * and the PyramidSolitaireTextualView rendering.
 */
public class PyramidSolitaireTests {

  private PyramidSolitaireModel<Card> model;
  private List<Card> validDeck;

  @Before
  public void setUp() {
    this.model = new BasicPyramidSolitaire();
    this.validDeck = this.model.getDeck();
  }

  // --- START GAME & DECK VALIDATION TESTS ---

  @Test
  public void testGetDeckCreatesValid52Cards() {
    List<Card> deck = this.model.getDeck();
    assertEquals(52, deck.size());
    // Ensure all 52 cards are completely unique
    long uniqueCount = deck.stream().distinct().count();
    assertEquals(52, uniqueCount);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsWithNullDeck() {
    this.model.startGame(null, false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsWithInvalidRows() {
    this.model.startGame(this.validDeck, false, 0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsWithNegativeDraw() {
    this.model.startGame(this.validDeck, false, 5, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsWhenDeckTooSmallForConfiguration() {
    // A 9-row pyramid needs (9 * 10) / 2 = 45 cards. 45 + 10 draw cards = 55 cards.
    // 55 cards exceeds a standard 52 card deck.
    this.model.startGame(this.validDeck, false, 9, 10);
  }

  @Test
  public void testStartGameCorrectlyDealsNoShuffle() {
    // Using un-shuffled deck: Row 0 Col 0 should be Ace of Clubs (value 1)
    this.model.startGame(this.validDeck, false, 3, 3);
    assertEquals(3, this.model.getNumRows());
    assertEquals(3, this.model.getNumDraw());
    assertEquals("A♣", this.model.getCardAt(0, 0).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testMethodsThrowBeforeGameStarts() {
    this.model.getScore();
  }

  // --- REMOVAL MECHANICS TESTS ---

  @Test
  public void testRemoveTwoExposedCardsSummingTo13() {
    // Start with a completely valid 52-card unique deck
    List<Card> customDeck = this.model.getDeck();

    // Let's set up a 2-row pyramid (requires 3 cards total: 1 in row 0, 2 in row 1)
    // Row 1 Col 0 is index 1. Row 1 Col 1 is index 2.
    // Let's find an Ace (value 1) and a Queen (value 12) in our valid deck and swap them into indices 1 and 2.
    int aceIndex = -1;
    int queenIndex = -1;
    for (int i = 0; i < customDeck.size(); i++) {
      if (customDeck.get(i).getValue() == 1 && aceIndex == -1) {
        aceIndex = i;
      }
      if (customDeck.get(i).getValue() == 12 && queenIndex == -1) {
        queenIndex = i;
      }
    }

    // Swap them safely into position so the deck remains 100% unique but ordered
    Card temp1 = customDeck.get(1);
    customDeck.set(1, customDeck.get(aceIndex));
    customDeck.set(aceIndex, temp1);

    Card temp2 = customDeck.get(2);
    customDeck.set(2, customDeck.get(queenIndex));
    customDeck.set(queenIndex, temp2);

    // Now index 1 is an Ace (1) and index 2 is a Queen (12). They sum to 13!
    this.model.startGame(customDeck, false, 2, 1);

    // Verify they are both there and can be removed
    assertEquals(1, this.model.getCardAt(1, 0).getValue());
    assertEquals(12, this.model.getCardAt(1, 1).getValue());

    this.model.remove(1, 0, 1, 1);
    assertNull(this.model.getCardAt(1, 0));
    assertNull(this.model.getCardAt(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveThrowsWhenCardsAreNotExposed() {
    this.model.startGame(this.validDeck, false, 4, 2);
    // Row 0 Col 0 is heavily covered by Row 1 cards.
    this.model.remove(0, 0, 3, 0);
  }

  @Test
  public void testRemoveSingleKing() {
    // Start with a completely valid 52-card unique deck
    List<Card> customDeck = this.model.getDeck();

    // Let's set up a 2-row pyramid (requires 3 cards total: 1 in row 0, 2 in row 1)
    // We want a King (value 13) at Row 1 Col 0 (index 1 in the deck)
    int kingIndex = -1;
    for (int i = 0; i < customDeck.size(); i++) {
      if (customDeck.get(i).getValue() == 13) {
        kingIndex = i;
        break;
      }
    }

    // Swap the King safely into index 1
    Card temp = customDeck.get(1);
    customDeck.set(1, customDeck.get(kingIndex));
    customDeck.set(kingIndex, temp);

    // Start the game with the structured but valid deck
    this.model.startGame(customDeck, false, 2, 2);

    // Verify the King is at (1, 0) and remove it
    assertEquals(13, this.model.getCardAt(1, 0).getValue());
    this.model.remove(1, 0);

    assertNull(this.model.getCardAt(1, 0)); // Spot should now be empty
  }

  // --- DRAW PILE & GAME OVER TESTS ---

  @Test
  public void testDiscardDrawReplacesCardFromStock() {
    this.model.startGame(this.validDeck, false, 3, 2);
    List<Card> initialDraw = this.model.getDrawCards();
    Card firstDrawCard = initialDraw.get(0);

    this.model.discardDraw(0);
    List<Card> updatedDraw = this.model.getDrawCards();

    // Ensure the card at index 0 has changed because it was replaced from stock
    assertFalse(firstDrawCard.equals(updatedDraw.get(0)));
  }

  @Test
  public void testIsGameOverReturnsFalseWhenMovesRemain() {
    this.model.startGame(this.validDeck, false, 4, 3);
    assertFalse(this.model.isGameOver());
  }

  // --- VIEW RENDERING TESTS ---

  @Test
  public void testViewReturnsEmptyStringBeforeStart() {
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(this.model);
    assertEquals("", view.toString());
  }

  @Test
  public void testViewRendersInitialGameStructureCorrectly() {
    // Small 2-row layout to verify accurate text positioning rules
    this.model.startGame(this.validDeck, false, 2, 2);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(this.model);

    String expected =
            "  A♣\n" +
                    "2♣  3♣\n" +
                    "Draw: 4♣, 5♣";

    assertEquals(expected, view.toString());
  }
}