// Represents a calendar date
class NewDate {
  int day;
  int month;
  int year;

  NewDate(int day, int month, int year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }
}

// Represents a single entry in a runner's training log
class Entry {
  NewDate date;
  double distance; // miles
  int duration; // minutes
  String comment;

  Entry(NewDate date, double distance, int duration, String comment) {
    this.date = date;
    this.distance = distance;
    this.duration = duration;
    this.comment = comment;
  }
}

// The union of classes representing a list of runner's log entries
interface ILog {
  // To compute the distance of the longest run in this log
  // Returns 0 if the log is empty
  double longestRun();
}

// Represents an empty log
class MTLog implements ILog {
  MTLog() {
  }

  public double longestRun() {
    return 0;
  }
}

// Represents a non-empty log containing one entry and the rest of the log
class ConsLog implements ILog {
  Entry first;
  ILog rest;

  ConsLog(Entry first, ILog rest) {
    this.first = first;
    this.rest = rest;
  }

  public double longestRun() {
    return Math.max(this.first.distance, this.rest.longestRun());
  }
}

// --- Examples Class to run Exercise 15.2 ---
class ExamplesILog {
  // Creating the four individual entries as requested in Exercise 15.2
  Entry e1 = new Entry(new NewDate(15, 6, 2004), 15.3, 87, "feeling great");
  Entry e2 = new Entry(new NewDate(16, 6, 2004), 12.8, 84, "feeling good");
  Entry e3 = new Entry(new NewDate(23, 6, 2004), 26.2, 250, "feeling dead");
  Entry e4 = new Entry(new NewDate(28, 6, 2004), 26.2, 150, "feeling very good");

  // Assembling them into a full log (list structure)
  ILog fullLog = new ConsLog(this.e4,
      new ConsLog(this.e3, new ConsLog(this.e2, new ConsLog(this.e1, new MTLog()))));
}