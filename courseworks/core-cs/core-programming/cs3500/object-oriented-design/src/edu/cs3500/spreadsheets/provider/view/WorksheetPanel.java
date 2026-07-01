package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.IException;
import edu.cs3500.spreadsheets.provider.model.ValAdapter;
import edu.cs3500.spreadsheets.provider.model.ValueVisitor;
import java.awt.Dimension;
//import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;

import java.awt.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Adjustable;
import java.util.Objects;

import edu.cs3500.spreadsheets.provider.controller.FeaturesMain;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ViewWorksheet;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
//import edu.cs3500.spreadsheets.model.datastructures.values.BlankVal;
//import edu.cs3500.spreadsheets.model.datastructures.values.IException;
import edu.cs3500.spreadsheets.provider.model.IVal;
//import edu.cs3500.spreadsheets.model.datastructures.visitors.ValueVisitor;

/**
 * Worksheet panel constructs a Worksheet panel to be placed in the visual view with the models cell
 * information.
 */
public class WorksheetPanel extends JPanel implements AdjustmentListener, WorksheetPane {

  private final int cellHeight;
  private final int cellWidth;
  private int numCellsHeight;
  private int numCellsLength;
  private ViewWorksheet values;
  private Coord topLeftCoord;
  private Coord selectedCell;

  /**
   * Constructor for the worksheet panel that takes in a model and sets the fields in the worksheet
   * panel object.
   */
  WorksheetPanel(Worksheet model) {
    super();
    cellHeight = 20;
    cellWidth = 40;
    this.numCellsHeight = 20;
    this.numCellsLength = 30;
    this.topLeftCoord = new Coord(1, 1);
    this.values = model;
  }

  /**
   * Constructor for the worksheet panel that takes in a model, top left coordinents, cell length
   * and height, and the numbers of cells length-wise and height-wise.
   *
   * @param model          view worksheet object
   * @param topLeft        coord of the top left cell
   * @param numCellsHeight size of the cells height
   * @param numCellsLength size of the cells length
   * @param cellHeight     number of cells height wise
   * @param cellLength     number of cells length
   */
  private WorksheetPanel(ViewWorksheet model, Coord topLeft, int numCellsHeight, int numCellsLength,
      int cellHeight, int cellLength) {
    super();
    this.topLeftCoord = topLeft;
    this.numCellsHeight = numCellsHeight;
    this.numCellsLength = numCellsLength;
    this.cellHeight = cellHeight;
    cellWidth = cellLength;
    this.values = model;
    setPreferredSize(new Dimension(numCellsHeight * numCellsLength,
        (numCellsLength * numCellsHeight) / 2));
  }

  @Override
  public Coord getTopLeft() {
    return new Coord(this.topLeftCoord.col,
        this.topLeftCoord.row);
  }

  @Override
  public void moveX(int x) {
    this.selectedCell = new Coord(this.selectedCell.col + x, this.selectedCell.row);
  }

  @Override
  public void moveY(int y) {
    this.selectedCell = new Coord(this.selectedCell.col, this.selectedCell.row + y);
  }

  @Override
  public void highlightCell(Coord c) {
    if (c.equals(selectedCell)) {
      this.selectedCell = null;
    } else {
      this.selectedCell = c;
    }
  }

  @Override
  public void addFeatures(FeaturesMain f) {
    // does nothing from provider
  }

  @Override
  public Coord selectCell(int x, int y) {
    int xPos = (int) ((x - 1.2 * cellWidth) / this.cellWidth + this.topLeftCoord.col);
    int yPos = (int) ((y - 3.9 * cellHeight) / this.cellHeight) + this.topLeftCoord.row;

    Coord c;
    try {
      c = new Coord(xPos, yPos);
    } catch (IllegalArgumentException e) {
      c = new Coord(1, 1);
    }
    return c;
  }

  @Override
  public void setModel(ViewWorksheet model) {
    this.values = model;
  }

  /**
   * Builder for the worksheet panel that.
   */
  public static class WorksheetPanelBuilder {

    ViewWorksheet panel;
    private Coord topLeft = new Coord(1, 1);
    private int displayCellsHeight = 20;
    private int displayCellsLength = 40;
    private int cellHeight = 20;
    private int cellLength = 40;

    /**
     * sets height for worksheet panel.
     *
     * @param height height of display cell
     */
    public void setDisplayHeight(int height) {
      this.displayCellsHeight = height;
    }

    /**
     * sets lenght for worksheet panel.
     *
     * @param length length of display cell
     */
    public void setDisplayCellsLength(int length) {
      this.displayCellsLength = length;
    }

    /**
     * sets cell height for worksheet panel.
     *
     * @param height height of cell
     */
    public void setCellHeight(int height) {
      this.cellHeight = height;
    }

    /**
     * sets cell length for worksheet panel.
     *
     * @param length length of cell
     */
    public void setCellLength(int length) {
      this.cellLength = length;
    }

    /**
     * sets panel model for worksheet panel.
     *
     * @param model view worksheet model
     */
    public void setModel(ViewWorksheet model) {
      this.panel = model;
    }

