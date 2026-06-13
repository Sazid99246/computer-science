// Represents anything that can be iterated over
interface Iterable<T> {
  // Returns an iterator over this collection
  Iterator<T> iterator();
}