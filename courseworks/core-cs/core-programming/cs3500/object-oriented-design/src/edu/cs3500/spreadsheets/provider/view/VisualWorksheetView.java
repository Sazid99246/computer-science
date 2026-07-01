package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.ModelAdapter;
import edu.cs3500.spreadsheets.provider.model.ViewWorksheet;
import edu.cs3500.spreadsheets.provider.model.Worksheet;
import java.awt.BorderLayout;
import java.awt.Dimension;
//import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JFrame;
import javax.swing.JFileChooser;

import edu.cs3500.spreadsheets.provider.controller.FeaturesMain;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Visual Worksheet View that creates a visual grid representation of a worksheet.
 */
public class VisualWorksheetView extends JFrame implements WorksheetView {

  private ViewWorksheet model;
  private JFileChooser jfc = new JFileChooser();
  private EditableView editablePanel;
  private int numRows;
  private int numCols;
  private FeaturesMain f;
  private Map<Character, Runnable> keyTypes = new HashMap<>();
  private Map<Integer, Runnable> keyPresses = new HashMap<>();
  private Map<Integer, Runnable> keyReleases = new HashMap<>();


  /**
   * Construtor to render a blank visual view with empty cells.
   */
  public VisualWorksheetView() {

    super();

    //this.model = new BasicWorksheet.BasicWorksheetBuilder().createWorksheet();
    this.model = new ModelAdapter(new BasicSpreadsheetModel());
    this.numCols = 30;
    this.numRows = 20;

    //spreadsheetPanel = new ScrollableWorksheet(model);
    BorderLayout bl = new BorderLayout();
    bl.addLayoutComponent(editablePanel, BorderLayout.CENTER);

    this.add(editablePanel);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Spreadsheet Visual View");
    this.setVisible(false);
    this.pack();
  }

  /**
   * Constructor for the visual view that takes in a model, the number of columns, rows, the height
   * of a cell and length of a cell and renders a view with the model information.
   *
   * @param model      Worksheet model object
   * @param numCols    number of columns in the view
   * @param numRows    number of rows in the view
   * @param cellHeight height of the cells in the view
   * @param cellLength length of the cells in the view
   */
  public VisualWorksheetView(Worksheet model, int numCols, int numRows,
      int cellHeight, int cellLength) {
    super();

    this.model = model;//new BasicWorksheet.BasicWorksheetBuilder().createWorksheet();

    this.numCols = numCols;
    this.numRows = numRows;

    //spreadsheetPanel = new ScrollableWorksheet(model);

    BorderLayout bl = new BorderLayout();
    bl.addLayoutComponent(editablePanel, BorderLayout.CENTER);

    this.add(editablePanel);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Spreadsheet Visual View");
    this.setPreferredSize(new Dimension(this.numCols * cellLength,
        this.numRows * cellHeight));
    this.setVisible(false);
    this.pack();
  }

  /**
   * Constructor for the visual view that takes in a model and renders a view with the model
   * information.
   *
   * @param model Worksheet model object
   */
  /*public VisualWorksheetView(Worksheet model) {
    super();
    this.model = new BasicWorksheet.BasicWorksheetBuilder().createWorksheet();
    values = model.getAllValues();
    this.numCols = 30;
    this.numRows = 20;
    this.cellHeight = 20;
    this.cellLength = 40;

    spreadsheetPanel = new ScrollableWorksheet(model);
    //BorderLayout bl = new BorderLayout();
    //bl.addLayoutComponent(spreadsheetPanel, BorderLayout.CENTER);

    this.add(spreadsheetPanel);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Spreadsheet Visual View");
    this.setPreferredSize(spreadsheetPanel.getPreferredSize());
    this.setVisible(false);
    this.pack();
  }*/

  /**
   * Constructor for the visual view that takes in a model and renders a view with the model
   * information.
   *
   * @param model Worksheet model object
   */
  public VisualWorksheetView(ViewWorksheet model) {

    super();

    this.model = model;//new BasicWorksheet.BasicWorksheetBuilder().createWorksheet();
    this.numCols = 30;
    this.numRows = 20;

    editablePanel = new EditableView(model);
    BorderLayout bl = new BorderLayout();
    bl.addLayoutComponent(editablePanel, BorderLayout.CENTER);

    this.add(editablePanel);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Spreadsheet Visual View");
    this.setPreferredSize(editablePanel.getPreferredSize());
    this.setVisible(false);
    this.pack();
  }

