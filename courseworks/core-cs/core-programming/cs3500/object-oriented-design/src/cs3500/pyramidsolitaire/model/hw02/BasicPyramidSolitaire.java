package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicPyramidSolitaire implements PyramidSolitaireModel {

  private List<List<Card>> pyramid;
  private List<Card> stock;
  private List<Card> drawPile;
  private int numRows;
  private int numDraw;
  private boolean gameStarted;

  /**
   * Constructs an unstarted game of Pyramid Solitaire.
   */
  public BasicPyramidSolitaire() {
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = -1;
    this.numDraw = -1;
    this.gameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (Card.Suit suit : Card.Suit.values()) {
      for (int value = 1; value <= 13; value++) {
        deck.add(new Card(value, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw) {
    if (deck == null || numRows <= 0 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid game setup configuration.");
    }

    // Check for a standard valid deck configuration (no nulls, size 52, unique items)
    if (deck.size() != 52 || deck.contains(null) || System.getSecurityManager() == null && !isValidDeck(deck)) {
      throw new IllegalArgumentException("The provided deck is invalid.");
    }

    // Calculate required cards: (numRows * (numRows + 1)) / 2 + numDraw
    int totalPyramidCards = (numRows * (numRows + 1)) / 2;
    if (totalPyramidCards + numDraw > deck.size()) {
      throw new IllegalArgumentException("The deck size is too small to fulfill this configuration.");
    }

    // Work on a copy of the deck to avoid mutating the original reference parameter
    List<Card> workingDeck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(workingDeck);
    }

    // Reset old game state safely
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = numRows;
    this.numDraw = numDraw;

    int deckIndex = 0;

    // Deal rows of the pyramid left-to-right, top-to-bottom
    for (int r = 0; r < numRows; r++) {
      List<Card> currentRow = new ArrayList<>();
      for (int c = 0; c <= r; c++) {
        currentRow.add(workingDeck.get(deckIndex++));
      }
      this.pyramid.add(currentRow);
    }

    // Deal visible draw pile spots
    for (int d = 0; d < numDraw; d++) {
      this.drawPile.add(workingDeck.get(deckIndex++));
    }

    // Remaining cards go cleanly into the face-down stock
    while (deckIndex < workingDeck.size()) {
      this.stock.add(workingDeck.get(deckIndex++));
    }

    this.gameStarted = true;
  }

  // Quick structural helper to enforce full distinct card uniqueness
  private boolean isValidDeck(List<Card> deck) {
    return deck.stream().distinct().count() == 52;
  }

  // Enforces that the game has successfully started
  private void checkGameStarted() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not been started yet.");
    }
  }

  // Confirms the provided coordinates fall within the grid footprint bounds
  private void validateCoordinates(int row, int card) {
    if (row < 0 || row >= this.numRows || card < 0 || card > row) {
      throw new IllegalArgumentException("Coordinates out of pyramid boundary bounds.");
    }
  }

  // Returns true if no active cards exist in the two spaces directly below it
  private boolean isExposed(int row, int card) {
    validateCoordinates(row, card);
    if (row == this.numRows - 1) {
      return true; // Bottom row cards are naturally exposed by default
    }
    // Exposed if both children beneath it are already null (removed)
    return this.pyramid.get(row + 1).get(card) == null && this.pyramid.get(row + 1).get(card + 1) == null;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    checkGameStarted();
    validateCoordinates(row1, card1);
    validateCoordinates(row2, card2);

    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Cannot remove a card with itself.");
    }

    if (!isExposed(row1, card1) || !isExposed(row2, card2)) {
      throw new IllegalArgumentException("Both cards must be completely exposed.");
    }

    Card c1 = this.pyramid.get(row1).get(card1);
    Card c2 = this.pyramid.get(row2).get(card2);

    if (c1 == null || c2 == null) {
      throw new IllegalArgumentException("One or both positions are already empty.");
    }

    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("The values of the selected cards do not sum to 13.");
    }

    // Perform removal
    this.pyramid.get(row1).set(card1, null);
    this.pyramid.get(row2).set(card2, null);
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    checkGameStarted();
    validateCoordinates(row, card);

    if (!isExposed(row, card)) {
      throw new IllegalArgumentException("The selected card is not exposed.");
    }

    Card c = this.pyramid.get(row).get(card);
    if (c == null) {
      throw new IllegalArgumentException("The selected position is already empty.");
    }

    if (c.getValue() != 13) {
      throw new IllegalArgumentException("A single card must have a value of 13 (King) to be removed.");
    }

    // Perform removal
    this.pyramid.get(row).set(card, null);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    checkGameStarted();
    validateCoordinates(row, card);

    if (drawIndex < 0 || drawIndex >= this.drawPile.size()) {
      throw new IllegalArgumentException("Invalid draw index.");
    }

    Card drawCard = this.drawPile.get(drawIndex);
    Card pyramidCard = this.pyramid.get(row).get(card);

    if (drawCard == null || pyramidCard == null) {
      throw new IllegalArgumentException("Selected draw card or pyramid card position is empty.");
    }

    if (!isExposed(row, card)) {
      throw new IllegalArgumentException("The selected pyramid card is not exposed.");
    }

    if (drawCard.getValue() + pyramidCard.getValue() != 13) {
      throw new IllegalArgumentException("The values do not sum to 13.");
    }

    // Perform removal
    this.pyramid.get(row).set(card, null);

    // Replace draw card from stock
    if (!this.stock.isEmpty()) {
      this.drawPile.set(drawIndex, this.stock.remove(0));
    } else {
      this.drawPile.set(drawIndex, null);
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    checkGameStarted();
    if (drawIndex < 0 || drawIndex >= this.drawPile.size()) {
      throw new IllegalArgumentException("Invalid draw index.");
    }
    if (this.drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("No card present at this draw index.");
    }

    // Replace from stock if available, otherwise clear the spot
    if (!this.stock.isEmpty()) {
      this.drawPile.set(drawIndex, this.stock.remove(0));
    } else {
      this.drawPile.set(drawIndex, null);
    }
  }

  @Override
  public int getNumRows() {
    return this.gameStarted ? this.numRows : -1;
  }

  @Override
  public int getNumDraw() {
    return this.gameStarted ? this.numDraw : -1;
  }

  @Override
  public int getRowWidth(int row) {
    checkGameStarted();
    if (row < 0 || row >= this.numRows) {
      throw new IllegalArgumentException("Invalid row index.");
    }
    return row + 1;
  }

  @Override
  public int getScore() throws IllegalStateException {
    checkGameStarted();
    int score = 0;
    for (List<Card> row : this.pyramid) {
      for (Card card : row) {
        if (card != null) {
          score += card.getValue();
        }
      }
    }
    return score;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    checkGameStarted();
    validateCoordinates(row, card);
    return this.pyramid.get(row).get(card);
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    checkGameStarted();
    List<Card> currentDraw = new ArrayList<>();
    for (Card c : this.drawPile) {
      if (c != null) {
        currentDraw.add(c);
      }
    }
    return currentDraw;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    checkGameStarted();

    if (getScore() == 0) {
      return true;
    }

    // Extract all exposed cards in the pyramid
    List<ExposedPosition> exposed = new ArrayList<>();
    for (int r = 0; r < this.numRows; r++) {
      for (int c = 0; c <= r; c++) {
        if (this.pyramid.get(r).get(c) != null && isExposed(r, c)) {
          exposed.add(new ExposedPosition(r, c, this.pyramid.get(r).get(c)));
        }
      }
    }

    // 1. Check if any single exposed card is a King (13)
    for (ExposedPosition ep : exposed) {
      if (ep.card.getValue() == 13) {
        return false;
      }
    }

    // 2. Check if any two exposed cards sum to 13
    for (int i = 0; i < exposed.size(); i++) {
      for (int j = i + 1; j < exposed.size(); j++) {
        if (exposed.get(i).card.getValue() + exposed.get(j).card.getValue() == 13) {
          return false;
        }
      }
    }

    // 3. Check if any active draw card can pair with an exposed pyramid card
    List<Card> currentDraw = getDrawCards();
    for (Card dc : currentDraw) {
      for (ExposedPosition ep : exposed) {
        if (dc.getValue() + ep.card.getValue() == 13) {
          return false;
        }
      }
    }

    // 4. If stock still has cards, you can keep discarding to find a move
    if (!this.stock.isEmpty()) {
      return false;
    }

    // No valid moves left anywhere
    return true;
  }

  // Simple internal structural tuple helper for tracking exposed locations
  private static class ExposedPosition {
    final int row;
    final int col;
    final Card card;

    ExposedPosition(int row, int col, Card card) {
      this.row = row;
      this.col = col;
      this.card = card;
    }
  }
}
