package cs3500.lec08;

import java.util.Scanner;

/**
 * Provides a simple console UI for Connect Four.
 */
public final class ConsoleConnectFour {
  public static void main(String[] args) {
    ConnectNModel game = ConnectNModelImpl.connectFour();
    Scanner in = new Scanner(System.in);

    while (!game.isGameOver()) {
      System.out.println(game);

      int who = game.getNextPlayer();
      int where;

      while (true) {
        System.out.print("Player " + who + "? ");
        where = in.nextInt();

        if (where < 0 || where >= game.getWidth()) {
          System.out.println("That column doesn’t exist! Please try again.");
          continue;
        }

        if (game.isColumnFull(where)) {
          System.out.println("That column is full! Please try again.");
          continue;
        }

        break;
      }

      game.move(who, where);
    }

    System.out.println(game);
    System.out.println("Game over!");

    if (game.getStatus() == ConnectNModelImpl.Status.Stalemate) {
      System.out.println("Nobody wins :/");
    } else {
      System.out.printf("Player %d wins :)\n", game.getWinner());
    }
  }
}
