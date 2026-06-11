import java.util.function.Predicate;

// Represents a node in a doubly-linked list
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // Helper: Counts the number of actual data nodes following this node
  abstract int countNodes();

  // Helper: Returns the data of this node if it is a data Node
  abstract T getDataValue();

  // Helper: Traverses the list to find the first node matching the predicate
  abstract ANode<T> findHelp(Predicate<T> pred);

  // EFFECT: Removes this node from the list by adjusting surrounding links
  void removeSelf() {
    this.prev.next = this.next;
    this.next.prev = this.prev;
  }
}