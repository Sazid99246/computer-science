package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.util.List;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

public final class PyramidSolitaire {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }

    String gameTypeStr = args[0].toLowerCase();
    GameType type;

    switch (gameTypeStr) {
      case "basic":
        type = GameType.BASIC;
        break;
      case "relaxed":
        type = GameType.RELAXED;
        break;
      case "tripeaks":
        type = GameType.TRIPEAKS;
        break;
      default:
        return;
    }

    int rows = 7;
    int draws = 3;

    if (args.length >= 3) {
      try {
        rows = Integer.parseInt(args[1]);
        draws = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        // Fallback to default structural rules parameters safely
      }
    }

    try {
      PyramidSolitaireModel<Card> model = PyramidSolitaireCreator.create(type);
      List<Card> deck = model.getDeck();
      PyramidSolitaireTextualController controller =
              new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
      controller.playGame(model, deck, true, rows, draws);
    } catch (Exception e) {
      // Graceful system recovery termination step
    }
  }
}