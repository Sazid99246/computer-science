class ExamplesLecture26 {

  // Test suite verifying object signature bindings and polymorphic equality
  // behaviors
  void testEqualityAndHashing(Tester t) {
    Author a1 = new Author("Bill Nye", 1955);
    Object a2 = new Author("Bill Nye", 1955); // Declared as supertype Object
    Author a3 = new Author("Neil deGrasse Tyson", 1958);

    // Verify that our overriding intercepts standard Object types correctly
    t.checkExpect(a1.equals(a2), true);
    t.checkExpect(a1.equals(a3), false);
    t.checkExpect(a1.hashCode() == a2.hashCode(), true);

    // Shape dynamic double-dispatch checking verifications
    AShape c1 = new Circle(5);
    AShape c2 = new Circle(5);
    AShape s1 = new Square(5);

    t.checkExpect(c1.equals(c2), true);
    t.checkExpect(c1.equals(s1), false); // Properly dispatches types without casting exceptions
    t.checkExpect(c1.hashCode() == c2.hashCode(), true);
  }
}