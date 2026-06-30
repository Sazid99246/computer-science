package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;

/**
 * 简单的基本图块，拥有颜色，有零或多个点.
 * 文本视图为 [Color*dots],例如 2 点的红色图块，[Rd*2];
 * 如果没有点，则省略 *dots, 例如 0 点的红色图块，[Rd].
 */
public class SimpleTile implements Tile{
  IColor color;
  int dots;

  public SimpleTile(IColor color, int dots) {
    if (color == null) {
      throw new IllegalArgumentException("Invalid color: color cannot be null.");
    }
    if (dots < 0) {
      throw new IllegalArgumentException("Invalid dots: dots must >= 0.");
    }

    this.color = color;
    this.dots = dots;
  }

  @Override
  public boolean canMove() {
    return dots > 0;
  }

  @Override
  public boolean canMoveTo() {
    return false;
  }

  @Override
  public void spendOneDot() throws IllegalStateException {
    if (dots <= 0) {
      throw new IllegalStateException("Tile's dots not enough.");
    }

    dots--;
  }

  @Override
  public boolean canPaint() {
    return dots > 0;
  }

  @Override
  public boolean canBePainted() {
    return true;
  }

  @Override
  public IColor getColor() throws IllegalStateException {
    return color;
  }

  @Override
  public void setColor(IColor color) throws IllegalStateException {
    this.color = color;
  }

  @Override
  public int getDots() {
    return dots;
  }

  @Override
  public boolean achieveGoalColor(IColor goalColor) {
    return color.equals(goalColor);
  }

  @Override
  public Tile deepCopy() {
    return new SimpleTile(color, dots);
  }

  @Override
  public boolean isPainterTile() {
    return false;
  }

  @Override
  public String toString() {
    if (dots <= 0) {
      return "[" + color.getShortName() + "]";
    } else {
      return "[" + color.getShortName() + "*" + dots + "]";
    }
  }
}
