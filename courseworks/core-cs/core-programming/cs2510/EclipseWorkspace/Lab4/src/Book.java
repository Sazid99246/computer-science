import tester.Tester;

/**
 * Represents a book that can be borrowed from a library. The system tracks
 * titles, checkout dates, and calculates overdue fines.
 */
interface IBook {
  // Returns the number of days this book is past its due date on the given day.
  // Negative values represent days remaining before the book is due.
  int daysOverdue(int today);

  // Determines if the book is overdue on the given day.
  boolean isOverdue(int today);

  // Calculates the fine in cents if the book is returned on the given day.
  int computeFine(int today);
}

/**
 * Provides shared fields and common logic for all library books.
 */
abstract class ABook implements IBook {
  String title;
  int dayTaken; // Days since Jan 1, 2001

  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  /*
   * * TEMPLATE: Fields: this.title, this.dayTaken Methods: this.isOverdue(int),
   * this.daysOverdue(int), this.computeFine(int)
   */

  // Common logic: a book is overdue if the calculation of days overdue is
  // positive.
  public boolean isOverdue(int today) {
    return this.daysOverdue(today) > 0;
  }
}

/**
 * An intermediate abstraction for books that possess an author field. This
 * prevents duplicating the 'author' field in Book and AudioBook, while keeping
 * RefBook (which has no author) clean.
 */
abstract class AAbsBook extends ABook {
  String author;

  AAbsBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }
}

/**
 * Represents a standard library book with a 14-day loan period.
 */
class Book extends AAbsBook {
  Book(String title, String author, int dayTaken) {
    super(title, author, dayTaken);
  }

  // Regular books are due 14 days after dayTaken.
  public int daysOverdue(int today) {
    return today - (this.dayTaken + 14);
  }

  // Regular books accrue a fine of 10 cents per day overdue.
  public int computeFine(int today) {
    return Math.max(0, this.daysOverdue(today) * 10);
  }
}

/**
 * Represents an audio book with a 14-day loan period and higher overdue rates.
 */
class AudioBook extends AAbsBook {
  AudioBook(String title, String author, int dayTaken) {
    super(title, author, dayTaken);
  }

  // Audio books are due 14 days after dayTaken.
  public int daysOverdue(int today) {
    return today - (this.dayTaken + 14);
  }

  // Audio books accrue a fine of 20 cents per day overdue.
  public int computeFine(int today) {
    return Math.max(0, this.daysOverdue(today) * 20);
  }
}

/**
 * Represents a reference book with a short 2-day loan period and no author.
 */
class RefBook extends ABook {
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }

  // Reference books are due only 2 days after dayTaken.
  public int daysOverdue(int today) {
    return today - (this.dayTaken + 2);
  }

  // Reference books accrue a fine of 10 cents per day overdue.
  public int computeFine(int today) {
    return Math.max(0, this.daysOverdue(today) * 10);
  }
}

class ExamplesBooks {
  // b1: Regular Book - Taken 7780, Due 7794. On day 7800, it is 6 days late.
  IBook b1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 7780);
  // r1: Ref Book - Taken 7799, Due 7801. On day 7800, it has 1 day left.
  IBook r1 = new RefBook("Encyclopedia Britannica", 7799);
  // a1: Audio Book - Taken 7780, Due 7794. On day 7800, it is 6 days late.
  IBook a1 = new AudioBook("Becoming", "Michelle Obama", 7780);
  // a2: Audio Book - Taken 7786, Due 7800. On day 7800, it is exactly on time.
  IBook a2 = new AudioBook("Kitchen Confidential", "Anthony Bourdain", 7786);

  // Tests for calculating days past the due date
  boolean testDaysOverdue(Tester t) {
    return t.checkExpect(b1.daysOverdue(7800), 6) && t.checkExpect(r1.daysOverdue(7800), -1)
        && t.checkExpect(a1.daysOverdue(7800), 6) && t.checkExpect(a2.daysOverdue(7800), 0);
  }

  // Tests for the boolean overdue status
  boolean testIsOverdue(Tester t) {
    return t.checkExpect(b1.isOverdue(7800), true) && t.checkExpect(r1.isOverdue(7800), false)
        && t.checkExpect(a2.isOverdue(7800), false); // Due today != overdue
  }

  // Tests for the monetary fine calculation (in cents)
  boolean testComputeFine(Tester t) {
    return t.checkExpect(b1.computeFine(7800), 60) // 6 days * 10c
        && t.checkExpect(r1.computeFine(7800), 0) // -1 days -> 0c
        && t.checkExpect(a1.computeFine(7800), 120) // 6 days * 20c
        && t.checkExpect(a2.computeFine(7800), 0); // 0 days -> 0c
  }
}