  /*
  public void renderCells() {
    for (int x = lowestCoord().row; x <= highestCoord().row; x++) {
      for (int i = lowestCoord().col; i <= highestCoord().col; i++) {
        Coord current = new Coord(i, x);
        if (values.containsKey(current)) {
          spreadsheetPanel.add(new JTextArea((values.get(current)).toString()));
        } else {
          spreadsheetPanel.add(new JTextArea(" "));
        }
      }
    }
  }

  private Coord highestCoord() {
    int row = 0;
    int col = 0;
    int counter = values.size();
    int firstCol = 0;
    int secondCol = 1;
    int firstRow = 0;
    int secondRow = 1;

    ArrayList<Coord> coordList = new ArrayList<>(this.values.keySet());

    while (counter > 0) {
      if (coordList.get(firstCol).col > coordList.get(secondCol).col) {
        col = coordList.get(firstCol).col;
        secondCol++;
      } else if (coordList.get(firstCol).col == coordList.get(secondCol).col) {
        col = coordList.get(firstCol).col;
        secondCol++;
      } else {
        col = coordList.get(secondCol).col;
        firstCol++;
      }

      if (coordList.get(firstRow).row > coordList.get(secondRow).row) {
        row = coordList.get(firstRow).row;
        secondRow++;
      } else if (coordList.get(firstRow).row == coordList.get(secondRow).row) {
        row = coordList.get(firstRow).row;
        secondRow++;
      } else {
        row = coordList.get(secondRow).row;
        firstRow++;
      }
      counter--;
    }
    return new Coord(col, row);
  }

  private Coord lowestCoord() {
    int row = 0;
    int col = 0;
    int counter = values.size();
    int firstCol = 0;
    int secondCol = 1;
    int firstRow = 0;
    int secondRow = 1;

    ArrayList<Coord> coordList = new ArrayList<>(this.values.keySet());

    while (counter > 1) {
      if (coordList.get(firstCol).col < coordList.get(secondCol).col) {
        col = coordList.get(firstCol).col;
        secondCol++;
      } else if (coordList.get(firstCol).col == coordList.get(secondCol).col) {
        col = coordList.get(firstCol).col;
        secondCol++;
      } else {
        col = coordList.get(secondCol).col;
        firstCol++;
      }

      if (coordList.get(firstRow).row < coordList.get(secondRow).row) {
        row = coordList.get(firstRow).row;
        secondRow++;
      } else if (coordList.get(firstRow).row == coordList.get(secondRow).row) {
        row = coordList.get(firstRow).row;
        secondRow++;
      } else {
        row = coordList.get(secondRow).row;
        firstRow++;
      }
      counter--;
    }
    return new Coord(col, row);
  }*/

  /**
   * Opens the visual view so it is visual to the user.
   */
  @Override
  public void render() {
    this.setVisible(true);
  }

  /**
   * Updates the current view.
   */
  @Override
  public void refresh() {
    this.repaint();
    this.editablePanel.repaint();
  }


  /**
   * Sets the action listeners for the visual view.
   *
   * @param flocal an object containing callbacks
   */
  @Override
  public void addFeatures(FeaturesMain flocal) {
    this.editablePanel.addFeatures(flocal);
    this.f = flocal;
    keyPresses.put(KeyEvent.VK_LEFT, () -> f.moveSelectedLeft());
    keyPresses.put(KeyEvent.VK_DOWN, () -> f.moveSelectedDown());
    keyPresses.put(KeyEvent.VK_RIGHT, () -> f.moveSelectedRight());
    keyPresses.put(KeyEvent.VK_UP, () -> f.moveSelectedUp());
    keyPresses.put(KeyEvent.VK_BACK_SPACE, () -> f.removeCell());

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        if (keyTypes.containsKey(keyEvent.getKeyChar())) {
          System.out.println("hello");
          keyTypes.get(keyEvent.getKeyChar()).run();
        }
      }

      @Override
      public void keyPressed(KeyEvent keyEvent) {
        if (keyPresses.containsKey(keyEvent.getKeyCode())) {
          keyPresses.get(keyEvent.getKeyCode()).run();
        }
      }

      @Override
      public void keyReleased(KeyEvent keyEvent) {
        if (keyReleases.containsKey(keyEvent.getKeyCode())) {
          keyReleases.get(keyEvent.getKeyCode()).run();
        }
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Coord c = editablePanel.selectCell(e.getX(), e.getY());
        flocal.selectCell(c);
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        //super.mouseDragged(e);
      }
    });
    //this.editablePanel.addFeatures(featuresListener);
  }

  /**
   * Highlights a certain given cell.
   *
   * @param c Coord object with cell coordinates
   */
  @Override
  public void highlightCell(Coord c) {
    this.editablePanel.highlightCell(c);
    editablePanel.setText(this.model.getRawVal(c));
    this.repaint();
  }

  @Override
  public void moveX(int i) {
    this.editablePanel.moveX(i);

  }

  @Override
  public void moveY(int i) {
    this.editablePanel.moveY(i);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public Coord selectCell(int x, int y) {
    return this.editablePanel.selectCell(x, y);
  }

  @Override
  public void setText(String s) {
    this.editablePanel.setText(s);
  }

  @Override
  public void setModel(ViewWorksheet model) {
    this.model = model;
    this.editablePanel.setModel(model);
  }

  @Override
  public String getOpenFile() {
    int rv = this.jfc.showOpenDialog(this.editablePanel);
    if (rv == JFileChooser.APPROVE_OPTION) {
      return jfc.getSelectedFile().getPath();
    } else {
      return "";
    }
  }

  @Override
  public String getSaveFile() {
    if (this.jfc.showSaveDialog(this.editablePanel)
        == JFileChooser.APPROVE_OPTION) {
      return jfc.getSelectedFile().getPath();
    } else {
      return "";
    }
  }
}