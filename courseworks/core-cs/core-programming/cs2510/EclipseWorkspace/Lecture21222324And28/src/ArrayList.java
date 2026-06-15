import java.util.ArrayList;
import java.util.Comparator;
import tester.Tester;

interface IFunc<Arg, Ret> {
  Ret apply(Arg arg);
}

interface IFunc2<Arg1, Arg2, Ret> {
  Ret apply(Arg1 arg1, Arg2 arg2);
}

interface IPred<T> {
  boolean apply(T t);
}

class ArrayUtils {

  // EFFECT: Exchanges the values at the given two indices in the given array
  <T> void swap(ArrayList<T> arr, int index1, int index2) {
    arr.set(index2, arr.set(index1, arr.get(index2)));
  }

  <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
    ArrayList<U> result = new ArrayList<U>();
    for (T t : arr) {
      result.add(func.apply(t));
    }
    return result;
  }

  // Fold Left (For-Each)
  <T, U> U foldlLoop(ArrayList<T> arr, IFunc2<U, T, U> combiner, U initial) {
    U acc = initial;
    for (T element : arr) {
      acc = combiner.apply(acc, element);
    }
    return acc;
  }

  // Fold Right (For-Each)
  <T, U> U foldrLoop(ArrayList<T> arr, IFunc2<T, U, U> combiner, U initial) {
    ArrayList<T> reversed = new ArrayList<T>();
    for (T element : arr) {
      reversed.add(0, element);
    }
    U acc = initial;
    for (T element : reversed) {
      acc = combiner.apply(element, acc);
    }
    return acc;
  }

  // Returns the index of the first item passing the predicate at or after the
  // given index, or -1 if no such item was found
  <T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
    if (index >= arr.size()) {
      return -1;
    }
    else if (whichOne.apply(arr.get(index))) {
      return index;
    }
    else {
      return findHelp(arr, whichOne, index + 1);
    }
  }

  // ===========================================================================
  // SORTING & MINIMUM VALUE METHODS (Exercise 22.5 & 22.6)
  // ===========================================================================

  // Finds the index of the minimum string starting from a specific index
  int findMinIdx(ArrayList<String> arr, int startIdx) {
    int minIdx = startIdx;
    for (int i = startIdx + 1; i < arr.size(); i++) {
      if (arr.get(i).compareTo(arr.get(minIdx)) < 0) {
        minIdx = i;
      }
    }
    return minIdx;
  }

  public int findMin(ArrayList<Integer> numbers) {
    if (numbers.isEmpty()) {
      throw new RuntimeException("Cannot find minimum of an empty list");
    }

    int minVal = numbers.get(0);
    int i = 1; // 1. Initialize the index counter outside the loop

    // 2. The while loop handles the boundary condition check
    while (i < numbers.size()) {
      if (numbers.get(i) < minVal) {
        minVal = numbers.get(i);
      }
      i++; // 3. Manually increment the counter at the very end of the block
    }

    return minVal;
  }

  // EFFECT: Sorts the given list of strings alphabetically using selection sort
  void sort(ArrayList<String> arr) {
    for (int idx = 0; idx < arr.size(); idx++) {
      int idxOfMinValue = this.findMinIdx(arr, idx);
      this.swap(arr, idx, idxOfMinValue);
    }
  }

  // ===========================================================================
  // INTERLEAVE & UNSHUFFLE METHODS (Exercise 22.5 Syntax Practice)
  // ===========================================================================

  // Interleaves two ArrayLists of the same size
  <T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2) {
    ArrayList<T> result = new ArrayList<T>();
    for (int i = 0; i < arr1.size(); i++) {
      result.add(arr1.get(i));
      result.add(arr2.get(i));
    }
    return result;
  }

  // Segregates even-indexed elements followed by odd-indexed elements
  <T> ArrayList<T> unshuffle(ArrayList<T> arr) {
    ArrayList<T> result = new ArrayList<T>();
    // First pass: add even positions (0, 2, 4...)
    for (int i = 0; i < arr.size(); i += 2) {
      result.add(arr.get(i));
    }
    // Second pass: add odd positions (1, 3, 5...)
    for (int i = 1; i < arr.size(); i += 2) {
      result.add(arr.get(i));
    }
    return result;
  }

  // ===========================================================================
  // BINARY SEARCH IMPLEMENTATIONS
  // ===========================================================================

  // Binary Search Version 1 (Closed Interval)
  int binarySearch_v1(ArrayList<String> strings, String target) {
    return this.binarySearchHelp_v1(strings, target, 0, strings.size() - 1);
  }

  int binarySearchHelp_v1(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
    if (lowIdx > highIdx) {
      return -1;
    }
    int midIdx = (lowIdx + highIdx) / 2;
    if (target.compareTo(strings.get(midIdx)) == 0) {
      return midIdx;
    }
    else if (target.compareTo(strings.get(midIdx)) > 0) {
      return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx);
    }
    else {
      return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx - 1);
    }
  }

  // Binary Search Version 2 (Semi-Open Interval)
  int binarySearch_v2(ArrayList<String> strings, String target) {
    return this.binarySearchHelp_v2(strings, target, 0, strings.size());
  }

  int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
    if (lowIdx >= highIdx) {
      return -1;
    }
    int midIdx = (lowIdx + highIdx) / 2;
    if (target.compareTo(strings.get(midIdx)) == 0) {
      return midIdx;
    }
    else if (target.compareTo(strings.get(midIdx)) > 0) {
      return this.binarySearchHelp_v2(strings, target, midIdx + 1, highIdx);
    }
    else {
      return this.binarySearchHelp_v2(strings, target, lowIdx, midIdx);
    }
  }

  // Generalized Binary Search over semi-open intervals
  <T> int gen_binarySearch(ArrayList<T> arr, T target, Comparator<T> comp) {
    return this.gen_binarySearchHelp(arr, target, comp, 0, arr.size());
  }

  <T> int gen_binarySearchHelp(ArrayList<T> arr, T target, Comparator<T> comp, int lowIdx,
      int highIdx) {
    if (lowIdx >= highIdx) {
      return -1;
    }
    int midIdx = (lowIdx + highIdx) / 2;
    int cmp = comp.compare(target, arr.get(midIdx));
    if (cmp == 0) {
      return midIdx;
    }
    else if (cmp > 0) {
      return this.gen_binarySearchHelp(arr, target, comp, midIdx + 1, highIdx);
    }
    else {
      return this.gen_binarySearchHelp(arr, target, comp, lowIdx, midIdx);
    }
  }

  <U> ArrayList<U> buildList(int n, IFunc<Integer, U> func) {
    ArrayList<U> result = new ArrayList<U>();
    for (int i = 0; i < n; i = i + 1) {
      result.add(func.apply(i));
    }
    return result;
  }

  // EFFECT: Modifies all the books in the given ArrayList, to capitalize their
  // titles
  void capitalizeTitles_good(ArrayList<Book> books) {
    for (Book b : books) {
      b.capitalizeTitle();
    }
  }

//EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles
  void capitalizeTitles_ok(ArrayList<Book> books) {
    for (int i = 0; i < books.size(); i = i + 1) {
      // get the old book...
      Book oldB = books.get(i);
      // ... construct the new book ...
      Book newB = new Book(oldB.title.toUpperCase(), oldB.author);
      // and set it in place of the old book, at the current index
      books.set(i, newB);
    }
  }

  boolean getsToOne(int n) {
    while (n > 1) {
      if (n % 2 == 0) {
        n = n / 2;
      }
      else {
        n = 3 * n + 1;
      }
    }
    return true;
  }

  // EFFECT: Sorts the given ArrayList according to the given comparator
  <T> void quicksortCopying(ArrayList<T> arr, IComparator<T> comp) {
    // Create a temporary array
    ArrayList<T> temp = new ArrayList<T>();
    // Make sure the temporary array is exactly as big as the given array
    for (int i = 0; i < arr.size(); i = i + 1) {
      temp.add(arr.get(i));
    }
    quicksortCopyingHelp(arr, temp, comp, 0, arr.size());
  }

//EFFECT: sorts the source array according to comp, in the range of indices [loIdx, hiIdx)
  <T> void quicksortCopyingHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
      int loIdx, int hiIdx) {
    // Step 0: check for completion
    if (loIdx >= hiIdx) {
      return; // There are no items to sort
    }
    // Step 1: select pivot
    T pivot = source.get(loIdx);
    // Step 2: partition items to lower or upper portions of the temp list
    int pivotIdx = partitionCopying(source, temp, comp, loIdx, hiIdx, pivot);
    // Step 4: sort both halves of the list
    quicksortCopyingHelp(source, temp, comp, loIdx, pivotIdx);
    quicksortCopyingHelp(source, temp, comp, pivotIdx + 1, hiIdx);
  }

  // Returns the index where the pivot element ultimately ends up in the sorted
  // source
  // EFFECT: Modifies the source and comp lists in the range [loIdx, hiIdx) such
  // that
  // all values to the left of the pivot are less than (or equal to) the pivot
  // and all values to the right of the pivot are greater than it
  <T> int partitionCopying(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp, int loIdx,
      int hiIdx, T pivot) {
    int curLo = loIdx;
    int curHi = hiIdx - 1;
    // Notice we skip the loIdx index, because that's where the pivot was
    for (int i = loIdx + 1; i < hiIdx; i = i + 1) {
      if (comp.compare(source.get(i), pivot) <= 0) { // lower
        temp.set(curLo, source.get(i));
        curLo = curLo + 1; // advance the current lower index
      }
      else { // upper
        temp.set(curHi, source.get(i));
        curHi = curHi - 1; // advance the current upper index
      }
    }
    temp.set(curLo, pivot); // place the pivot in the remaining spot
    // Step 3: copy all items back into the source
    for (int i = loIdx; i < hiIdx; i = i + 1) {
      source.set(i, temp.get(i));
    }
    return curLo;
  }

  // Returns the index where the pivot element ultimately ends up in the sorted
  // source
  // EFFECT: Modifies the source list in the range [loIdx, hiIdx) such that
  // all values to the left of the pivot are less than (or equal to) the pivot
  // and all values to the right of the pivot are greater than it
  <T> int partition(ArrayList<T> source, IComparator<T> comp, int loIdx, int hiIdx, T pivot) {
    int curLo = loIdx;
    int curHi = hiIdx - 1;
    while (curLo < curHi) {
      // Advance curLo until we find a too-big value (or overshoot the end of the
      // list)
      while (curLo < hiIdx && comp.compare(source.get(curLo), pivot) <= 0) {
        curLo = curLo + 1;
      }
      // Advance curHi until we find a too-small value (or undershoot the start of the
      // list)
      while (curHi >= loIdx && comp.compare(source.get(curHi), pivot) > 0) {
        curHi = curHi - 1;
      }
      if (curLo < curHi) {
        swap(source, curLo, curHi);
      }
    }
    swap(source, loIdx, curHi); // place the pivot in the remaining spot
    return curHi;
  }

