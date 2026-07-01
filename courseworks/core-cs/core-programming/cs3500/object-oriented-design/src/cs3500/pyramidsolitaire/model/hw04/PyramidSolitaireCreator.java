package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public class PyramidSolitaireCreator {

  public static PyramidSolitaireModel<Card> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null.");
    }
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case TRIPEAKS:
        return new TriPeaksPyramidSolitaire();
      default:
        throw new IllegalArgumentException("Unknown type configuration.");
    }
  }

  public enum GameType {
    BASIC, RELAXED, TRIPEAKS
  }
}