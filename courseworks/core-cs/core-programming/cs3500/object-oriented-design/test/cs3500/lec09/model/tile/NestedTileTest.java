package cs3500.lec09.model.tile;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.common.IColor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NestedTileTest {

  @Test
  void testConstructor_nullColors() {
    assertThrows(
            IllegalArgumentException.class,
            () -> new NestedTile(null)
    );
  }

  @Test
  void testConstructor_emptyColors() {
    List<IColor> colors = new ArrayList<>();
    assertThrows(
            IllegalArgumentException.class,
            () -> new NestedTile(colors)
    );
  }

  @Test
  void testConstructor_adjacentSameColors() {
    List<IColor> colors = new ArrayList<>();
    colors.add(GameColor.RED);
    colors.add(GameColor.RED);
    // 红、红
    assertThrows(
            IllegalArgumentException.class,
            () -> new NestedTile(colors)
    );

    List<IColor> colors1 = new ArrayList<>();
    colors.add(GameColor.RED);
    colors.add(GameColor.YELLOW);
    colors.add(GameColor.YELLOW);
    colors.add(GameColor.GREEN);
    // 红，黄，黄，绿
    assertThrows(
            IllegalArgumentException.class,
            () -> new NestedTile(colors1)
    );
  }

  @Test
  void testConstructor() {
    List<IColor> colors = new ArrayList<>();
    colors.add(GameColor.RED);
    // 红
    Tile tile = new NestedTile(colors);
    assertEquals(GameColor.RED, tile.getColor());

    List<IColor> colors1 = new ArrayList<>(List.of(
            GameColor.RED,
            GameColor.YELLOW,
            GameColor.GREEN,
            GameColor.BLUE,
            GameColor.PURPLE
    ));
    // 红、黄、绿、蓝、紫
    Tile tile1 = new NestedTile(colors1);
    assertEquals(GameColor.RED, tile1.getColor());
    assertEquals("[Rd/Yl/Gn/Bl/Pu]", tile1.toString());
  }

  @Test
  void testSetColor_NotMerge() {
    List<IColor> colors = new ArrayList<>(List.of(
            GameColor.RED,
            GameColor.YELLOW,
            GameColor.GREEN,
            GameColor.BLUE,
            GameColor.PURPLE
    ));
    // 红、黄、绿、蓝、紫
    Tile tile = new NestedTile(colors);
    assertEquals("[Rd/Yl/Gn/Bl/Pu]", tile.toString());
    tile.setColor(GameColor.GREEN);
    assertEquals("[Gn/Yl/Gn/Bl/Pu]", tile.toString());
    tile.setColor(GameColor.PURPLE);
    assertEquals("[Pu/Yl/Gn/Bl/Pu]", tile.toString());
  }

  @Test
  void testSetColor_mergeColor() {
    List<IColor> colors = new ArrayList<>(List.of(
            GameColor.RED,
            GameColor.YELLOW,
            GameColor.GREEN,
            GameColor.BLUE,
            GameColor.PURPLE
    ));
    // 红、黄、绿、蓝、紫
    Tile tile = new NestedTile(colors);
    assertEquals("[Rd/Yl/Gn/Bl/Pu]", tile.toString());
    tile.setColor(GameColor.YELLOW);
    assertEquals("[Yl/Gn/Bl/Pu]", tile.toString());
    tile.setColor(GameColor.GREEN);
    assertEquals("[Gn/Bl/Pu]", tile.toString());
    tile.setColor(GameColor.BLUE);
    assertEquals("[Bl/Pu]", tile.toString());
    tile.setColor(GameColor.PURPLE);
    assertEquals("[Pu]", tile.toString());
    tile.setColor(GameColor.PURPLE);
    assertEquals("[Pu]", tile.toString());
    tile.setColor(GameColor.RED);
    assertEquals("[Rd]", tile.toString());
  }

  @Test
  void testAchieveGoalColor() {
    List<IColor> colors = new ArrayList<>(List.of(
            GameColor.RED,
            GameColor.YELLOW,
            GameColor.GREEN,
            GameColor.BLUE,
            GameColor.PURPLE
    ));
    // 红、黄、绿、蓝、紫
    Tile tile = new NestedTile(colors);
    assertFalse(tile.achieveGoalColor(GameColor.YELLOW));
    assertFalse(tile.achieveGoalColor(GameColor.RED));

    tile.setColor(GameColor.YELLOW);
    // 黄、绿、蓝、紫

    tile.setColor(GameColor.GREEN);
    // 绿、蓝、紫
    tile.setColor(GameColor.BLUE);
    // 蓝、紫
    tile.setColor(GameColor.PURPLE);
    // 紫
    assertFalse(tile.achieveGoalColor(GameColor.RED));
    assertTrue(tile.achieveGoalColor(GameColor.PURPLE));
  }
}