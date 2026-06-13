import java.util.ArrayList;

class ArrayListIterator<T> implements Iterator<T> {
  // the list of items that this iterator iterates over
  ArrayList<T> items;
  // the index of the next item to be returned
  int nextIdx;

  // Construct an iterator for a given ArrayList
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }

  // Does this sequence (of items in the array list) have at least one more value?
  public boolean hasNext() {
    return this.nextIdx < this.items.size();
  }

  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  public T next() {
    T answer = this.items.get(this.nextIdx);
    this.nextIdx = this.nextIdx + 1;
    return answer;
  }

  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
