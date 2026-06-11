import java.util.function.Predicate;

// Represents a data-carrying node in the list
class Node<T> extends ANode<T> {
  T data;

  // Constructor 1: Takes just a value, leaves links null
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  // Constructor 2: Convenience constructor with neighborhood link wire-up
  Node(T data, ANode<T> next, ANode<T> prev) {
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Given neighbor nodes cannot be null");
    }
    this.data = data;
    this.next = next;
    this.prev = prev;

    // Mutate existing nodes to securely clamp this new node between them
    prev.next = this;
    next.prev = this;
  }

  // Accumulates total elements recursively
  int countNodes() {
    return 1 + this.next.countNodes();
  }

  // Returns payload safely
  T getDataValue() {
    return this.data;
  }

  // Validates predicate or moves forward down the line
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    return this.next.findHelp(pred);
  }
}