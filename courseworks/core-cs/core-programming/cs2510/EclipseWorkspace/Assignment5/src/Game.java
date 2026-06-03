import tester.*;
import java.awt.Color;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.util.Random;
import javalib.funworld.WorldScene;

abstract class AGamePiece {
  Posn loc;
  int vx; // velocity x
  int vy; // velocity y
  int radius;
  Color color;

  AGamePiece(Posn loc, int vx, int vy, int radius, Color color) {
    this.loc = loc;
    this.vx = vx;
    this.vy = vy;
    this.radius = radius;
    this.color = color;
  }

  abstract AGamePiece movePiece();

  // Returns a new Posn representing the next location based on velocity
  Posn move() {
    return new Posn(this.loc.x + this.vx, this.loc.y + this.vy);
  }

  // Draw the piece as a circle on the given scene
  WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color),
        this.loc.x, this.loc.y);
  }

  // Is this piece entirely off the screen?
  // Screen bounds are defined as 500x300
  boolean isOffScreen() {
    return this.loc.x < 0 || this.loc.x > 500 || this.loc.y < 0 || this.loc.y > 300;
  }

  boolean collidesWith(AGamePiece other) {
    // Distance formula: sqrt((x1-x2)^2 + (y1-y2)^2)
    double distance = Math.hypot(this.loc.x - other.loc.x, this.loc.y - other.loc.y);
    return distance < (this.radius + other.radius);
  }
}

class Ship extends AGamePiece {
  Ship(Posn loc, int vx, int vy, int radius, Color color) {
    super(loc, vx, vy, radius, color);
  }

  // Specific constructor for a ship spawning at a side
  // v is speed (magnitude), direction is 1 (right) or -1 (left)
  Ship(int y, int direction, int speed) {
    super(new Posn(direction == 1 ? 0 : 500, y), direction * speed, 0, 300 / 30, Color.CYAN);
  }

  @Override
  AGamePiece movePiece() {
    return new Ship(this.move(), this.vx, this.vy, this.radius, this.color);
  }
}

class Bullet extends AGamePiece {
  int explosionLevel; // n

  Bullet(Posn loc, int vx, int vy, int radius, Color color, int explosionLevel) {
    super(loc, vx, vy, radius, color);
    this.explosionLevel = explosionLevel;
  }

  IList<Bullet> explode() {
    int n = this.explosionLevel;
    int numFragments = n + 1;
    IList<Bullet> fragments = new MtList<Bullet>();

    for (int i = 0; i < numFragments; i++) {
      // Calculate angle in degrees, then convert to Radians
      double angleDeg = i * (360.0 / numFragments);
      double angleRad = Math.toRadians(angleDeg);

      // Calculate velocity components
      double vx = Math.cos(angleRad) * 8; // 8 is BULLET_SPEED
      double vy = Math.sin(angleRad) * 8;

      // Radius grows by 2 each time, capped at 10
      int newRadius = Math.min(10, 2 + (n * 2));

      fragments = new ConsList<Bullet>(
          new Bullet(this.loc, (int) vx, (int) vy, newRadius, Color.PINK, n + 1), fragments);
    }
    return fragments;
  }

  @Override
  AGamePiece movePiece() {
    return new Bullet(this.move(), this.vx, this.vy, this.radius, this.color, this.explosionLevel);
  }
}

interface IConstants {
  int SCREEN_WIDTH = 500;
  int SCREEN_HEIGHT = 300;
  double TICK_RATE = 1.0 / 28.0;

  int SHIP_RADIUS = SCREEN_HEIGHT / 30;
  int SHIP_SPEED = 4; // Half of bullet speed
  int SPAWN_MARGIN = SCREEN_HEIGHT / 7; // Ships don't spawn in top/bottom 1/7th

  int BULLET_SPEED = 8;
  int BULLET_RADIUS_START = 2;
  int BULLET_RADIUS_GROWTH = 2;
  int BULLET_RADIUS_MAX = 10;

