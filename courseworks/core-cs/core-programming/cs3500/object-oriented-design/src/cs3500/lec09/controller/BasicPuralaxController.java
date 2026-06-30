package cs3500.lec09.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.view.PuralaxTexturalView;
import cs3500.lec09.view.PuralaxView;

/**
 * 指令：
 * move r1 c1 r2 c2  -> 将 (r1,c1) 的 tile 移动到 (r2, c2)
 * paint r1 c1 r2 c2 -> 将 (r1,c1) 的 tile 作为染色源，对(r2,c2) 的 tile 进行染色
 * undo -> 撤销
 * redo -> 重做
 * <p>
 * 如果指令不符合规范，显示提示信息。
 * 指令大小写不敏感。
 */
public class BasicPuralaxController implements PuralaxController {

  // 操作指令列表
  private final List<String> operateCommand =
          List.of("move", "paint", "undo", "redo");
  // 退出指令列表
  private final List<String> quitCommand =
          List.of("q", "quit");

  private final Readable rd;   // 读取输入
  private final Appendable ap; // 写入输出

  public BasicPuralaxController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable or Appendable cannot be null.");
    }

    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(PuralaxModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    // 游戏应该已经开始
    if (gameNotStarted(model)) {
      throw new IllegalStateException("Game not Started.");
    }
    // 视图
    PuralaxView view = new PuralaxTexturalView(model);
    // 读取器
    Scanner scanner = new Scanner(this.rd);
    // 循环直到游戏结束
    while (gameNotOver(model)) {
      try {
        // 渲染游戏模型
        view.render();
        String op = readOperateOrQuit(scanner);
        executeOperate(op, scanner, model);
      } catch (ExitGameException e) {
        quitGame();
        return;  // 退出函数
      } catch (IOException e) {
        throw new IllegalStateException("Failed read or write.", e);
      }
    }
    // 游戏结束
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Failed read or write.", e);
    }
  }

  // 返回 true，如果游戏未开始
  private boolean gameNotStarted(PuralaxModel model) {
    return model.getNumRows() == -1;
  }

  // 返回 true，如果游戏未结束
  private boolean gameNotOver(PuralaxModel model) {
    return !model.allTilesGoalColor() && !model.noValidOperate();
  }

  // 向输出流添加内容并换行
  private void appendLine(String str) throws IOException {
    this.ap.append(str).append("\n");
  }

  // 读取命令或退出指令
  private String readOperateOrQuit(Scanner scanner)
          throws IOException, ExitGameException {
    // 循环直到读取到有用信息
    while (true) {
      // 输出流结束，发出退出信号
      if (!scanner.hasNext()) {
        throw new ExitGameException("Input stream end.");
      }
      String input = scanner.next();
      String lowerCaseInput = input.toLowerCase();
      if (this.quitCommand.contains(lowerCaseInput)) {
        throw new ExitGameException("User request quit.");
      } else if (this.operateCommand.contains(lowerCaseInput)) {
        return input;
      }

      appendLine("Unknow command: " + input);
    }
  }

  // 退出游戏
  private void quitGame() {
    try {
      appendLine("Quit Game.");
    } catch (IOException e) {
      throw new IllegalStateException("Failed write", e);
    }
  }

  // 执行操作指令
  private void executeOperate(String op, Scanner scanner,
                              PuralaxModel model)
          throws IOException, ExitGameException {
    switch (op.toLowerCase()) {
      case "move":
        executeMove(scanner, model);
        return;
      case "paint":
        executePaint(scanner, model);
        return;
      case "undo":
        executeUndo(model);
        break;
      case "redo":
        executeRedo(model);
        break;
      default:
        throw new RuntimeException("ExecuteOperate function no matched command.");
    }
  }

  // 执行移动指令
  private void executeMove(Scanner scanner, PuralaxModel model)
          throws IOException, ExitGameException {
    int fromRow = readIntOrQuit(scanner);
    int fromCol = readIntOrQuit(scanner);
    int toRow = readIntOrQuit(scanner);
    int toCol = readIntOrQuit(scanner);

    try {
      model.moveTile(fromRow, fromCol, toRow, toCol);
    } catch (IllegalArgumentException | IllegalStateException e) {
      appendLine("Invalid move: " + e.getMessage());
    }
  }

  // 执行涂色指令
  private void executePaint(Scanner scanner, PuralaxModel model)
          throws IOException, ExitGameException {
    int sourceRow = readIntOrQuit(scanner);
    int sourceCol = readIntOrQuit(scanner);
    int targetRow = readIntOrQuit(scanner);
    int targetCol = readIntOrQuit(scanner);

    try {
      model.paint(sourceRow, sourceCol, targetRow, targetCol);
    } catch (IllegalArgumentException | IllegalStateException e) {
      appendLine("Invalid paint: " + e.getMessage());
    }
  }

  // 读取数字或退出
  private int readIntOrQuit(Scanner scanner)
          throws IOException, ExitGameException {
    while (true) {
      if (!scanner.hasNext()) {
        throw new ExitGameException("Input stream end.");
      }
      String input = scanner.next();
      String lowerCaseInput = input.toLowerCase();
      if (this.quitCommand.contains(lowerCaseInput)) {
        throw new ExitGameException("User request quit.");
      }
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        appendLine("Input a int.");
      }
    }
  }

  // 撤销
  private void executeUndo(PuralaxModel model) {
    model.undo();
  }

  // 重做
  private void executeRedo(PuralaxModel model) {
    model.redo();
  }
}
