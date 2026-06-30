package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;

/**
 * 画家方块，有单元格底部的一条色条标识。
 * 当某个方块被一如该单元格时，会被涂上该色条对应的颜色，符合泛洪填充规则。
 * 画家方块是一次性的，方块进入后画家方块消失。
 * 文本视图：[Bl_], "Bl"为色条颜色
 */
public class PainterTile implements Tile{
  // 底部色条颜色
  private IColor barColor;

  public PainterTile(IColor barColor) {
    if (barColor == null) {
      throw new IllegalArgumentException("BarColor cannot be null.");
    }

    this.barColor = barColor;
  }

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
    throw new IllegalStateException("Painter tile cannot spend dots.");
  }

  @Override
  public boolean canPaint() {
    return false;
  }

  @Override
  public boolean canBePainted() {
    return true;
  }

  @Override
  public IColor getColor() throws IllegalStateException {
    return barColor;
  }

  @Override
  public void setColor(IColor color) throws IllegalStateException {
    this.barColor = color;
  }

  @Override
  public int getDots() {
    return 0;
  }

  @Override
  public boolean achieveGoalColor(IColor goalColor) {
    return barColor.equals(goalColor);
  }

  @Override
  public Tile deepCopy() {
    return new PainterTile(barColor);
  }

  @Override
  public boolean isPainterTile() {
    return true;
  }

  @Override
  public String toString() {
    return "[" + barColor.getShortName() + "_]";
  }
}
