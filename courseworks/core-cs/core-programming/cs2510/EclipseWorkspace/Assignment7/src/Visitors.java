import java.util.function.Function;
import java.util.function.BiFunction;
import tester.*;

// ============================================================================
// DATA DEFINITIONS FOR ARITHMETIC EXPRESSIONS
// ============================================================================

// Represents an arithmetic expression tree
interface IArith {
  <R> R accept(IArithVisitor<R> visitor);
}

// Represents a constant numeric value
class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

// Represents a unary operation applied to one child expression
class UnaryFormula implements IArith {
  Function<Double, Double> func;
  String name;
  IArith child;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.func = func;
    this.name = name;
    this.child = child;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnary(this);
  }
}

// Represents a binary operation applied to two child expressions
class BinaryFormula implements IArith {
  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;

  BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinary(this);
  }
}

// ============================================================================
// VISITOR DESIGN PATTERN INTERFACES AND IMPLEMENTATIONS
// ============================================================================

// Generic visitor interface that also acts as a Function object
interface IArithVisitor<R> extends Function<IArith, R> {
  R visitConst(Const c);

  R visitUnary(UnaryFormula uf);

  R visitBinary(BinaryFormula bf);
}

// Abstract base class to implement the function application shortcut
abstract class ARithVisitor<R> implements IArithVisitor<R> {
  public R apply(IArith obj) {
    return obj.accept(this);
  }
}

// 1. EvalVisitor: Evaluates the expression tree to a Double
class EvalVisitor extends ARithVisitor<Double> {
  public Double visitConst(Const c) {
    return c.num;
  }

  public Double visitUnary(UnaryFormula uf) {
    return uf.func.apply(uf.child.accept(this));
  }

  public Double visitBinary(BinaryFormula bf) {
    return bf.func.apply(bf.left.accept(this), bf.right.accept(this));
  }
}

// 2. PrintVisitor: Outputs fully-parenthesized Racket prefix notation
class PrintVisitor extends ARithVisitor<String> {
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  public String visitUnary(UnaryFormula uf) {
    return "(" + uf.name + " " + uf.child.accept(this) + ")";
  }

  public String visitBinary(BinaryFormula bf) {
    return "(" + bf.name + " " + bf.left.accept(this) + " " + bf.right.accept(this) + ")";
  }
}

// 3. DoublerVisitor: Clones the tree with all constants doubled
class DoublerVisitor extends ARithVisitor<IArith> {
  public IArith visitConst(Const c) {
    return new Const(c.num * 2.0);
  }

  public IArith visitUnary(UnaryFormula uf) {
    return new UnaryFormula(uf.func, uf.name, uf.child.accept(this));
  }

  public IArith visitBinary(BinaryFormula bf) {
    return new BinaryFormula(bf.func, bf.name, bf.left.accept(this), bf.right.accept(this));
  }
}

// 4. NoNegativeResults: Verifies a negative number is never encountered during evaluation
class NoNegativeResults extends ARithVisitor<Boolean> {
  public Boolean visitConst(Const c) {
    return c.num >= 0;
  }

  public Boolean visitUnary(UnaryFormula uf) {
    Double val = uf.child.accept(new EvalVisitor());
    return uf.child.accept(this) && (uf.func.apply(val) >= 0);
  }

  public Boolean visitBinary(BinaryFormula bf) {
    Double leftVal = bf.left.accept(new EvalVisitor());
    Double rightVal = bf.right.accept(new EvalVisitor());
    return bf.left.accept(this) && bf.right.accept(this) && (bf.func.apply(leftVal, rightVal) >= 0);
  }
}

// ============================================================================
// EXAMPLES AND TEST SUITE FOR VISITORS
// ============================================================================

class ExamplesVisitors {
  // Standard Operations
  BiFunction<Double, Double, Double> plus = (a, b) -> a + b;
  BiFunction<Double, Double, Double> minus = (a, b) -> a - b;
  BiFunction<Double, Double, Double> mul = (a, b) -> a * b;
  BiFunction<Double, Double, Double> div = (a, b) -> a / b;
  Function<Double, Double> neg = x -> -x;
  Function<Double, Double> sqr = x -> x * x;

  IArith c1 = new Const(1.0);
  IArith c2 = new Const(2.0);
  IArith c1_5 = new Const(1.5);

  // (div (plus 1.0 2.0) (neg 1.5))
  IArith expr = new BinaryFormula(div, "div", new BinaryFormula(plus, "plus", c1, c2),
      new UnaryFormula(neg, "neg", c1_5));

  public void testEval(Tester t) {
    t.checkExpect(new EvalVisitor().apply(expr), -2.0);
    t.checkExpect(new EvalVisitor().apply(new UnaryFormula(sqr, "sqr", c2)), 4.0);
  }

  public void testPrint(Tester t) {
    t.checkExpect(new PrintVisitor().apply(expr), "(div (plus 1.0 2.0) (neg 1.5))");
  }

  public void testDoubler(Tester t) {
    IArith doubled = new DoublerVisitor().apply(expr);
    t.checkExpect(new EvalVisitor().apply(doubled), -2.0);
    t.checkExpect(new PrintVisitor().apply(doubled), "(div (plus 2.0 4.0) (neg 3.0))");
  }

  public void testNoNegativeResults(Tester t) {
    t.checkExpect(new NoNegativeResults().apply(expr), false);
    t.checkExpect(new NoNegativeResults().apply(new BinaryFormula(plus, "plus", c1, c2)), true);
  }
}