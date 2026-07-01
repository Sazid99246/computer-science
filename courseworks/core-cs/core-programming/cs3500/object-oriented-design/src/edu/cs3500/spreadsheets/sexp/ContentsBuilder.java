package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.AddObject;
import edu.cs3500.spreadsheets.model.CVBool;
import edu.cs3500.spreadsheets.model.CVDouble;
import edu.cs3500.spreadsheets.model.CVError;
import edu.cs3500.spreadsheets.model.CVString;
import edu.cs3500.spreadsheets.model.CompareObject;
import edu.cs3500.spreadsheets.model.ConcatObject;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.Function;
import edu.cs3500.spreadsheets.model.Operation;
import edu.cs3500.spreadsheets.model.ProductObject;
import edu.cs3500.spreadsheets.model.Reference;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sexp visitor to build into cell values.
 */
public class ContentsBuilder implements SexpVisitor<Formula> {

  Map<String, Operation> operations;
  Coord position;
  SpreadsheetModel model;

  /**
   * Default constructor.
   *
   * @param position to put the new formula.
   * @param model to add the contents to.
   */
  public ContentsBuilder(Coord position, SpreadsheetModel model) {
    operations = new HashMap<String, Operation>();
    operations.put("SUM", new AddObject());
    operations.put("CONCAT", new ConcatObject());
    operations.put("PRODUCT", new ProductObject());
    operations.put("<", new CompareObject());
    this.model = model;

    this.position = position;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new CVBool(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new CVDouble(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    return parseList(l);
  }

  @Override
  public Formula visitSymbol(String s) {
    try {
      checkValidReference(s);
    } catch (IllegalArgumentException e) {
      return new CVError();
    }

    if (!s.contains(":")) {
      try {
        return new Reference(new Coord(s), position, model);
      } catch (NumberFormatException ex1) {
        return new CVString(s);
      } catch (IllegalArgumentException ex2) {
        return new CVError();
      }
    } else {
      int colonIndex = s.indexOf(":");
      try {
        return new Reference(new Coord(s.substring(0, colonIndex)),
            new Coord(s.substring(colonIndex + 1)), position, model);
      } catch (NumberFormatException ex1) {
        return new CVString(s);
      } catch (IllegalArgumentException ex2) {
        return new CVError();
      }
    }
  }

  @Override
  public Formula visitString(String s) {

    return new CVString(s);
  }

  private Formula parseList(List<Sexp> l) {
    ArrayList<Formula> forms = new ArrayList<>();
    String symbol = l.get(0).toString();
    for (int i = 1; i < l.size(); i++) {
      Formula f = l.get(i).accept(this);
      forms.add(f);
    }

    Function.clearEval();

    return new Function(operations.get(symbol), forms);
  }

  private void checkValidReference(String s) {
    int acc = 0;

    for (Character c : s.toCharArray()) {
      if (c.equals(':')) {
        acc += 1;
      }
    }

    if (acc > 1) {
      throw new IllegalArgumentException("not a valid reference");
    }
  }

}
