package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Simple main class to make a triangular text file.
 */
public class MakeTriangleRecurFile {

  /**
   * Main method runs and creates a triangle input file.
   * @param args passed from command line.
   */
  public static void main(String[] args) {

    File file = new File("resources/triangleinput.txt");

    PrintWriter clear;
    try {
      clear = new PrintWriter(file.getAbsoluteFile());
    } catch (Exception ex) {
      throw new IllegalStateException("file not found");
    }
    //clear the file
    clear.print("");
    clear.close();

    PrintWriter pw;

    try {
      file.createNewFile();
      pw = new PrintWriter(new FileOutputStream(file, true));
    } catch (Exception ex) {
      throw new IllegalStateException("Cant open or make file");
    }

    pw.append("A1 1\n");

    for (int i = 2; i <= 50; i++) {
      String toAppend = "A";

      toAppend += i;
      toAppend += " =(SUM A";
      toAppend += (i - 1);
      toAppend += " ";
      toAppend += i;
      toAppend += ")\n";

      pw.append(toAppend);

    }

    pw.close();
  }
}

