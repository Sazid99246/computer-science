import java.util.ArrayList;

class Curriculum {
  ArrayList<Course> courses;

  Curriculum() {
    this.courses = new ArrayList<Course>();
  }

  void addCourse(Course c) {
    this.courses.add(c);
  }

  // Checks if a single course c appears AFTER all its prerequisites in the
  // schedule
  boolean comesAfterPrereqs(ArrayList<Course> schedule, Course c) {
    int courseIndex = schedule.indexOf(c);

    // If the course isn't even in the schedule, it's invalid
    if (courseIndex == -1) {
      return false;
    }

    for (Course prereq : c.prereqs) {
      int prereqIndex = schedule.indexOf(prereq);
      // Prerequisite must exist in the schedule and must appear BEFORE the course
      if (prereqIndex == -1 || prereqIndex >= courseIndex) {
        return false;
      }
    }
    return true;
  }

  // Checks if the entire schedule is valid for every single course contained
  // within it
  boolean validSchedule(ArrayList<Course> schedule) {
    for (Course c : schedule) {
      if (!this.comesAfterPrereqs(schedule, c)) {
        return false;
      }
    }
    return true;
  }

  public ArrayList<Course> topSort() {
    ArrayList<Course> result = new ArrayList<Course>();

    // Process every single course to make sure disconnected graph items are caught
    for (Course c : this.courses) {
      c.process(result);
    }

    return result;
  }
}