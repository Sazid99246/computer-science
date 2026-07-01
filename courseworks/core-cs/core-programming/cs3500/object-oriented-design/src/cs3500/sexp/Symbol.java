package cs3500.sexp;

import java.util.List;

import cs3500.husky.withvars.success.AndExp;
import cs3500.husky.withvars.success.Expr;
import cs3500.husky.withvars.success.LetExp;
import cs3500.husky.withvars.success.NotExp;
import cs3500.husky.withvars.success.OrExp;
import cs3500.husky.withvars.success.Variable;

/**
 * An arbitrary symbol.
 */
public class Symbol implements Sexp {
  String name;

  public Symbol(String name) {
    this.name = name;
  }

  @Override
  public Expr translate() {
    return new Variable(this.name);
  }

  @Override
  public Expr translate(List<Sexp> kids) {
    if (kids.size() == 2) {
      if (this.name.equals("not")) {
        return new NotExp(kids.get(1).translate());
      }
    }
    else if (kids.size() == 3) {
      if (this.name.equals("and")) {
        return new AndExp(kids.get(1).translate(), kids.get(2).translate());
      }
      else if (this.name.equals("or")) {
        return new OrExp(kids.get(1).translate(), kids.get(2).translate());
      }
    }
    else if (kids.size() == 4) {
      if (this.name.equals("let") && kids.get(1) instanceof Symbol) {
        return new LetExp(((Symbol) kids.get(1)).name, kids.get(2).translate(),
                kids.get(3).translate());
      }
    }
    throw new IllegalStateException("No such Expr");
  }
}
