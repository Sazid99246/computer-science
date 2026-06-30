package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;

/**
 * 并非真正的 Tile, 用来代表棋盘中的空单元格
 * 文本视图为 [  ]
 */
public class EmptyTile implements Tile{

  @Override
  public boolean canMove() {
    return false;
  }

  @Override
  public boolean canMoveTo() {
    return true;
  }

  @Override
  public void spendOneDot() throws IllegalStateException {
    throw new IllegalStateException("Empty cell cannot operate.");
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
    throw new IllegalStateException("Empty cell cannot operate.");
  }

  @Override
  public void setColor(IColor color) throws IllegalStateException {
    throw new IllegalStateException("Empty cell cannot operate.");
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
    return new EmptyTile();
  }

  @Override
  public boolean isPainterTile() {
    return false;
  }

  @Override
  public String toString() {
    return "[  ]";
  }
}
