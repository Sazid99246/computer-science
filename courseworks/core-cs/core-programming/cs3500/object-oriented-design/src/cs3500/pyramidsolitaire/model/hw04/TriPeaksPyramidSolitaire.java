package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.Card;

public class TriPeaksPyramidSolitaire extends AbstractPyramidSolitaire {

  public TriPeaksPyramidSolitaire() {
    super();
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      for (Card.Suit suit : Card.Suit.values()) {
        for (int value = 1; value <= 13; value++) {
          deck.add(new Card(value, suit));
        }
      }
    }
    return deck;
  }

  @Override
  public int getRowWidth(int row) {
    // Replace checkGameStarted() with a manual unstarted check if that's what's required by your assignment suite:
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not been started yet.");
    }
    if (row < 0 || row >= this.numRows) {
      throw new IllegalArgumentException("Row out of bounds.");
    }
    int overlapRow = (int) Math.ceil(this.numRows / 2.0);
    if (row < overlapRow) {
      return (row + 1) * 3;
    }
    return row + 1 + (overlapRow * 2);
  }

  // Add this helper inside TriPeaksPyramidSolitaire.java
  private int calculateRowWidthHelper(int row, int totalRows) {
    int overlapRow = (int) Math.ceil(totalRows / 2.0);
    if (row < overlapRow) {
      return (row + 1) * 3;
    }
    return row + 1 + (overlapRow * 2);
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    if (numRows <= 0 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid layout inputs.");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    validateDeck(deck, 104);

    List<Card> gameDeck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(gameDeck);
    }

    this.pyramid.clear();
    this.stock.clear();
    this.drawPile.clear();

    int overlapRow = (int) Math.ceil(numRows / 2.0);
    int cursor = 0;

    for (int r = 0; r < numRows; r++) {
      List<Card> row = new ArrayList<>();
      int width = calculateRowWidthHelper(r, numRows);
      for (int c = 0; c < width; c++) {
        if (r < overlapRow) {
          int peakWidth = r + 1;
          if (c < peakWidth || (c >= peakWidth * 2 && c < peakWidth * 3)
                  || (c >= peakWidth && c < peakWidth * 2)) {
            int currentPeak = c / peakWidth;
            int currentPeakCol = c % peakWidth;

            int peak1End = peakWidth;
            int peak2Start = peakWidth + (overlapRow - 1 - r);
            int peak2End = peak2Start + peakWidth;
            int peak3Start = (peakWidth * 2) + ((overlapRow - 1 - r) * 2);

            boolean isBlank = false;
            if (c >= peak1End && c < peak2Start) {
              isBlank = true;
            }
            if (c >= peak2End && c < peak3Start) {
              isBlank = true;
            }

            if (isBlank) {
              row.add(null);
            } else {
              row.add(gameDeck.get(cursor++));
            }
          } else {
            row.add(gameDeck.get(cursor++));
          }
        } else {
          row.add(gameDeck.get(cursor++));
        }
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
      throw new IllegalArgumentException("Out of bounds.");
    }
    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Same card.");
    }
    if (!isExposed(row1, card1) || !isExposed(row2, card2)) {
      throw new IllegalArgumentException("Cards not fully open.");
    }
    Card c1 = this.pyramid.get(row1).get(card1);
    Card c2 = this.pyramid.get(row2).get(card2);
    if (c1 == null || c2 == null || c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Sum mismatch.");
    }
    this.pyramid.get(row1).set(card1, null);
    this.pyramid.get(row2).set(card2, null);
  }

  @Override
  public boolean isGameOver() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game not running.");
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