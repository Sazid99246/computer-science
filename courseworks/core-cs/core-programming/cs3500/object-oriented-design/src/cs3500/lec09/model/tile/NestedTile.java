package cs3500.lec09.model.tile;

import java.util.ArrayList;
import java.util.List;

import cs3500.lec09.common.IColor;

/**
 * 嵌套图块有多种颜色，无法移动或涂色操作，仅能被涂色。
 * 被涂色时，仅最外层被染色，若新颜色与下一层相同，则合并。
 * 文本视图：
 * [Rd/Yl/Gn/Bl/Pu]
 * 最外层为红色，最内层为紫色
 */
public class NestedTile implements Tile {
  // 嵌套图块的颜色列表, 最外层颜色在列表首位
  private List<IColor> colors;

  public NestedTile(List<IColor> colors) {
    if (colors == null) {
      throw new IllegalArgumentException("Colors cannot be null.");
    }
    // 至少要有一个颜色
    if (colors.isEmpty()) {
      throw new IllegalArgumentException("Colors cannot be empty.");
    }
    // 嵌套颜色列表中不能有两个相邻的相同颜色
    if (hasAdjacentSameColor(colors)) {
      throw new IllegalArgumentException("Adjacent color must different.");
    }

    this.colors = colors;
  }

  private boolean hasAdjacentSameColor(List<IColor> colors) {
    if (colors.size() == 1) {
      return false;
    }
    IColor pre = colors.get(0);
    IColor now;
    for (int i = 1; i < colors.size(); i++) {
      now = colors.get(i);
      // 有相邻相等的颜色
      if (now.equals(pre)) return true;

      pre = now;
    }

    return false;
  }

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
    throw new IllegalArgumentException("Nested tile cannot spend dot.");
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
    return colors.get(0);
  }

  @Override
  public void setColor(IColor color) throws IllegalStateException {
    // 只有一个颜色
    if (colors.size() == 1) {
      colors.set(0, color);
    } else {
      // 下一层颜色如果相同，则合并
      if (color.equals(colors.get(1))) {
        colors.remove(0);
      } else {
        colors.set(0, color);
      }
    }
  }

  @Override
  public int getDots() {
    return 0;
  }

  @Override
  public boolean achieveGoalColor(IColor goalColor) {
    return colors.size() == 1 &&
            colors.get(0).equals(goalColor);
  }

  @Override
  public Tile deepCopy() {
    List<IColor> copyColors = new ArrayList<>(colors);
    return new NestedTile(copyColors);
  }

  @Override
  public boolean isPainterTile() {
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < colors.size(); i++) {
      sb.append(colors.get(i).getShortName());
      if (i < colors.size() - 1) {
        sb.append("/");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
