import java.util.ArrayList;
import java.util.HashMap;

class UnionFind {
  HashMap<Cell, Cell> representatives;

  UnionFind(ArrayList<Cell> allCells) {
    this.representatives = new HashMap<>();
    for (Cell c : allCells) {
      this.representatives.put(c, c); // Initially, every node points to itself
    }
  }

  // Finds the absolute root representative of a given cell
  Cell find(Cell c) {
    Cell current = c;
    while (current != this.representatives.get(current)) {
      current = this.representatives.get(current);
    }
    return current;
  }

  // Links two representatives together
  void union(Cell root1, Cell root2) {
    this.representatives.put(root1, root2);
  }
}