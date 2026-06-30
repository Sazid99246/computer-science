package cs3500.lec09.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.lec09.common.GameColor;
import cs3500.lec09.common.IColor;
import cs3500.lec09.model.tile.Tile;
import cs3500.lec09.model.tile.TileFactory;

/**
 * 构造器首行为棋盘高度，棋盘宽度，游戏目标颜色，用空格分割。
 * 下面行数不固定，每行是对一个方块描述，
 * 格式为 行，列，方块类型，(方块颜色)，(方块点数)，用空格分割
 * 依据方块类型不同，方块颜色,方块点数,为不定项。
 * 方块位置坐标使用 0 索引。
 * 没描述的行为空单元格。
 * 如果每行在正确输入外，有多余的部分，忽略，不报错，
 * 例如："3 3 B something error"，解析为蓝色目标颜色，高3，宽3的棋盘。
 * <p>
 * 例如:
 * "3 3 B     // 高3，宽3，目标颜色为 Blue
 * 1 1 M B 1  // 在(1,1)为蓝色可以动方块，有 1 个点
 * 1 2 I G"   // 在(1,2)为绿色不可移动方块
 * <p>
 * 方块类型:
 * M -> 可移动方块，需要 颜色，点数 参数
 * I -> 不可移动方块，需要 颜色 参数
 * W -> 墙壁方块，无需参数
 * N -> 嵌套方块，需要颜色列表, 例如：[R,Y,B,G,P]
 * P -> 画家方块，需要 色条颜色
 * <p>
 * 颜色：
 * R -> 红色
 * Y -> 黄色
 * B -> 蓝色
 * G -> 绿色
 * P -> 紫色
 */
public class BasicModelBuilder implements ModelBuilder {
  TileFactory factory;

  public BasicModelBuilder(TileFactory factory) {
    this.factory = factory;
  }

  @Override
  public PuralaxModel parseInputString(String in) {
    if (in == null) {
      throw new IllegalArgumentException("Input string cannot be null");
    }

    List<String> lines = Arrays.asList(in.trim().split("\n"));
    String header = lines.get(0);
    List<String> bodyLines = lines.subList(1, lines.size());
    // 解析首行
    HeaderInfo info = parseHeader(header);
    // 创建空棋盘
    Tile[][] board = createEmptyBoard(info.height, info.width);
    // 目标颜色
    IColor goalColor = info.goalColor;

    // 解析方块描述行
    parseBody(bodyLines, board);

    // 组装模型，开始游戏
    PuralaxModel model = new BasicPuralaxModel();
    model.startGame(board, goalColor);
    return model;
  }

  // 解析首行
  private HeaderInfo parseHeader(String header) {
    try {
      String[] parts = header.trim().split(" ");
      int height = Integer.parseInt(parts[0]);
      int width = Integer.parseInt(parts[1]);
      IColor goalColor = stringToColor(parts[2]);

      return new HeaderInfo(height, width, goalColor);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error input header format." + "\n" + e);
    }
  }

  // 解析主体
  private void parseBody(List<String> lines, Tile[][] board) {
    for (String line : lines) {
      parseLine(line, board);
    }
  }

  // 解析行
  private void parseLine(String line, Tile[][] board) {
    try {
      String[] parts = line.trim().split(" ");
      int row = Integer.parseInt(parts[0]);
      int col = Integer.parseInt(parts[1]);
      String type = parts[2];
      IColor color;
      int dots;
      Tile tile;
      switch (type) {
        case "M":
          color = stringToColor(parts[3]);
          dots = Integer.parseInt(parts[4]);
          tile = factory.createMobileTile(color, dots);
          break;
        case "I":
          color = stringToColor(parts[3]);
          tile = factory.createImmobileTile(color);
          break;
        case "W":
          tile = factory.createWall();
          break;
        case "N":
          List<IColor> colors = parseColorList(parts[3]);
          tile = factory.createNestedTile(colors);
          break;
        case "P":
          IColor barColor = stringToColor(parts[3]);
          tile = factory.createPainterTile(barColor);
          break;
        default:
          throw new IllegalArgumentException("Not match tile type");
      }
      board[row][col] = tile;
    } catch (Exception e) {
      throw new IllegalArgumentException("Error input body format." + "\n" + e);
    }
  }

  // 将对应的字符串转换为游戏颜色类
  private IColor stringToColor(String s) throws IllegalArgumentException {
    switch (s) {
      case "R":
        return GameColor.RED;
      case "G":
        return GameColor.GREEN;
      case "B":
        return GameColor.BLUE;
      case "Y":
        return GameColor.YELLOW;
      case "P":
        return GameColor.PURPLE;
      default:
        throw new IllegalArgumentException("Not match color: " + s);
    }

  }

  // 创建空棋盘
  private Tile[][] createEmptyBoard(int height, int width) {
    Tile[][] board = new Tile[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = factory.createEmptyTile();
      }
    }
    return board;
  }

  // 解析颜色列表
  private List<IColor> parseColorList(String s) {
    String cleaned = s.replace("[", "").replace("]", "");
    String[] parts = cleaned.split(",");
    List<IColor> colors = new ArrayList<>();
    for (String part : parts) {
      colors.add(stringToColor(part));
    }
    return colors;
  }

  // 输入首行信息
  private static class HeaderInfo {
    final int height;
    final int width;
    final IColor goalColor;

    HeaderInfo(int height, int width, IColor goalColor) {
      this.height = height;
      this.width = width;
      this.goalColor = goalColor;
    }
  }
}
