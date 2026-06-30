package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;
import java.util.List;

public interface TileFactory {

  /**
   * 创建空图块，相当与空单元格
   *
   * @return 空图块
   */
  Tile createEmptyTile();

  /**
   * 创建可移动的图块
   *
   * @param color 颜色
   * @param dots 点数
   * @return 可移动的图块
   */
  Tile createMobileTile(IColor color, int dots);

  /**
   * 创建不可移动的图块
   *
   * @param color 颜色
   * @return 不可移动的图块
   */
  Tile createImmobileTile(IColor color);

  /**
   * 创建无法移动染色的墙壁方块
   *
   * @return 墙壁方块
   */
  Tile createWall();

  /**
   * 创建嵌套方块
   *
   * @param colors 嵌套颜色列表
   * @return 嵌套方块
   */
  Tile createNestedTile(List<IColor> colors);

  /**
   * 创建画家方块
   *
   * @param barColor 色条颜色
   * @return 画家方块
   */
  Tile createPainterTile(IColor barColor);
}
