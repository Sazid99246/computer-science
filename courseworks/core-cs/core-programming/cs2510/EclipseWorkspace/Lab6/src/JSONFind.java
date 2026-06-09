// Predicate checking if a JSON value is NOT blank
class IsNotBlank implements IPred<JSON> {
  public Boolean apply(JSON input) {
    return !(input instanceof JSONBlank);
  }
}

// Evaluates a single structural key pair for a target keyword match
class PairFinder implements IFunc<Pair<String, JSON>, JSON> {
  String targetKey;

  PairFinder(String targetKey) {
    this.targetKey = targetKey;
  }

  public JSON apply(Pair<String, JSON> pair) {
    if (pair.x.equals(targetKey)) {
      return pair.y;
    }
    return new JSONBlank();
  }
}

// Visitor that scans JSONObjects for a designated key string
class JSONFind implements IJSONVisitor<JSON> {
  String targetKey;

  JSONFind(String targetKey) {
    this.targetKey = targetKey;
  }

  public JSON visitObject(JSONObject o) {
    return o.pairs.findSolutionOrElse(new PairFinder(this.targetKey), new IsNotBlank(),
        new JSONBlank());
  }

  // Base fallback variants
  public JSON visitBlank(JSONBlank b) {
    return new JSONBlank();
  }

  public JSON visitNumber(JSONNumber n) {
    return new JSONBlank();
  }

  public JSON visitBool(JSONBool b) {
    return new JSONBlank();
  }

  public JSON visitString(JSONString s) {
    return new JSONBlank();
  }

  public JSON visitList(JSONList l) {
    return new JSONBlank();
  }
}