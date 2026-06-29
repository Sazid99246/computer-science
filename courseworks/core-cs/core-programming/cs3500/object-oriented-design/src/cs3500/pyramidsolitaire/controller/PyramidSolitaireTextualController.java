package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A textual controller implementation for running a game of Pyramid Solitaire.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a controller with input and output sources.
   *
   * @param rd input source stream
   * @param ap output destination stream
   * @throws IllegalArgumentException if either parameter is null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    try {
      // 1. Try starting the game
      try {
        model.startGame(deck, shuffle, numRows, numDraw);
      } catch (IllegalArgumentException | NullPointerException e) {
        throw new IllegalStateException("Game cannot be started with these parameters: " + e.getMessage());
      }

      Scanner scanner = new Scanner(this.rd);
      PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);

      // Loop game operations until terminal conditions are met
      while (true) {
        // Transmit game state and current score
        view.render();
        this.ap.append("\n");
        this.ap.append("Score: ").append(String.valueOf(model.getScore())).append("\n");

        if (model.isGameOver()) {
          break;
        }

        // Fetch command token
        if (!scanner.hasNext()) {
          throw new IllegalStateException("Readable source ran out of inputs prematurely.");
        }
        String cmd = scanner.next();

        if (cmd.equalsIgnoreCase("q")) {
          handleQuit(view, model);
          return;
        }

        // Process known moves
        switch (cmd) {
          case "rm1":
            Integer r1 = readNextValue(scanner);
            if (r1 == null) { handleQuit(view, model); return; }
            Integer c1 = readNextValue(scanner);
            if (c1 == null) { handleQuit(view, model); return; }

            try {
              model.remove(r1 - 1, c1 - 1);
            } catch (IllegalArgumentException e) {
              this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
            }
            break;

          case "rm2":
            Integer row1 = readNextValue(scanner);
            if (row1 == null) { handleQuit(view, model); return; }
            Integer card1 = readNextValue(scanner);
            if (card1 == null) { handleQuit(view, model); return; }
            Integer row2 = readNextValue(scanner);
            if (row2 == null) { handleQuit(view, model); return; }
            Integer card2 = readNextValue(scanner);
            if (card2 == null) { handleQuit(view, model); return; }

            try {
              model.remove(row1 - 1, card1 - 1, row2 - 1, card2 - 1);
            } catch (IllegalArgumentException e) {
              this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
            }
            break;

          case "rmwd":
            Integer drawIdx = readNextValue(scanner);
            if (drawIdx == null) { handleQuit(view, model); return; }
            Integer pyrRow = readNextValue(scanner);
            if (pyrRow == null) { handleQuit(view, model); return; }
            Integer pyrCard = readNextValue(scanner);
            if (pyrCard == null) { handleQuit(view, model); return; }

            try {
              model.removeUsingDraw(drawIdx - 1, pyrRow - 1, pyrCard - 1);
            } catch (IllegalArgumentException e) {
              this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
            }
            break;

          case "dd":
            Integer dIdx = readNextValue(scanner);
            if (dIdx == null) { handleQuit(view, model); return; }

            try {
              model.discardDraw(dIdx - 1);
            } catch (IllegalArgumentException e) {
              this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
            }
            break;

          default:
            // Unexpected command tokens are simply ignored or re-prompted implicitly
            break;
        }
      }

    } catch (IOException e) {
      throw new IllegalStateException("Transmission failed across IO channels.", e);
    }
  }

  /**
   * Continuously pulls tokens from the scanner until a valid integer or a 'q' is read.
   * Returns null if the user chooses to quit.
   */
  private Integer readNextValue(Scanner scanner) {
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        return scanner.nextInt();
      }
      String token = scanner.next();
      if (token.equalsIgnoreCase("q")) {
        return null;
      }
      // If it is an invalid non-integer string, loop continues to ask/wait for input
    }
    throw new IllegalStateException("Input stream terminated unexpectedly.");
  }

  /**
   * Helper to write out the requested payload block when a user aborts.
   */
  private <K> void handleQuit(PyramidSolitaireView view, PyramidSolitaireModel<K> model) throws IOException {
    this.ap.append("Game quit!\n");
    this.ap.append("State of game when quit:\n");
    view.render();
    this.ap.append("\n");
    this.ap.append("Score: ").append(String.valueOf(model.getScore())).append("\n");
  }
}