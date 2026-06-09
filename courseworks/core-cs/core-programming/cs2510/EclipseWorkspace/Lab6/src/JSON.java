// The JSON Visitor Interface
interface IJSONVisitor<R> {
  R visitBlank(JSONBlank b);

  R visitNumber(JSONNumber n);

  R visitBool(JSONBool b);

  R visitString(JSONString s);

  R visitList(JSONList l);

  R visitObject(JSONObject o);
}

// Core JSON interface (Strict Visitor Pattern)
interface JSON {
  <R> R accept(IJSONVisitor<R> visitor);
}

// No value
class JSONBlank implements JSON {
  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitBlank(this);
  }
}

// A number
class JSONNumber implements JSON {
  int number;

  JSONNumber(int number) {
    this.number = number;
  }

  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitNumber(this);
  }
}

// A boolean
class JSONBool implements JSON {
  boolean bool;

  JSONBool(boolean bool) {
    this.bool = bool;
  }

  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitBool(this);
  }
}

// A string
class JSONString implements JSON {
  String str;

  JSONString(String str) {
    this.str = str;
  }

  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitString(this);
  }
}

// A list of JSON values
class JSONList implements JSON {
  IList<JSON> values;

  JSONList(IList<JSON> values) {
    this.values = values;
  }

  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitList(this);
  }
}

// A list of JSON pairs
class JSONObject implements JSON {
  IList<Pair<String, JSON>> pairs;

  JSONObject(IList<Pair<String, JSON>> pairs) {
    this.pairs = pairs;
  }

  public <R> R accept(IJSONVisitor<R> visitor) {
    return visitor.visitObject(this);
  }
}