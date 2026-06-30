package cs3500.lec09.view;

import java.io.IOException;

import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.model.tile.Tile;

/**
 * 示例：
 * Game goal color: Blue
 * [..]   [..] [..]
 * [Bl*4] [##] [Gn]
 * [..]   [..] [..]
 * 1. 使用 [..] 表示空单元格
 * 2. 使用 [##] 表示墙壁
 * 3. 方块用[]包围，内部为其信息，颜色使用两字符缩写，比如"Rd","Bl".如果有点数则用"*"+数字
 */
public class PuralaxTexturalView implements PuralaxView {

  private final PuralaxModel model;
  private final Appendable output;

  public PuralaxTexturalView(PuralaxModel model, Appendable output) {
    if (model == null || output == null) {
      throw new IllegalArgumentException("Model or output must not be null.");
    }
    this.model = model;
    this.output = output;
  }

  // 以 System.out 作为输出
  public PuralaxTexturalView(PuralaxModel model) {
    this(model, System.out);
  }

  @Override
  public String toString() {
    if (!isGameStarted()) {    // 游戏未开始
      return "Game not started.\n";
    } else if (isGameWin()) {  // 游戏胜利
      return renderGoalColorAndBoard() +
              "You Win!\n";
    } else if (isGameOver()) { // 游戏结束
      return renderGoalColorAndBoard() +
              "Game Over.\n";
    } else {                   // 游戏进行中
      return renderGoalColorAndBoard();
    }
  }

  // 判断游戏是否开始
  private boolean isGameStarted() {
    return model.getNumRows() != -1;
  }

  // 判断游戏是否胜利
  private boolean isGameWin() {
    return model.allTilesGoalColor();
  }

  // 判断游戏是否彻底结束，即没有可行动方块
  private boolean isGameOver() {
    return model.noValidOperate();
  }

  // 渲染游戏目标颜色文本
  private String renderGoalColor() {
    return "Game goal color: " + model.getGoalColor().getFullName() + "\n";
  }

  // 渲染棋盘文本
  private String renderBoard() {
    int rows = model.getNumRows();
    int cols = model.getNumCols();

    int[] colWidths = new int[cols];
    String[][] cellStrings = new String[rows][cols];
    // 获取每列的最大宽度，和 tile 字符
    for (int j = 0; j < cols; j++) {
      int maxWidth = 0;
      for (int i = 0; i < rows; i++) {
        Tile t = model.getTileAt(i, j);
        String s = t.toString();
        cellStrings[i][j] = s;
        if (s.length() > maxWidth)
          maxWidth = s.length();
      }
      colWidths[j] = maxWidth;
    }
    // 构建棋盘
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        String tile = cellStrings[i][j];
        // 左对齐并填充空格
        // %-Ns 表示：左对齐，总宽度为N，不足补空格
        sb.append(String.format("%-" + colWidths[j] + "s", tile));

        // 列之间空格
        if (j < cols - 1) sb.append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  // 渲染目标颜色和棋盘
  private String renderGoalColorAndBoard() {
    return renderGoalColor() +
            renderBoard();
  }

  @Override
  public void render() throws IOException {
    output.append(this.toString());
  }
}
