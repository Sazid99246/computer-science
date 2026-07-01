package cs3500.Scrollbars.src.blobs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The model interface. It supports functions to add a blob, get the minimum and maximum extents of
 * the smallest rectangular area that contains all the blobs and finally all the blobs themselves
 */
public interface IBlobModel {
  void add(int x, int y, int radius);

  Point2D getMax();

  Point2D getMin();

  List<Blob> getBlobs();
}
