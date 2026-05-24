import java.awt.Color;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import tester.*;

// to represent a tree structure
interface ITree {
  // renders the ITree into a visual WorldImage anchored at its base pinhole
  WorldImage draw();

  // determines if any twigs (stems/branches) point downward (outside 0 to 180
  // degrees)
  boolean isDrooping();

  // rotates the entire tree structure by an extra offset angle in degrees
  ITree rotate(double extraTheta);

  // combines this tree (left) and another tree (right) with a rotational twist
  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree);

  // computes the total bounding-box width of the tree structure
  double getWidth();

  // helper to find extreme x-coordinates relative to the current structural base
  double getExtremumX(double currentX, double currentTheta, boolean findMax);
}

// to represent a terminal leaf element of the tree
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }

  // renders the leaf as a solid circle with its pinhole positioned at its center
  // base boundary
  public WorldImage draw() {
    WorldImage circle = new CircleImage(this.size, OutlineMode.SOLID, this.color);
    return circle.movePinhole(0, this.size);
  }

  // a leaf cannot point downward as it has no stem components
  public boolean isDrooping() {
    return false;
  }

  // rotating a perfect circle leaves its spatial footprint unchanged
  public ITree rotate(double extraTheta) {
    return this;
  }

  // returns a new twisted branch matrix using this leaf as the left anchor branch
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        otherTree.rotate(rightTheta - 90));
  }

  // standard diameter calculation for width limits
  public double getWidth() {
    return this.size * 2;
  }

  // leaf terminal boundary calculation
  public double getExtremumX(double currentX, double currentTheta, boolean findMax) {
    return findMax ? (currentX + this.size) : (currentX - this.size);
  }
}

// to represent a straight structural extension segment
class Stem implements ITree {
  int length;
  double theta; // in degrees, relative to +x axis
  ITree tree;

  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }

  // renders the line, moves the pinhole to the base, and overlays the child at
  // the tip
  public WorldImage draw() {
    double rad = Math.toRadians(this.theta);
    double dx = this.length * Math.cos(rad);
    double dy = -this.length * Math.sin(rad); // Computer graphics y-axis points down

    WorldImage line = new LineImage(new Posn((int) dx, (int) dy), Color.BLACK);
    // Pin the line's starting point
    line = line.movePinhole(-dx / 2, -dy / 2);

    // Position the rest of the tree at the tip of this stem
    WorldImage childImage = this.tree.draw();
    childImage = childImage.movePinhole(-dx, -dy);

    return new OverlayImage(line, childImage);
  }

  // checks if the stem angle drops below the horizon plane
  public boolean isDrooping() {
    double normalizedAngle = (this.theta % 360 + 360) % 360;
    return (normalizedAngle > 180 && normalizedAngle < 360) || this.tree.isDrooping();
  }

  // adds rotational shift down to children nodes
  public ITree rotate(double extraTheta) {
    return new Stem(this.length, this.theta + extraTheta, this.tree.rotate(extraTheta));
  }

  // combines two structural trees with a twist mapping
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        otherTree.rotate(rightTheta - 90));
  }

  // calculates absolute horizontal profile footprint
  public double getWidth() {
    double minX = this.getExtremumX(0.0, 90.0, false);
    double maxX = this.getExtremumX(0.0, 90.0, true);
    return maxX - minX;
  }

  // computes absolute geometric x layout shifts
  public double getExtremumX(double currentX, double currentTheta, boolean findMax) {
    double rad = Math.toRadians(this.theta);
    double nextX = currentX + (this.length * Math.cos(rad));
    return this.tree.getExtremumX(nextX, this.theta, findMax);
  }
}

