import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color; // general colors (as triples of red,green,blue values)
                       // and predefined colors (Color.RED, Color.GRAY, etc.)
import java.util.Random;

class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  // adds 2 MyPosn and return a new MyPosn
  MyPosn add(MyPosn that) {
    return new MyPosn(this.x + that.x, this.y + that.y);
  }

  // determines if this position lies outside of the given width and height
  boolean isOffscreen(int width, int height) {
    return this.x < 0 || this.x > width || this.y < 0 || this.y > height;
  }
}

// to represent a circle
class Circle {
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  int radius;
  Color color;

  // constructor
  Circle(MyPosn position, MyPosn velocity, int radius, Color color) {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  // produces a new Circle moved by its velocity for one tick
  Circle move() {
    return new Circle(this.position.add(this.velocity), this.velocity, this.radius, this.color);
  }

  // Returns an image of the circle
  WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, this.color);
  }

  // Places this circle's image onto the given scene
  WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(), this.position.x, this.position.y);
  }

  // determines if this circle is offscreen by asking its position
  boolean isOffscreen(int width, int height) {
    return this.position.isOffscreen(width, height);
  }
}

interface ILoCircle {
  ILoCircle moveAll();

  ILoCircle removeOffscreen(int width, int height);

  WorldScene placeAll(WorldScene scene);

  int length();
}

// The empty list of circles
class MtLoCircle implements ILoCircle {
  public ILoCircle moveAll() {
    return this;
  }

  public ILoCircle removeOffscreen(int width, int height) {
    return this;
  }

  public WorldScene placeAll(WorldScene scene) {
    return scene;
  }

  public int length() {
    return 0;
  }
}

// A non-empty list of circles
class ConsLoCircle implements ILoCircle {
  Circle first;
  ILoCircle rest;

  ConsLoCircle(Circle first, ILoCircle rest) {
    this.first = first;
    this.rest = rest;
  }

  // Moves the first circle and moves the rest of the list
  public ILoCircle moveAll() {
    return new ConsLoCircle(this.first.move(), this.rest.moveAll());
  }

  // Keeps the circle only if it is NOT offscreen
  public ILoCircle removeOffscreen(int width, int height) {
    if (this.first.isOffscreen(width, height)) {
      return this.rest.removeOffscreen(width, height);
    }
    else {
      return new ConsLoCircle(this.first, this.rest.removeOffscreen(width, height));
    }
  }

  // Places the first circle onto the scene, then asks the rest to do the same
  public WorldScene placeAll(WorldScene scene) {
    return this.rest.placeAll(this.first.place(scene));
  }

  public int length() {
    return 1 + this.rest.length();
  }
}

class CircleWorld extends World {
  ILoCircle circles;
  int remaining;
  Random rand;

  // Default constructor
  CircleWorld(ILoCircle circles, int remaining, Random rand) {
    this.circles = circles;
    this.remaining = remaining;
    this.rand = rand;
  }

  // Convenience constructor
  CircleWorld(int remaining) {
    this(new MtLoCircle(), remaining, new Random());
  }

  // Draw the game state
  public WorldScene makeScene() {
    WorldScene canvas = new WorldScene(500, 500);
    return this.circles.placeAll(canvas)
        .placeImageXY(new TextImage("Remaining: " + this.remaining, 20, Color.BLACK), 80, 20);
  }

  // Handle game over
  public WorldEnd worldEnds() {
    if (this.remaining <= 0) {
      return new WorldEnd(true, this.makeEndScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // Create a scene for the game over screen
  WorldScene makeEndScene() {
    WorldScene endCanvas = new WorldScene(500, 500);
    return endCanvas.placeImageXY(new TextImage("GAME OVER", 40, Color.RED), 250, 250);
  }

  // Add a circle on click with random direction
  public World onMouseClicked(Posn mouse) {
    // Generate a random velocity between -5 and 5 for both X and Y
    int vx = this.rand.nextInt(11) - 5;
    int vy = this.rand.nextInt(11) - 5;

    // Ensure it's not standing still
    if (vx == 0 && vy == 0) {
      vy = -5;
    }

    Circle newCircle = new Circle(new MyPosn(mouse), new MyPosn(vx, vy), 15, Color.BLUE);
    return new CircleWorld(new ConsLoCircle(newCircle, this.circles), this.remaining, this.rand);
  }

  // Update the game every tick
  public World onTick() {
    ILoCircle moved = this.circles.moveAll();
    ILoCircle filtered = moved.removeOffscreen(500, 500);

    // Calculate how many were lost this tick
    int numRemoved = moved.length() - filtered.length();

    return new CircleWorld(filtered, this.remaining - numRemoved, this.rand);
  }
}

class ExamplesGame {
  ExamplesGame() {
  }

  MyPosn p1 = new MyPosn(50, 50);
  MyPosn p2 = new MyPosn(10, 20);
  MyPosn pOff = new MyPosn(600, 100); // Assuming a 500px wide screen

  MyPosn startPos = new MyPosn(100, 100);
  MyPosn vel = new MyPosn(5, 10);
  Circle c1 = new Circle(startPos, vel, 10, Color.RED);
  Circle cIn = new Circle(new MyPosn(100, 100), new MyPosn(0, 0), 10, Color.RED);
  Circle cOut = new Circle(new MyPosn(600, 600), new MyPosn(0, 0), 10, Color.BLUE);

  ILoCircle list = new ConsLoCircle(cIn, new ConsLoCircle(cOut, new MtLoCircle()));

  // Test the add method
  boolean testAdd(Tester t) {
    return t.checkExpect(this.p1.add(this.p2), new MyPosn(60, 70));
  }

  // Test the isOffscreen method
  boolean testIsOffscreen(Tester t) {
    return t.checkExpect(this.p1.isOffscreen(500, 500), false) // Inside
        && t.checkExpect(this.pOff.isOffscreen(500, 500), true) // Outside (x > width)
        && t.checkExpect(new MyPosn(-5, 10).isOffscreen(500, 500), true); // Outside (x < 0)
  }

  boolean testMoveCircle(Tester t) {
    return t.checkExpect(this.c1.move(), new Circle(new MyPosn(105, 110), this.vel, 10, Color.RED));
  }

  boolean testFiltering(Tester t) {
    return t.checkExpect(list.removeOffscreen(500, 500), new ConsLoCircle(cIn, new MtLoCircle()));
  }

  boolean testImages(Tester t) {
    return t.checkExpect(new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY),
        new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY));
  }

  /*
  boolean testFailure(Tester t) {
    return t.checkExpect(
        new ScaleImageXY(new RectangleImage(60, 40, OutlineMode.SOLID, Color.GRAY), 0.5, 0.25),
        new RectangleImage(30, 15, OutlineMode.SOLID, Color.GRAY));
  }
  */

  boolean testRunGame(Tester t) {
    CircleWorld initialWorld = new CircleWorld(10); // Start with 10 "lives"
    return initialWorld.bigBang(500, 500, 1.0 / 28.0);
  }
}