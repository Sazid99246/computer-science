package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;

public interface Tile {

  /**
   * 判断图块是否可以执行移动操作
   *
   * @return true 如果可移动
   */
  boolean canMove();

  /**
   * 判断是否可作为移动操作的目标，即空图块
   *
   * @return true 如果可被移动其位置
   */
  boolean canMoveTo();

  /**
   * 消耗图块的一个点
   *
   * @throws IllegalStateException 如果图块没有点可以消耗
   */
  void spendOneDot() throws IllegalStateException;

  /**
   * 判断图块是否可以执行涂色操作
   *
   * @return true 如果可涂色操作
   */
  boolean canPaint();

  /**
   * 判断图块是否可以被涂色
   *
   * @return true 如果可被涂色
   */
  boolean canBePainted();

  /**
   * 获取图块颜色
   *
   * @return 图块颜色
   * @throws IllegalStateException 如果图块没有颜色
   */
  IColor getColor() throws IllegalStateException;

  /**
   * 设置图块颜色
   *
   * @param color 指定的颜色
   * @throws IllegalStateException 如果图块无法设置颜色
   */
  void setColor(IColor color) throws IllegalStateException;

  /**
   * 获取图块的点数
   * 对于空单元格图块，返回 0
   *
   * @return 图块的点数
   */
  int getDots();

  /**
   * 判断方块是否达成游戏目标颜色，
   * 对于简单方块，判断颜色是否相等，
   * 对于空单元格或墙壁等无颜色操作的方块放回 true，
   * 对于嵌套方块， 判断是否仅有一个颜色，且等于目标颜色
   *
   * @return 是否方块达成了游戏目标颜色
   */
  boolean achieveGoalColor(IColor goalColor);

  /**
   * 深度拷贝当前 tile, 返回一个相同的副本
   *
   * @return 当前 tile 的相同的副本
   */
  Tile deepCopy();

  /**
   * 判断是否是画家方块
   *
   * @return true,如果是画家方块
   */
  boolean isPainterTile();
}
