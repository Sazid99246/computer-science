package edu.cs3500.spreadsheets.view;


import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Frame view for a spreadsheets model. Basic gui.
 */
public class SpreadsheetFrameView extends JFrame implements SpreadsheetView {

  private static int WIDTH = 15;
  private static int HEIGHT = 30;
  JTextField[][] fieldGrid;
  private SpreadsheetGUIViewPanel panel;

  /**
   * Constructor that takes a read only model.
   *
   * @param model to be displayed
   */
  public SpreadsheetFrameView(SpreadsheetModel model) {
    super();
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());

    fieldGrid = new JTextField[HEIGHT][WIDTH];

    panel = new SpreadsheetGUIViewPanel(model, WIDTH, HEIGHT);

    this.add(panel, BorderLayout.CENTER);
    this.pack();
    this.setLocationByPlatform(true);
  }



  /**
   * Renders the spreadsheets view.
   */
  @Override
  public void render() throws IOException {
    setVisible(true);
  }

  @Override
  public void refresh() {
    this.panel.refresh();
    this.pack();
    this.repaint();
  }

  /**
   * For loading a file. Updates this view to contain the cells from the loaded model.
   *
   * @param model the model to use.
   */
  @Override
  public void updateModel(SpreadsheetModel model) {
    this.getContentPane().removeAll();

    panel = new SpreadsheetGUIViewPanel(model, WIDTH, HEIGHT);

    this.add(panel, BorderLayout.CENTER);

    this.pack();


    refresh();
  }

}

