// Standard integer summation builder for lists of numbers
class SumBuilder implements IFunc2<Integer, Integer> {
  public Integer apply(Integer first, Integer accum) {
    return first + accum;
  }
}

// Maps a JSON element to an Integer using the JSONToNumber visitor
class JSONToNumberFunc implements IFunc<JSON, Integer> {
  public Integer apply(JSON input) {
    return input.accept(new JSONToNumber());
  }
}

// Maps a Pair<String, JSON> to its sub-component's numeric value
class PairValueToNumberFunc implements IFunc<Pair<String, JSON>, Integer> {
  public Integer apply(Pair<String, JSON> pair) {
    return pair.y.accept(new JSONToNumber());
  }
}

// Visitor that converts any JSON element to its matching numeric value rules
class JSONToNumber implements IJSONVisitor<Integer> {
  public Integer visitBlank(JSONBlank b) {
    return 0;
  }

  public Integer visitNumber(JSONNumber n) {
    return n.number;
  }

  public Integer visitBool(JSONBool b) {
    return b.bool ? 1 : 0;
  }

  public Integer visitString(JSONString s) {
    return s.str.length();
  }

  public Integer visitList(JSONList l) {
    return l.values.map(new JSONToNumberFunc()).foldr(new SumBuilder(), 0);
  }

  public Integer visitObject(JSONObject o) {
    return o.pairs.map(new PairValueToNumberFunc()).foldr(new SumBuilder(), 0);
  }
}