import tester.Tester;

/*
             +----------------------------+
             | <<interface>>              |
             | IPicture                   |
             +----------------------------+
             | int getWidth()             |
             | int countShapes()          |
             | int comboDepth()           |
             | IPicture mirror()          |
             | String pictureRecipe(int d)|
             +----------------------------+
                           |
       -------------------------------------+
       |                                    |
+----------------------------+       +----------------------------+
| Shape                      |       | Combo                      |
+----------------------------+       +----------------------------+
| String kind                |       | String name                |
| int size                   |       | IOperation operation       |----+
+----------------------------+       +----------------------------+    |
| int getWidth()             |       | int getWidth()             |    |
| int countShapes()          |       | int countShapes()          |    |
| int comboDepth()           |       | int comboDepth()           |    |
| IPicture mirror()          |       | IPicture mirror()          |    |
| String pictureRecipe(int d)|       | String pictureRecipe(int d)|    |
+----------------------------+       +----------------------------+    |
                                                                       |
                                                                       v
                                                       +----------------------------+
                                                       | <<interface>>              |
                                                       | IOperation                 |
                                                       +----------------------------+
                                                       | int getWidth()             |
                                                       | int countShapes()          |
                                                       | int comboDepth()           |
                                                       | IOperation mirror()        |
                                                       | String getFormula(int d)   |
                                                       +----------------------------+
                                                                     |
                                    ----------------------------------------------------
                                    |                                |                 |
                     +----------------------------+    +----------------------------+    +----------------------------+
                     | Scale                      |    | Beside                     |    | Overlay                    |
                     +----------------------------+    +----------------------------+    +----------------------------+
                     | IPicture picture           |    | IPicture picture1          |    | IPicture top               |
                     +----------------------------+    | IPicture picture2          |    | IPicture bottom            |
                     | int getWidth()             |    +----------------------------+    +----------------------------+
                     | int countShapes()          |    | int getWidth()             |    | int getWidth()             |
                     | int comboDepth()           |    | int countShapes()          |    | int countShapes()          |
                     | IOperation mirror()        |    | int comboDepth()           |    | int comboDepth()           |
                     | String getFormula(int d)   |    | IOperation mirror()        |    | IOperation mirror()        |
                     +----------------------------+    | String getFormula(int d)   |    | String getFormula(int d)   |
                                                       +----------------------------+    +----------------------------+
*/

/**
 * Represents any visual picture element. Provides capabilities to analyze
 * structural properties, apply geometric reflections, and reconstruct
 * construction recipes.
 */
interface IPicture {
  // Computes the total width of the rendering bounding framework
  int getWidth();

  // tallies the absolute count of atomic shapes embedded in this picture
  int countShapes();

  // Determines the maximum depth of nested combination operations
  int comboDepth();

  // Produces a geometric horizontal reflection copy of this picture hierarchy
  IPicture mirror();

  // Generates a structural formula string expanded up to the specified nesting
  // depth limit
  String pictureRecipe(int depth);
}

/**
 * Represents an atomic, single geometric element in an image tree. Forms the
 * base-case/leaves of the composite picture structure.
 */
class Shape implements IPicture {
  String kind; // e.g., "circle", "square"
  int size; // Assuming width and height are identical

  // constructor
  Shape(String kind, int size) {
    this.kind = kind;
    this.size = size;
  }

  // Returns the primitive base unit width
  public int getWidth() {
    return this.size;
  }

  // An atomic shape counts inherently as exactly 1 element
  public int countShapes() {
    return 1;
  }

  // Primitives do not contain any nested combination operations
  public int comboDepth() {
    return 0;
  }

  // Refection on a primitive element yields the identical instance
  public IPicture mirror() {
    return this;
  }

  // Reaching a primitive element terminates expansion, printing its raw shape
  // identifier
  public String pictureRecipe(int depth) {
    return this.kind;
  }
}

/**
 * Represents a composite image container combining child elements together.
 * Delegates layout physics calculations downwards onto an underlying abstract
 * operation rule.
 */
