package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public abstract class AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected List<List<Card>> pyramid;
  protected List<Card> stock;
  protected List<Card> drawPile;
  protected int numRows;
  protected int numDraw;
  protected boolean gameStarted;

  public AbstractPyramidSolitaire() {
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = -1;
    this.numDraw = -1;
    this.gameStarted = false;
  }

  protected void checkGameStarted() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not been started yet.");
    }
  }

  protected boolean isValidCoordinates(int row, int card) {
    if (row < 0 || row >= this.numRows) {
      return false;
    }
    return card >= 0 && card < this.getRowWidth(row);
  }

  protected boolean isExposed(int row, int card) {
    if (!isValidCoordinates(row, card) || this.pyramid.get(row).get(card) == null) {
      return false;
    }
    if (row == this.numRows - 1) {
      return true;
    }
    return this.pyramid.get(row + 1).get(card) == null
            && this.pyramid.get(row + 1).get(card + 1) == null;
  }

  protected void validateDeck(List<Card> deck, int totalNeeded) {
    if (deck == null || deck.size() != totalNeeded) {
      throw new IllegalArgumentException("Invalid deck size.");
    }
    for (int i = 0; i < deck.size(); i++) {
      if (deck.get(i) == null) {
        throw new IllegalArgumentException("Deck contains null cards.");
      }
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j)) && totalNeeded == 52) {
          throw new IllegalArgumentException("Deck contains duplicate cards.");
        }
      }
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    checkGameStarted();
    if (!isValidCoordinates(row, card)) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    Card c = this.pyramid.get(row).get(card);
    if (c == null || !isExposed(row, card)) {
      throw new IllegalArgumentException("Card is not exposed or already removed.");
    }
    if (c.getValue() != 13) {
      throw new IllegalArgumentException("Card is not a King.");
    }
    this.pyramid.get(row).set(card, null);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    checkGameStarted();
    if (drawIndex < 0 || drawIndex >= this.drawPile.size() || this.drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("Invalid draw index.");
    }
    if (!isValidCoordinates(row, card)) {
      throw new IllegalArgumentException("Invalid pyramid coordinates.");
    }
    Card pyramidCard = this.pyramid.get(row).get(card);
    if (pyramidCard == null || !isExposed(row, card)) {
      throw new IllegalArgumentException("Pyramid card is unavailable.");
    }
    Card drawCard = this.drawPile.get(drawIndex);
    if (pyramidCard.getValue() + drawCard.getValue() != 13) {
      throw new IllegalArgumentException("Cards do not sum to 13.");
    }
    this.pyramid.get(row).set(card, null);
    if (!this.stock.isEmpty()) {
      this.drawPile.set(drawIndex, this.stock.remove(0));
    } else {
      this.drawPile.set(drawIndex, null);
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    checkGameStarted();
    if (drawIndex < 0 || drawIndex >= this.drawPile.size() || this.drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("Invalid draw index.");
    }
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
      throw new IllegalArgumentException("Invalid row coordinate.");
    }
    return row + 1;
  }

  @Override
  public int getScore() {
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
  public Card getCardAt(int row, int card) {
    checkGameStarted();
    if (!isValidCoordinates(row, card)) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    return this.pyramid.get(row).get(card);
  }

  @Override
  public List<Card> getDrawCards() {
    checkGameStarted();
    List<Card> activeDraw = new ArrayList<>();
    for (Card c : this.drawPile) {
      if (c != null) {
        activeDraw.add(c);
      }
    }
    return activeDraw;
  }
}