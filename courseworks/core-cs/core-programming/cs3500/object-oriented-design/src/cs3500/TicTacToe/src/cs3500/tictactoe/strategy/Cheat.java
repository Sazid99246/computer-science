package cs3500.TicTacToe.src.cs3500.tictactoe.strategy;

import cs3500.tictactoe.model.Piece;
import cs3500.tictactoe.model.TicTacToeModel;

/**
 * A Strategy: when no one's looking fill in the board and claim to "move"
 * to the last spot to win.
 */
public class Cheat implements PosnStrategy {
  @Override
  public Posn choosePosn(TicTacToeModel model, Piece player) {
    Posn ans = null;
    for (int c = 0; c < model.getWidth(); c++)
      for (int r = 0; r < model.getHeight(); r++) {
        if (model.getPieceAt(r, c) == Piece.EMPTY) {
          if (ans == null) {
            ans = new Posn(r, c);
          } else {
            model.setPieceAt(r, c, player);
          }
        }
      }
    return ans;
  }
}
