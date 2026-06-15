// Represents a wall/connection between two adjacent cells
class Edge implements Comparable<Edge> {
  Cell from;
  Cell to;
  int weight;

  Edge(Cell from, Cell to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  // Allows easy sorting by weight for Kruskal's algorithm
  public int compareTo(Edge other) {
    return Integer.compare(this.weight, other.weight);
  }
}