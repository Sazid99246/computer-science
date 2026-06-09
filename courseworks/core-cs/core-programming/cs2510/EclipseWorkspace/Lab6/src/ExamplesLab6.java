import tester.Tester;

public class ExamplesLab6 {

  // Test data variables
  IList<Integer> standardNums;
  JSON blankValue;
  JSON numberValue;
  JSON stringValue;
  JSON nestedList;
  JSON complexObject;

  void initData() {
    this.standardNums = new ConsList<>(1, new ConsList<>(2, new ConsList<>(3, new MtList<>())));
    this.blankValue = new JSONBlank();
    this.numberValue = new JSONNumber(42);
    this.stringValue = new JSONString("Hello");

    // List elements inside JSONList: [42, "Hello", Blank] -> total expected weight
    // = 42 + 5 + 0 = 47
    this.nestedList = new JSONList(new ConsList<>(this.numberValue,
        new ConsList<>(this.stringValue, new ConsList<>(this.blankValue, new MtList<>()))));

    // Object elements inside JSONObject: {"target": 42, "ignored": "Hello"}
    this.complexObject = new JSONObject(new ConsList<>(new Pair<>("target", this.numberValue),
        new ConsList<>(new Pair<>("ignored", this.stringValue), new MtList<>())));
  }

  // 1. Test foldr accumulation function
  void testFoldr(Tester t) {
    initData();
    t.checkExpect(this.standardNums.foldr(new SumBuilder(), 0), 6);
  }

  // 2. Test visitor calculation value conversions
  void testJSONToNumberVisitor(Tester t) {
    initData();
    t.checkExpect(this.blankValue.accept(new JSONToNumber()), 0);
    t.checkExpect(this.numberValue.accept(new JSONToNumber()), 42);
    t.checkExpect(this.stringValue.accept(new JSONToNumber()), 5);
    t.checkExpect(this.nestedList.accept(new JSONToNumber()), 47);
    t.checkExpect(this.complexObject.accept(new JSONToNumber()), 47);
  }

  // 3. Test structural map searches
  void testJSONFindVisitor(Tester t) {
    initData();
    t.checkExpect(this.complexObject.accept(new JSONFind("target")), this.numberValue);
    t.checkExpect(this.complexObject.accept(new JSONFind("missingKey")), new JSONBlank());
    t.checkExpect(this.nestedList.accept(new JSONFind("target")), new JSONBlank());
  }
}