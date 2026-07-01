package cs3500.husky.withvars.success;

import java.util.Map;

/**
 * Represents a variable, whose value is determined by some environment.
 */
public class Variable implements Expr {
  String name;

  public Variable(String name) {
    this.name = name;
  }

  @Override
  public boolean evaluate(Map<String, Boolean> env) {
    if (env.containsKey(this.name)) {
      return env.get(this.name);
    }
    throw new IllegalStateException("No such variable: " + this.name);
  }
}
