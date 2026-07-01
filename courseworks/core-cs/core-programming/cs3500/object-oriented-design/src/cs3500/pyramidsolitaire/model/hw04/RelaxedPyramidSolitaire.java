package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  public RelaxedPyramidSolitaire() {
    super();
  }

  private boolean isRelaxedExposed(int r1, int c1, int r2, int c2) {
    if (isExposed(r1, c1)) {
      return true;
    }
    if (r2 == r1 + 1 && (c2 == c1 || c2 == c1 + 1)) {
      if (this.pyramid.get(r1).get(c1) == null) {
        return false;
      }
      int leftChildC = c1;
      int rightChildC = c1 + 1;
      Card leftChild = this.pyramid.get(r1 + 1).get(leftChildC);
      Card rightChild = this.pyramid.get(r1 + 1).get(rightChildC);

      if (c2 == leftChildC) {
        return rightChild == null && isExposed(r2, c2);
      } else {
        return leftChild == null && isExposed(r2, c2);
      }
    }
    return false;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) {
    checkGameStarted();
    if (!isValidCoordinates(row1, card1) || !isValidCoordinates(row2, card2)) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Cannot pair card with itself.");
    }

    Card c1 = this.pyramid.get(row1).get(card1);
    Card c2 = this.pyramid.get(row2).get(card2);
    if (c1 == null || c2 == null || c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Cards must add to 13.");
    }

    boolean validPair = (isExposed(row1, card1) && isExposed(row2, card2))
            || isRelaxedExposed(row1, card1, row2, card2)
            || isRelaxedExposed(row2, card2, row1, card1);

    if (!validPair) {
      throw new IllegalArgumentException("The cards are locked by other rules.");
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
    if (!super.isGameOver()) {
      return false;
    }

    for (int r1 = 0; r1 < this.numRows; r1++) {
      for (int c1 = 0; c1 < getRowWidth(r1); c1++) {
        Card card1 = this.pyramid.get(r1).get(c1);
        if (card1 == null) {
          continue;
        }
        if (r1 < this.numRows - 1) {
          int r2 = r1 + 1;
          int[] cols = {c1, c1 + 1};
          for (int c2 : cols) {
            Card card2 = this.pyramid.get(r2).get(c2);
            if (card2 != null && card1.getValue() + card2.getValue() == 13) {
              if (isRelaxedExposed(r1, c1, r2, c2)) {
                return false;
              }
            }
          }
        }
      }
    }
    return true;
  }
}