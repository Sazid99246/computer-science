import java.util.ArrayList;
import tester.Tester;

interface IFunc<Arg, Ret> {
  Ret apply(Arg arg);
}

interface IFunc2<Arg1, Arg2, Ret> {
  Ret apply(Arg1 arg1, Arg2 arg2);
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

//===========================================================================
  // ITERATIVE FOLDS (FOR-EACH ONLY)
  // ===========================================================================

  // Fold Left (For-Each)
  // Smoothly matches the natural forward flow of a for-each loop
  <T, U> U foldlLoop(ArrayList<T> arr, IFunc2<U, T, U> combiner, U initial) {
    U acc = initial;
    for (T element : arr) {
      acc = combiner.apply(acc, element);
    }
    return acc;
  }

  // Fold Right (For-Each) — THE HARD ONE!
  // We cannot use a standard reverse counted-for loop because of the prompt's
  // rule.
  <T, U> U foldrLoop(ArrayList<T> arr, IFunc2<T, U, U> combiner, U initial) {
    // Step 1: Copy references into a reversed temporary list to invert the sequence
    ArrayList<T> reversed = new ArrayList<T>();
    for (T element : arr) {
      reversed.add(0, element); // Shifts existing items right; expensive but keeps for-each syntax
    }

    // Step 2: Forward iterate over the reversed list
    U acc = initial;
    for (T element : reversed) {
      acc = combiner.apply(element, acc);
    }
    return acc;
  }
}

class ExampleArrayLists {
  void testGet(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    t.checkException(new IndexOutOfBoundsException("Index: 0, Size: 0"), someStrings, "get", 0);
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
    someStrings.add(1, "Squeezed in");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Squeezed in");
    t.checkExpect(someStrings.get(2), "Second string");
    t.checkException(new IndexOutOfBoundsException("Index: 3, Size: 2"), someStrings, "get", 3);
  }

  void testSwap(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("Second string");
    someStrings.add("First string");

    ArrayUtils au = new ArrayUtils();
    au.swap(someStrings, 0, 1);

    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
  }
}
