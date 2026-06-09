import java.util.Comparator;
import tester.Tester;

// Represents an individual book entry
class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

// Compares Books alphabetically by Title
class BooksByTitle implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

// Compares Books alphabetically by Author
class BooksByAuthor implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

// Compares Books numerically by Price (increasing)
class BooksByPrice implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;
  }
}

interface IList<T> {
  // We use standard structural tracking or standard methods if needed
}

class MtList<T> implements IList<T> {
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

abstract class ABST<T> {
  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

  // Inserts an item into the correct sorted position in this BST
  abstract ABST<T> insert(T item);

  // Checks if an item exists matching the search criteria of this tree's order
  abstract boolean present(T item);

  // Finds the leftmost terminal element in the tree
  abstract T getLeftmost();

  // Returns everything except the leftmost terminal element of this tree
  abstract ABST<T> getRight();

  // Structural identical check: matching layout structure and data
  abstract boolean sameTree(ABST<T> given);

  // Specific internal helper dispatch patterns for structural checks
  abstract boolean sameLeaf(Leaf<T> given);

  abstract boolean sameNode(Node<T> given);

  // Set identity data check: checks if both trees store exactly the same sorted
  // content
  boolean sameData(ABST<T> given) {
    return this.buildList().equals(given.buildList()); // Plain language logic match
  }

  // Generates an ordered IList processing sequence out of the tree structure
  abstract IList<T> buildList();
}

class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    super(order);
  }

  public ABST<T> insert(T item) {
    return new Node<T>(this.order, item, this, this);
  }

  public boolean present(T item) {
    return false;
  }

  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  public boolean sameTree(ABST<T> given) {
    return given.sameLeaf(this);
  }

  public boolean sameLeaf(Leaf<T> given) {
    return true;
  }

  public boolean sameNode(Node<T> given) {
    return false;
  }

  public IList<T> buildList() {
    return new MtList<T>();
  }
}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  public ABST<T> insert(T item) {
    if (this.order.compare(item, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(item), this.right);
    }
    else {
      // Duplicate items go to the right-side subtree
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
  }

  public boolean present(T item) {
    int compResult = this.order.compare(item, this.data);
    if (compResult == 0) {
      return true;
    }
    else if (compResult < 0) {
      return this.left.present(item);
    }
    else {
      return this.right.present(item);
    }
  }

  public T getLeftmost() {
    try {
      return this.left.getLeftmost();
    }
    catch (RuntimeException e) {
      // Left child was a leaf, so this current node data is the leftmost item
      return this.data;
    }
  }

  public ABST<T> getRight() {
    try {
      // Try to discard the leftmost item from the left subtree
      return new Node<T>(this.order, this.data, this.left.getRight(), this.right);
    }
    catch (RuntimeException e) {
      // Left was a leaf, so this element is discarded; return right child subtree
      return this.right;
    }
  }

  public boolean sameTree(ABST<T> given) {
    return given.sameNode(this);
  }

  public boolean sameLeaf(Leaf<T> given) {
    return false;
  }

  public boolean sameNode(Node<T> given) {
    return this.order.compare(this.data, given.data) == 0 && this.left.sameTree(given.left)
        && this.right.sameTree(given.right);
  }

  // To build the list in sorted order, we gather left, current item, then right
  public IList<T> buildList() {
    return append(this.left.buildList(), new ConsList<T>(this.data, this.right.buildList()));
  }

  // Helper utility to combine sequence lists structurally
  private IList<T> append(IList<T> list1, IList<T> list2) {
    if (list1 instanceof MtList) {
      return list2;
    }
    else {
      ConsList<T> cons = (ConsList<T>) list1;
      return new ConsList<T>(cons.first, append(cons.rest, list2));
    }
  }
}

class ExamplesBST {
  ExamplesBST() {}
  // Comparators
  Comparator<Book> byTitle = new BooksByTitle();
  Comparator<Book> byPrice = new BooksByPrice();

  // Books
  Book b1 = new Book("Animal Farm", "Orwell", 10);
  Book b2 = new Book("Brave New World", "Huxley", 15);
  Book b3 = new Book("Catch-22", "Heller", 12);
  Book b4 = new Book("Dune", "Herbert", 20);
  Book b5 = new Book("Emma", "Austen", 8);

  // Empty Leaves
  ABST<Book> leafTitle = new Leaf<>(byTitle);
  ABST<Book> leafPrice = new Leaf<>(byPrice);

  // BST Instances ordered by Title
  ABST<Book> bstA;
  ABST<Book> bstB;
  ABST<Book> bstC;
  ABST<Book> bstD;

  void initTrees() {
    // bstA structure layout: b3 as root, b2 on left, b4 on right, b1 on left of b2
    bstA = new Node<>(byTitle, b3,
        new Node<>(byTitle, b2, new Node<>(byTitle, b1, leafTitle, leafTitle), leafTitle),
        new Node<>(byTitle, b4, leafTitle, leafTitle));

    // bstB layout exactly identical structural composition to bstA
    bstB = new Node<>(byTitle, b3,
        new Node<>(byTitle, b2, new Node<>(byTitle, b1, leafTitle, leafTitle), leafTitle),
        new Node<>(byTitle, b4, leafTitle, leafTitle));

    // bstC layout structurally different root but stores equivalent underlying
    // sorted set data
    bstC = new Node<>(byTitle, b2, new Node<>(byTitle, b1, leafTitle, leafTitle),
        new Node<>(byTitle, b4, new Node<>(byTitle, b3, leafTitle, leafTitle), leafTitle));

    // bstD stores distinct overall book values (includes b5, lacks b2 entirely)
    bstD = new Node<>(byTitle, b3, new Node<>(byTitle, b1, leafTitle, leafTitle),
        new Node<>(byTitle, b4, leafTitle, new Node<>(byTitle, b5, leafTitle, leafTitle)));
  }

  // Test insertion mechanics logic
  void testInsert(Tester t) {
    initTrees();
    ABST<Book> testTree = leafTitle.insert(b3).insert(b4).insert(b2).insert(b1);
    t.checkExpect(testTree.sameTree(bstA), true);
  }

  // Test content presentation checks
  void testPresent(Tester t) {
    initTrees();
    t.checkExpect(bstA.present(b2), true);
    t.checkExpect(bstA.present(b5), false);
  }

  // Test structural identical tracking constraints
  void testSameTree(Tester t) {
    initTrees();
    t.checkExpect(bstA.sameTree(bstB), true);
    t.checkExpect(bstA.sameTree(bstC), false);
    t.checkExpect(bstA.sameTree(bstD), false);
  }
}