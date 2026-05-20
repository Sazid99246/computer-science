/*
               +--------------------------------------+
               | ILoBook                              |<----------------------+
               +--------------------------------------+                       |
               | double totalPrice()                  |                       |
               | int count()                          |                       |
               | ILoBook allBooksBefore(int year)     |                       |
               | ILoBook sortByPrice()                |                       |
               | ILoBook sortByTitle()                |                       |
               | ILoBook insert(Book b)               |                       |
               | ILoBook insertByTitle(Book b)        |                       |
               | ILoBook cheaperThan(int price)       |                       |
               +--------------------------------------+                       |
                                  |                                           |
                                 / \                                          |
                                 ---                                          |
                                  |                                           |
                    -------------------------------                            |
                    |                             |                            |
+--------------------------------------+   +--------------------------------------+ 
| MtLoBook                             |   | ConsLoBook                           |
+--------------------------------------+   +--------------------------------------+
+--------------------------------------+   | Book first                           |
| double totalPrice()                  |   | ILoBook rest                         |
| int count()                          |   +--------------------------------------+
| ILoBook allBooksBefore(int year)     |   | double totalPrice()                  |
| ILoBook sortByPrice()                |   | int count()                          |
| ILoBook sortByTitle()                |   | ILoBook allBooksBefore(int year)     |
| ILoBook insert(Book b)               |   | ILoBook sortByPrice()                |
| ILoBook insertByTitle(Book b)        |   | ILoBook sortByTitle()                |
| ILoBook cheaperThan(int price)       |   | ILoBook insert(Book b)               |
+--------------------------------------+   | ILoBook insertByTitle(Book b)        |
                                           | ILoBook cheaperThan(int price)       |
                                           +--------------------------------------+
                                                              |
                                                              v
                                       +--------------------------------------+
                                       | Book                                 |
                                       +--------------------------------------+
                                       | String name                          |
                                       | String author                        |
                                       | double price                         |
                                       | int year                             |
                                       +--------------------------------------+
                                       | double getPrice()                    |
                                       | double discount(double rate)         |
                                       | Book discountedBook(double rate)     |
                                       | boolean cheaperThan(Book that)       |
                                       | boolean titleBefore(Book that)       |
                                       +--------------------------------------+
*/

import tester.*;

class Book {
  String name;
  String author;
  double price;
  int year;

  Book(String name, String author, double price, int year) {
    this.name = name;
    this.author = author;
    this.price = price;
    this.year = year;
  }

  double getPrice() {
    return this.price;
  }

  // to return the discounted price of this book given the discount rate
  double discount(double rate) {
    return this.price - (rate * this.price);
  }

  // to return a new book with the same author and name as this book,
  // but with price discounted at the given rate
  Book discountedBook(double rate) {
    return new Book(this.name, this.author, this.discount(rate), this.year);
  }

  // is the price of this book cheaper than the price of the given book?
  boolean cheaperThan(Book that) {
    return this.price < that.price;
  }

  // does this book title come before the given book title?
  boolean titleBefore(Book that) {
    return this.name.compareTo(that.name) < 0;
  }
}

/*
 * GOAL:
 * Represent a bunch of books, and be able to compute
 * - their total price
 * - how many books we have
 * - all the books published before the given year
 * - sorted lists of books
 */

/*
 * A list of books is one of
 * empty
 * (cons book list-of-books)
 */

interface ILoBook {

  // to compute the total price of all books in this list of books
  double totalPrice();

  // to count how many books are in this list of books
  int count();

  // to return a list of all the books in this list of books
  // published before the given year
  ILoBook allBooksBefore(int year);

  // to sort books increasing by price
  ILoBook sortByPrice();

  // to sort books alphabetically by title
  ILoBook sortByTitle();

  // to insert a book into a price-sorted list
  ILoBook insert(Book b);

  // to insert a book into a title-sorted list
  ILoBook insertByTitle(Book b);

  // all books cheaper than the given price
  ILoBook cheaperThan(int price);
}

class MtLoBook implements ILoBook {

  MtLoBook() {
    // nothing to do!
  }

  public double totalPrice() {
    return 0;
  }

  public int count() {
    return 0;
  }