class Combo implements IPicture {
  String name; // The description label representing this unique combo state
  IOperation operation; // The explicit modification or positioning strategy rule

  // constructor
  Combo(String name, IOperation operation) {
    this.name = name;
    this.operation = operation;
  }

  // Delegates bounding measurements downwards to look inside the layout physics
  // rule
  public int getWidth() {
    return this.operation.getWidth();
  }

  // Cascades counting downward throughout the current structural operation branch
  public int countShapes() {
    return this.operation.countShapes();
  }

  // Registers the presence of an operation layer and tallies down the recursive
  // paths
  public int comboDepth() {
    return 1 + this.operation.comboDepth();
  }

  // Traverses internal structures to trigger reflections while retaining
  // identifying descriptors
  public IPicture mirror() {
    return new Combo(this.name, this.operation.mirror());
  }

  // Coordinates recursive string formulas; returns the container alias if the
  // depth limit has
  // run out, otherwise steps deeper into the formatting blueprint.
  public String pictureRecipe(int depth) {
    if (depth <= 0) {
      return this.name;
    }
    else {
      return this.operation.getFormula(depth - 1);
    }
  }
}

/**
 * Represents a manipulation or alignment process applied to image groups.
 * Drives polymorphic double dispatch handling layout mechanics without type
 * casting.
 */
interface IOperation {
  // Computes the combined element boundary width
  int getWidth();

  // Aggregates sub-element quantities across current structural pathways
  int countShapes();

  // Tallies internal nested operational offsets across current branches
  int comboDepth();

  // Applies deep structural reflection maps to contained sub-components
  IOperation mirror();

  // Resolves the specialized functional layout recipe expression string
  String getFormula(int depth);
}

/**
 * Represents a resizing operation that doubles the scale of a single picture.
 */
class Scale implements IOperation {
  IPicture picture; // The target sub-image being altered

  // constructor
  Scale(IPicture picture) {
    this.picture = picture;
  }

  // Scales the total nested space limits by a factor of 2
  public int getWidth() {
    return 2 * this.picture.getWidth();
  }

  // Forwards counting directly downstream to inspect the internal sub-tree
  // elements
  public int countShapes() {
    return this.picture.countShapes();
  }

  // Simply traces depth layers deeper downwards throughout the single sub-tree
  // branch
  public int comboDepth() {
    return this.picture.comboDepth();
  }

  // Cascades geometric structural tracking recursively onto its referenced item
  public IOperation mirror() {
    return new Scale(this.picture.mirror());
  }

  // Prints the parameterized linear functional container formatting string code
  public String getFormula(int depth) {
    return "scale(" + this.picture.pictureRecipe(depth) + ")";
  }
}

/**
 * Represents a horizontal alignment grouping two sub-pictures side-by-side.
 */
class Beside implements IOperation {
  IPicture picture1; // The item positioned on the left side
  IPicture picture2; // The item positioned on the right side

  // constructor
  Beside(IPicture picture1, IPicture picture2) {
    this.picture1 = picture1;
    this.picture2 = picture2;
  }

  // Combines horizontal widths additively across both alignment segments
  public int getWidth() {
    return this.picture1.getWidth() + this.picture2.getWidth();
  }

  // Pools active internal content tallies across both structural directions
  public int countShapes() {
    return this.picture1.countShapes() + this.picture2.countShapes();
  }

  // Extracts the maximum deep nested offset value discovered among its layout
  // splits
  public int comboDepth() {
    return Math.max(this.picture1.comboDepth(), this.picture2.comboDepth());
  }

  // Inverts left and right orientation layers dynamically while recurring
  // downstream
  public IOperation mirror() {
    return new Beside(this.picture2.mirror(), this.picture1.mirror());
  }

  // Prints the dual-parameter side-by-side functional layout alignment expression
  public String getFormula(int depth) {
    return "beside(" + this.picture1.pictureRecipe(depth) + ", "
        + this.picture2.pictureRecipe(depth) + ")";
  }
}

/**
 * Represents a layered center-aligned stacking combination of two pictures.
 */
