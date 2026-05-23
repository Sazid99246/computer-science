import tester.*;

// Represents a functional list of integers
interface ILoInt {
  // Variant A
  boolean satisfiesA();
  boolean satisfiesAHelp(boolean metEven, boolean metPosOdd, boolean metBetween);

  // Variant B
  boolean satisfiesB();
  boolean satisfiesBHelp(boolean needEven, boolean needPosOdd, boolean needBetween);

  // Variant C
  boolean satisfiesC();
  boolean satisfiesCHelp(boolean needEven, boolean needPosOdd, boolean needBetween);
  
  // Helpers
  int length();
}

class ConsLoInt implements ILoInt {
  int first;
  ILoInt rest;

  ConsLoInt(int first, ILoInt rest) {
    this.first = first;
    this.rest = rest;
  }

  public int length() { 
    return 1 + this.rest.length(); 
  }

  // --- Variant A ---
  public boolean satisfiesA() {
    return this.satisfiesAHelp(false, false, false);
  }

  public boolean satisfiesAHelp(boolean metEven, boolean metPosOdd, boolean metBetween) {
    boolean newEven = metEven || (this.first % 2 == 0);
    boolean newPosOdd = metPosOdd || (this.first > 0 && this.first % 2 != 0);
    boolean newBetween = metBetween || (this.first >= 5 && this.first <= 10);

    if (newEven && newPosOdd && newBetween) {
      return true;
    }
    return this.rest.satisfiesAHelp(newEven, newPosOdd, newBetween);
  }

  // --- Variant B ---
  public boolean satisfiesB() {
    return this.satisfiesBHelp(true, true, true);
  }

  public boolean satisfiesBHelp(boolean needEven, boolean needPosOdd, boolean needBetween) {
    if (!needEven && !needPosOdd && !needBetween) {
      return true;
    }

    boolean matchesEven = (this.first % 2 == 0);
    boolean matchesPosOdd = (this.first > 0 && this.first % 2 != 0);
    boolean matchesBetween = (this.first >= 5 && this.first <= 10);

    boolean tryEven = needEven && matchesEven && this.rest.satisfiesBHelp(false, needPosOdd, needBetween);
    boolean tryPosOdd = needPosOdd && matchesPosOdd && this.rest.satisfiesBHelp(needEven, false, needBetween);
    boolean tryBetween = needBetween && matchesBetween && this.rest.satisfiesBHelp(needEven, needPosOdd, false);
    boolean trySkip = this.rest.satisfiesBHelp(needEven, needPosOdd, needBetween);

    return tryEven || tryPosOdd || tryBetween || trySkip;
  }

  // --- Variant C ---
  public boolean satisfiesC() {
    if (this.length() != 3) {
      return false;
    }
    return this.satisfiesCHelp(true, true, true);
  }

  public boolean satisfiesCHelp(boolean needEven, boolean needPosOdd, boolean needBetween) {
    if (!needEven && !needPosOdd && !needBetween) {
      return false; 
    }

    boolean matchesEven = (this.first % 2 == 0);
    boolean matchesPosOdd = (this.first > 0 && this.first % 2 != 0);
    boolean matchesBetween = (this.first >= 5 && this.first <= 10);

    boolean tryEven = needEven && matchesEven && this.rest.satisfiesCHelp(false, needPosOdd, needBetween);
    boolean tryPosOdd = needPosOdd && matchesPosOdd && this.rest.satisfiesCHelp(needEven, false, needBetween);
    boolean tryBetween = needBetween && matchesBetween && this.rest.satisfiesCHelp(needEven, needPosOdd, false);

    return tryEven || tryPosOdd || tryBetween; // Skipping Banned here
  }
}

class MtLoInt implements ILoInt {
  MtLoInt() {}

  public int length() { return 0; }

  public boolean satisfiesA() { return false; }
  public boolean satisfiesAHelp(boolean metEven, boolean metPosOdd, boolean metBetween) {
    return metEven && metPosOdd && metBetween;
  }

  public boolean satisfiesB() { return false; }
  public boolean satisfiesBHelp(boolean needEven, boolean needPosOdd, boolean needBetween) {
    return !needEven && !needPosOdd && !needBetween;
  }

  public boolean satisfiesC() { return false; }
  public boolean satisfiesCHelp(boolean needEven, boolean needPosOdd, boolean needBetween) {
    return !needEven && !needPosOdd && !needBetween;
  }
}


// =============================================================================
// TEST SUITE
// =============================================================================

class ExamplesComprehensive {
  ExamplesComprehensive() {}

  ILoInt listAOk = new ConsLoInt(6, new ConsLoInt(5, new MtLoInt())); // (6, 5)
  ILoInt listAFail = new ConsLoInt(4, new ConsLoInt(3, new MtLoInt())); // (4, 3)
  
  ILoInt listBOk = new ConsLoInt(6, new ConsLoInt(5, new ConsLoInt(6, new MtLoInt()))); // (6, 5, 6)
  ILoInt listBWithExtraneous = new ConsLoInt(6, new ConsLoInt(5, new ConsLoInt(42, new ConsLoInt(6, new MtLoInt())))); // (6, 5, 42, 6)

  boolean testProblem2VariantA(Tester t) {
    return t.checkExpect(this.listAOk.satisfiesA(), true)
        && t.checkExpect(this.listAFail.satisfiesA(), false);
  }

  boolean testProblem2VariantB(Tester t) {
    return t.checkExpect(this.listAOk.satisfiesB(), false)
        && t.checkExpect(this.listBOk.satisfiesB(), true)
        && t.checkExpect(this.listBWithExtraneous.satisfiesB(), true);
  }

  boolean testProblem2VariantC(Tester t) {
    return t.checkExpect(this.listBOk.satisfiesC(), true)
        && t.checkExpect(this.listBWithExtraneous.satisfiesC(), false);
  }
}