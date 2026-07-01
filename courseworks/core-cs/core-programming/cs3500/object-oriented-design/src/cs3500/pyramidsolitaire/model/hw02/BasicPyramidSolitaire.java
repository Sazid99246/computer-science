package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw04.AbstractPyramidSolitaire;

public class BasicPyramidSolitaire extends AbstractPyramidSolitaire {

  public BasicPyramidSolitaire() {
    super();
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
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    if (numRows <= 0 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid game dimensions requested.");
    }
    int spacesNeeded = (numRows * (numRows + 1)) / 2;
    validateDeck(deck, 52);

    List<Card> gameDeck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(gameDeck);
    }

    if (gameDeck.size() < spacesNeeded + numDraw) {
      throw new IllegalArgumentException("Insufficient cards to populate requested rules configuration.");
    }

    this.numRows = numRows;
    this.numDraw = numDraw;
    this.pyramid.clear();
    this.stock.clear();
    this.drawPile.clear();

    int cursor = 0;
    for (int r = 0; r < numRows; r++) {
      List<Card> row = new ArrayList<>();
      for (int c = 0; c <= r; c++) {
        row.add(gameDeck.get(cursor++));
      }
      this.pyramid.add(row);
    }

    for (int i = 0; i < numDraw; i++) {
      if (cursor < gameDeck.size()) {
        this.drawPile.add(gameDeck.get(cursor++));
      }
    }

    while (cursor < gameDeck.size()) {
      this.stock.add(gameDeck.get(cursor++));
    }

    this.gameStarted = true;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) {
    checkGameStarted();
    if (!isValidCoordinates(row1, card1) || !isValidCoordinates(row2, card2)) {
      throw new IllegalArgumentException("Coordinates out of bounds.");
    }
    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Cannot pair a card with itself.");
    }
    if (!isExposed(row1, card1) || !isExposed(row2, card2)) {
      throw new IllegalArgumentException("Both cards must be fully uncovered.");
    }
    Card c1 = this.pyramid.get(row1).get(card1);
    Card c2 = this.pyramid.get(row2).get(card2);
    if (c1 == null || c2 == null || c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Cards do not sum to 13.");
    }
    this.pyramid.get(row1).set(card1, null);
    this.pyramid.get(row2).set(card2, null);
  }

  @Override
  public boolean isGameOver() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game not started.");
    }
    if (getScore() == 0) {
      return true;
    }

    List<Card> exposed = new ArrayList<>();
    for (int r = 0; r < this.numRows; r++) {
      for (int c = 0; c < getRowWidth(r); c++) {
        if (this.pyramid.get(r).get(c) != null && isExposed(r, c)) {
          exposed.add(this.pyramid.get(r).get(c));
        }
      }
    }

    for (Card c : exposed) {
      if (c.getValue() == 13) {
        return false;
      }
    }
    for (int i = 0; i < exposed.size(); i++) {
      for (int j = i + 1; j < exposed.size(); j++) {
        if (exposed.get(i).getValue() + exposed.get(j).getValue() == 13) {
          return false;
        }
      }
    }
    List<Card> currentDraw = getDrawCards();
    for (Card dc : currentDraw) {
      for (Card ec : exposed) {
        if (dc.getValue() + ec.getValue() == 13) {
          return false;
        }
      }
    }
    return this.stock.isEmpty() && currentDraw.isEmpty();
  }
}