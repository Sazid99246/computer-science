package cs3500.pyramidsolitaire.view;

import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents a textual view of the Pyramid Solitaire game state.
 */
public class PyramidSolitaireTextualView {
  private final PyramidSolitaireModel<?> model;

  /**
   * Constructs a textual view for the given Pyramid Solitaire model.
   *
   * @param model the model to be viewed
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  @Override
  public String toString() {
    // 1. If the game has not been started, return an empty string
    int numRows;
    try {
      numRows = this.model.getNumRows();
      if (numRows == -1) {
        return "";
      }
    } catch (IllegalStateException e) {
      return "";
    }

    // 2. If the pyramid is completely empty, the player wins
    if (this.model.getScore() == 0) {
      return "You win!";
    }

    // 3. If the game is over and no moves remain, print the game over status
    if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }

    // 4. Otherwise, render the active game state grid
    StringBuilder sb = new StringBuilder();

    for (int r = 0; r < numRows; r++) {
      // Add padding spaces on the left side of the row to visually center/align the pyramid
      // Each card is 3 characters wide, plus 1 space between cards.
      // The indentation shrinks by 2 spaces for each subsequent row.
      int numLeadingSpaces = (numRows - 1 - r) * 2;
      for (int s = 0; s < numLeadingSpaces; s++) {
        sb.append(" ");
      }

      int rowWidth = this.model.getRowWidth(r);
      for (int c = 0; c < rowWidth; c++) {
        Object card = this.model.getCardAt(r, c);

        if (card == null) {
          sb.append("   "); // 3 spaces for empty slot padding
        } else {
          String cardStr = card.toString();
          sb.append(cardStr);
          if (cardStr.length() == 2) {
            sb.append(" "); // Add a trailing padding space if the card string is short (e.g., "A♣")
          }
        }

        // Add 1 separating space between columns, but NOT after the last card slot in the row
        if (c < rowWidth - 1) {
          sb.append(" ");
        }
      }

      // Strip off trailing whitespace if the final card positions in this row were empty spaces
      while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
        sb.deleteCharAt(sb.length() - 1);
      }

      sb.append("\n");
    }

    // Render the draw line underneath the pyramid grid
    sb.append("Draw:");
    List<?> drawCards = this.model.getDrawCards();
    if (!drawCards.isEmpty()) {
      sb.append(" "); // Space after "Draw:" only if there are items following it
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