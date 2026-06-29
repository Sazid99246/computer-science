package cs3500.lec08;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a Connect-N game, which is a generalization of Connect Four. This
 * class represents the state of the game and allows making moves.
 */
public final class ConnectNModelImpl implements ConnectNModel {
  /**
   * Offsets specifying the directions in which lines may be made.
   */
  private static final int[][] DIRECTIONS =
          new int[][]{{0, 1},  // Up and down
                  {1, 0},  // Left and right
                  {1, 1},  // Up-right and down-left
                  {1, -1},  // Up-left and down-right
          };
  private final int width;
  private final int height;
  private final int goal;         // the length of line needed to win
  private final int players;
  private final List<List<Integer>> columns;
  private Status status;
  private int turn;           // if the status is Won then turn is the winner

  private ConnectNModelImpl(int w, int h, int g, int p) {
    width = w;
    height = h;
    goal = g;
    players = p;

    status = Status.Playing;
    turn = 0;

    columns = new ArrayList<>(width);
    for (int i = 0; i < width; ++i) {
      columns.add(new ArrayList<>(height));
    }
  }

  /**
   * Constructs a new game model with the given parameters.
   *
   * @param width   the width of the grid (positive)
   * @param height  the height of the grid (positive)
   * @param goal    the goal line length for the game ({@code > 1})
   * @param players the array of player names (non-null, non-empty, and
   *                each element non-null)
   * @return the new game model
   * @see #connectFour()
   * @see #builder()
   */
  public static ConnectNModel connectN(int width, int height,
                                       int goal, int players) {
    // Checked by the builder:
    /*
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("dimensions must be positive");
    }

    if (goal < 2) {
      throw new IllegalArgumentException("N must be at least 2");
    }

    Objects.requireNonNull(playerFactory);
    */

    return builder()
            .width(width)
            .height(height)
            .goal(goal)
            .players(players)
            .build();
  }

  /**
   * Constructs a new game model for Connect Four with the default parameters.
   *
   * @return the new game model
   * @see #connectN(int, int, int, int)
   * @see #builder()
   */
  public static ConnectNModel connectFour() {
    return builder().build();
  }

  /**
   * Constructs a builder for configuring and then creating a game model
   * instance. Defaults to a game of Connect Four with players named “White”
   * and “Red”.
   *
   * @return the new builder
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getGoal() {
    return goal;
  }

  @Override
  public int getPlayers() {
    return players;
  }

  @Override
  public Status getStatus() {
    return status;
  }

  @Override
  public int getNextPlayer() {
    if (isGameOver()) {
      throw new IllegalStateException("the game is over");
    }

    return turn;
  }

  @Override
  public int getWinner() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over");
    }

    return turn;
  }

  @Override
  public Integer getPlayerAt(int x, int y) {
    ensureBounds(x, y);

    List<Integer> column = columns.get(x);

    if (y < column.size()) {
      return column.get(y);
    } else {
      return null;
    }
  }

  @Override
  public int move(int who, int where) {
    if (status != Status.Playing) {
      throw new IllegalStateException("game over");
    }

    if (who != turn) {
      throw new IllegalStateException("out of turn");
    }

    // every column has a row 0, so this effectively checks the column:
    ensureBounds(where, 0);

    if (isColumnFull(where)) {
      throw new IllegalStateException("attempt to play in full column");
    }

    /* The column list is only long enough to contain the tokens played
     * thus far, so it's possible that a valid row is above the filled
     * portion of the column. */
    List<Integer> column = columns.get(where);
    int row = column.size();
    column.add(who);

    if (!checkForWinner(where, row) && !checkForStalemate()) {
      turn = (turn + 1) % players;
    }