  public ILoBook allBooksBefore(int year) {
    return this;
  }

  public ILoBook sortByPrice() {
    return this;
  }

  public ILoBook sortByTitle() {
    return this;
  }

  public ILoBook insert(Book b) {
    return new ConsLoBook(b, this);
  }

  public ILoBook insertByTitle(Book b) {
    return new ConsLoBook(b, this);
  }

  public ILoBook cheaperThan(int price) {
    return this;
  }
}

class ConsLoBook implements ILoBook {
  Book first;
  ILoBook rest;

  ConsLoBook(Book first, ILoBook rest) {
    this.first = first;
    this.rest = rest;
  }

  public double totalPrice() {
    return this.first.getPrice() + this.rest.totalPrice();
  }

  public int count() {
    return 1 + this.rest.count();
  }

  public ILoBook allBooksBefore(int year) {
    if (this.first.year < year) {
      return new ConsLoBook(this.first,
          this.rest.allBooksBefore(year));
    }
    else {
      return this.rest.allBooksBefore(year);
    }
  }

  // sort by price
  public ILoBook sortByPrice() {
    return this.rest.sortByPrice().insert(this.first);
  }

  // sort by title
  public ILoBook sortByTitle() {
    return this.rest.sortByTitle().insertByTitle(this.first);
  }

  // insert into a price-sorted list
  public ILoBook insert(Book b) {
    if (this.first.cheaperThan(b)) {
      return new ConsLoBook(this.first, this.rest.insert(b));
    }
    else {
      return new ConsLoBook(b, this);
    }
  }

  // insert into a title-sorted list
  public ILoBook insertByTitle(Book b) {
    if (this.first.titleBefore(b)) {
      return new ConsLoBook(this.first,
          this.rest.insertByTitle(b));
    }
    else {
      return new ConsLoBook(b, this);
    }
  }

  public ILoBook cheaperThan(int price) {
    if (this.first.price < price) {
      return new ConsLoBook(this.first,
          this.rest.cheaperThan(price));
    }
    else {
      return this.rest.cheaperThan(price);
    }
  }
}

class ILoBooksExample {

  ILoBooksExample() { }

  Book hp1 = new Book("hp1", "JKR", 20, 1997);
  Book hp2 = new Book("hp2", "JKR", 30, 1999);
  Book hp3 = new Book("hp3", "JKR", 40, 2001);
  Book hp4 = new Book("hp4", "JKR", 10, 1995);

  Book apple = new Book("Apple", "A", 50, 2000);
  Book zoo = new Book("Zoo", "B", 15, 1990);
  Book moon = new Book("Moon", "C", 25, 2010);

  ILoBook emptyList = new MtLoBook();

  ILoBook hpList1 =
      new ConsLoBook(hp1, new MtLoBook());

  ILoBook hpList3 =
      new ConsLoBook(hp1,
          new ConsLoBook(hp2,
              new ConsLoBook(hp3,
                  new MtLoBook())));

  ILoBook unsortedList =
      new ConsLoBook(hp2,
          new ConsLoBook(hp3,
              new ConsLoBook(hp4,
                  new ConsLoBook(hp1,
                      new MtLoBook()))));

  ILoBook titleList =
      new ConsLoBook(zoo,
          new ConsLoBook(moon,
              new ConsLoBook(apple,
                  new MtLoBook())));

  // ---------------------------------------------------
  // Book method tests

  boolean testGetPrice(Tester t) {
    return t.checkExpect(hp1.getPrice(), 20.0)
        && t.checkExpect(hp3.getPrice(), 40.0);
  }

  boolean testDiscount(Tester t) {
    return t.checkExpect(hp1.discount(0.5), 10.0)
        && t.checkExpect(hp2.discount(0.25), 22.5)
        && t.checkExpect(hp3.discount(0.0), 40.0);
  }

  boolean testDiscountedBook(Tester t) {
    return t.checkExpect(
        hp1.discountedBook(0.5),
        new Book("hp1", "JKR", 10.0, 1997))
        &&

        t.checkExpect(
            hp2.discountedBook(0.1),
            new Book("hp2", "JKR", 27.0, 1999));
  }

