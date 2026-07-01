package cs3500.lec11;

/**
 * A string accumulator implementation that just stores the string. This is
 * very simple to implement, but may suffer poor performance.
 */
public final class StringStringAccumulator implements StringAccumulator {
  private String contents = "";

  @Override
  public String stringValue() {
    return contents;
  }

  @Override
  public String toString() {
    return contents;
  }

  @Override
  public StringStringAccumulator append(char c) {
    contents += c;
    return this;
  }

  @Override
  public StringStringAccumulator append(CharSequence csq) {
    contents += csq;
    return this;
  }

  @Override
  public int length() {
    return contents.length();
  }
}
