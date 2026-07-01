package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.view.SpreadsheetFrameView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Represents a simple main method to test building worksheets. Debugging purposes only.
 */
public class TestMain {

  /**
   * Main method.
   *
   * @param args The command line args passed in
   * @throws IllegalArgumentException if input formatting is incorrect.
   * @throws IllegalStateException    if the file cannot be found.
   */
  public static void main(String[] args) {

    if (args.length > 4) {
      throw new IllegalArgumentException("too many args");
    } else if (!(args[0].equals("-in")) || !(args[2].equals("-eval"))) {
      throw new IllegalArgumentException("invalid syntax");
    }

    BasicSpreadsheetModel model;

    try {
      model = WorksheetReader
          .read(new WorkSheetBuilderImpl(), new FileReader(args[1]));
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("file not found");
    }

    Map<Coord, ICell> result = model.getAllCells();
    ICell cur;
    for (Map.Entry<Coord, ICell> e : result.entrySet()) {
      cur = e.getValue();
      if (cur.getValue() instanceof CVError) {
        System.out.println("Error in cell " + cur.getCoord().toString());
      }
    }

    CellValue val = model.getCellAt(new Coord(args[3])).getValue();

    if (val instanceof CVBlank) {
      System.out.println("Cell " + args[3] + " is blank");
    } else if (val instanceof CVString) {

      String contents = val.toString();

      contents = contents.replaceAll("\\\\", "\\\\\\\\");
      contents = contents.replaceAll("\"", "\\\\\"");

      String ret = "\"";
      ret += contents + "\"";

      System.out.println(ret);

    } else {
      System.out.println(val.toString());
    }

    System.out.println(model.coordMap);
    SpreadsheetFrameView view = new SpreadsheetFrameView(new ReadOnlyModel(model));
    try {
      view.render();
    } catch (IOException ex) {
      throw new IllegalStateException("it broke");
    }
  }

}

