package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a single playing card in a standard 52-card deck for Pyramid Solitaire.
 */
public class Card {

  private final int value; // 1 (Ace) through 13 (King)
  private final Suit suit;
  /**
   * Constructs a Card with a specific value and suit.
   *
   * @param value the value of the card (1 to 13)
   * @param suit  the suit of the card
   * @throws IllegalArgumentException if the value is not between 1 and 13 inclusive,
   *                                  or if the suit is null
   */
  public Card(int value, Suit suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Card value must be between 1 and 13 inclusive.");
    }
    if (suit == null) {
      throw new IllegalArgumentException("Suit cannot be null.");
    }
    this.value = value;
    this.suit = suit;
  }

  /**
   * Returns the numeric value of this card.
   *
   * @return the value (1-13)
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the suit of this card.
   *
   * @return the suit
   */
  public Suit getSuit() {
    return this.suit;
  }

  /**
   * Returns a string representation of the card.
   * Number cards use their number, face cards use initials (A, J, Q, K).
   */
  @Override
  public String toString() {
    String valueStr;
    switch (this.value) {
      case 1:
        valueStr = "A";
        break;
      case 11:
        valueStr = "J";
        break;
      case 12:
        valueStr = "Q";
        break;
      case 13:
        valueStr = "K";
        break;
      default:
        valueStr = String.valueOf(this.value);
        break;
    }
    return valueStr + this.suit.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return this.value == card.value && this.suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value, this.suit);
  }

  /**
   * Represents the four standard card suits.
   */
  public enum Suit {
    CLUBS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥'),
    SPADES('♠');

    private final char symbol;

    Suit(char symbol) {
      this.symbol = symbol;
    }

    @Override
    public String toString() {
      return String.valueOf(symbol);
    }
  }
}