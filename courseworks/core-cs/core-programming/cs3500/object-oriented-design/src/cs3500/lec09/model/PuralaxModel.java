package cs3500.lec09.model;

import cs3500.lec09.common.IColor;
import cs3500.lec09.model.tile.Tile;

/**
 * 1. 使用 Tile 二维数组代表棋盘，
 *    棋盘只能为矩形，且不能有空，使用特俗的 EmptyTile 代表空单元格。
 * 2. 对棋盘中元素统一使用 0 索引。
 * 3. 使用 IColor 表示游戏模型中的颜色。
 * 4. 坐标相邻是 上下左右 四个位置
 */
public interface PuralaxModel {

  /**
   * 以指定的棋盘和目标颜色开始游戏
   *
   * @param board 游戏棋盘，为图块的二维数组
   * @param goalColor  目标颜色
   * @throws IllegalArgumentException
   *  如果: 1.board 或 goalColor 为空
   *       2.board 不为长宽大于零的矩形
   */
  void startGame(Tile[][] board, IColor goalColor);

  /**
   * 将图块移动到相邻单元格，并消耗其一个点
   *
   * @param fromRow 要移动图块所在的行
   * @param fromCol 要移动图块所在的列
   * @param toRow   终点所在的行
   * @param toCol   终点所在的列
   * @throws IllegalArgumentException
   *  如果: 1.坐标超出棋盘范围
   *       2.坐标不相邻
   * @throws IllegalStateException
   * 如果: 1.游戏未开始
   *      2. form 坐标没有 可移动图块
   *      3. to 坐标不是空单元格
   */
  void moveTile(int fromRow, int fromCol, int toRow, int toCol)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * 消耗图块一个点，将相邻的不同颜色的图块染为相同颜色。
   * 染色是一种泛洪操作：所有相邻且颜色相同的方块都会被染色。
   *
   * @param sourceRow 操作图块所在的行
   * @param sourceCol 操作图块所在的列
   * @param targetRow 染色目标图块所在的行
   * @param targetCol 染色目标图块所在的列
   * @throws IllegalArgumentException
   *  如果: 1.坐标超出棋盘范围
   *       2.坐标不相邻
   * @throws IllegalStateException
   *  如果: 1.游戏未开始
   *       2.source 坐标没有可进行染色操作的图块
   *       3.target 坐标没有可被染色的图块
   *       4.两位置的 tile 颜色相同
   */
  void paint(int sourceRow, int sourceCol, int targetRow, int targetCol)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * 返回游戏棋盘的行数，或 -1 如果游戏未开始
   *
   * @return 棋盘的高度，或 -1
   */
  int getNumRows();

  /**
   * 返回游戏棋盘的列数，或 -1 如果游戏未开始
   *
   * @return 棋盘的宽度，或 -1
   */
  int getNumCols();

  /**
   * 判断是否所有可涂色图块都为目标颜色
   *
   * @return true 如果所有可涂色图块都为目标颜色
   * @throws IllegalStateException 如果游戏未开始
   */
  boolean allTilesGoalColor() throws IllegalStateException;

  /**
   * 判断游戏是否无法进行任何操作
   *
   * @return true 如果游戏无法进行任何操作
   * @throws IllegalStateException 如果游戏未开始
   */
  boolean noValidOperate() throws IllegalStateException;

  /**
   * 返回指定坐标的图块
   *
   * @param row 图块所在带行
   * @param col 图块所在的列
   * @return 在指定的坐标 Tile
   * @throws IllegalArgumentException 如果坐标超出棋盘范围
   * @throws IllegalStateException 如果游戏未开始
   */
  Tile getTileAt(int row, int col) throws IllegalStateException;

  /**
   * 获取游戏的目标颜色
   *
   * @return 游戏的目标颜色
   * @throws IllegalStateException 如果游戏未开始
   */
  IColor getGoalColor() throws IllegalStateException;

  /**
   * 撤销，如果撤销栈为空，不进行任何操作
   *
   * @throws IllegalStateException 如果游戏未开始
   */
  void undo() throws IllegalStateException;

  /**
   * 重做，如果重做栈为空，不进行任何操作
   *
   * @throws IllegalStateException 如果游戏未开始
   */
  void redo() throws IllegalStateException;
}
