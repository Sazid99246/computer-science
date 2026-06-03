class Rectangle {
  int x, y, width, height;

  // Constants for the canvas limits
  int CANVAS_WIDTH = 500;
  int CANVAS_HEIGHT = 500;

  Rectangle(int x, int y, int width, int height) {
    // 1. Check if dimensions are positive
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive.");
    }

    // 2. Check if the top-left corner is inside the canvas
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Rectangle must start within the canvas (x,y >= 0).");
    }

    // 3. Check if the bottom-right corner exceeds the canvas
    if ((x + width) > CANVAS_WIDTH || (y + height) > CANVAS_HEIGHT) {
      throw new IllegalArgumentException("Rectangle extends beyond the canvas boundaries.");
    }

    // If all checks pass, initialize the fields
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
}