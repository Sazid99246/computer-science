import java.util.function.Predicate;

// Represents the structural boundary/header node of the Deque
class Sentinel<T> extends ANode<T> {

  // Default zero-argument constructor: points to itself
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // Base case: Sentinel does not count as data
  int countNodes() {
    return 0;
  }

  // A sentinel contains no valid client data
  T getDataValue() {
    throw new RuntimeException("Sentinel has no data payload");
  }

  // Base case: Reached the end of the list without finding a match
  ANode<T> findHelp(Predicate<T> pred) {
    return this;
  }
}