  boolean testBookCheaperThan(Tester t) {
    return t.checkExpect(hp1.cheaperThan(hp2), true)
        && t.checkExpect(hp3.cheaperThan(hp1), false)
        && t.checkExpect(hp2.cheaperThan(hp2), false);
  }

  // NEW TESTS

  boolean testTitleBefore(Tester t) {
    return t.checkExpect(apple.titleBefore(moon), true)
        && t.checkExpect(zoo.titleBefore(apple), false)
        && t.checkExpect(moon.titleBefore(zoo), true);
  }

  // ---------------------------------------------------
  // ILoBook method tests

  boolean testILoBookCount(Tester t) {
    return t.checkExpect(hpList3.count(), 3)
        && t.checkExpect(emptyList.count(), 0)
        && t.checkExpect(hpList1.count(), 1);
  }

  boolean testILoBookTotalPrice(Tester t) {
    return t.checkExpect(hpList3.totalPrice(), 90.0)
        && t.checkExpect(emptyList.totalPrice(), 0.0)
        && t.checkExpect(hpList1.totalPrice(), 20.0);
  }

  boolean testILoBookCheaperThan(Tester t) {
    return t.checkExpect(
        emptyList.cheaperThan(10),
        new MtLoBook())

        &&

        t.checkExpect(
            hpList3.cheaperThan(40),
            new ConsLoBook(hp1,
                new ConsLoBook(hp2,
                    new MtLoBook())))

        &&

        t.checkExpect(
            hpList1.cheaperThan(10),
            new MtLoBook());
  }

  boolean testAllBooksBefore(Tester t) {
    return t.checkExpect(
        hpList3.allBooksBefore(2000),
        new ConsLoBook(hp1,
            new ConsLoBook(hp2,
                new MtLoBook())))

        &&

        t.checkExpect(
            hpList3.allBooksBefore(1990),
            new MtLoBook())

        &&

        t.checkExpect(
            emptyList.allBooksBefore(2020),
            new MtLoBook());
  }

  boolean testInsert(Tester t) {

    ILoBook sortedList =
        new ConsLoBook(hp1,
            new ConsLoBook(hp2,
                new ConsLoBook(hp3,
                    new MtLoBook())));

    return t.checkExpect(
        sortedList.insert(hp4),

        new ConsLoBook(hp4,
            new ConsLoBook(hp1,
                new ConsLoBook(hp2,
                    new ConsLoBook(hp3,
                        new MtLoBook())))))

        &&

        t.checkExpect(
            emptyList.insert(hp1),
            new ConsLoBook(hp1, new MtLoBook()));
  }

  boolean testSortByPrice(Tester t) {

    ILoBook sortedResult =
        new ConsLoBook(hp4,
            new ConsLoBook(hp1,
                new ConsLoBook(hp2,
                    new ConsLoBook(hp3,
                        new MtLoBook()))));

    return t.checkExpect(
        unsortedList.sortByPrice(),
        sortedResult)

        &&

        t.checkExpect(
            emptyList.sortByPrice(),
            new MtLoBook());
  }

  // NEW TESTS

  boolean testInsertByTitle(Tester t) {

    ILoBook sortedTitles =
        new ConsLoBook(apple,
            new ConsLoBook(moon,
                new ConsLoBook(zoo,
                    new MtLoBook())));

    Book hello = new Book("Hello", "D", 100, 2020);

    return t.checkExpect(

        sortedTitles.insertByTitle(hello),

        new ConsLoBook(apple,
            new ConsLoBook(hello,
                new ConsLoBook(moon,
                    new ConsLoBook(zoo,
                        new MtLoBook())))))

        &&

        t.checkExpect(
            emptyList.insertByTitle(apple),
            new ConsLoBook(apple, new MtLoBook()));
  }

  boolean testSortByTitle(Tester t) {

    ILoBook sortedTitles =
        new ConsLoBook(apple,
            new ConsLoBook(moon,
                new ConsLoBook(zoo,
                    new MtLoBook())));

    return t.checkExpect(
        titleList.sortByTitle(),
        sortedTitles)

        &&

        t.checkExpect(
            emptyList.sortByTitle(),
            new MtLoBook());
  }
}