// to represent a binary fork division in the tree structure
class Branch implements ITree {
  int leftLength;
  int rightLength;
  double leftTheta; // in degrees, relative to +x axis
  double rightTheta; // in degrees, relative to +x axis
  ITree left;
  ITree right;

  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left,
      ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  // renders binary structural forks cleanly utilizing relative pinhole shifts
  public WorldImage draw() {
    double leftRad = Math.toRadians(this.leftTheta);
    double leftDx = this.leftLength * Math.cos(leftRad);
    double leftDy = -this.leftLength * Math.sin(leftRad);

    WorldImage leftLine = new LineImage(new Posn((int) leftDx, (int) leftDy), Color.BLACK);
    leftLine = leftLine.movePinhole(-leftDx / 2, -leftDy / 2);
    WorldImage leftChild = this.left.draw().movePinhole(-leftDx, -leftDy);
    WorldImage leftCombined = new OverlayImage(leftLine, leftChild);

    double rightRad = Math.toRadians(this.rightTheta);
    double rightDx = this.rightLength * Math.cos(rightRad);
    double rightDy = -this.rightLength * Math.sin(rightRad);

    WorldImage rightLine = new LineImage(new Posn((int) rightDx, (int) rightDy), Color.BLACK);
    rightLine = rightLine.movePinhole(-rightDx / 2, -rightDy / 2);
    WorldImage rightChild = this.right.draw().movePinhole(-rightDx, -rightDy);
    WorldImage rightCombined = new OverlayImage(rightLine, rightChild);

    return new OverlayImage(leftCombined, rightCombined);
  }

  // scans forks for downwards dipping components
  public boolean isDrooping() {
    double normLeft = (this.leftTheta % 360 + 360) % 360;
    double normRight = (this.rightTheta % 360 + 360) % 360;

    return (normLeft > 180 && normLeft < 360) || (normRight > 180 && normRight < 360)
        || this.left.isDrooping() || this.right.isDrooping();
  }

  // recursively applies rotational twist shifts to both paths
  public ITree rotate(double extraTheta) {
    return new Branch(this.leftLength, this.rightLength, this.leftTheta + extraTheta,
        this.rightTheta + extraTheta, this.left.rotate(extraTheta), this.right.rotate(extraTheta));
  }

  // dynamic combination implementation wrapper
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this.rotate(leftTheta - 90),
        otherTree.rotate(rightTheta - 90));
  }

  // evaluates absolute width bounding metrics
  public double getWidth() {
    double minX = this.getExtremumX(0.0, 90.0, false);
    double maxX = this.getExtremumX(0.0, 90.0, true);
    return maxX - minX;
  }

  // tracks dynamic paths to fetch minimum and maximum horizon boundaries
  public double getExtremumX(double currentX, double currentTheta, boolean findMax) {
    double realLeftRad = Math.toRadians(this.leftTheta);
    double realRightRad = Math.toRadians(this.rightTheta);

    double nextLeftX = currentX + (this.leftLength * Math.cos(realLeftRad));
    double nextRightX = currentX + (this.rightLength * Math.cos(realRightRad));

    double leftExtremum = this.left.getExtremumX(nextLeftX, this.leftTheta, findMax);
    double rightExtremum = this.right.getExtremumX(nextRightX, this.rightTheta, findMax);

    if (findMax) {
      return Math.max(currentX, Math.max(leftExtremum, rightExtremum));
    }
    else {
      return Math.min(currentX, Math.min(leftExtremum, rightExtremum));
    }
  }
}

// matching requirements: name must be ExamplesTree
class ExamplesTree {
  ITree leafRed = new Leaf(10, Color.RED);
  ITree leafBlue = new Leaf(15, Color.BLUE);

  ITree simpleStem = new Stem(40, 90, leafRed);
  ITree droopingStem = new Stem(30, 270, leafBlue);

  ITree tree1 = new Branch(30, 30, 135, 40, leafRed, leafBlue);
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));

  // test logic confirming drooping parameters
  boolean testIsDrooping(Tester t) {
    return t.checkExpect(this.leafRed.isDrooping(), false)
        && t.checkExpect(this.simpleStem.isDrooping(), false)
        && t.checkExpect(this.droopingStem.isDrooping(), true)
        && t.checkExpect(this.tree1.isDrooping(), false);
  }

  // evaluates and captures correct physical structural tilt trends
  boolean testCombineStructuralForm(Tester t) {
    ITree combined = this.tree1.combine(40, 50, 150, 30, this.tree2);
    return t.checkExpect(combined.isDrooping(), true); // verified tree1 left tilts past 180 degrees
  }

  // cross-verifies coordinate computation values
  boolean testGetWidthMetrics(Tester t) {
    return t.checkInexact(this.leafRed.getWidth(), 20.0, 0.01)
        && t.checkInexact(this.simpleStem.getWidth(), 20.0, 0.01);
  }

  // launch canvas rendering engine visually as suggested in prompt specifications
  boolean testDrawTreeVisual(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    ITree beautifulTree = this.tree1.combine(60, 60, 140, 40, this.tree2);

    return c.drawScene(s.placeImageXY(beautifulTree.draw(), 250, 400)) && c.show();
  }
}