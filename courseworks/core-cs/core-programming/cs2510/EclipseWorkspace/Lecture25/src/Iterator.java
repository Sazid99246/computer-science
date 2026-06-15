import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import tester.Tester;

// =============================================================================
// 1. CORE ITERATOR & ITERABLE INTERFACES (Provided for architectural reference)
// =============================================================================

/* // Java standard library definitions shown for clarity:
interface Iterator<T> {
  boolean hasNext();
  T next();
  void remove(); // Optional
}

interface Iterable<T> {
  Iterator<T> iterator();
}
*/

// =============================================================================
// 2. DATA STRUCTURE DEFINITIONS (IList & Deque Support Layouts)
// =============================================================================

// Represents a custom list structure tracking element items
interface IList<T> extends Iterable<T> {
  boolean isCons();

  ConsList<T> asCons();
}

// Non-empty custom list implementation
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isCons() {
    return true;
  }

  public ConsList<T> asCons() {
    return this;
  }

  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}

// Empty custom list implementation
class MtList<T> implements IList<T> {
  public boolean isCons() {
    return false;
  }

  public ConsList<T> asCons() {
    throw new ClassCastException("Empty list cannot be cast to ConsList");
  }

  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}

// --- DEQUE MUTABLE DOUBLE LINKED STRUCTURAL FRAMEWORK ---

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  abstract T getDataValue();
}

class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  T getDataValue() {
    throw new RuntimeException("Sentinel contains no data payload");
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    this.next = next;
    this.prev = prev;
    prev.next = this;
    next.prev = this;
  }

  T getDataValue() {
    return this.data;
  }
}

class Deque<T> implements Iterable<T> {
  Sentinel<T> sentinel;

  Deque() {
    this.sentinel = new Sentinel<T>();
  }

  int size() {
    int count = 0;
    ANode<T> curr = this.sentinel.next;
    while (curr != this.sentinel) {
      count++;
      curr = curr.next;
    }
    return count;
  }

  void addAtHead(T value) {
    new Node<T>(value, this.sentinel.next, this.sentinel);
  }

  void addAtTail(T value) {
    new Node<T>(value, this.sentinel, this.sentinel.prev);
  }

  T removeAtHead() {
    if (this.sentinel.next == this.sentinel) {
      throw new RuntimeException("Empty Deque");
    }
    T val = this.sentinel.next.getDataValue();
    this.sentinel.next = this.sentinel.next.next;
    this.sentinel.next.prev = this.sentinel;
    return val;
  }

  public Iterator<T> iterator() {
    return new DequeForwardIterator<T>(this.sentinel.next, this.sentinel);
  }

  public Iterator<T> reverseIterator() {
    return new DequeReverseIterator<T>(this.sentinel.prev, this.sentinel);
  }
}

// =============================================================================
// 3. ITERATOR IMPLEMENTATIONS (Section 25.2 Data Type Iteration)
// =============================================================================

// 25.2.1 Iterator for ArrayLists
class ArrayListIterator<T> implements Iterator<T> {
  ArrayList<T> items;
  int nextIdx;

  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }

  public boolean hasNext() {
    return this.nextIdx < this.items.size();
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("No more items left to fetch");
    }
    T answer = this.items.get(this.nextIdx);
    this.nextIdx = this.nextIdx + 1;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

// 25.2.2 Iterator for ILists
class IListIterator<T> implements Iterator<T> {
  IList<T> items;

  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("No more items left to fetch");
    }
    ConsList<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

// 25.2.3 Iteration in Multiple Directions (Forward & Backward Deque Iterators)
class DequeForwardIterator<T> implements Iterator<T> {
  ANode<T> curr;
  Sentinel<T> bound;

  DequeForwardIterator(ANode<T> start, Sentinel<T> bound) {
    this.curr = start;
    this.bound = bound;
  }

  public boolean hasNext() {
    return this.curr != this.bound;
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("No more items");
    }
    T data = this.curr.getDataValue();
    this.curr = this.curr.next;
    return data;
  }
}

class DequeReverseIterator<T> implements Iterator<T> {
  ANode<T> curr;
  Sentinel<T> bound;

  DequeReverseIterator(ANode<T> start, Sentinel<T> bound) {
    this.curr = start;
    this.bound = bound;
  }

  public boolean hasNext() {
    return this.curr != this.bound;
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("No more items");
    }
    T data = this.curr.getDataValue();
    this.curr = this.curr.prev;
    return data;
  }
}

// 25.2.4 Iterator for Fibonacci Numbers (Computing on Demand)
class FibonacciIterator implements Iterator<Integer> {
  int prevVal = 0;
  int curVal = 1;

  public boolean hasNext() {
    return true;
  }

  public Integer next() {
    int answer = this.prevVal + this.curVal;
    this.prevVal = this.curVal;
    this.curVal = answer;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

// 25.2.5 Higher-order Iterators
class EveryOtherIter<T> implements Iterator<T> {
  Iterator<T> source;

  EveryOtherIter(Iterator<T> source) {
    this.source = source;
  }

  public boolean hasNext() {
    return this.source.hasNext();
  }

  public T next() {
    T answer = this.source.next();
    if (this.source.hasNext()) {
      this.source.next(); // Skip the next index element item payload
    }
    return answer;
  }

  public void remove() {
    this.source.remove();
  }
}

class TakeN<T> implements Iterator<T> {
  Iterator<T> source;
  int howMany;
  int countSoFar;

  TakeN(Iterator<T> source, int n) {
    this.source = source;
    this.howMany = n;
    this.countSoFar = 0;
  }

  public boolean hasNext() {
    return (this.countSoFar < this.howMany) && this.source.hasNext();
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("Limit threshold bound reached");
    }
    this.countSoFar = this.countSoFar + 1;
    return this.source.next();
  }

  public void remove() {
    this.source.remove();
  }
}

// =============================================================================
// 4. TREE STRUCTURES AND GRAPH WORKLIST ITERATIONS (Section 25.2.6)
// =============================================================================

interface IBinaryTree<T> {
  boolean isNode();

