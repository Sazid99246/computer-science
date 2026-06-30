package cs3500.lec09.model;

import java.util.Stack;

import cs3500.lec09.common.IColor;
import cs3500.lec09.model.tile.BasicTileFactory;
import cs3500.lec09.model.tile.Tile;
import cs3500.lec09.model.tile.TileFactory;

public class BasicPuralaxModel implements PuralaxModel {
  // 游戏棋盘，由 Tile 构成的 二维数组
  // 没有空元素，用特殊的 EmptyTile 表示空单元格
  private Tile[][] board;
  private int numRows; // 棋盘的行数
  private int numCols; // 棋盘的列数
  // 游戏目标颜色
  private IColor goalColor;
  // 游戏是否开始
  private boolean gameStarted;
  // 撤销栈
  private Stack<Tile[][]> undoStack;
  // 重做栈
  private Stack<Tile[][]> redoStack;
  // tile 工厂
  private TileFactory tileFactory;


  public BasicPuralaxModel() {
    this.gameStarted = false;
    this.undoStack = new Stack<>();
    this.redoStack = new Stack<>();
    this.tileFactory = new BasicTileFactory();
  }

  @Override
  public void startGame(Tile[][] board, IColor goalColor) {
    if (board == null) {
      throw new IllegalArgumentException("Invalid board: board cannot be null;");
    }
    if (goalColor == null) {
      throw new IllegalArgumentException("Invalid goalColor: goalColor cannot be null.");
    }
    if (board.length == 0) {
      throw new IllegalArgumentException("Invalid board: board height must > 0.");
    }
    if (board[0].length == 0) {
      throw new IllegalArgumentException("Invalid board: board weight must > 0.");
    }
    if (!isRectBoard(board)) {
      throw new IllegalArgumentException("Invalid board: board must be a rect.");
    }
    if (boardHasNull(board)) {
      throw new IllegalArgumentException("Invalid board: board cannot has null.");
    }

    this.board = board;
    this.numRows = board.length;
    this.numCols = board[0].length;
    this.goalColor = goalColor;
    this.gameStarted = true;
  }

  // 判断棋盘是否是矩形
  private boolean isRectBoard(Tile[][] board) {
    if (board.length == 0 || board[0].length == 0) return false;
    int row0Height = board[0].length;
    for (Tile[] row : board) {
      if (row.length != row0Height) return false;
    }
    return true;
  }

  // 判断棋盘中是否有元素为 null
  private boolean boardHasNull(Tile[][] board) {
    for (Tile[] row : board) {
      for (Tile t : row) {
        if (t == null) return true;
      }
    }
    return false;
  }

  @Override
  public void moveTile(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }
    if (isPositionOutBoardRange(fromRow, fromCol) ||
            isPositionOutBoardRange(toRow, toCol)) {
      throw new IllegalArgumentException("From position or to position out board range.");
    }
    if (!isAdjacentPosition(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Tow position must adjacent.");
    }
    if (!board[fromRow][fromCol].canMove()) {
      throw new IllegalStateException("From position cannot move.");
    }
    if (!board[toRow][toCol].canMoveTo()) {
      throw new IllegalStateException("To position cannot move to.");
    }

    recordState();

    // 消耗要移动图块的一个点
    Tile fromTile = board[fromRow][fromCol];
    fromTile.spendOneDot();
    // 移动到位置的方块
    Tile toTile = board[toRow][toCol];
    // 移动方块
    board[toRow][toCol] = fromTile;
    // 原来位置用空方块覆盖
    board[fromRow][fromCol] = tileFactory.createEmptyTile();
    // 如果移动到位置是画家方块，则涂色
    if (toTile.isPainterTile()) {
      IColor barColor = toTile.getColor();
      floodFillPaint(toRow, toCol, barColor);
    }
  }

  // 判断位置坐标是否超出棋盘范围
  private boolean isPositionOutBoardRange(int row, int col) {
    return (row < 0 || row >= numRows) || (col < 0 || col >= numCols);
  }

  // 判断两位置坐标是否相邻，即上下左右四个方向
  // 不判断坐标是否有效，即对 (0, 0), (0, -1) 返回 true
  private boolean isAdjacentPosition(int row1, int col1, int row2, int col2) {
    return ((row1 == row2) && (Math.abs(col1 - col2) == 1)) ||
            ((col1 == col2) && (Math.abs(row1 - row2) == 1));
  }

