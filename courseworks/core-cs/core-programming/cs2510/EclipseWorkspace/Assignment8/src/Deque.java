import java.util.function.Predicate;

// Represents the generic doubly-linked list wrapper
class Deque<T> {
  Sentinel<T> header;

  // Constructor 1: Initializes an empty Deque
  Deque() {
    this.header = new Sentinel<T>();
  }

  // Constructor 2: Convenience constructor with a predefined sentinel
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // Determines total client data nodes present
  int size() {
    return this.header.next.countNodes();
  }

  // EFFECT: Inserts a new element at the front of the list
  void addAtHead(T value) {
    new Node<T>(value, this.header.next, this.header);
  }

  // EFFECT: Inserts a new element at the rear of the list
  void addAtTail(T value) {
    new Node<T>(value, this.header, this.header.prev);
  }

  // EFFECT: Extracts and removes the head item
  T removeFromHead() {
    if (this.size() == 0) {
      throw new RuntimeException("Cannot remove from an empty Deque");
    }
    T data = this.header.next.getDataValue();
    this.header.next.removeSelf();
    return data;
  }

  // EFFECT: Extracts and removes the tail item
  T removeFromTail() {
    if (this.size() == 0) {
      throw new RuntimeException("Cannot remove from an empty Deque");
    }
    T data = this.header.prev.getDataValue();
    this.header.prev.removeSelf();
    return data;
  }

  // Locates the first node where the predicate is satisfied
  ANode<T> find(Predicate<T> pred) {
    return this.header.next.findHelp(pred);
  }

  // EFFECT: Extracts the target node if it is not the sentinel head
  void removeNode(ANode<T> target) {
    if (target != this.header) {
      target.removeSelf();
    }
  }
}