  BTNode<T> asNode();
}

class BTLeaf<T> implements IBinaryTree<T> {
  public boolean isNode() {
    return false;
  }

  public BTNode<T> asNode() {
    throw new ClassCastException("Leaf nodes cannot be unboxed to structural BTNodes");
  }
}

class BTNode<T> implements IBinaryTree<T> {
  T data;
  IBinaryTree<T> left;
  IBinaryTree<T> right;

  BTNode(T data, IBinaryTree<T> left, IBinaryTree<T> right) {
    this.data = data;
    this.left = left;
    this.right = right;
  }

  public boolean isNode() {
    return true;
  }

  public BTNode<T> asNode() {
    return this;
  }
}

// Breadth-First Data Stream Worklist Pipeline Traversal Engine
class BreadthFirstIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;

  BreadthFirstIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }

  void addIfNotLeaf(IBinaryTree<T> bt) {
    if (bt.isNode()) {
      this.worklist.addAtTail(bt);
    }
  }

  public boolean hasNext() {
    return this.worklist.size() > 0;
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("Worklist pipeline exhausted");
    }
    BTNode<T> node = this.worklist.removeAtHead().asNode();
    this.addIfNotLeaf(node.left);
    this.addIfNotLeaf(node.right);
    return node.data;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

// Pre-Order Data Stream Stack Depth-First Traversal Engine
class PreOrderIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;

  PreOrderIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }

  void addIfNotLeaf(IBinaryTree<T> bt) {
    if (bt.isNode()) {
      this.worklist.addAtHead(bt);
    }
  }

  public boolean hasNext() {
    return this.worklist.size() > 0;
  }

  public T next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException("Worklist pipeline exhausted");
    }
    BTNode<T> node = this.worklist.removeAtHead().asNode();
    this.addIfNotLeaf(node.right); // Track right element side first on stack to extract left first
    this.addIfNotLeaf(node.left);
    return node.data;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

// =============================================================================
// 5. COMPREHENSIVE LECTURE CODE TESTING SUITE
// =============================================================================

class ExamplesLecture25 {

  // ArrayList Iterator verification logic tests
  void testArrayListIteration(Tester t) {
    ArrayList<String> arr = new ArrayList<>(Arrays.asList("A", "B", "C"));
    Iterator<String> iter = new ArrayListIterator<>(arr);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.next(), "A");
    t.checkExpect(iter.next(), "B");
    t.checkExpect(iter.next(), "C");
    t.checkExpect(iter.hasNext(), false);
  }

  // IList Recursive Custom Links Collection Verification logic tests
  void testIListIteration(Tester t) {
    IList<String> list = new ConsList<>("A", new ConsList<>("B", new MtList<>()));
    Iterator<String> iter = list.iterator();

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.next(), "A");
    t.checkExpect(iter.next(), "B");
    t.checkExpect(iter.hasNext(), false);
  }

  // Deque Terminal Ends Bi-Directional Processing validation assertions
  void testDequeBiDirectionalIteration(Tester t) {
    Deque<String> dq = new Deque<>();
    dq.addAtTail("First");
    dq.addAtTail("Second");

    Iterator<String> forward = dq.iterator();
    t.checkExpect(forward.next(), "First");
    t.checkExpect(forward.next(), "Second");

    Iterator<String> reverse = dq.reverseIterator();
    t.checkExpect(reverse.next(), "Second");
    t.checkExpect(reverse.next(), "First");
  }

  // Higher-order dynamic processing mapping filters validation
  void testHigherOrderIterators(Tester t) {
    ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    // Testing TakeN
    Iterator<Integer> limited = new TakeN<>(new ArrayListIterator<>(nums), 3);
    t.checkExpect(limited.next(), 1);
    t.checkExpect(limited.next(), 2);
    t.checkExpect(limited.next(), 3);
    t.checkExpect(limited.hasNext(), false);

    // Testing EveryOther
    Iterator<Integer> skipped = new EveryOtherIter<>(new ArrayListIterator<>(nums));
    t.checkExpect(skipped.next(), 1);
    t.checkExpect(skipped.next(), 3);
    t.checkExpect(skipped.next(), 5);
    t.checkExpect(skipped.hasNext(), false);
  }

  // Hierarchical worklist structural search traversals verification
  void testTreeIterators(Tester t) {
    // Structural layout representation:
    // A
    // / \
    // B C
    IBinaryTree<String> tree = new BTNode<>("A", new BTNode<>("B", new BTLeaf<>(), new BTLeaf<>()),
        new BTNode<>("C", new BTLeaf<>(), new BTLeaf<>()));

    // Breadth-First verification tracking pipeline (Level-Order: A, B, C)
    Iterator<String> bf = new BreadthFirstIterator<>(tree);
    t.checkExpect(bf.next(), "A");
    t.checkExpect(bf.next(), "B");
    t.checkExpect(bf.next(), "C");
    t.checkExpect(bf.hasNext(), false);

    // Pre-Order verification tracking stack framework (Depth-First: A, B, C)
    Iterator<String> pre = new PreOrderIterator<>(tree);
    t.checkExpect(pre.next(), "A");
    t.checkExpect(pre.next(), "B");
    t.checkExpect(pre.next(), "C");
    t.checkExpect(pre.hasNext(), false);
  }
}