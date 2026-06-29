package cs3500.lec08;

/**
 * Represents the abstract state of a game of Connect N.
 */
public interface ConnectNModel {
  /**
   * The default width of the grid.
   */
  int DEFAULT_WIDTH = 7;

  /**
   * The default height of the grid.
   */
  int DEFAULT_HEIGHT = 6;

  /**
   * The default length of the line needed to win.
   */
  int DEFAULT_GOAL = 4;

  /**
   * The default number of players.
   */
  int DEFAULT_PLAYERS = 2;
  //
  // Getters:
  //

  int getWidth();

  int getHeight();

  int getGoal();

  int getPlayers();

  Status getStatus();

  /**
   * Determines whether the game is over.
   *
   * @return whether the game is over
   */
  default boolean isGameOver() {
    return getStatus() != Status.Playing;
  }

  /**
   * Gets the player whose turn it is now.
   *
   * <p><strong>PRECONDITION:</strong> the game isn't over
   *
   * @return the next player
   * @throws IllegalStateException if {@code isGameOver()}
   */
  int getNextPlayer();

  /**
   * Returns the winner of the game.
   *
   * <p><strong>PRECONDITION:</strong> the game is over and has a winner
   *
   * @return the winner
   * @throws IllegalStateException if {@code getStatus() != Status.Won}
   */
  int getWinner();

  /**
   * Gets the player whose token is at the given column and row. The coordinates
   * are zero-based and start in the lower left. Returns {@code null} if there
   * is no token in the given position.
   *
   * @param x the column coordinate ({@code 0 <= x < width})
   * @param y the row coordinate ({@code 0 <= y < height})
   * @return the player in the given position, or {@code null}
   * @throws IndexOutOfBoundsException if (x, y) is out of bounds
   */
  Integer getPlayerAt(int x, int y);

  /**
   * Determines whether the specified column is full and thus cannot be played
   * in.
   *
   * @param which the column to check
   * @return whether column {@code which} is full
   * @throws IndexOutOfBoundsException if {@code which} is out of bounds
   */
  default boolean isColumnFull(int which) {
    // A column is full when there's a token at the top
    return getPlayerAt(which, getHeight() - 1) != null;
  }

  /**
   * Plays a move. Given the player (whose turn it must be) and the column
   * number (zero-based from the left), attempts to add that player to that
   * column. If this move ends the game then the game state is updated to
   * reflect that.
   *
   * @param who   the player who is moving
   * @param where which column to play in
   * @return the height of the column after playing
   * @throws IllegalStateException     if the game is over
   * @throws IllegalStateException     if it isn't {@code who}'s turn
   * @throws IllegalStateException     if the requested column is full
   * @throws IndexOutOfBoundsException if the requested column does not exist
   */
  int move(int who, int where);

  /**
   * The game can be ongoing, or it can be over without or with a
   * winner.
   */
  enum Status {Playing, Stalemate, Won,}
}
