import tester.Tester;

class Bagel {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  // Main Constructor: The "Validator"
  // All other constructors chain to this one to ensure rules are enforced.
  Bagel(double flour, double water, double yeast, double salt, double malt) {
    if (Math.abs(flour - water) > 0.001) {
      throw new IllegalArgumentException("Flour must equal water!");
    }
    if (Math.abs(yeast - malt) > 0.001) {
      throw new IllegalArgumentException("Yeast must equal malt!");
    }
    if (Math.abs((salt + yeast) - (flour / 20.0)) > 0.001) {
      throw new IllegalArgumentException("Incorrect salt and yeast ratio!");
    }

    this.flour = flour;
    this.water = water;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = malt;
  }

  // Constructor 2: Shortcuts weights by calculating ratios automatically
  Bagel(double flour, double yeast) {
    this(flour, flour, yeast, (flour / 20.0) - yeast, yeast);
  }

  // Constructor 3: Converts volumes to weights and then validates
  Bagel(double fCups, double wCups, double yTsp, double sTsp, double mTsp, boolean isVol) {
    this(fCups * 4.25, wCups * 8.0, (yTsp / 48.0) * 5.0, (sTsp / 48.0) * 10.0,
        (mTsp / 48.0) * 11.0);
  }

  /**
   * Compares this recipe to another for equality within a 0.001 ounce tolerance.
   */
  boolean sameRecipe(Bagel other) {
    return Math.abs(this.flour - other.flour) < 0.001 && Math.abs(this.water - other.water) < 0.001
        && Math.abs(this.yeast - other.yeast) < 0.001 && Math.abs(this.salt - other.salt) < 0.001
        && Math.abs(this.malt - other.malt) < 0.001;
  }
}

class ExamplesBagel {
  // A perfect bagel with 20oz flour/water and 1oz yeast/malt
  // Salt should be (20/20) - 1 = 0
  Bagel b1 = new Bagel(20.0, 20.0, 1.0, 0.0, 1.0);
  
  // A bagel made using the shortcut constructor
  // flour = 40, yeast = 1.0. 
  // Should result in water = 40, malt = 1.0, salt = (40/20) - 1 = 1.0
  Bagel b2 = new Bagel(40.0, 1.0);
  Bagel b2Expected = new Bagel(40.0, 40.0, 1.0, 1.0, 1.0);

  // A bagel from volumes (Values chosen to satisfy weight ratios)
  // Water: 4.25 cups * 8.0 = 34oz
  // Flour: 8.0 cups * 4.25 = 34oz (Match!)
  // Yeast: 48 tsp / 48 * 5 = 5oz
  // Malt: 21.81 tsp / 48 * 11 = ~5oz (Match!)
  // Salt: 5.76 tsp / 48 * 10 = 1.2oz. (Total salt+yeast = 6.2. Flour 34/20 = 1.7. Fail!)
  // Note: Volume-based perfect bagels are mathematically very rare!

  // Test equality method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(b1.sameRecipe(b1), true)
        && t.checkExpect(b1.sameRecipe(b2), false)
        && t.checkExpect(b2.sameRecipe(b2Expected), true);
  }

  // Test main constructor exceptions
  boolean testConstructorExceptions(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Flour must equal water!"),
        "Bagel", 20.0, 15.0, 1.0, 0.0, 1.0)
        && t.checkConstructorException(
        new IllegalArgumentException("Yeast must equal malt!"),
        "Bagel", 20.0, 20.0, 1.0, 0.0, 2.0);
  }
  
  // Test volume constructor failures
  boolean testVolumeFail(Tester t) {
    // 1 cup of flour (4.25oz) and 1 cup of water (8.0oz) should fail
    return t.checkConstructorException(
        new IllegalArgumentException("Flour must equal water!"),
        "Bagel", 1.0, 1.0, 48.0, 0.0, 48.0, true);
  }
}