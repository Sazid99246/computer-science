import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class ExamplesShapesInJUnit {
  public ExamplesShapesInJUnit() {
  }

  CartPt pt1 = new CartPt(0, 0);
  CartPt pt2 = new CartPt(3, 4);
  CartPt pt3 = new CartPt(7, 1);

  IShape c1 = new Circle(new CartPt(50, 50), 10, "red");
  IShape c2 = new Circle(new CartPt(50, 50), 30, "red");
  IShape c3 = new Circle(new CartPt(30, 100), 30, "blue");

  IShape s1 = new Square(new CartPt(50, 50), 30, "red");
  IShape s2 = new Square(new CartPt(50, 50), 50, "red");
  IShape s3 = new Square(new CartPt(20, 40), 10, "green");

  @Test
  public void testDistToOrigin() {
    assertEquals(0.0, this.pt1.distToOrigin(), 0.001);
    assertEquals(5.0, this.pt2.distToOrigin(), 0.001);
  }

  @Test
  public void testDistTo() {
    assertEquals(5.0, this.pt1.distTo(this.pt2), 0.001);
    assertEquals(5.0, this.pt2.distTo(this.pt3), 0.001);
  }

  @Test
  public void testCircleArea() {
    assertEquals(314.15, this.c1.area(), 0.01);
  }

  @Test
  public void testSquareArea() {
    assertEquals(900.0, this.s1.area(), 0.01);
  }

  @Test
  public void testCircleDistToOrigin() {
    assertEquals(60.71, this.c1.distToOrigin(), 0.01);
    assertEquals(74.40, this.c3.distToOrigin(), 0.01);
  }

  @Test
  public void testSquareDistToOrigin() {
    assertEquals(70.71, this.s1.distToOrigin(), 0.01);
    assertEquals(44.72, this.s3.distToOrigin(), 0.01);
  }

  @Test
  public void testCircleGrow() {
    assertEquals(this.c2, this.c1.grow(20));
  }

  @Test
  public void testSquareGrow() {
    assertEquals(this.s2, this.s1.grow(20));
  }

  @Test
  public void testCircleBiggerThan() {
    assertFalse(this.c1.biggerThan(this.c2));
    assertTrue(this.c2.biggerThan(this.c1));
    assertFalse(this.c1.biggerThan(this.s1));
    assertTrue(this.c1.biggerThan(this.s3));
  }

  @Test
  public void testSquareBiggerThan() {
    assertFalse(this.s1.biggerThan(this.s2));
    assertTrue(this.s2.biggerThan(this.s1));
    assertTrue(this.s1.biggerThan(this.c1));
    assertFalse(this.s3.biggerThan(this.c1));
  }

  @Test
  public void testCircleContains() {
    assertFalse("The contains method for circle does not work properly",
        this.c1.contains(new CartPt(100, 100)));
    assertTrue(this.c2.contains(new CartPt(40, 60)));
  }

  @Test
  public void testSquareContains() {
    assertFalse(this.s1.contains(new CartPt(100, 100)));
    assertTrue(this.s2.contains(new CartPt(55, 60)));
  }

  @Test(timeout = 3000)
  public void testPerformanceTimeout() {
    // Basic structural verification checking execution efficiency constraints
    assertTrue(this.c1.contains(new CartPt(50, 50)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCircleException() {
    IShape c = new Circle(30, 30, -10, "blue");
  }

  @Test
  public void testCircleExceptionAlternativeWay() {
    try {
      IShape c = new Circle(30, 30, -10, "blue");
      fail("This test did not throw any exception");
    }
    catch (IllegalArgumentException e) {
      // Test passed successfully
    }
    catch (Exception e) {
      fail("This test did not throw the correct kind of exception");
    }
  }
}