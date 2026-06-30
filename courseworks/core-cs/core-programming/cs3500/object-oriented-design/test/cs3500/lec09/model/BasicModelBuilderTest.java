package cs3500.lec09.model;

import org.junit.jupiter.api.Test;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.model.tile.BasicTileFactory;
import cs3500.lec09.model.tile.NestedTile;
import cs3500.lec09.model.tile.PainterTile;
import cs3500.lec09.model.tile.Tile;
import cs3500.lec09.model.tile.TileFactory;
import cs3500.lec09.model.tile.WallTile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicModelBuilderTest {
  @Test
  void testPraseInputString_nullInput() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    String in = null;
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );
  }

  @Test
  void testPraseInputString_emptyInput() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    String in = "";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );
  }

  @Test
  void testPraseInputString_headNotEqual3() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    String in = "3 3";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );
  }

  @Test
  void testPraseInputString_heightOrWidthNotInt() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    String in = "a 3 B";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );

    String in1 = "3 a B";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in1)
    );
  }

  @Test
  void testPraseInputString_colorStringNotMatch() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    String in = "3 3 A";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );
  }

  @Test
  void testPraseInputString_tileLineInvalid() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);

    // 图块描述行小于4个参数
    String in =
            "3 3 B\n" +
                    "1 1 M";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in)
    );
    // 没有符合的图块类型
    String in1 =
            "3 3 B\n" +
                    "1 1 a B";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in1)
    );
    // 行不为整数
    String in2 =
            "3 3 B\n" +
                    "a 1 M B 1";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in2)
    );
    // 列不为整数
    String in3 =
            "3 3 B\n" +
                    "1 a M B 1";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in3)
    );
    // 点数不为整数
    String in4 =
            "3 3 B\n" +
                    "1 1 M B a";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in4)
    );
    // 颜色没有匹配的字符
    String in5 =
            "3 3 B\n" +
                    "1 1 M A 1";
    assertThrows(
            IllegalArgumentException.class,
            () -> builder.parseInputString(in5)
    );
  }

  @Test
  void testPraseInputString() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);
    String in =
            "3 3 B\n" +
                    "1 1 M B 1\n" +
                    "1 2 I G";
    PuralaxModel model = builder.parseInputString(in);

    assertEquals(3, model.getNumRows());
    assertEquals(3, model.getNumCols());
    assertEquals(GameColor.BLUE, model.getGoalColor());
    Tile t11 = model.getTileAt(1, 1);
    Tile t12 = model.getTileAt(1, 2);
    assertEquals(GameColor.BLUE, t11.getColor());
    assertEquals(1, t11.getDots());
    assertEquals(GameColor.GREEN, t12.getColor());
  }

  @Test
  void testPraseInputString_wallTile() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);
    String in =
            "3 3 B\n" +
                    "1 1 W";
    PuralaxModel model = builder.parseInputString(in);

    Tile wall = model.getTileAt(1, 1);
    assertEquals(WallTile.class, wall.getClass());
  }

  @Test
  void testPraseInputString_nestedTile() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);
    String in =
            "3 3 B\n" +
                    "1 1 N [R,Y,G,B,P]";
    PuralaxModel model = builder.parseInputString(in);

    Tile nestedTile = model.getTileAt(1, 1);
    assertEquals(NestedTile.class, nestedTile.getClass());
    assertEquals("[Rd/Yl/Gn/Bl/Pu]", nestedTile.toString());
  }

  @Test
  void testPraseInputString_PainterTile() {
    TileFactory factory = new BasicTileFactory();
    ModelBuilder builder = new BasicModelBuilder(factory);
    String in =
            "3 3 B\n" +
                    "1 1 P B";
    PuralaxModel model = builder.parseInputString(in);

    Tile painterTile = model.getTileAt(1, 1);
    assertEquals(PainterTile.class, painterTile.getClass());
    assertEquals(GameColor.BLUE, painterTile.getColor());
  }
}