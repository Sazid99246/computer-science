package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;

/**
 * Worksheet builder that makes a basic spreadsheets model by making a
 * blank model, editing the cells, and returning the completed model.
 */
public class WorkSheetBuilderImpl implements WorksheetBuilder<BasicSpreadsheetModel> {

  BasicSpreadsheetModel model = new BasicSpreadsheetModel();

  @Override
  public WorksheetBuilder<BasicSpreadsheetModel> createCell(int row, int col, String contents) {
    model.editCell(new Coord(col, row), contents);
    return this;
  }

  @Override
  public BasicSpreadsheetModel createWorksheet() {
    return model;
  }
}
