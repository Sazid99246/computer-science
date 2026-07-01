package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.ViewWorksheet;
import java.awt.TextField;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollBar;

import edu.cs3500.spreadsheets.provider.controller.FeaturesMain;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * For java style.
 */
public class EditableView extends JPanel implements WorksheetPane {

  private TextField input;
  private JButton submit;
  private JButton discard;
  private JButton save;
  private JButton load;
  private WorksheetPanel worksheetPanel;

  EditableView(ViewWorksheet model) {
    super();

    JPanel editPanel = new JPanel(new FlowLayout());
    this.input = new TextField(75);
    this.submit = new JButton("√");
    this.submit.setActionCommand("submit");
    this.discard = new JButton("X");
    this.discard.setActionCommand("discard");
    this.save = new JButton("Save");
    this.save.setActionCommand("save");
    this.load = new JButton("Load");
    this.load.setActionCommand("load");
    editPanel.add(input);
    editPanel.add(submit);
    editPanel.add(load);
    editPanel.add(save);
    editPanel.add(discard);

    //this.gridPanel = new JPanel();

    WorksheetPanel.WorksheetPanelBuilder builder = new WorksheetPanel.WorksheetPanelBuilder();
    builder.setModel(model);
    this.worksheetPanel = builder.buildWorksheet();
    //this.values = model.getAllValues();
    //Coord topLeftCoord = new Coord(1, 1);
    JScrollBar vertical = new JScrollBar(JScrollBar.VERTICAL, 1, 100, 1, 600);
    JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL, 1, 100, 1, 600);
    vertical.addAdjustmentListener(worksheetPanel);
    horizontal.addAdjustmentListener(worksheetPanel);

    GridBagConstraints gbc = new GridBagConstraints();
    setLayout(new GridBagLayout());

    gbc.anchor = GridBagConstraints.PAGE_START;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = .25;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(this.input, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    add(this.submit, gbc);

    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    add(this.discard, gbc);

    gbc.gridx = 3;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    add(this.save, gbc);

    gbc.gridx = 4;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    add(this.load, gbc);

    gbc.gridx = 5;
    gbc.gridy = 1;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.VERTICAL;
    add(vertical, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0;
    gbc.weighty = .5;
    gbc.fill = GridBagConstraints.BOTH;
    add(this.worksheetPanel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(horizontal, gbc);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    //addMouseListener(this);

    /*this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Editable Visual View");
    this.setVisible(true);
    this.pack();*/
  }

  @Override
  public Coord getTopLeft() {
    return this.worksheetPanel.getTopLeft();
  }

  @Override
  public void moveX(int x) {
    this.worksheetPanel.moveX(x);
  }

  @Override
  public void moveY(int y) {
    this.worksheetPanel.moveY(y);
  }

  @Override
  public void highlightCell(Coord c) {
    this.worksheetPanel.highlightCell(c);
  }

  @Override
  public void addFeatures(FeaturesMain flocal) {
    this.submit.addActionListener(evt -> flocal.putCell(input.getText()));
    this.discard.addActionListener(evt -> flocal.discard());
    this.save.addActionListener(evt -> flocal.save());
    this.load.addActionListener(evt -> flocal.load());
  }

  @Override
  public Coord selectCell(int x, int y) {
    return this.worksheetPanel.selectCell(x, y);
  }


  @Override
  public void setModel(ViewWorksheet model) {
    this.worksheetPanel.setModel(model);
  }

  @Override
  public void addKeyListener(KeyListener kl) {
    this.worksheetPanel.addKeyListener(kl);
  }


  void setText(String s) {
    this.input.setText(s);
  }


}
