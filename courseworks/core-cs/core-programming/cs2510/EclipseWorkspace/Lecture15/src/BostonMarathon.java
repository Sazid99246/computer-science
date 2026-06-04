import tester.*;

// --- FUNCTION OBJECTS FOR FILTERING (Lecture 13) ---

/**
 * * To represent a boolean-valued question about a runner. Used for filtering
 * lists of runners.
 */
interface IRunnerPredicate {
  // Does the given runner satisfy the condition?
  boolean apply(Runner r);
}

/** Returns true if the runner is male. */
class RunnerIsMale implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return r.isMale;
  }
}

/** Returns true if the runner is female. */
class RunnerIsFemale implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return !r.isMale;
  }
}

// --- FUNCTION OBJECTS FOR SORTING & COMPARISON (Lecture 14) ---

/**
 * To compute a three-way comparison between two Runners. This is an abstraction
 * over "ordering" logic.
 */
interface IRunnerComparator {
  /**
   * Returns: - a negative number if r1 comes before r2 - zero if r1 and r2 are
   * considered "tied" - a positive number if r1 comes after r2
   */
  int compare(Runner r1, Runner r2);
}

/** Compares runners by race time (shorter time is "less than" / better). */
class CompareByTime implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    // Returns negative if r1.time < r2.time
    return r1.time - r2.time;
  }
}

/**
 * Compares runners by name alphabetically using String's built-in comparison.
 */
class CompareByName implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    return r1.name.compareTo(r2.name);
  }
}

/**
 * * A decorator that flips the result of another comparator. If the base
 * comparator says r1 < r2, this returns r1 > r2.
 */
class ReverseComparator implements IRunnerComparator {
  IRunnerComparator base;

  ReverseComparator(IRunnerComparator base) {
    this.base = base;
  }

  public int compare(Runner r1, Runner r2) {
    // Simply multiply by -1 to flip the ordering logic
    return -1 * this.base.compare(r1, r2);
  }
}

// --- DATA DEFINITIONS ---

/** To represent a runner in the Boston Marathon with their stats. */
class Runner {
  String name;
  int age;
  int bib;
  boolean isMale;
  int pos;
  int time;

  Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
    this.name = name;
    this.age = age;
    this.bib = bib;
    this.isMale = isMale;
    this.pos = pos;
    this.time = time;
  }
}

// --- LIST INTERFACE AND CLASSES ---

/**
 * Represents a list of Runners and operations that can be performed on them.
 */
interface ILoRunner {
  /** Produces a list containing only runners that satisfy the given predicate. */
  ILoRunner find(IRunnerPredicate pred);

  /** Produces a new list sorted according to the given comparison logic. */
  ILoRunner sortBy(IRunnerComparator comp);

  /**
   * Inserts a runner into a list that is already sorted by the given comparator.
   */
  ILoRunner insertBy(IRunnerComparator comp, Runner r);

  /** Convenience method to find the runner with the fastest time. */
  Runner findWinner();

  /** Finds the "minimum" runner in the list based on the provided comparator. */
  Runner findMin(IRunnerComparator comp);

  /** Finds the "maximum" runner in the list based on the provided comparator. */
  Runner findMax(IRunnerComparator comp);

  /**
   * Helper for findMin: uses an accumulator to track the best runner seen so far.
   */
  Runner findMinHelp(IRunnerComparator comp, Runner acc);

  /** Returns the first runner in the list; throws exception if empty. */
  Runner getFirst();
}

/** Represents an empty list of runners. */
class MtLoRunner implements ILoRunner {
  // Empty lists have no runners to find
  public ILoRunner find(IRunnerPredicate pred) {
    return this;
  }

  // Sorting an empty list results in an empty list
  public ILoRunner sortBy(IRunnerComparator comp) {
    return this;
  }

  // Inserting into an empty list creates a single-element list
  public ILoRunner insertBy(IRunnerComparator comp, Runner r) {
    return new ConsLoRunner(r, this);
  }

  // Operation fails as there is no data
  public Runner getFirst() {
    throw new RuntimeException("No first of an empty list");
  }

  // Operation fails as there is no data
  public Runner findWinner() {
    throw new RuntimeException("No winner of an empty list");
  }

  // Operation fails as there is no data
  public Runner findMin(IRunnerComparator comp) {
    throw new RuntimeException("No minimum runner in an empty list");
  }

  public Runner findMax(IRunnerComparator comp) {
    throw new RuntimeException("No maximum runner in an empty list");
  }

  // When we reach the end of the list, the accumulator is the final answer
  public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
    return acc;
  }
}

/** Represents a non-empty list of runners. */
class ConsLoRunner implements ILoRunner {
  Runner first;
  ILoRunner rest;

  ConsLoRunner(Runner first, ILoRunner rest) {
    this.first = first;
    this.rest = rest;
  }

  /** Recursively builds a list of runners that match the predicate. */
  public ILoRunner find(IRunnerPredicate pred) {
    if (pred.apply(this.first)) {
      return new ConsLoRunner(this.first, this.rest.find(pred));
    }
    else {
      return this.rest.find(pred);
    }
  }

