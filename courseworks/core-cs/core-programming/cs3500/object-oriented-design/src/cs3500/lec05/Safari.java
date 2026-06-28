package cs3500.lec05;

public class Safari {
  public static final class OldWidget {
    public OldWidget() {
      widgetId = widgetIdCounter++;
    }

    public int getWidgetId() {
      return widgetId;
    }

    private final int widgetId;

    public static void resetWidgetIdCounter() {
      widgetIdCounter = 0;
    }

    private static int widgetIdCounter = 0;
  }

  public static final class Widget {
    private Widget(int widgetId0) {
      widgetId = widgetId0;
    }

    public int getWidgetId() {
      return widgetId;
    }

    private final int widgetId;

    public static final class Factory {
      private int widgetIdCounter = 0;

      public Widget build() {
        return new Widget(widgetIdCounter++);
      }
    }
  }

  /*
   * Rationals with different equalities
   */

  public abstract static class AbstractRational {
    public AbstractRational(int numerator, int denominator) {
      if (denominator == 0) {
        throw new IllegalArgumentException("denominator cannot be 0");
      }

      num = numerator;
      den = denominator;
    }

    protected final int num;
    protected final int den;

    public double toDouble() {
      return num / (double) den;
    }

    protected abstract boolean equals(AbstractRational that);

    public boolean equals(Object that) {
      return this.getClass() == that.getClass()
              && this.equals((AbstractRational) that);
    }
  }

  public static class IntensionalRational extends AbstractRational {
    public IntensionalRational(int n, int d) {
      super(n, d);
    }

    @Override
    protected boolean equals(AbstractRational that) {
      return this == that;
    }
  }

  public static class ExtensionalRational extends AbstractRational {
    public ExtensionalRational(int n, int d) {
      super(n, d);
    }

    @Override
    protected boolean equals(AbstractRational that) {
      return this.num * that.den == this.den * that.num;
    }
  }

  public static class StructuralRational extends AbstractRational {
    public StructuralRational(int n, int d) {
      super(n, d);
    }

    @Override
    protected boolean equals(AbstractRational that) {
      return this.num == that.num && this.den == that.den;
    }
  }
}