class Overlay implements IOperation {
  IPicture top; // The foreground layered image element
  IPicture bottom; // The background layered image element

  // constructor
  Overlay(IPicture top, IPicture bottom) {
    this.top = top;
    this.bottom = bottom;
  }

  // Detects and preserves whichever horizontal space boundary layer is wider
  public int getWidth() {
    return Math.max(this.top.getWidth(), this.bottom.getWidth());
  }

  // Aggregates sub-elements additively spanning the vertical stack layers
  public int countShapes() {
    return this.top.countShapes() + this.bottom.countShapes();
  }

  // Extracts the maximum deep nested offset value discovered among its layout
  // layers
  public int comboDepth() {
    return Math.max(this.top.comboDepth(), this.bottom.comboDepth());
  }

  // Cascades operational reflection instructions recursively downward to lower
  // branches
  public IOperation mirror() {
    return new Overlay(this.top.mirror(), this.bottom.mirror());
  }

  // Prints the dual-parameter stacking overlay functional layout expression
  public String getFormula(int depth) {
    return "overlay(" + this.top.pictureRecipe(depth) + ", " + this.bottom.pictureRecipe(depth)
        + ")";
  }
}

/**
 * Collection of test sets and data instances verified using the Tester
 * architecture framework.
 */
class ExamplesPicture {
  ExamplesPicture() {
  }

  // --- Core Assignment Elements ---
  IPicture circle = new Shape("circle", 20);
  IPicture square = new Shape("square", 30);
  IPicture bigCircle = new Combo("big circle", new Scale(this.circle));
  IPicture squareOnCircle = new Combo("square on circle", new Overlay(this.square, this.bigCircle));
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
      new Beside(this.squareOnCircle, this.squareOnCircle));

  // --- Custom Auxiliary Extension Examples ---
  IPicture tinySquare = new Shape("square", 5);
  IPicture scaledSquare = new Combo("scaled square", new Scale(this.tinySquare));
  IPicture sideBySideShapes = new Combo("circle beside square",
      new Beside(this.circle, this.square));
  IPicture stackedShapes = new Combo("circle on square", new Overlay(this.circle, this.square));

  // Evaluates width measurement boundaries across simple and layered components
  boolean testGetWidth(Tester t) {
    return t.checkExpect(this.circle.getWidth(), 20) && t.checkExpect(this.square.getWidth(), 30)
        && t.checkExpect(this.bigCircle.getWidth(), 40) // 20 * 2
        && t.checkExpect(this.squareOnCircle.getWidth(), 40) // max(30, 40)
        && t.checkExpect(this.doubledSquareOnCircle.getWidth(), 80); // 40 + 40
  }

  // Evaluates quantity computation rules traversing composite image paths
  boolean testCountShapes(Tester t) {
    return t.checkExpect(this.circle.countShapes(), 1)
        && t.checkExpect(this.bigCircle.countShapes(), 1)
        && t.checkExpect(this.squareOnCircle.countShapes(), 2) // square + circle
        && t.checkExpect(this.doubledSquareOnCircle.countShapes(), 4); // 2 * (1 + 1)
  }

  // Evaluates deep layer accumulation tracking calculations
  boolean testComboDepth(Tester t) {
    return t.checkExpect(this.circle.comboDepth(), 0)
        && t.checkExpect(this.bigCircle.comboDepth(), 1)
        && t.checkExpect(this.squareOnCircle.comboDepth(), 2)
        && t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3);
  }

  // Evaluates tree node transformation inversion logic across beside operations
  boolean testMirror(Tester t) {
    return t.checkExpect(this.squareOnCircle.mirror(), this.squareOnCircle)
        && t.checkExpect(this.doubledSquareOnCircle.mirror(), new Combo("doubled square on circle",
            new Beside(this.squareOnCircle, this.squareOnCircle)));
  }

  // Evaluates formula parsing and string resolution behaviors across targeted
  // depth barriers
  boolean testPictureRecipe(Tester t) {
    return t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(0), "doubled square on circle")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2),
            "beside(overlay(square, big circle), overlay(square, big circle))")
        && t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(3),
            "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
  }
}