  /**
   * Performs insertion sort by sorting the rest and then inserting the first
   * element.
   */
  public ILoRunner sortBy(IRunnerComparator comp) {
    return this.rest.sortBy(comp).insertBy(comp, this.first);
  }

  /**
   * * Recursively finds the correct position for Runner r in a sorted list. Uses
   * comp.compare to determine if 'r' belongs before 'this.first'.
   */
  public ILoRunner insertBy(IRunnerComparator comp, Runner r) {
    if (comp.compare(r, this.first) < 0) {
      return new ConsLoRunner(r, this);
    }
    else {
      return new ConsLoRunner(this.first, this.rest.insertBy(comp, r));
    }
  }

  /** Simply returns the head of the list. */
  public Runner getFirst() {
    return this.first;
  }

  /** Finds the runner with the lowest time using findMin logic. */
  public Runner findWinner() {
    return this.findMin(new CompareByTime());
  }

  /**
   * * Initiates a search for the minimum element using the head as the first
   * "best" candidate.
   */
  public Runner findMin(IRunnerComparator comp) {
    return this.rest.findMinHelp(comp, this.first);
  }

  /**
   * Uses findMin combined with a ReverseComparator to find the maximum element.
   */
  public Runner findMax(IRunnerComparator comp) {
    return this.findMin(new ReverseComparator(comp));
  }

  /**
   * * Accumulator method: compares the current head (this.first) with the current
   * minimum (acc). Passes the better of the two to the rest of the list.
   */
  public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
    if (comp.compare(this.first, acc) < 0) {
      // Current element is better than the accumulator; update accumulator
      return this.rest.findMinHelp(comp, this.first);
    }
    else {
      // Accumulator is still better; keep it
      return this.rest.findMinHelp(comp, acc);
    }
  }
}

// --- EXAMPLES AND TESTS ---

/** Contains sample data and test cases to verify the logic. */
class ExamplesMarathon {
  Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
  Runner frank = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

  ILoRunner mt = new MtLoRunner();
  ILoRunner list = new ConsLoRunner(johnny,
      new ConsLoRunner(joan, new ConsLoRunner(frank, new ConsLoRunner(bill, mt))));

  /** Verifies that sortBy properly orders runners by time. */
  boolean testSortByTime(Tester t) {
    return t.checkExpect(this.list.sortBy(new CompareByTime()), new ConsLoRunner(bill,
        new ConsLoRunner(frank, new ConsLoRunner(joan, new ConsLoRunner(johnny, mt)))));
  }

  /**
   * Verifies that findWinner identifies the fastest runner without sorting the
   * whole list.
   */
  boolean testFindWinner(Tester t) {
    return t.checkExpect(this.list.findWinner(), bill);
  }

  /** Local comparator class for testing purposes. */
  class CompareByAge implements IRunnerComparator {
    public int compare(Runner r1, Runner r2) {
      return r1.age - r2.age;
    }
  }

  /** Verifies that findMax can find the oldest runner. */
  boolean testFindOldest(Tester t) {
    return t.checkExpect(this.list.findMax(new CompareByAge()), johnny);
  }

  // Benoit (B) -> Kelly (K) -> Rogers (R) -> Shorter (S)
  boolean testSortByName(Tester t) {
    return t.checkExpect(this.list.sortBy(new CompareByName()), new ConsLoRunner(joan,
        new ConsLoRunner(johnny, new ConsLoRunner(bill, new ConsLoRunner(frank, mt)))));
  }

//1. Test Empty List Behavior
  boolean testEmptyList(Tester t) {
    return t.checkExpect(this.mt.find(new RunnerIsMale()), this.mt)
        && t.checkExpect(this.mt.sortBy(new CompareByTime()), this.mt) &&
        // Testing that findMin throws a RuntimeException on empty list
        t.checkException(new RuntimeException("No minimum runner in an empty list"), this.mt,
            "findMin", new CompareByTime());
  }

  // 2. Test Single-Element List
  ILoRunner singleList = new ConsLoRunner(frank, mt);

  boolean testSingleElement(Tester t) {
    return t.checkExpect(this.singleList.sortBy(new CompareByTime()), this.singleList)
        && t.checkExpect(this.singleList.findWinner(), frank);
  }

  // 3. Test Ties (Two runners with the same time)
  Runner billTwin = new Runner("Rogers Twin", 36, 778, true, 120, 129); // Same time as Bill
  ILoRunner tieList = new ConsLoRunner(bill, new ConsLoRunner(billTwin, mt));

  boolean testTies(Tester t) {
    return t.checkExpect(this.tieList.sortBy(new CompareByTime()),
        new ConsLoRunner(billTwin, new ConsLoRunner(bill, mt)));
  }

  // 4. Test Reverse Sorting (Oldest to Youngest)
  boolean testReverseSort(Tester t) {
    // Using ReverseComparator with age to get Oldest first
    IRunnerComparator oldestFirst = new ReverseComparator(new CompareByAge());
    return t.checkExpect(this.list.sortBy(oldestFirst).getFirst(), johnny);
  }

  // 5. Test findMax by Time (The "Loser" / Slowest runner)
  boolean testFindSlowest(Tester t) {
    // The "max" of time is the largest number (360 mins)
    return t.checkExpect(this.list.findMax(new CompareByTime()), johnny);
  }
}