    return row;
  }

  /**
   * Checks for a winner and updates the state. Takes the coordinates of
   * the most recent move and looks for newly completed lines starting there.
   * If a winner is found, updates the state to reflect this.
   *
   * @param x the column of the most recent move
   * @param y the row of the most recent move
   * @return whether the game is over
   */
  private boolean checkForWinner(int x, int y) {
    for (int[] dir : DIRECTIONS) {
      int dx = dir[0];
      int dy = dir[1];

      // We count in the forward direction using the pair of offsets and the
      // backward direction by using their opposites.
      int fwd = countInDirection(x, y, dx, dy);
      int bwd = countInDirection(x, y, -dx, -dy);

      // Counting both directions and the current move, if we find a line
      // longer than goal then the game is won! (We need to subtract 1 so
      // that we don't double-count the current move.)
      if (fwd - 1 + bwd >= goal) {
        status = Status.Won;
        return true;
      }
    }

    return false;
  }

  /**
   * Counts the length of the same-player line in a given direction. Includes
   * the player at the specified coordinates in the count. The direction is
   * specified as x and y offsets.
   *
   * @param x  column to start at
   * @param y  row to start at
   * @param dx step in the x direction
   * @param dy step in the y direction
   * @return the length of the line in the given direction
   */
  private int countInDirection(int x, int y, int dx, int dy) {
    int count = 0;
    Integer player = getPlayerAt(x, y);

    // There should always be a token at (x, y) when this method is called
    assert (player != null);

    while (areInBounds(x, y)) {
      // Important that player is the receiver here, since getPlayerAt may
      // return null
      if (player.equals(getPlayerAt(x, y))) {
        ++count;
        x += dx;
        y += dy;
      } else {
        break;
      }
    }

    return count;
  }

  /**
   * Sets the game state to stalemate if the grid is full with no winner.
   *
   * @return whether the game is over
   */
  private boolean checkForStalemate() {
    for (List<?> column : columns) {
      if (column.size() < getHeight()) {
        return false;
      }
    }

    status = Status.Stalemate;
    return true;
  }

  /**
   * Ensures that the coordinates are in bounds for this game.
   *
   * @param x the column, 0-based from left
   * @param y the row, 0-based from bottom
   * @throws IndexOutOfBoundsException if (x, y) are out of bounds
   */
  private void ensureBounds(int x, int y) {
    if (!areInBounds(x, y)) {
      throw new IndexOutOfBoundsException("coordinates are out of bounds");
    }
  }

  /**
   * Checks whether the coordinates are in bounds for this game.
   *
   * @param x the column, 0-based from left
   * @param y the row, 0-based from bottom
   * @return whether (x, y) are in bounds
   */
  private boolean areInBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    // Assumption: there are no more than 10 players, or this gets wacky
    for (int y = height - 1; y >= 0; --y) {
      for (int x = 0; x < width; ++x) {
        sb.append('|');

        Integer player = getPlayerAt(x, y);

        if (player == null) {
          sb.append(' ');
        } else {
          sb.append(player);
        }
      }

      sb.append("|\n");
    }

    for (int i = 0; i < width; ++i) {
      sb.append("+-");
    }
    sb.append("+\n");

    for (int i = 0; i < width; ++i) {
      sb.append(" " + i % 10);
    }
    sb.append('\n');

    return sb.toString();
  }

  /**
   * Builds a {@link ConnectNModelImpl}, allowing the client to configure several
   * parameters. This is an instance of the <em>builder pattern</em>.
   */
  public static final class Builder {
    /*
     * INVARIANTS:
     *  - width is positive
     *  - height is positive
     *  - goal > 1
     *  - players is non-null, non-empty, and elements are non-null
     */
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int goal = DEFAULT_GOAL;
    private int players = DEFAULT_PLAYERS;

    /**
     * Builds and returns the specified {@link ConnectNModelImpl}.
     *
     * @return a new {@code ConnectNModel}
     */
    public ConnectNModelImpl build() {
      return new ConnectNModelImpl(width, height, goal, players);
    }

    /**
     * Sets the width of the game grid.
     *
     * @param width the width (positive)
     * @return {@code this}, for method chaining
     */
    public Builder width(int width) {
      if (width < 1) {
        throw new IllegalArgumentException("dimensions must be positive");
      }

      this.width = width;
      return this;
    }

    /**
     * Sets the height of the game grid.
     *
     * @param height the height (positive)
     * @return {@code this}, for method chaining
     */
    public Builder height(int height) {
      if (height < 1) {
        throw new IllegalArgumentException("dimensions must be positive");
      }

      this.height = height;
      return this;
    }

    /**
     * Sets the goal line length for the game.
     *
     * @param goal the goal (positive)
     * @return {@code this}, for method chaining
     */
    public Builder goal(int goal) {
      if (goal < 2) {
        throw new IllegalArgumentException("N must be at least 2");
      }

      this.goal = goal;
      return this;
    }

    /**
     * Sets the players for the game.
     *
     * @param players the array of player names (non-null, non-empty,
     *                and every element non-null)
     * @return {@code this}, for method chaining
     */
    public Builder players(int players) {
      if (players == 0) {
        throw new IllegalArgumentException("must have at least one player");
      }

      this.players = players;
      return this;
    }
  }
}
