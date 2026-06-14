// 26.5.1 & 26.5.2 Custom overriding for Book
class Book {
  Author author;
  String title;
  int year;

  Book(Author author, String title, int year) {
    this.author = author;
    this.title = title;
    this.year = year;
  }

  // Fields used in hashCode must be a subset of fields evaluated in equals
  @Override
  public int hashCode() {
    return this.author.hashCode() * 10000 + this.year;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Book)) {
      return false;
    }
    Book that = (Book) other;
    return this.author.equals(that.author) && this.year == that.year
        && this.title.equals(that.title);
  }
}

// Custom overriding for Author
class Author {
  String name;
  int yob;

  Author(String name, int yob) {
    this.name = name;
    this.yob = yob;
  }

  // Broken-up recursive chain: skips mapping the Book field to prevent
  // StackOverflowError
  @Override
  public int hashCode() {
    return this.name.hashCode() * 10000 + this.yob;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Author)) {
      return false;
    }
    Author that = (Author) other;
    return this.name.equals(that.name) && this.yob == that.yob;
  }
}