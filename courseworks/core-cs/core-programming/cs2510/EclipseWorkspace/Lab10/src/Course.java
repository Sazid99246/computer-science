import java.util.ArrayList;

class Course {
  String name;
  ArrayList<Course> prereqs;

  Course(String name) {
    this.name = name;
    this.prereqs = new ArrayList<Course>();
  }

  void addPrereq(Course c) {
    this.prereqs.add(c);
  }

  // Post-order DFS traversal for topological sort
  void process(ArrayList<Course> processed) {
    // If this course has already been processed and added, do nothing
    if (processed.contains(this)) {
      return;
    }

    // Recursively process all prerequisites first
    for (Course prereq : this.prereqs) {
      prereq.process(processed);
    }

    // Add this course to the result list after its prerequisites are handled
    processed.add(this);
  }
}