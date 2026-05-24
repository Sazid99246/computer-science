import tester.*;

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }
}

class Date {
  int day;
  int month;
  int year;

  Date(int year, int month, int day) {
    this.year = new Utils().checkRange(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
    this.month = new Utils().checkRange(month, 1, 12, "Invalid month " + Integer.toString(month));
    this.day = new Utils().checkRange(day, 1, 31, "Invalid day: " + Integer.toString(day));
  }

  Date(int month, int day) {
    this(2026, month, day);
  }
}

class ExamplesDates {
  ExamplesDates() {
  }

  // Only keep valid data as fields
  Date d20100228 = new Date(2010, 2, 28);
  Date d20091012 = new Date(2009, 10, 12);

  void testConstructor(Tester t) {
    t.checkExpect(new Date(2010, 2, 28), new Date(2010, 2, 28));
    t.checkConstructorException(new IllegalArgumentException("Invalid year: 53000"), "Date", 53000,
        12, 30);
    t.checkConstructorException(new IllegalArgumentException("Invalid month 15"), "Date", 2020, 15,
        10);
    t.checkConstructorException(new IllegalArgumentException("Invalid day: 42"), "Date", 2020, 10,
        42);
  }

  void testCheckRange(Tester t) {
    Utils u = new Utils();

    t.checkExpect(u.checkRange(10, 1, 20, "Out of range"), 10);

    t.checkException(new IllegalArgumentException("Value is too high!"), u, "checkRange", 50, 1, 10,
        "Value is too high!");
  }
}