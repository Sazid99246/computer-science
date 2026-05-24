// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // produce a new list sorted in case-insensitive alphabetical order
  ILoString sort();

  // helper for sort: inserts a string into a sorted list in a case-insensitive
  // way
  ILoString insert(String s);

  // determines if this list is sorted lexicographically (case-insensitive)
  boolean isSorted();

  // helper for isSorted: checks if the given string comes before the first
  // element
  boolean isSortedHelp(String given);

  // interleaves this list with the given list alternatingly
  ILoString interleave(ILoString that);

  // helper for interleave: called on 'that' list to continue alternating
  ILoString interleaveHelp(String standardFirst, ILoString standardRest);

  // merges two sorted lists into a single sorted list (keeps duplicates)
  ILoString merge(ILoString that);

  // helper for merge: compares elements when both lists are non-empty
  ILoString mergeHelp(String standardFirst, ILoString standardRest);

  // produces a new list with elements in reverse order
  ILoString reverse();

  // helper for reverse: uses an accumulator to reverse elements cleanly
  ILoString reverseAcc(ILoString acc);

  // determines if the list consists entirely of pairs of identical strings
  boolean isDoubledList();

  // helper for isDoubledList: verifies if the next string matches the tracked one
  boolean isDoubledListHelp(String expected);

  // determines if the list reads the same forward and backward
  boolean isPalindromeList();

  // helper to check if a list is empty cleanly
  boolean isMtLoString();

  // determines if this list is identical to the given list element-by-element
  boolean sameList(ILoString that);

  // helper for sameList: compares heads when both lists are non-empty
  boolean sameListHelp(String standardFirst, ILoString standardRest);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // sorting an empty list results in an empty list
  public ILoString sort() {
    return this;
  }

  // inserting into an empty list yields a single-element list
  public ILoString insert(String s) {
    return new ConsLoString(s, this);
  }

  // an empty list is vacuously sorted
  public boolean isSorted() {
    return true;
  }

  // anything coming before an empty list is fine; it's still sorted
  public boolean isSortedHelp(String given) {
    return true;
  }

  // interleaving an empty list with 'that' list simply returns 'that' list
  public ILoString interleave(ILoString that) {
    return that;
  }

  // helper for interleave: since 'this' is empty, append the deferred item to
  // standardRest
  public ILoString interleaveHelp(String standardFirst, ILoString standardRest) {
    return new ConsLoString(standardFirst, standardRest);
  }

  // merging an empty list with 'that' list simply returns 'that' list
  public ILoString merge(ILoString that) {
    return that;
  }

  // helper for merge: since 'this' is empty, reconstruct the remaining sorted
  // list
  public ILoString mergeHelp(String standardFirst, ILoString standardRest) {
    return new ConsLoString(standardFirst, standardRest);
  }

  // reversing an empty list results in an empty list
  public ILoString reverse() {
    return this;
  }

  // the accumulated reversed list is complete
  public ILoString reverseAcc(ILoString acc) {
    return acc;
  }

  // an empty list contains balanced pairs vacuously
  public boolean isDoubledList() {
    return true;
  }

  // if looking for a matching pair item and encountering empty, it's uneven
  // (false)
  public boolean isDoubledListHelp(String expected) {
    return false;
  }

  // an empty list reads the same forwards and backwards
  public boolean isPalindromeList() {
    return true;
  }

  // an empty list is only identical to another empty list
  public boolean sameList(ILoString that) {
    return that.isMtLoString();
  }

  // helper to safely identify an empty list without using instanceof
  public boolean isMtLoString() {
    return true;
  }

  public boolean sameListHelp(String standardFirst, ILoString standardRest) {
    // If we are here, 'this' is empty but the caller was a ConsLoString.
    return false;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // standard insertion sort
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  // inserts an item case-insensitively into this already sorted list
  public ILoString insert(String s) {
    if (s.toLowerCase().compareTo(this.first.toLowerCase()) <= 0) {
      return new ConsLoString(s, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
  }

  // determines if this list is sorted
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first);
  }

  // returns true if the previous item is lexicographically <= this list's first
  // item
  public boolean isSortedHelp(String given) {
    return given.toLowerCase().compareTo(this.first.toLowerCase()) <= 0
        && this.rest.isSortedHelp(this.first);
  }

  // alternate elements between this list and that list
  public ILoString interleave(ILoString that) {
    return that.interleaveHelp(this.first, this.rest);
  }

  // dynamic dispatch helper to keep order shifting cleanly
  public ILoString interleaveHelp(String standardFirst, ILoString standardRest) {
    return new ConsLoString(standardFirst, this.interleave(standardRest));
  }

  // standard merge of two sorted lists
  public ILoString merge(ILoString that) {
    return that.mergeHelp(this.first, this.rest);
  }

  // dynamically compares heads without breaking encapsulation rules
  public ILoString mergeHelp(String standardFirst, ILoString standardRest) {
    if (standardFirst.toLowerCase().compareTo(this.first.toLowerCase()) <= 0) {
      return new ConsLoString(standardFirst, this.merge(standardRest));
    }
    else {
      return new ConsLoString(this.first,
          this.rest.merge(new ConsLoString(standardFirst, standardRest)));
    }
  }

  // interface entry point for reverse
  public ILoString reverse() {
    return this.reverseAcc(new MtLoString());
  }

  // passes down the head item to accumulate the stack in inverted order
  public ILoString reverseAcc(ILoString acc) {
    return this.rest.reverseAcc(new ConsLoString(this.first, acc));
  }

  // interface entry point for checking doubled status
  public boolean isDoubledList() {
    return this.rest.isDoubledListHelp(this.first);
  }

  // checks if this string matches the expected tracker, then recurses to next
  // structural pair
  public boolean isDoubledListHelp(String expected) {
    return this.first.equals(expected) && this.rest.isDoubledList();
  }

  // a list is a palindrome list if it matches its own reversed version
  // item-for-item
  public boolean isPalindromeList() {
    return this.sameList(this.reverse());
  }

  // helper to safely identify an empty list without using instanceof
  public boolean isMtLoString() {
    return false;
  }

  // standard structural equivalence manager for two lists
  public boolean sameList(ILoString that) {
    return that.sameListHelp(this.first, this.rest);
  }

  // executes head-to-head equality checking safely
  public boolean sameListHelp(String standardFirst, ILoString standardRest) {
    return this.first.equals(standardFirst) && this.rest.sameList(standardRest);
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mt = new MtLoString();

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  // sample lists for sort / isSorted tests
  ILoString unsorted = new ConsLoString("banana",
      new ConsLoString("Apple", new ConsLoString("cherry", mt)));
  ILoString sortedCase = new ConsLoString("Apple",
      new ConsLoString("banana", new ConsLoString("cherry", mt)));
  ILoString sortedLower = new ConsLoString("apple",
      new ConsLoString("banana", new ConsLoString("cherry", mt)));

  // sample lists for interleave / merge tests
  ILoString list1 = new ConsLoString("A", new ConsLoString("C", mt));
  ILoString list2 = new ConsLoString("B", new ConsLoString("D", new ConsLoString("E", mt)));

  ILoString sortedM1 = new ConsLoString("apple", new ConsLoString("cherry", mt));
  ILoString sortedM2 = new ConsLoString("banana", new ConsLoString("dates", mt));

  // sample lists for doubled/palindrome tests
  ILoString doubledValid = new ConsLoString("apple",
      new ConsLoString("apple", new ConsLoString("orange", new ConsLoString("orange", mt))));
  ILoString doubledInvalid = new ConsLoString("apple",
      new ConsLoString("orange", new ConsLoString("orange", mt)));

  ILoString palindromeValid = new ConsLoString("racecar",
      new ConsLoString("radar", new ConsLoString("racecar", mt)));
  ILoString palindromeInvalid = new ConsLoString("racecar", new ConsLoString("radar", mt));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test sort method
  boolean testSort(Tester t) {
    return t.checkExpect(this.mt.sort(), this.mt)
        && t.checkExpect(this.unsorted.sort(), this.sortedCase);
  }

  // test isSorted method
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.mt.isSorted(), true)
        && t.checkExpect(this.sortedCase.isSorted(), true)
        && t.checkExpect(this.sortedLower.isSorted(), true)
        && t.checkExpect(this.unsorted.isSorted(), false);
  }

  // test interleave method
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mt.interleave(this.list1), this.list1)
        && t.checkExpect(this.list1.interleave(this.mt), this.list1)
        && t.checkExpect(this.list1.interleave(this.list2),
            new ConsLoString("A", new ConsLoString("B",
                new ConsLoString("C", new ConsLoString("D", new ConsLoString("E", mt))))));
  }

  // test merge method
  boolean testMerge(Tester t) {
    return t.checkExpect(this.mt.merge(this.sortedM1), this.sortedM1)
        && t.checkExpect(this.sortedM1.merge(this.mt), this.sortedM1)
        && t.checkExpect(this.sortedM1.merge(this.sortedM2), new ConsLoString("apple",
            new ConsLoString("banana", new ConsLoString("cherry", new ConsLoString("dates", mt)))));
  }

  // test reverse method
  boolean testReverse(Tester t) {
    return t.checkExpect(this.mt.reverse(), this.mt) && t.checkExpect(this.sortedM1.reverse(),
        new ConsLoString("cherry", new ConsLoString("apple", mt)));
  }

  // test isDoubledList method
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.mt.isDoubledList(), true)
        && t.checkExpect(this.doubledValid.isDoubledList(), true)
        && t.checkExpect(this.doubledInvalid.isDoubledList(), false);
  }

  // test isPalindromeList method
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(this.mt.isPalindromeList(), true)
        && t.checkExpect(this.palindromeValid.isPalindromeList(), true)
        && t.checkExpect(this.palindromeInvalid.isPalindromeList(), false);
  }
}