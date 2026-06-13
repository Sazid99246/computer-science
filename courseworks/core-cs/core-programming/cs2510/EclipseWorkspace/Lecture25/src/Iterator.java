// Represents the ability to produce a sequence of values
// of type T, one at a time
interface Iterator<T> {
  // Does this sequence have at least one more value?
  boolean hasNext();

  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  T next();

  // EFFECT: Remove the item just returned by next()
  // NOTE: This method may not be supported by every iterator; ignore it for now
  void remove();
}