package cs3500.Scrollbars.src.blobs.view;

import blobs.model.Point2D;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.sql.Blob;
import java.util.List;
import java.util.Random;

/**
 * A view based on JFrame
 */
public class BlobView extends JFrame implements IBlobView {

  private JPanel drawPanel;
  blobs.controller.IBlobController controller;


  public BlobView(int width, int height, blobs.controller.IBlobController controller) {
    super();
    this.controller = controller;
    setTitle("Blobs");
    //set a default size of the frame
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //this is the panel on which all the blobs are drawn
    //its size depends on the blobs themselves, whereas the size of the window
    //is fixed as above.
    drawPanel = new MyPanel();

    //create a JScrollPane and add the drawPanel to it
    JScrollPane scrolls = new JScrollPane(drawPanel);
    //add the JScrollPane to wherever you would have added the drawPanel
    this.add(scrolls);
    setVisible(true);
  }

  /**
   * This method is called whenever the drawing area changes Our drawing area is drawPanel, which is
   * decorated by the JScrollPane We reset the area of the drawPanel, and then ask it to revalidate.
   * This will cause its parent, the scrollbar, to update itself. Read more at {@see <a
   * href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#revalidate()"
   * revalidate documentation</a>}
   *
   * Finally, call repaint to redraw the panel
   */
  @Override
  public void setCanvasSize(int width, int height) {
    drawPanel.setPreferredSize(new Dimension(width, height));
    drawPanel.revalidate();
    drawPanel.repaint();
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    drawPanel.addMouseListener(listener);
  }


  class MyPanel extends JPanel {

    public MyPanel() {
      super();
      setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      List<blobs.model.Blob> blobs = controller.getBlobs();
      Point2D max = controller.getCanvasMax();
      Random r = new Random();

      for (int i = 0; i < blobs.size(); i++) {
        blobs.model.Blob b = blobs.get(i);
        Point2D center = b.getCenter();
        r.setSeed(i);
        Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        g.setColor(c);
        g.fillOval(center.x - b.getRadius(), center.y - b.getRadius(), 2 * b.getRadius(), 2 * b.getRadius());
      }
    }


  }
}
