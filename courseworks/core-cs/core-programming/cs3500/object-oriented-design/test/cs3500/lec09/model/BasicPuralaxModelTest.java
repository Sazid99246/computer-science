package cs3500.lec09.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.common.IColor;
import java.util.ArrayList;
import java.util.List;
import cs3500.lec09.model.tile.EmptyTile;
import cs3500.lec09.model.tile.NestedTile;
import cs3500.lec09.model.tile.PainterTile;
import cs3500.lec09.model.tile.SimpleTile;
import cs3500.lec09.model.tile.Tile;
import cs3500.lec09.model.tile.WallTile;
import org.junit.jupiter.api.Test;

class BasicPuralaxModelTest {

  @Test
  void testStartGame_rejectNullBoard() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile[][] board = null;
    IColor color = GameColor.RED;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_rejectNullColor() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile[][] board = {{new EmptyTile()}};
    IColor color = null;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_rejectZeroHeightBoard() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile[][] board = {};
    IColor color = GameColor.RED;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_rejectZeroWidthBoard() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile[][] board = {{}, {}, {}};
    IColor color = GameColor.RED;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_rejectNotRectBoard() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile t = new EmptyTile();
    Tile[][] board = {{t}, {t, t}};
    IColor color = GameColor.RED;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_rejectHasNullBoard() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile t = new EmptyTile();
    Tile[][] board = {{t, null}, {t, t}};
    IColor color = GameColor.RED;
    assertThrows(
        IllegalArgumentException.class,
        () -> model.startGame(board, color)
    );
  }

  @Test
  void testStartGame_validInput() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile t = new EmptyTile();
    Tile[][] board = {{t, t, t}, {t, t, t}};
    IColor color = GameColor.RED;
    model.startGame(board, color);
    assertEquals(2, model.getNumRows());
    assertEquals(3, model.getNumCols());
  }

  @Test
  void testMoveTile_GameNotStarted() {
    PuralaxModel model = new BasicPuralaxModel();
    // B □ □
    // □ □ □
    // □ □ □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 1), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    // model.startGame(board, color);
    assertThrows(
        IllegalStateException.class,
        () -> model.moveTile(0, 0, 0, 1)
    );
  }

  @Test
  void testMoveTile_rejectOutRangePosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B □ □
    // □ □ □
    // □ □ □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 1), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(-1, 0, 0, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(3, 0, 2, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, -1, 0, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 3, 0, 2)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 0, -1, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(2, 0, 3, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 0, 0, -1)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 2, 0, 3)
    );
  }

  @Test
  void testMoveTile_rejectNotAdjacentPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B □ □
    // □ □ □
    // □ □ □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 1), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 0, 1, 1)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.moveTile(0, 0, 0, 2)
    );
  }

  @Test
  void testMoveTile_rejectCannotMoveFromPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B  □  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 0), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    // 无法移动空单元格
    assertThrows(
        IllegalStateException.class,
        () -> model.moveTile(1, 0, 1, 1)
    );
    // 无法移动点数为 0 的图块
    assertThrows(
        IllegalStateException.class,
        () -> model.moveTile(0, 0, 0, 1)
    );
  }

  @Test
  void testMoveTile_rejectCannotMoveToPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B1 G1 □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 1), new SimpleTile(GameColor.GREEN, 1), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    // 无法移动到其他图块上
    assertThrows(
        IllegalStateException.class,
        () -> model.moveTile(0, 0, 0, 1)
    );
  }

  @Test
  void testMoveTile() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G1 □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 1), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);

    model.moveTile(0, 0, 1, 0);
    // □  G1 □
    // B1 □  □
    // □  □  □
    Tile t = model.getTileAt(1, 0);
    assertEquals(GameColor.BLUE, t.getColor());
    assertEquals(1, t.getDots());

    model.moveTile(0, 1, 0, 2);
    // □  □  G
    // B1 □  □
    // □  □  □
    Tile t1 = model.getTileAt(0, 2);
    assertEquals(GameColor.GREEN, t1.getColor());
    assertEquals(0, t1.getDots());
  }

  @Test
  void testPaint_GameNotStarted() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G1 □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 1), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
