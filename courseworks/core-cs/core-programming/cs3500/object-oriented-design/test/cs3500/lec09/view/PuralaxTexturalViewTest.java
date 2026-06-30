package cs3500.lec09.view;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.common.IColor;
import cs3500.lec09.model.BasicPuralaxModel;
import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.model.tile.EmptyTile;
import cs3500.lec09.model.tile.NestedTile;
import cs3500.lec09.model.tile.SimpleTile;
import cs3500.lec09.model.tile.Tile;
import cs3500.lec09.model.tile.WallTile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuralaxTexturalViewTest {
  @Test
  void testToSting_gameNotStarted() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  □
    Tile[][] board = {
            {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
//    model.startGame(board, color);
    PuralaxView view = new PuralaxTexturalView(model);
    String expected = "Game not started.\n";
    assertEquals(expected, view.toString());
  }

  @Test
  void testToSting_gameWin() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 □  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
            {new SimpleTile(GameColor.BLUE, 2), new EmptyTile(), new EmptyTile()},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    PuralaxView view = new PuralaxTexturalView(model);
    String expected =
            "Game goal color: Blue\n" +
                    "[Bl*2] [  ] [  ]\n" +
                    "[  ]   [  ] [  ]\n" +
                    "[  ]   [  ] [  ]\n" +
                    "You Win!\n";
    assertEquals(expected, view.toString());
  }

  @Test
  void testToSting_gameOver() {
    PuralaxModel model = new BasicPuralaxModel();
    // R  □  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
            {new SimpleTile(GameColor.RED, 0), new EmptyTile(), new EmptyTile()},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    PuralaxView view = new PuralaxTexturalView(model);
    String expected =
            "Game goal color: Blue\n" +
                    "[Rd] [  ] [  ]\n" +
                    "[  ] [  ] [  ]\n" +
                    "[  ] [  ] [  ]\n" +
                    "Game Over.\n";
    assertEquals(expected, view.toString());
  }

  @Test
  void testToSting_gameProcess() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  □
    Tile[][] board = {
            {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    PuralaxView view = new PuralaxTexturalView(model);
    String expected =
            "Game goal color: Blue\n" +
                    "[Bl*2] [Gn] [Gn]\n" +
                    "[Rd]   [Gn] [Gn]\n" +
                    "[  ]   [  ] [  ]\n";
    assertEquals(expected, view.toString());
  }


  @Test
  void testRender_appendToStringBuilder() throws IOException {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  □
    Tile[][] board = {
            {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    StringBuilder out = new StringBuilder();
    PuralaxView view = new PuralaxTexturalView(model, out);
    String expected =
            "Game goal color: Blue\n" +
                    "[Bl*2] [Gn] [Gn]\n" +
                    "[Rd]   [Gn] [Gn]\n" +
                    "[  ]   [  ] [  ]\n";

    view.render();
    assertEquals(expected, out.toString());
  }

  @Test
  void testToSting_wallTile() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // ■  ■  □
    Tile[][] board = {
            {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
                    new SimpleTile(GameColor.GREEN, 0)},
            {new WallTile(), new WallTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    PuralaxView view = new PuralaxTexturalView(model);
    String expected =
            "Game goal color: Blue\n" +
                    "[Bl*2] [Gn] [Gn]\n" +
                    "[Rd]   [Gn] [Gn]\n" +
                    "[##]   [##] [  ]\n";
    assertEquals(expected, view.toString());
  }

  @Test
  void testToString_NestedTile() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile nestedTile = new NestedTile(new ArrayList<>(List.of(
            GameColor.RED,
            GameColor.YELLOW,
            GameColor.GREEN,
            GameColor.BLUE,
            GameColor.PURPLE
    )));
    Tile[][] board = {
            {new EmptyTile(),
                    new SimpleTile(GameColor.YELLOW, 1),
                    new EmptyTile()},
            {new SimpleTile(GameColor.GREEN, 1),
                    nestedTile,
                    new SimpleTile(GameColor.BLUE, 1)},
            {new EmptyTile(),
                    new SimpleTile(GameColor.PURPLE, 1),
                    new EmptyTile()},
    };
    IColor goalColor = GameColor.PURPLE;
    model.startGame(board, goalColor);
    // Game goal color: Purple
    // [  ]   [Yl*1]           [  ]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [  ]   [Pu*1]           [  ]
    PuralaxView view = new PuralaxTexturalView(model);

    String expected =
            "Game goal color: Purple\n" +
                    "[  ]   [Yl*1]           [  ]  \n" +
                    "[Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]\n" +
                    "[  ]   [Pu*1]           [  ]  \n";

    assertEquals(expected, view.toString());

  }
}