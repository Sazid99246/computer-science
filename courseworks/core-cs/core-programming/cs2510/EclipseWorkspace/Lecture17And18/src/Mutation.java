import tester.*;

// ---------------------------------------------------------
// Represents a list of items
// ---------------------------------------------------------
interface IList<T> {
}

// ---------------------------------------------------------
// Represents an empty list
// ---------------------------------------------------------
class MtList<T> implements IList<T> {

  MtList() {
  }
}

// ---------------------------------------------------------
// Represents a non-empty list
// ---------------------------------------------------------
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // Constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

// ---------------------------------------------------------
// Represents an author
// ---------------------------------------------------------
class Author {
  String first;
  String last;
  int yob;

  // All books written by this author
  IList<Book> books;

  // Constructor
  Author(String first, String last, int yob) {
    this.first = first;
    this.last = last;
    this.yob = yob;

    // An author starts with no books
    this.books = new MtList<Book>();
  }

  // Determines whether this author has the same
  // name and year of birth as the given author
  boolean sameAuthor(Author that) {
    return this.first.equals(that.first) && this.last.equals(that.last) && this.yob == that.yob;
  }

  // EFFECT:
  // Adds the given book to this author's list of books.
  // Throws an exception if the book was not written
  // by this author.
  void addBook(Book b) {
    if (!b.author.sameAuthor(this)) {
      throw new RuntimeException("book was not written by this author");
    }

    this.books = new ConsList<Book>(b, this.books);
  }
}

// ---------------------------------------------------------
// Represents a book
// ---------------------------------------------------------
class Book {
  String title;
  int price;
  int quantity;
  Author author;

  // Constructor
  Book(String title, int price, int quantity, Author author) {

    this.title = title;
    this.price = price;
    this.quantity = quantity;
    this.author = author;

    // Automatically update author's list
    this.author.addBook(this);
  }

  // Determines whether two books are the same
  boolean sameBook(Book that) {
    return this.title.equals(that.title) && this.price == that.price
        && this.quantity == that.quantity && this.author.sameAuthor(that.author);
  }
}

// ---------------------------------------------------------
// Examples and Tests
// ---------------------------------------------------------
class ExampleBooks {

  Author knuth;
  Author shakespeare;

  Book taocp1;
  Book taocp2;
  Book comedyOfErrors;

  // EFFECT:
  // Re-initialize all test data
  void initTestConditions() {

    this.knuth = new Author("Donald", "Knuth", 1938);

    this.shakespeare = new Author("William", "Shakespeare", 1564);

    this.taocp1 = new Book("The Art of Computer Programming (volume 1)", 100, 2, this.knuth);

    this.taocp2 = new Book("The Art of Computer Programming (volume 2)", 120, 3, this.knuth);

    this.comedyOfErrors = new Book("The Comedy of Errors", 42, 1, this.shakespeare);
  }

  // Tests that books are automatically added
  // to the author's list
  void testAuthorBooks(Tester t) {
    this.initTestConditions();

    t.checkExpect(this.knuth.books instanceof ConsList, true);

    t.checkExpect(this.shakespeare.books instanceof ConsList, true);
  }

  // Tests sameAuthor
  void testSameAuthor(Tester t) {
    Author a1 = new Author("Donald", "Knuth", 1938);

    Author a2 = new Author("Donald", "Knuth", 1938);

    Author a3 = new Author("Alan", "Turing", 1912);

    t.checkExpect(a1.sameAuthor(a2), true);
    t.checkExpect(a1.sameAuthor(a3), false);
  }

  // Tests sameBook
  void testSameBook(Tester t) {
    this.initTestConditions();

    Book copy = new Book("The Art of Computer Programming (volume 1)", 100, 2,
        new Author("Donald", "Knuth", 1938));

    t.checkExpect(this.taocp1.sameBook(copy), true);
  }

  // Tests exception when book belongs
  // to the wrong author
  void testWrongAuthor(Tester t) {

    Author fakeAuthor = new Author("Fake", "Author", 2000);

    Book fakeBook = new Book("Fake Book", 10, 1, fakeAuthor);

    t.checkException(new RuntimeException("book was not written by this author"), this.knuth,
        "addBook", fakeBook);
  }
}