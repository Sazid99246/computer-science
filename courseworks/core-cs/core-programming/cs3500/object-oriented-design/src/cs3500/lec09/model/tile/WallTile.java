package cs3500.lec09.model.tile;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.common.IColor;

/**
 * 深灰色墙壁，无法移动或涂色
 * 文本视图为 [##]
 */
public class WallTile implements Tile {

  @Override
  public boolean canMove() {
    return false;
  }

  @Override
  public boolean canMoveTo() {
    return false;
  }

  @Override
  public void spendOneDot() throws IllegalStateException {
    throw new IllegalStateException("Wall cannot spend dots.");
  }

  @Override
  public boolean canPaint() {
    return false;
  }

  @Override
  public boolean canBePainted() {
    return false;
  }

  @Override
  public IColor getColor() throws IllegalStateException {
    return GameColor.GRAY;
  }

  @Override
  public void setColor(IColor color) throws IllegalStateException {
    throw new IllegalStateException("Wall cannot set color.");
  }

  @Override
  public int getDots() {
    return 0;
  }

  @Override
  public boolean achieveGoalColor(IColor goalColor) {
    return true;
  }

  @Override
  public Tile deepCopy() {
    return new WallTile();
  }

  @Override
  public boolean isPainterTile() {
    return false;
  }

  @Override
  public String toString() {
    return "[##]";
  }
}