//EFFECT: Sorts the given ArrayList according to the given comparator
  <T> void quicksort(ArrayList<T> arr, IComparator<T> comp) {
    quicksortHelp(arr, comp, 0, arr.size());
  }

  // EFFECT: sorts the source array according to comp, in the range of indices
  // [loIdx, hiIdx)
  <T> void quicksortHelp(ArrayList<T> source, IComparator<T> comp, int loIdx, int hiIdx) {
    // Step 0: check for completion
    if (loIdx >= hiIdx) {
      return; // There are no items to sort
    }
    // Step 1: select pivot
    T pivot = source.get(loIdx);
    // Step 2: partition items to lower or upper portions of the temp list
    int pivotIdx = partition(source, comp, loIdx, hiIdx, pivot);
    // Step 3: sort both halves of the list
    quicksortHelp(source, comp, loIdx, pivotIdx);
    quicksortHelp(source, comp, pivotIdx + 1, hiIdx);
  }

  // EFFECT: Sorts the provided list according to the given comparator
  <T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
    // Create a temporary array
    ArrayList<T> temp = new ArrayList<T>();
    // Make sure the temporary array is exactly as big as the given array
    for (int i = 0; i < arr.size(); i = i + 1) {
      temp.add(arr.get(i));
    }
    mergesortHelp(arr, temp, comp, 0, arr.size());
  }

// EFFECT: Sorts the provided list in the region [loIdx, hiIdx) according to the given comparator.
// Modifies both lists in the range [loIdx, hiIdx)
  <T> void mergesortHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp, int loIdx,
      int hiIdx) {
// Step 0: stop when finished
    if (hiIdx - loIdx <= 1) {
      return; // nothing to sort
    }
// Step 1: find the middle index
    int midIdx = (loIdx + hiIdx) / 2;
// Step 2: recursively sort both halves
    mergesortHelp(source, temp, comp, loIdx, midIdx);
    mergesortHelp(source, temp, comp, midIdx, hiIdx);
// Step 3: merge the two sorted halves
    merge(source, temp, comp, loIdx, midIdx, hiIdx);
  }