  @Override
  public void paint(int sourceRow, int sourceCol, int targetRow, int targetCol)
          throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }
    if (isPositionOutBoardRange(sourceRow, sourceCol) ||
            isPositionOutBoardRange(targetRow, targetCol)) {
      throw new IllegalArgumentException("From position or to position out board range.");
    }
    if (!isAdjacentPosition(sourceRow, sourceCol, targetRow, targetCol)) {
      throw new IllegalArgumentException("Tow position must adjacent.");
    }
    Tile sourceTile = board[sourceRow][sourceCol];
    Tile targetTile = board[targetRow][targetCol];
    if (!sourceTile.canPaint()) {
      throw new IllegalStateException("Source position cannot paint.");
    }
    if (!targetTile.canBePainted()) {
      throw new IllegalStateException("Target position cannot be painted.");
    }
    IColor sourceColor = sourceTile.getColor();
    if (sourceColor.equals(targetTile.getColor())) {
      throw new IllegalStateException("Two tile must has different color.");
    }
    recordState();

    sourceTile.spendOneDot();
    floodFillPaint(targetRow, targetCol, sourceColor);
  }

  // 将该位置所有相邻且颜色相同的方块都染色
  private void floodFillPaint(int row, int col, IColor paintColor) {
    Tile t = board[row][col];
    // 起始图块的颜色
    IColor originColor = t.getColor();
    // 染为目标颜色
    t.setColor(paintColor);
    // 向 上下左右 相邻位置拓展
    floodFillPaint_helper(row - 1, col, originColor, paintColor);
    floodFillPaint_helper(row + 1, col, originColor, paintColor);
    floodFillPaint_helper(row, col - 1, originColor, paintColor);
    floodFillPaint_helper(row, col + 1, originColor, paintColor);
  }

  private void floodFillPaint_helper(int row, int col, IColor originColor, IColor paintColor) {
    // 检查坐标是否超出棋盘范围
    if (isPositionOutBoardRange(row, col)) return;
    Tile t = board[row][col];
    // 检查该位置图块是否能被涂色
    if (!t.canBePainted()) return;
    // 检查该位置图块是否和起始图块颜色相同
    if (!t.getColor().equals(originColor)) return;

    // 符合泛洪填充条件，染色
    t.setColor(paintColor);
    // 继续拓展
    floodFillPaint_helper(row - 1, col, originColor, paintColor);
    floodFillPaint_helper(row + 1, col, originColor, paintColor);
    floodFillPaint_helper(row, col - 1, originColor, paintColor);
    floodFillPaint_helper(row, col + 1, originColor, paintColor);
  }

  @Override
  public int getNumRows() {
    return gameStarted ? numRows : -1;
  }

  @Override
  public int getNumCols() {
    return gameStarted ? numCols : -1;
  }

  @Override
  public boolean allTilesGoalColor() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }

    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numCols; c++) {
        Tile t = board[r][c];
        if (!t.achieveGoalColor(goalColor)) return false;
      }
    }
    return true;
  }

  @Override
  public boolean noValidOperate() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }

    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numCols; c++) {
        Tile t = board[r][c];
        if (t.canMove() || t.canPaint()) return false;
      }
    }
    return true;
  }

  @Override
  public Tile getTileAt(int row, int col) throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }

    return board[row][col];
  }

  @Override
  public IColor getGoalColor() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }
    return goalColor;
  }

  @Override
  public void undo() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }

    // 撤销栈空，什么都不做，表现为撤销到最初始状态
    if (undoStack.isEmpty()) return;
    // 保存当前状态到重做栈
    redoStack.push(deppCopyCurrentBoard());
    // 恢复上一个状态
    this.board = undoStack.pop();
  }

  @Override
  public void redo() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game not started.");
    }

    // 重做栈为空，什么都不做
    if (redoStack.isEmpty()) return;
    // 保存当前状态到撤销栈
    undoStack.push(deppCopyCurrentBoard());
    // 恢复下一个状态
    this.board = redoStack.pop();
  }

  // 深度拷贝模型当前棋盘
  private Tile[][] deppCopyCurrentBoard() {
    Tile[][] copy = new Tile[numRows][numCols];
    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numCols; c++) {
        copy[r][c] = board[r][c].deepCopy();
      }
    }
    return copy;
  }

  // 记录棋盘状态，所有操作前调用，以提供撤销重做功能
  private void recordState() {
    undoStack.push(deppCopyCurrentBoard());
    redoStack.clear();
  }
}
