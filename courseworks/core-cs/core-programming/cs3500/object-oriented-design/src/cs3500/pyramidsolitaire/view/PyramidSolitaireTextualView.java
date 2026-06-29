package cs3500.pyramidsolitaire.view;

import java.io.IOException;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents a textual view of the Pyramid Solitaire game state.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable appendable;

  /**
   * Traditional constructor taking only a model (defaults appendable to null or unused).
   *
   * @param model the model to be viewed
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.appendable = null;
  }

  /**
   * New constructor taking both a model and an Appendable destination.
   *
   * @param model      the model to be viewed
   * @param appendable the destination for rendering output
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    if (this.appendable == null) {
      throw new IllegalStateException("Appendable object was not provided to this view.");
    }
    this.appendable.append(this.toString());
  }

  @Override
  public String toString() {
    // Keep your exact toString() implementation from Assignment 2 here intact.
    int numRows;
    try {
      numRows = this.model.getNumRows();
      if (numRows == -1) {
        return "";
      }
    } catch (IllegalStateException e) {
      return "";
    }

    if (this.model.getScore() == 0) {
      return "You win!";
    }

    if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }

    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < numRows; r++) {
      int numLeadingSpaces = (numRows - 1 - r) * 2;
      for (int s = 0; s < numLeadingSpaces; s++) {
        sb.append(" ");
      }

      int rowWidth = this.model.getRowWidth(r);
      for (int c = 0; c < rowWidth; c++) {
        Object card = this.model.getCardAt(r, c);
        if (card == null) {
          sb.append("   ");
        } else {
          String cardStr = card.toString();
          sb.append(cardStr);
          if (cardStr.length() == 2) {
            sb.append(" ");
          }
        }
        if (c < rowWidth - 1) {
          sb.append(" ");
        }
      }

      while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
        sb.deleteCharAt(sb.length() - 1);
      }
      sb.append("\n");
    }

    sb.append("Draw:");
    List<?> drawCards = this.model.getDrawCards();
    if (!drawCards.isEmpty()) {
      sb.append(" ");
      for (int i = 0; i < drawCards.size(); i++) {
        sb.append(drawCards.get(i).toString());
        if (i < drawCards.size() - 1) {
          sb.append(", ");
        }
      }
    }

    return sb.toString();
  }
}