//    model.startGame(board, color);
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(0, 0, 0, 1)
    );
  }

  @Test
  void testPaint_rejectOutRangePosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G1 □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 1), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(-1, 0, 0, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(3, 0, 2, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, -1, 0, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 3, 0, 2)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 0, -1, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(2, 0, 3, 0)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 0, 0, -1)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 2, 0, 3)
    );
  }

  @Test
  void testPaint_rejectNotAdjacentPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G1 □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 1), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 0, 1, 1)
    );
    assertThrows(
        IllegalArgumentException.class,
        () -> model.paint(0, 0, 0, 2)
    );
  }

  @Test
  void testPaint_rejectCannotPaintSourcePosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    // 空单元格不能进行涂色操作
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(1, 0, 0, 0)
    );
    // 没有点数的图块不能涂色操作
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(0, 1, 0, 0)
    );
  }

  @Test
  void testPaint_rejectCannotPaintedTargetPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    // 空单元格不能被进行涂色操作
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(0, 0, 1, 0)
    );
  }

  @Test
  void testPaint_rejectTwoEqualColorPosition() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 B  □
    // □  □  □
    // □  □  □
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.BLUE, 0), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);
    // 两个相同颜色的图块不能涂色操作
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(0, 0, 0, 1)
    );
  }

  @Test
  void testPaint() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  R
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new SimpleTile(GameColor.RED, 0)}
    };
    IColor color = GameColor.RED;
    model.startGame(board, color);

    // 根据行列命名 tile
    Tile t00 = model.getTileAt(0, 0);
    Tile t01 = model.getTileAt(0, 1);
    Tile t02 = model.getTileAt(0, 2);
    Tile t10 = model.getTileAt(1, 0);
    Tile t11 = model.getTileAt(1, 1);
    Tile t12 = model.getTileAt(1, 2);
    Tile t20 = model.getTileAt(2, 0);
    Tile t21 = model.getTileAt(2, 1);
    Tile t22 = model.getTileAt(2, 2);

    model.paint(0, 0, 1, 0);
    // B1 G  G
    // B  G  G
    // □  □  R
    assertEquals(1, t00.getDots());
    assertEquals(GameColor.BLUE, t10.getColor());
    assertEquals(GameColor.GREEN, t11.getColor());

    // 泛洪填充
    model.paint(0, 0, 0, 1);
    // B  B  B
    // B  B  B
    // □  □  R
    assertEquals(0, t00.getDots());
    assertEquals(GameColor.BLUE, t01.getColor());
    assertEquals(GameColor.BLUE, t02.getColor());
    assertEquals(GameColor.BLUE, t12.getColor());
    assertEquals(GameColor.BLUE, t12.getColor());
    assertEquals(GameColor.RED, t22.getColor());
  }

  @Test
  void testGetNumRows() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  R
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new SimpleTile(GameColor.RED, 0)}
    };
    IColor color = GameColor.RED;

    assertEquals(-1, model.getNumRows());
    model.startGame(board, color);
    assertEquals(3, model.getNumRows());
  }

  @Test
  void testGetNumCols() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  R
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new SimpleTile(GameColor.RED, 0)}
    };
    IColor color = GameColor.RED;

    assertEquals(-1, model.getNumCols());
    model.startGame(board, color);
    assertEquals(3, model.getNumCols());
  }

  @Test
  void testAllTilesGoalColor_GameNotStarted() {
    PuralaxModel model = new BasicPuralaxModel();
    // B2 G  G
    // R  G  G
    // □  □  R
    Tile[][] board = {
        {new SimpleTile(GameColor.BLUE, 2), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new SimpleTile(GameColor.RED, 0), new SimpleTile(GameColor.GREEN, 0),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new SimpleTile(GameColor.RED, 0)}
    };
    IColor color = GameColor.RED;
//    model.startGame(board, color);
    assertThrows(
        IllegalStateException.class,
        () -> model.allTilesGoalColor()
    );
  }

  @Test
  void testAllTilesGoalColor() {
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

    assertFalse(model.allTilesGoalColor());
    model.paint(0, 0, 1, 0);
    // B1 G  G
    // B  G  G
    // □  □  □
    assertFalse(model.allTilesGoalColor());
    model.paint(0, 0, 0, 1);
    // B  B  B
    // B  B  B
    // □  □  □
    assertTrue(model.allTilesGoalColor());
  }

  @Test
  void testNoValidOperate_GameNotStarted() {
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
    assertThrows(
        IllegalStateException.class,
        () -> model.noValidOperate()
    );
  }

  @Test
  void testNoValidOperate() {
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

    assertFalse(model.noValidOperate());
    model.paint(0, 0, 1, 0);
    // B1 G  G
    // B  G  G
    // □  □  □
    assertFalse(model.noValidOperate());
    model.paint(0, 0, 0, 1);
    // B  B  B
    // B  B  B
    // □  □  □
    assertTrue(model.noValidOperate());
  }

  @Test
  void testGetTileAt_GameNotStarted() {
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
    assertThrows(
        IllegalStateException.class,
        () -> model.getTileAt(0, 0)
    );
  }

  @Test
  void testGetTileAt() {
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

    Tile t00 = model.getTileAt(0, 0);
    assertEquals(GameColor.BLUE, t00.getColor());
    assertEquals(2, t00.getDots());
  }

  @Test
  void testGetCoalColor_gameNotStarted() {
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
    assertThrows(
        IllegalStateException.class,
        () -> model.getGoalColor()
    );
  }

  @Test
  void testGetCoalColor() {
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
    assertEquals(GameColor.BLUE, model.getGoalColor());
  }

  @Test
  void testWallTile_canNotMoveTo() {
    PuralaxModel model = new BasicPuralaxModel();
    // □  □  □
    // B4 ■  G
    // □  □  □
    Tile[][] board = {
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new SimpleTile(GameColor.BLUE, 4),
            new WallTile(),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    assertThrows(
        IllegalStateException.class,
        () -> model.moveTile(1, 0, 1, 1)
    );
  }

  @Test
  void testWallTile_canNotBePainted() {
    PuralaxModel model = new BasicPuralaxModel();
    // □  □  □
    // B4 ■  G
    // □  □  □
    Tile[][] board = {
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new SimpleTile(GameColor.BLUE, 4),
            new WallTile(),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);
    assertThrows(
        IllegalStateException.class,
        () -> model.paint(1, 0, 1, 1)
    );
  }

  @Test
  void testWallTile() {
    PuralaxModel model = new BasicPuralaxModel();
    // □  □  □
    // B4 ■  G
    // □  □  □
    Tile[][] board = {
        {new EmptyTile(), new EmptyTile(), new EmptyTile()},
        {new SimpleTile(GameColor.BLUE, 4),
            new WallTile(),
            new SimpleTile(GameColor.GREEN, 0)},
        {new EmptyTile(), new EmptyTile(), new EmptyTile()}
    };
    IColor color = GameColor.BLUE;
    model.startGame(board, color);

    model.moveTile(1, 0, 2, 0);
    // □  □  □
    // □  ■  G
    // B3 □  □
    model.moveTile(2, 0, 2, 1);
    // □  □  □
    // □  ■  G
    // □  B2  □
    model.moveTile(2, 1, 2, 2);
    // □  □  □
    // □  ■  G
    // □  □  B1
    model.paint(2, 2, 1, 2);
    // □  □  □
    // □  ■  B
    // □  □  B
    Tile t11 = model.getTileAt(1, 1);
    Tile t12 = model.getTileAt(1, 2);
    Tile t22 = model.getTileAt(2, 2);
    assertEquals(GameColor.GRAY, t11.getColor());
    assertFalse(t11.canMove());
    assertEquals(GameColor.BLUE, t12.getColor());
    assertEquals(GameColor.BLUE, t22.getColor());
    assertTrue(model.allTilesGoalColor());
  }

  @Test
  void testNestedTile() {
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
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]
    Tile t01 = model.getTileAt(0, 1);
    Tile t10 = model.getTileAt(1, 0);
    Tile t12 = model.getTileAt(1, 2);
    Tile t21 = model.getTileAt(2, 1);

    model.paint(0, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]
    assertEquals(GameColor.YELLOW, nestedTile.getColor());

    model.paint(1, 0, 1, 1);
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]
    assertEquals(GameColor.GREEN, nestedTile.getColor());
    assertEquals(GameColor.GREEN, t01.getColor());

    model.paint(1, 2, 1, 1);
    // Game goal color: Purple
    // [..] [Bl]    [..]
    // [Bl] [Bl/Pu] [Bl]
    // [..] [Pu*1]  [..]
    assertEquals(GameColor.BLUE, nestedTile.getColor());
    assertEquals(GameColor.BLUE, t01.getColor());
    assertEquals(GameColor.BLUE, t10.getColor());
    assertEquals(GameColor.PURPLE, t21.getColor());

    model.paint(2, 1, 1, 1);
    // Game goal color: Purple
    // [..] [Pu] [..]
    // [Pu] [Pu] [Pu]
    // [..] [Pu] [..]
    assertEquals(GameColor.PURPLE, nestedTile.getColor());
    assertEquals(GameColor.PURPLE, t01.getColor());
    assertEquals(GameColor.PURPLE, t10.getColor());
    assertEquals(GameColor.PURPLE, t12.getColor());
    assertEquals(GameColor.PURPLE, t21.getColor());
    // 达成目标颜色
    assertTrue(model.allTilesGoalColor());
  }

  @Test
  void testUndo_gameNotStarted() {
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
//    model.startGame(board, goalColor);
    assertThrows(
        IllegalStateException.class,
        () -> model.undo()
    );
  }

  @Test
  void testUndo_stackEmpty() {
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

    model.undo();
    assertEquals(GameColor.RED, nestedTile.getColor());
  }

  @Test
  void testUndo() {
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
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]

    model.paint(0, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]
    Tile t11_0 = model.getTileAt(1, 1);
    assertEquals(GameColor.YELLOW, t11_0.getColor());
    Tile t01_0 = model.getTileAt(0, 1);
    assertEquals(0, t01_0.getDots());

    model.undo();
    // Game goal color: Purple
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]
    Tile t11_1 = model.getTileAt(1, 1);
    assertEquals(GameColor.RED, t11_1.getColor());
    Tile t01_1 = model.getTileAt(0, 1);
    assertEquals(1, t01_1.getDots());

    model.paint(0, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]

    model.paint(1, 0, 1, 1);
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]

    model.paint(1, 2, 1, 1);
    // Game goal color: Purple
    // [..] [Bl]    [..]
    // [Bl] [Bl/Pu] [Bl]
    // [..] [Pu*1]  [..]

    model.paint(2, 1, 1, 1);
    // Game goal color: Purple
    // [..] [Pu] [..]
    // [Pu] [Pu] [Pu]
    // [..] [Pu] [..]
    // 达成目标颜色
    assertTrue(model.allTilesGoalColor());

    model.undo();
    model.undo();
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]
    assertFalse(model.allTilesGoalColor());
    Tile t01_2 = model.getTileAt(0, 1);
    Tile t10_2 = model.getTileAt(1, 0);
    Tile t11_2 = model.getTileAt(1, 1);
    Tile t12_2 = model.getTileAt(1, 2);
    Tile t21_2 = model.getTileAt(2, 1);
    assertEquals(GameColor.GREEN, t01_2.getColor());
    assertEquals(GameColor.GREEN, t10_2.getColor());
    assertEquals(GameColor.GREEN, t11_2.getColor());
    assertEquals(GameColor.BLUE, t12_2.getColor());
    assertEquals(GameColor.PURPLE, t21_2.getColor());
  }

  @Test
  void testRedo_gameNotStarted() {
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
//    model.startGame(board, goalColor);
    // Game goal color: Purple
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]
    assertThrows(
        IllegalStateException.class,
        () -> model.redo()
    );
  }

  @Test
  void testRedo_emptyRedoStack() {
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
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]

    model.redo();
    assertEquals(GameColor.RED, nestedTile.getColor());
  }

  @Test
  void testRedo() {
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
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]

    model.paint(0, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]

    model.paint(1, 0, 1, 1);
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]

    model.paint(1, 2, 1, 1);
    // Game goal color: Purple
    // [..] [Bl]    [..]
    // [Bl] [Bl/Pu] [Bl]
    // [..] [Pu*1]  [..]

    model.undo();
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]

    model.redo();
    // Game goal color: Purple
    // [..] [Bl]    [..]
    // [Bl] [Bl/Pu] [Bl]
    // [..] [Pu*1]  [..]
    Tile t01_0 = model.getTileAt(0, 1);
    Tile t10_0 = model.getTileAt(1, 0);
    Tile t11_0 = model.getTileAt(1, 1);
    Tile t12_0 = model.getTileAt(1, 2);
    Tile t21_0 = model.getTileAt(2, 1);
    assertEquals(GameColor.BLUE, t01_0.getColor());
    assertEquals(GameColor.BLUE, t10_0.getColor());
    assertEquals(GameColor.BLUE, t11_0.getColor());
    assertEquals(GameColor.BLUE, t12_0.getColor());
    assertEquals(GameColor.PURPLE, t21_0.getColor());
  }

  @Test
  void testRedo_clearAfterOperate() {
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
    // [..]   [Yl*1]           [..]
    // [Gn*1] [Rd/Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]           [..]

    model.paint(0, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]

    model.paint(1, 0, 1, 1);
    // Game goal color: Purple
    // [..] [Gn]       [..]
    // [Gn] [Gn/Bl/Pu] [Bl*1]
    // [..] [Pu*1]     [..]

    model.undo();
    // Game goal color: Purple
    // [..]   [Yl]          [..]
    // [Gn*1] [Yl/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu*1]        [..]

    model.paint(2, 1, 1, 1);
    // Game goal color: Purple
    // [..]   [Pu]          [..]
    // [Gn*1] [Pu/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu]          [..]
    Tile t01_0 = model.getTileAt(0, 1);
    Tile t10_0 = model.getTileAt(1, 0);
    Tile t11_0 = model.getTileAt(1, 1);
    Tile t12_0 = model.getTileAt(1, 2);
    Tile t21_0 = model.getTileAt(2, 1);
    assertEquals(GameColor.PURPLE, t01_0.getColor());
    assertEquals(GameColor.GREEN, t10_0.getColor());
    assertEquals(GameColor.PURPLE, t11_0.getColor());
    assertEquals(GameColor.BLUE, t12_0.getColor());
    assertEquals(GameColor.PURPLE, t21_0.getColor());

    model.redo();
    // Game goal color: Purple
    // [..]   [Pu]          [..]
    // [Gn*1] [Pu/Gn/Bl/Pu] [Bl*1]
    // [..]   [Pu]          [..]
    Tile t01_1 = model.getTileAt(0, 1);
    Tile t10_1 = model.getTileAt(1, 0);
    Tile t11_1 = model.getTileAt(1, 1);
    Tile t12_1 = model.getTileAt(1, 2);
    Tile t21_1 = model.getTileAt(2, 1);
    assertEquals(GameColor.PURPLE, t01_1.getColor());
    assertEquals(GameColor.GREEN, t10_1.getColor());
    assertEquals(GameColor.PURPLE, t11_1.getColor());
    assertEquals(GameColor.BLUE, t12_1.getColor());
    assertEquals(GameColor.PURPLE, t21_1.getColor());
  }

  @Test
  void testPainterTile() {
    PuralaxModel model = new BasicPuralaxModel();
    Tile[][] board = {
        {new EmptyTile(),
            new SimpleTile(GameColor.GREEN, 1),
            new EmptyTile()},
        {new EmptyTile(),
            new PainterTile(GameColor.BLUE),
            new EmptyTile()},
        {new EmptyTile(),
            new SimpleTile(GameColor.GREEN, 0),
            new EmptyTile()}
    };
    IColor goalColor = GameColor.BLUE;
    model.startGame(board, goalColor);
    // [  ] [Gn*1] [  ]
    // [  ] [Bl_]  [  ]
    // [  ] [Gn]   [  ]

    model.moveTile(0, 1, 1, 1);
    // [  ] [  ] [  ]
    // [  ] [Bl] [  ]
    // [  ] [Bl] [  ]
    Tile t01 = model.getTileAt(0, 1);
    Tile t11 = model.getTileAt(1, 1);
    Tile t21 = model.getTileAt(2, 1);
    assertEquals(EmptyTile.class, t01.getClass());
    assertEquals(GameColor.BLUE, t11.getColor());
    assertEquals(0, t11.getDots());
    assertEquals(GameColor.BLUE, t21.getColor());
    assertTrue(model.allTilesGoalColor());
  }
}