    /**
     * sets coord for top left in worksheet panel.
     *
     * @param tl top left coord
     */
    public void setTopLeft(Coord tl) {
      this.topLeft = tl;
    }

    /**
     * Builds an instance of a Worksheet Panel.
     *
     * @return worksheet panel
     */
    WorksheetPanel buildWorksheet() {
      return new WorksheetPanel(this.panel,
          this.topLeft, this.displayCellsHeight, this.displayCellsLength,
          this.cellHeight, this.cellLength);
    }
  }
  /*  void setTopLeftCoord(Coord c) {
    this.topLeftCoord = c;
  }

  void setHeight(int height) {
    this.height = height;
  }

  void setLength(int length) {
    this.length = length;
  }*/

  /**
   * Paints the boxes and cell values within the JPanel.
   *
   * @param g Graphics object
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    //int x_pos = 0;
    //int y_pos = 0;
    //g2d.drawRect(0, 0, cellWidth, cellHeight);
    for (int i = 1; i <= numCellsLength; i++) {
      Shape curClip = g2d.getClip();
      g2d.setClip(cellWidth * i, 0, cellWidth, cellHeight);
      g.setColor(Color.CYAN);
      g2d.fillRect(cellWidth * i, 0, cellWidth, cellHeight);
      g.setColor(Color.BLACK);
      g2d.drawString(Coord.colIndexToName(topLeftCoord.col + i - 1),
          (cellWidth * i) + cellWidth / 5,
          cellHeight / 2);
      g2d.setClip(curClip);
    }

    for (int i = 1; i <= numCellsHeight; i++) {
      Shape curClip = g2d.getClip();
      g2d.setClip(0, i * cellHeight, cellWidth, cellHeight);
      g.setColor(Color.ORANGE);
      g2d.fillRect(0, i * cellHeight, cellWidth, cellHeight);
      g.setColor(Color.BLACK);
      g2d.drawString(Integer.toString(i + topLeftCoord.row - 1), 0, (i + 1) * cellHeight);
      //y_pos += cellHeight;
      g2d.setClip(curClip);
    }

    for (int i = 1; i <= numCellsLength; i++) {
      for (int j = 1; j <= numCellsHeight; j++) {
        Shape curClip = g2d.getClip();
        g.setColor(Color.BLACK);
        //System.out.println("x,y - " + i * cellWidth + " " + j * cellHeight);
        g2d.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
        g2d.setClip(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
        IVal val = this.values.getAllValues().getOrDefault(new Coord(i + topLeftCoord.col - 1,
            j + topLeftCoord.row - 1), new ValAdapter());
        String res = val.accept(new RenderIVal());
        g2d.drawString(res, (i) * cellWidth, (j + 1) * cellHeight);
        g2d.setClip(curClip);
      }
    }

    if (Objects.nonNull(this.selectedCell)) {
      g.setColor(Color.RED);
      g2d.drawRect((selectedCell.col - topLeftCoord.col + 1) * cellWidth,
          (selectedCell.row - topLeftCoord.row + 1) * cellHeight,
          cellWidth, cellHeight);
    }
  }

  /**
   * Changes the top left coord value based on the scroll bar.
   *
   * @param adjustmentEvent listener for the scroll bar
   */
  @Override
  public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
    Adjustable adj = adjustmentEvent.getAdjustable();

    int orientation = adj.getOrientation();

    if (orientation == Adjustable.VERTICAL) {
      if (adj.getValue() > adj.getMaximum() * .8 && adj.getMaximum() + 100 > 0
          && topLeftCoord.row < adj.getValue()) {
        adj.setMaximum(adj.getMaximum() + 50);
      }
    } else {
      if (adj.getValue() > adj.getMaximum() * .8 && adj.getMaximum() + 100 > 0
          && topLeftCoord.col < adj.getValue()) {
        adj.setMaximum(adj.getMaximum() + 50);
      }
    }

    if (orientation == Adjustable.VERTICAL) {
      this.topLeftCoord = new Coord(this.topLeftCoord.col, adjustmentEvent.getValue());
    } else if (orientation == Adjustable.HORIZONTAL) {
      this.topLeftCoord = new Coord(adjustmentEvent.getValue(), this.topLeftCoord.row);
    }
    repaint();

  }

  /**
   * Values visitor for the values in the map.
   */

  static class RenderIVal implements ValueVisitor<String> {

    /**
     * returns double as a string.
     *
     * @param d the double value
     */
    @Override
    public String visitValueDouble(Double d) {
      return d.toString();
    }

    /**
     * returns boolean as a string.
     *
     * @param b the boolean value
     */
    @Override
    public String visitValueBoolean(Boolean b) {
      return b.toString();
    }

    /**
     * returns string.
     *
     * @param s the string value
     */
    @Override
    public String visitValueString(String s) {
      return s;
    }

    /**
     * returns blank value as a string.
     */
    @Override
    public String visitBlankValue() {
      return "";
    }

    /**
     * returns error as a string.
     *
     * @param e the error value
     */
    @Override
    public String visitErr(IException e) {
      return e.toString();
    }
  }


}