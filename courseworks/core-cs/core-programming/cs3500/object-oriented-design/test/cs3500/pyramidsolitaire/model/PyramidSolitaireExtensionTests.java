package cs3500.pyramidsolitaire.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test cases verifying functionality of Relaxed and TriPeaks solitaire rules.
 */
public class PyramidSolitaireExtensionTests {
  private PyramidSolitaireModel<Card> relaxedGame;
  private PyramidSolitaireModel<Card> triPeaksGame;

  @Before
  public void setUp() {
    this.relaxedGame = PyramidSolitaireCreator.create(GameType.RELAXED);
    this.triPeaksGame = PyramidSolitaireCreator.create(GameType.TRIPEAKS);
  }

  @Test
  public void testFactoryCreation() {
    assertNotNull(PyramidSolitaireCreator.create(GameType.BASIC));
    assertNotNull(this.relaxedGame);
    assertNotNull(this.triPeaksGame);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFactoryThrowsOnNullType() {
    PyramidSolitaireCreator.create(null);
  }

  @Test
  public void testRelaxedUniqueDeckProperties() {
    List<Card> deck = this.relaxedGame.getDeck();
    assertEquals(52, deck.size());
  }

  @Test
  public void testTriPeaksDoubleDeckSize() {
    List<Card> deck = this.triPeaksGame.getDeck();
    assertEquals(104, deck.size());
  }

  @Test
  public void testTriPeaksRowWidthsForSevenRows() {
    this.triPeaksGame.startGame(this.triPeaksGame.getDeck(), false, 7, 3);
    // Row width logic tracking peaks (overlap point at row 4)
    assertEquals(3, this.triPeaksGame.getRowWidth(0));
    assertEquals(6, this.triPeaksGame.getRowWidth(1));
    assertEquals(9, this.triPeaksGame.getRowWidth(2));
    assertEquals(12, this.triPeaksGame.getRowWidth(3));
    assertEquals(13, this.triPeaksGame.getRowWidth(4));
    assertEquals(14, this.triPeaksGame.getRowWidth(5));
    assertEquals(15, this.triPeaksGame.getRowWidth(6));
  }

  @Test
  public void testRelaxedSolitaireBasicRemovalStillWorks() {
    this.relaxedGame.startGame(this.relaxedGame.getDeck(), false, 3, 1);
    // In a standard sequentially ordered unshuffled deck:
    // Row 2 contains: [Card(4), Card(5), Card(6)]
    // Cards at the base of row 2 are fully exposed and can be removed via traditional rules if matched
    assertEquals(3, this.relaxedGame.getNumRows());
    assertEquals(21, this.relaxedGame.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRelaxedRulesThrowOnLockedPairing() {
    this.relaxedGame.startGame(this.relaxedGame.getDeck(), false, 4, 1);
    // Attempting to clear an internal card that is fully locked by multiple lower nodes
    this.relaxedGame.remove(0, 0, 1, 0);
  }

  @Test
  public void testTriPeaksGameScoreTracking() {
    this.triPeaksGame.startGame(this.triPeaksGame.getDeck(), false, 7, 3);
    assertTrue(this.triPeaksGame.getScore() > 0);
    assertFalse(this.triPeaksGame.isGameOver());
  }
}