  Color SHIP_COLOR = Color.CYAN;
  Color BULLET_COLOR = Color.PINK;
  Color TEXT_COLOR = Color.BLACK;
}

//Generic Interface for a List
interface IList<T> {
  WorldScene drawAll(WorldScene scene);

  IList<T> moveAll();

  IList<T> filterOffScreen();

  boolean isEmpty();

  int length(); // Missing in your snippet

  IList<T> append(IList<T> other); // Missing in your snippet

  boolean anyCollided(AGamePiece that);

  IList<Bullet> updateBullets(IList<Ship> ships);

  IList<Ship> updateShips(IList<Bullet> bullets);
}

class MtList<T> implements IList<T> {
  public WorldScene drawAll(WorldScene scene) {
    return scene;
  }

  public IList<T> moveAll() {
    return this;
  }

  public IList<T> filterOffScreen() {
    return this;
  }

  public boolean isEmpty() {
    return true;
  }

  public int length() {
    return 0;
  }

  public IList<T> append(IList<T> other) {
    return other;
  }

  public boolean anyCollided(AGamePiece that) {
    return false;
  }

  public IList<Bullet> updateBullets(IList<Ship> ships) {
    return new MtList<Bullet>();
  }

  public IList<Ship> updateShips(IList<Bullet> bullets) {
    return new MtList<Ship>();
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public WorldScene drawAll(WorldScene scene) {
    return this.rest.drawAll(((AGamePiece) this.first).draw(scene));
  }

  public IList<T> moveAll() {
    AGamePiece p = (AGamePiece) this.first;
    return new ConsList<T>((T) p.movePiece(), this.rest.moveAll());
  }

  public IList<T> filterOffScreen() {
    if (((AGamePiece) this.first).isOffScreen()) {
      return this.rest.filterOffScreen();
    }
    else {
      return new ConsList<T>(this.first, this.rest.filterOffScreen());
    }
  }

  public boolean isEmpty() {
    return false;
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public IList<T> append(IList<T> other) {
    return new ConsList<T>(this.first, this.rest.append(other));
  }

  public boolean anyCollided(AGamePiece that) {
    return that.collidesWith((AGamePiece) this.first) || this.rest.anyCollided(that);
  }

  public IList<Ship> updateShips(IList<Bullet> bullets) {
    if (bullets.anyCollided((AGamePiece) this.first)) {
      return this.rest.updateShips(bullets);
    }
    else {
      return new ConsList<Ship>((Ship) this.first, this.rest.updateShips(bullets));
    }
  }

  public IList<Bullet> updateBullets(IList<Ship> ships) {
    Bullet b = (Bullet) this.first;
    if (ships.anyCollided(b)) {
      // Explode and append fragments to the rest of the updated bullets
      // We cast b.explode() and the recursive call to ensure types match
      return (IList<Bullet>) b.explode().append(this.rest.updateBullets(ships));
    }
    else {
      return new ConsList<Bullet>(b, this.rest.updateBullets(ships));
    }
  }
}

class NBullets extends World implements IConstants {
  int bulletsLeft;
  int shipsDestroyed;
  IList<Ship> ships;
  IList<Bullet> bullets;
  Random rand;

  // Standard constructor for launching the game
  NBullets(int bulletsLeft) {
    this(bulletsLeft, 0, new MtList<Ship>(), new MtList<Bullet>(), new Random());
  }

  // Testing constructor: allows us to pass a seeded Random object
  NBullets(int bulletsLeft, int shipsDestroyed, IList<Ship> ships, IList<Bullet> bullets,
      Random rand) {
    this.bulletsLeft = bulletsLeft;
    this.shipsDestroyed = shipsDestroyed;
    this.ships = ships;
    this.bullets = bullets;
    this.rand = rand;
  }

  // Logic to spawn 1-3 ships at the edges
  IList<Ship> spawnShips() {
    // Only spawn ships roughly every 28 ticks (1 second)
    // For simplicity in this step, let's spawn 1 ship at a random Y
    int side = rand.nextBoolean() ? 1 : -1; // Left (1) or Right (-1)
    int randomY = rand.nextInt(SCREEN_HEIGHT - 2 * SPAWN_MARGIN) + SPAWN_MARGIN;

    Ship newShip = new Ship(randomY, side, SHIP_SPEED);
    return new ConsList<Ship>(newShip, this.ships);
  }

  // Implementation of makeScene to draw everything
  public WorldScene makeScene() {
    WorldScene canvas = new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT);

    // 1. Draw the score/stats text
    String stats = "Bullets left: " + this.bulletsLeft + " | Ships destroyed: "
        + this.shipsDestroyed;
    canvas = canvas.placeImageXY(new TextImage(stats, 13, TEXT_COLOR), 100, 20);

    // 2. Draw all bullets and ships (fold the draw method over the lists)
    // We'll assume a helper method in the list class or a loop handles this
    canvas = this.ships.drawAll(canvas);
    canvas = this.bullets.drawAll(canvas);

    return canvas;
  }

  public NBullets onKeyEvent(String ke) {
    if (ke.equals(" ")) {
      if (this.bulletsLeft > 0) {
        // Create a bullet at bottom-center, moving straight up
        Bullet fired = new Bullet(new Posn(SCREEN_WIDTH / 2, SCREEN_HEIGHT), // starting position
            0, -BULLET_SPEED, // vx is 0, vy is negative (up)
            BULLET_RADIUS_START, BULLET_COLOR, 1); // First generation

        return new NBullets(this.bulletsLeft - 1, this.shipsDestroyed, this.ships,
            new ConsList<Bullet>(fired, this.bullets), this.rand);
      }
    }
    return this;
  }

  public World onTick() {
    return this.moveEverything().handleCollisions().spawnLogic().clearOffScreen();
  }

  NBullets handleCollisions() {
    IList<Ship> remainingShips = this.ships.updateShips(this.bullets);
    IList<Bullet> currentBullets = this.bullets.updateBullets(this.ships);

    // Calculate how many ships were removed for the score
    int hitCount = this.ships.length() - remainingShips.length();

    return new NBullets(this.bulletsLeft, this.shipsDestroyed + hitCount, remainingShips,
        currentBullets, this.rand);
  }

  // Helper methods that return a new NBullets world
  NBullets moveEverything() {
    return new NBullets(this.bulletsLeft, this.shipsDestroyed, this.ships.moveAll(),
        this.bullets.moveAll(), this.rand);
  }

  NBullets clearOffScreen() {
    return new NBullets(this.bulletsLeft, this.shipsDestroyed, this.ships.filterOffScreen(),
        this.bullets.filterOffScreen(), this.rand);
  }

  NBullets spawnLogic() {
    // Spawns 1 ship roughly every 28 ticks (1 second)
    if (this.rand.nextInt(28) == 0) {
      return new NBullets(this.bulletsLeft, this.shipsDestroyed, this.spawnShips(), this.bullets,
          this.rand);
    }
    return this;
  }

  public WorldEnd worldEnds() {
    if (this.bulletsLeft == 0 && this.bullets.isEmpty()) {
      return new WorldEnd(true, this.makeFinalScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // Create a special scene to show when the game is over
  public WorldScene makeFinalScene() {
    WorldScene canvas = this.makeScene(); // Start with the last state of the game
    String gameOverText = "Game Over! Total Ships Destroyed: " + this.shipsDestroyed;

    return canvas.placeImageXY(new TextImage(gameOverText, 20, FontStyle.BOLD, Color.RED),
        SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
  }
}

class ExamplesNBullets {
  void testGame(Tester t) {
    NBullets initialWorld = new NBullets(10); // Start with 10 bullets
    initialWorld.bigBang(500, 300, 1.0 / 28.0);
  }
}