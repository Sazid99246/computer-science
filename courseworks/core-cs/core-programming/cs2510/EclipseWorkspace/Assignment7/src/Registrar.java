import tester.*;
import java.util.function.Predicate;

// ============================================================================
// GENERIC LIST IMPLEMENTATION (IList<T>)
// ============================================================================

interface IList<T> {
  boolean anySatisfy(Predicate<T> pred);

  int countSatisfy(Predicate<T> pred);

  IList<T> add(T element);
}

class MTList<T> implements IList<T> {
  public boolean anySatisfy(Predicate<T> pred) {
    return false;
  }

  public int countSatisfy(Predicate<T> pred) {
    return 0;
  }

  public IList<T> add(T element) {
    return new ConsList<T>(element, this);
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean anySatisfy(Predicate<T> pred) {
    return pred.test(this.first) || this.rest.anySatisfy(pred);
  }

  public int countSatisfy(Predicate<T> pred) {
    return (pred.test(this.first) ? 1 : 0) + this.rest.countSatisfy(pred);
  }

  public IList<T> add(T element) {
    return new ConsList<T>(element, this);
  }
}

// ============================================================================
// CORE REGISTRAR SYSTEM DOMAIN CLASSES
// ============================================================================

// Represents an Instructor teaching courses
class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MTList<Course>();
  }

  // EFFECT: Adds a course to this instructor's taught courses list
  void addCourse(Course c) {
    this.courses = this.courses.add(c);
  }

  // Determines if the given student is in more than one course taught by this
  // Instructor
  boolean dejavu(Student s) {
    return this.courses.countSatisfy(c -> c.students.anySatisfy(student -> student.id == s.id)) > 1;
  }
}

// Represents a Course offering
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MTList<Student>();
    this.prof.addCourse(this); // Maintain referential integrity constraint automatically
  }

  // EFFECT: Adds a student to this course
  void addStudent(Student s) {
    this.students = this.students.add(s);
  }
}

// Represents a Student enrolled in courses
class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MTList<Course>();
  }

  // EFFECT: Mutates both student and course records to enroll the student safely
  void enroll(Course c) {
    this.courses = this.courses.add(c);
    c.addStudent(this);
  }

  // Determines whether this student shares any courses with the given student
  boolean classmates(Student other) {
    return this.courses.anySatisfy(myCourse -> other.courses
        .anySatisfy(otherCourse -> myCourse.name.equals(otherCourse.name)));
  }
}

// ============================================================================
// EXAMPLES AND TEST SUITE FOR REGISTRAR
// ============================================================================

class ExamplesRegistrar {
  Instructor profAshesh;
  Instructor profVahid;

  Course fundies2;
  Course discrete;
  Course ood;
  Course algo;

  Student alice;
  Student bob;
  Student charlie;
  Student dave;
  Student eve;

  // Initializes sample network infrastructure graph
  void initRegistrarData() {
    profAshesh = new Instructor("Ashesh");
    profVahid = new Instructor("Vahid");

    fundies2 = new Course("Fundies 2", profAshesh);
    discrete = new Course("Discrete", profAshesh);
    ood = new Course("OOD", profVahid);
    algo = new Course("Algorithms", profVahid);

    alice = new Student("Alice", 101);
    bob = new Student("Bob", 102);
    charlie = new Student("Charlie", 103);
    dave = new Student("Dave", 104);
    eve = new Student("Eve", 105);

    // Perform Mutations via Enrollments
    alice.enroll(fundies2);
    alice.enroll(discrete);

    bob.enroll(fundies2);
    bob.enroll(ood);

    charlie.enroll(ood);
    charlie.enroll(algo);

    dave.enroll(fundies2);
    eve.enroll(algo);
  }

  public void testEnrollAndConstraints(Tester t) {
    initRegistrarData();
    // Verify cross-reference mappings are successfully written to list structure
    t.checkExpect(fundies2.students.anySatisfy(s -> s.id == alice.id), true);
    t.checkExpect(profAshesh.courses.anySatisfy(c -> c.name.equals("Discrete")), true);
  }

  public void testClassmates(Tester t) {
    initRegistrarData();
    t.checkExpect(alice.classmates(bob), true); // Both share Fundies 2
    t.checkExpect(alice.classmates(charlie), false); // No mutual classes
  }

  public void testDejavu(Tester t) {
    initRegistrarData();
    t.checkExpect(profAshesh.dejavu(alice), true); // Alice takes 2 classes with Ashesh
    t.checkExpect(profVahid.dejavu(bob), false); // Bob only takes 1 class with Vahid
  }
}