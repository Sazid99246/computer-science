package edu.cs3500.spreadsheets.model;

import java.util.Objects;


/**
 * A value type representing coordinates in a Worksheet}.
 */
public class Coord {

  public final int row;
  public final int col;

  /**
   * <p>Integer row and col constructor.</p>
   *
   * @param col the column.
   * @param row the row.
   */
  public Coord(int col, int row) {
    if (row < 1 || col < 1) {
      throw new IllegalArgumentException("Coordinates should be strictly positive");
    }
    this.row = row;
    this.col = col;
  }

  /**
   * <p>String constructor.</p>
   *
   * @param s the string form of the coordinate
   */
  public Coord(String s) {
    StringBuilder sbNums = new StringBuilder();
    StringBuilder sbLetters = new StringBuilder();
    boolean seenDigit = false;

    for (Character c : s.toCharArray()) {
      if (Character.isDigit(c)) {
        seenDigit = true;
        sbNums.append(c);
      } else if (seenDigit && Character.isAlphabetic(c)) {
        throw new IllegalArgumentException("Malformed input.");
      } else {
        sbLetters.append(c);
      }
    }

    this.row = Integer.valueOf(sbNums.toString());

    this.col = Coord.colNameToIndex(sbLetters.toString());
  }


  /**
   * <p>Converts from the A-Z column naming system to a 1-indexed numeric value.</p>
   *
   * @param name the column name
   * @return the corresponding column index
   */
  public static int colNameToIndex(String name) {
    name = name.toUpperCase();
    int ans = 0;
    for (int i = 0; i < name.length(); i++) {
      ans *= 26;
      ans += (name.charAt(i) - 'A' + 1);
    }
    return ans;
  }

  /**
   * <p>Converts a 1-based column index into the A-Z column naming system.</p>
   *
   * @param index the column index
   * @return the corresponding column name
   */
  public static String colIndexToName(int index) {
    StringBuilder ans = new StringBuilder();
    while (index > 0) {
      int colNum = (index - 1) % 26;
      ans.insert(0, Character.toChars('A' + colNum));
      index = (index - colNum) / 26;
    }
    return ans.toString();
  }

  @Override
  public String toString() {
    return colIndexToName(this.col) + this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row
        && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