//Merges the two sorted regions [loIdx, midIdx) and [midIdx, hiIdx) from source
//into a single sorted region according to the given comparator
//EFFECT: modifies the region [loIdx, hiIdx) in both source and temp
<T> void merge(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
              int loIdx, int midIdx, int hiIdx) {
 int curLo = loIdx;   // where to start looking in the lower half-list
 int curHi = midIdx;  // where to start looking in the upper half-list
 int curCopy = loIdx; // where to start copying into the temp storage
 while (curLo < midIdx && curHi < hiIdx) {
   if (comp.compare(source.get(curLo), source.get(curHi) <= 0) {
     // the value at curLo is smaller, so it comes first
     temp.set(curCopy, source.get(curLo));
     curLo = curLo + 1; // advance the lower index
   }
   else {
     // the value at curHi is smaller, so it comes first
     temp.set(curCopy, source.get(curHi));
     curHi = curHi + 1; // advance the upper index
   }
   curCopy = curCopy + 1; // advance the copying index
 }
 // copy everything that's left -- at most one of the two half-lists still has items in it
 while (curLo < midIdx) {
   temp.set(curCopy, source.get(curLo));
   curLo = curLo + 1;
   curCopy = curCopy + 1;
 }
 while (curHi < hiIdx) {
   temp.set(curCopy, source.get(curHi));
   curHi = curHi + 1;
   curCopy = curCopy + 1;
 }
 // copy everything back from temp into source
 for (int i = loIdx; i < hiIdx; i = i + 1) {
   source.set(i, temp.get(i));
 }
}

}

class ExampleArrayLists {
  ArrayList<String> words;
  ArrayList<String> unsortedWords;
  ArrayUtils au = new ArrayUtils();

  void initData() {
    words = new ArrayList<String>();
    words.add("apple");
    words.add("banana");
    words.add("cherry");
    words.add("date");
    words.add("fig");
    words.add("grape");
    words.add("honeydew");
    words.add("kiwi");
    words.add("watermelon");

    unsortedWords = new ArrayList<String>();
    unsortedWords.add("kiwi");
    unsortedWords.add("cherry");
    unsortedWords.add("apple");
    unsortedWords.add("date");
  }

  void testGet(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    // Updated the expected exception message string to match your JDK's output
    t.checkException(new IndexOutOfBoundsException("Index 0 out of bounds for length 0"),
        someStrings, "get", 0);
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
  }

  void testSwap(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("Second string");
    someStrings.add("First string");

    au.swap(someStrings, 0, 1);
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
  }

  void testBinarySearch(Tester t) {
    this.initData();
    // Test hit and miss conditions for Closed Interval (v1)
    t.checkExpect(au.binarySearch_v1(words, "grape"), 5);
    t.checkExpect(au.binarySearch_v1(words, "blueberry"), -1);
    t.checkExpect(au.binarySearch_v1(words, "apple"), 0);
    t.checkExpect(au.binarySearch_v1(words, "watermelon"), 8);

    // Test hit and miss conditions for Semi-Open Interval (v2)
    t.checkExpect(au.binarySearch_v2(words, "grape"), 5);
    t.checkExpect(au.binarySearch_v2(words, "blueberry"), -1);
    t.checkExpect(au.binarySearch_v2(words, "apple"), 0);
    t.checkExpect(au.binarySearch_v2(words, "watermelon"), 8);
  }

  void testSorting(Tester t) {
    this.initData();
    au.sort(unsortedWords);

    t.checkExpect(unsortedWords.get(0), "apple");
    t.checkExpect(unsortedWords.get(1), "cherry");
    t.checkExpect(unsortedWords.get(2), "date");
    t.checkExpect(unsortedWords.get(3), "kiwi");
  }

  void testInterleaveAndUnshuffle(Tester t) {
    ArrayList<Integer> l1 = new ArrayList<Integer>();
    ArrayList<Integer> l2 = new ArrayList<Integer>();
    l1.add(1);
    l1.add(3);
    l2.add(2);
    l2.add(4);

    ArrayList<Integer> expectedInterleave = new ArrayList<Integer>();
    expectedInterleave.add(1);
    expectedInterleave.add(2);
    expectedInterleave.add(3);
    expectedInterleave.add(4);

    // Interleaving [1, 3] and [2, 4] gives [1, 2, 3, 4]
    t.checkExpect(au.interleave(l1, l2), expectedInterleave);

    ArrayList<Integer> expectedUnshuffled = new ArrayList<Integer>();
    expectedUnshuffled.add(1); // index 0
    expectedUnshuffled.add(3); // index 2
    expectedUnshuffled.add(2); // index 1
    expectedUnshuffled.add(4); // index 3

    // Unshuffling [1, 2, 3, 4] pulls indices 0,2 then 1,3 -> [1, 3, 2, 4]
    t.checkExpect(au.unshuffle(expectedInterleave), expectedUnshuffled);
  }
}

class ExamplesCapitalize {
  void testCapitalizeTitles_bad(Tester t) {
    // Initialize data:
    Author mf = new Author("Matthias Felleisen", 1953);
    Book htdp = new Book("How to Design Programs", mf);
    ArrayList<Book> books = new ArrayList<Book>();
    books.add(htdp);
    // Modify it
    (new ArrayUtils()).capitalizeTitles_good(books);
    // Test for changes
    t.checkExpect(books.get(0).title, "HOW TO DESIGN PROGRAMS");
  }
}