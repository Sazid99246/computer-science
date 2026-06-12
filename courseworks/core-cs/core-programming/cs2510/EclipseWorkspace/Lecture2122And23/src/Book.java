class Book {
  String title;
  Author author;

  Book(String title, Author author) {
    this.title = title;
    this.author = author;
  }

  void capitalizeTitle() {
    this.title = this.title.toUpperCase();
  }
}