package cs3500.lec09.model;

public interface ModelBuilder {

  /**
   * 解析字符串构建模型
   * 例如:
   *  输入:
   *   3 3 B      // 棋盘高 3，宽 3，目标颜色 Blue
   *   1 1 M B 1  // 坐标 (1,1), 是可移动方块，颜色 Blue, 1 个点
   *   1 2 I G    // 坐标 (1,2), 是不可移动方块，颜色 Green
   *  构建模型:
   *    Game goal color: Blue
   *    □  □  □
   *    □  B1 G
   *    □  □  □
   *
   * @param in 输入的字符串
   * @return 已经开始游戏的模型
   * @throws IllegalArgumentException 如果输入不合规
   */
  PuralaxModel parseInputString(String in);
}
