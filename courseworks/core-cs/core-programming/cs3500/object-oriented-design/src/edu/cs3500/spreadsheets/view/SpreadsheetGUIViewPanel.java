package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The main panel for all gui views. Sub class of JPanel, uses a border layout.
 */
public class SpreadsheetGUIViewPanel extends JPanel {

  private static int WIDTH;
  private static int HEIGHT;
  private static int scrollLength = 10;
  int rowOffset;
  int colOffset;
  private static final Color BG = Color.DARK_GRAY;
  JTextField[][] fieldGrid;
  private JPanel mainPanel;
  private JPanel rows;
  private JPanel cols;
  private SpreadsheetModel model;

  /**
   * Default constructor.
   * @param model to be used.
   * @param width of text fields.
   * @param height of text fields.
   */
  public SpreadsheetGUIViewPanel(SpreadsheetModel model, int width, int height) {
    super(new BorderLayout());

    this.WIDTH = width;
    this.HEIGHT = height;

    this.model = model;
    this.colOffset = 0;
    this.rowOffset = 0;

    fieldGrid = new JTextField[HEIGHT][WIDTH];

    this.mainPanel = new JPanel(new GridLayout(HEIGHT, WIDTH));
    this.rows = new JPanel(new GridLayout(HEIGHT, 1));
    this.cols = new JPanel(new GridLayout(1, WIDTH + 1));

    fillGrid(mainPanel);
    this.build();
  }

  /**
   * Builds the GUI.
   */
  private void build() {

    fillCoords(rows, cols);

    JPanel xButtons = new JPanel(new GridLayout(1, 3));
    JPanel yButtons = new JPanel(new GridLayout(2, 1));
    createXButtons(xButtons);
    createYButtons(yButtons);

    this.add(cols, BorderLayout.NORTH);
    this.add(rows, BorderLayout.WEST);
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(xButtons, BorderLayout.SOUTH);
    this.add(yButtons, BorderLayout.EAST);

  }


  //creates x buttons
  private void createXButtons(JPanel xButtons) {
    JButton left = new JButton("Left");
    JButton right = new JButton("Right");
    JButton reset = new JButton("Reset");

    left.addActionListener(e -> scroll("left"));
    right.addActionListener(e -> scroll("right"));
    reset.addActionListener(e -> scroll("Reset"));

    xButtons.add(reset);
    xButtons.add(left);
    xButtons.add(right);
  }

  //creates y buttons
  private void createYButtons(JPanel yButtons) {
    JButton up = new JButton("Up");
    JButton down = new JButton("Down");

    up.addActionListener(e -> scroll("up"));
    down.addActionListener(e -> scroll("down"));

    yButtons.add(up);
    yButtons.add(down);

  }


  /**
   * Fills the Coordinate headers with the row and column values.
   */
  private void fillCoords(JPanel rows, JPanel cols) {
    rows.removeAll();
    cols.removeAll();

    for (int i = 0; i < WIDTH + 1; i++) {
      String col = Coord.colIndexToName(i + colOffset + 1);
      JTextField textfield = new JTextField(col);
      textfield.setEditable(false);
      textfield.setFocusable(false);
      cols.add(textfield);
    }

    for (int j = 0; j < HEIGHT; j++) {
      String row = Integer.toString(j + rowOffset + 1);
      JTextField textfield = new JTextField(row);
      textfield.setEditable(false);
      textfield.setFocusable(false);
      rows.add(textfield);
    }
  }

  /**
   * Fills the Grid with its Cells.
   *
   * @return JPanel the panel of Cells.
   */
  private void fillGrid(JPanel panel) {
    panel.removeAll();

    ICell curCell;
    String curText;
    JTextField field;

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {

        curCell = model.getCellAt(new Coord(j + colOffset + 1, i + rowOffset + 1));
        curText = curCell.getValue().toString();

        field = new JTextField(curText, 6);
        field.setEditable(false);
        field.setFocusable(true);
        field.setBackground(Color.WHITE);
        fieldGrid[i][j] = field;
        panel.add(fieldGrid[i][j]);
      }
    }
  }

  private void setUpGrid(JPanel panel) {

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        panel.add(fieldGrid[i][j]);
      }
    }
  }

  /**
   * Fills the Grid with its Cells.
   *
   * @return JPanel the panel of Cells.
   */
  private void updateGrid(JPanel panel) {

    ICell curCell;
    String curText;

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {

        curCell = model.getCellAt(new Coord(j + colOffset + 1, i + rowOffset + 1));
        curText = curCell.getValue().toString();

        fieldGrid[i][j].setText(curText);
      }
    }
  }


  private void scroll(String direction) {
    switch (direction) {
      case "down":
        rowOffset += scrollLength;
        break;
      case "up":
        if (rowOffset - scrollLength >= 0) {
          rowOffset = rowOffset - scrollLength;
        }
        break;
      case "left":
        if (colOffset - scrollLength >= 0) {
          colOffset = colOffset - scrollLength;
        }
        break;
      case "right":
        colOffset += scrollLength;
        break;
      default:
        colOffset = 0;
        rowOffset = 0;
        break;
    }

    fillCoords(rows, cols);
    this.refresh();
  }


  /**
   * Refreshes the main panel with the text fields.
   */
  public void refresh() {
    updateGrid(mainPanel);
    this.revalidate();
    this.repaint();
  }
}
