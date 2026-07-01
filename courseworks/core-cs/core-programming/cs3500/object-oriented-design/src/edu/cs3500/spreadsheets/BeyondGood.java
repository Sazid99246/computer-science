package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.controller.SpreadSheetController;
import edu.cs3500.spreadsheets.model.BasicSpreadsheetModel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorkSheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.provider.controller.ControllerAdapter;
import edu.cs3500.spreadsheets.provider.model.ModelAdapter;
import edu.cs3500.spreadsheets.view.SpreadsheetFrameView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main class for our program.
 */
public class BeyondGood {

  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    if (args.length > 4) {
      throw new IllegalArgumentException("too many args");
    }

    switch (args[0]) {
      case "-in":
        switch (args[2]) {
          case "-eval":
            evalHelp(args[1], args[3]);
            break;
          case "-save":
            saveHelp(args[1], args[3]);
            break;
          case "-gui":
            guiHelp(args[1]);
            break;
          case "-edit":
            editHelp(args[1]);
            break;
          case "-provider":
            providerHelp(args[1]);
            break;
          default:
            throw new IllegalArgumentException("invalid syntax");
        }
        break;
      case "-gui":
        guiHelp(null);
        break;
      case "-edit":
        editHelp(null);
        break;
      case "-provider":
        providerHelp(null);
        break;
      default:
        throw new IllegalArgumentException("invalid syntax");
    }
  }

  /**
   * <p>Helper that runs a {@link SpreadsheetFrameView} from the file.</p>
   *
   * @param fileName the file being viewed.
   */
  private static void guiHelp(String fileName) {
    BasicSpreadsheetModel model;

    if (fileName == null) {
      model = new BasicSpreadsheetModel();
    } else {
      try {
        model = WorksheetReader
            .read(new WorkSheetBuilderImpl(), new FileReader(fileName));
      } catch (FileNotFoundException ex) {
        System.out.println("File not found.");
        model = new BasicSpreadsheetModel();
      }
    }

    SpreadsheetView gui = new SpreadsheetFrameView(new ReadOnlyModel(model));
    try {
      gui.render();
    } catch (IOException ex) {
      throw new IllegalStateException("it broke");
    }
  }

  /**
   * <p>Helper that runs a {@link edu.cs3500.spreadsheets.view.SpreadsheetEditableView} from the
   * file.</p>
   *
   * @param fileName the file being viewed.
   */
  private static void editHelp(String fileName) {
    BasicSpreadsheetModel model;
    SpreadSheetController controller;

    if (fileName == null) {
      model = new BasicSpreadsheetModel();
    } else {
      try {
        model = WorksheetReader
            .read(new WorkSheetBuilderImpl(), new FileReader(fileName));
      } catch (FileNotFoundException ex) {
        System.out.println("File not found.");
        model = new BasicSpreadsheetModel();
      }
    }
    try {
      controller = new SpreadSheetController(model);
      controller.start();
    } catch (IOException ex) {
      throw new IllegalStateException("it broke");
    }
  }

  /**
   * <p>Helper that runs a {@link edu.cs3500.spreadsheets.provider.view.VisualWorksheetView}
   * from the file.</p>
   *
   * @param fileName the file being viewed.
   */
  private static void providerHelp(String fileName) {
    //TODO: controller and view
    BasicSpreadsheetModel model;

    if (fileName == null) {
      model = new BasicSpreadsheetModel();
    } else {
      try {
        model = WorksheetReader
            .read(new WorkSheetBuilderImpl(), new FileReader(fileName));
      } catch (FileNotFoundException ex) {
        System.out.println("File not found.");
        model = new BasicSpreadsheetModel();
      }
    }
    SpreadSheetController controller = new SpreadSheetController(model);
    ModelAdapter newModel = new ModelAdapter(model);
    ControllerAdapter controllerAdapter = new ControllerAdapter(newModel, controller);
    try {
      controllerAdapter.goRender();
    } catch (IOException ex) {
      throw new IllegalStateException("it broke");
    }
  }

  /**
   * <p>Saves the given file to a new file.</p>
   *
   * @param fileName    the file being saved.
   * @param newFileName the name of the new file being created.
   */
  private static void saveHelp(String fileName, String newFileName) {
    BasicSpreadsheetModel model;

    try {
      model = WorksheetReader
          .read(new WorkSheetBuilderImpl(), new FileReader(fileName));
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("file not found");
    }

    // clear the file
    File file = new File(newFileName);
    PrintWriter clear;
    try {
      clear = new PrintWriter(file.getAbsoluteFile());
    } catch (Exception ex) {
      throw new IllegalStateException("file not found");
    }

    clear.print("");
    clear.close();

    // now write to the file
    PrintWriter pw;

    try {
      file.createNewFile();
      pw = new PrintWriter(new FileOutputStream(file, true));
    } catch (Exception ex) {
      throw new IllegalStateException("Cant open or make file");
    }

    SpreadsheetView textView = new SpreadsheetTextualView(pw, model);

    try {
      textView.render();
    } catch (IOException ex) {
      throw new IllegalStateException("IOException");
    }

    pw.close();
  }

  /**
   * <p>Runs the evaluation main through the console.</p>
   *
   * @param fileName the file being read.
   * @param cell     the cell being evaluated.
   */
  private static void evalHelp(String fileName, String cell) {

    BasicSpreadsheetModel model;

    try {
      model = WorksheetReader
          .read(new WorkSheetBuilderImpl(), new FileReader(fileName));
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("file not found");
    }
    String rawCellVal = model.getCellAt(new Coord(cell)).toString();
    if (rawCellVal.equals("")) {
      System.out.println("Error in Cell " + cell + ": Cell is empty");
    } else if (rawCellVal.equals("#ERROR")) {
      System.out.println("Error in Cell " + cell + ": Invalid raw contents.");
    } else {
      System.out.println(rawCellVal);
    }
  }
}
