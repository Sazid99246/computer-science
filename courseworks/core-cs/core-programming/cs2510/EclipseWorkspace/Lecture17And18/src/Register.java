import tester.*;

// ---------------------------------------------------------
// Generic List Interface
// ---------------------------------------------------------

interface IList<T> {
}

// Empty List
class MtList<T> implements IList<T> {
  MtList() {
  }
}

// Non-empty List
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

// ---------------------------------------------------------
// Student
// ---------------------------------------------------------

class Student {
  String firstName;
  String lastName;

  // courses this student is taking
  IList<Course> courses;

  Student(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.courses = new MtList<Course>();
  }

  // EFFECT:
  // adds the given course to this student's schedule
  void addCourse(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }
}

// ---------------------------------------------------------
// Instructor
// ---------------------------------------------------------

class Instructor {
  String firstName;
  String lastName;

  // courses taught by this instructor
  IList<Course> courses;

  Instructor(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.courses = new MtList<Course>();
  }

  // EFFECT:
  // adds a course to this instructor's course list
  void addCourse(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }
}

// ---------------------------------------------------------
// Course
// ---------------------------------------------------------

class Course {
  String department;
  int courseNumber;

  Instructor instructor;

  // enrolled students
  IList<Student> students;

  Course(String department, int courseNumber, Instructor instructor) {

    this.department = department;
    this.courseNumber = courseNumber;
    this.instructor = instructor;
    this.students = new MtList<Student>();

    // automatically add this course to instructor
    this.instructor.addCourse(this);
  }

  // EFFECT:
  // enrolls the given student in this course
  void enrollStudent(Student s) {
    this.students = new ConsList<Student>(s, this.students);

    // keep student data consistent
    s.addCourse(this);
  }
}

// ---------------------------------------------------------
// Examples
// ---------------------------------------------------------

class ExamplesRegistrar {

  // Professors
  Instructor profSmith;
  Instructor profJohnson;

  // Courses
  Course cs2510;
  Course cs3500;
  Course math1341;

  // Students
  Student alice;
  Student bob;
  Student charlie;
  Student diana;

  // EFFECT:
  // initializes all examples
  void initData() {

    // Instructors
    this.profSmith = new Instructor("John", "Smith");

    this.profJohnson = new Instructor("Sarah", "Johnson");

    // Courses
    this.cs2510 = new Course("CS", 2510, this.profSmith);

    this.cs3500 = new Course("CS", 3500, this.profSmith);

    this.math1341 = new Course("MATH", 1341, this.profJohnson);

    // Students
    this.alice = new Student("Alice", "Brown");

    this.bob = new Student("Bob", "Davis");

    this.charlie = new Student("Charlie", "Wilson");

    this.diana = new Student("Diana", "Taylor");

    // Enrollments

    // Alice takes two classes
    this.cs2510.enrollStudent(this.alice);
    this.math1341.enrollStudent(this.alice);

    // Bob takes one class
    this.cs2510.enrollStudent(this.bob);

    // Charlie takes two classes
    this.cs3500.enrollStudent(this.charlie);
    this.math1341.enrollStudent(this.charlie);

    // Diana takes one class
    this.cs3500.enrollStudent(this.diana);
  }

  // Simple test
  void testRegistrar(Tester t) {
    this.initData();

    t.checkExpect(this.profSmith.courses instanceof ConsList, true);

    t.checkExpect(this.alice.courses instanceof ConsList, true);

    t.checkExpect(this.cs2510.students instanceof ConsList, true);
  }
}