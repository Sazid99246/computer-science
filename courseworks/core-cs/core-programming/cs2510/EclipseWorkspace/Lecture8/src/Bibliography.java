import tester.Tester;

// Represents any document in the bibliography ecosystem
interface IDocument {
  // Checks if this document is a Book
  boolean isBook();
  
  // Formats this document into "AuthorLastName, AuthorFirstName. \"Title\"."
  String formatEntry();
  
  // Gets the author's name of this document
  String getAuthor();
  
  // Gathers all direct and indirect documents in this document's bibliography
  ILoDoc gatherAll(ILoDoc seen);
}

// Represents a traditional Book
class Book implements IDocument {
  String authorFirstName;
  String authorLastName;
  String title;
  String publisher;
  ILoDoc bibliography;

  Book(String authorFirstName, String authorLastName, String title, String publisher, ILoDoc bibliography) {
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.title = title;
    this.publisher = publisher;
    this.bibliography = bibliography;
  }

  public boolean isBook() { return true; }

  public String formatEntry() {
    return this.authorLastName + ", " + this.authorFirstName + ". \"" + this.title + "\".";
  }

  public String getAuthor() { return this.authorLastName; }

  // Graph traversal using accumulator 'seen' to avoid cyclic infinite loops
  public ILoDoc gatherAll(ILoDoc seen) {
    if (seen.contains(this)) {
      return seen;
    }
    return this.bibliography.gatherAllHelp(new ConsLoDoc(this, seen));
  }
}

// Represents a Wikipedia Article
class Wiki implements IDocument {
  String authorFirstName;
  String authorLastName;
  String title;
  String url;
  ILoDoc bibliography;

  Wiki(String authorFirstName, String authorLastName, String title, String url, ILoDoc bibliography) {
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.title = title;
    this.url = url;
    this.bibliography = bibliography;
  }

  public boolean isBook() { return false; }

  // Wiki articles won't be formatted since they are excluded, but placeholder logic matches interface
  public String formatEntry() { return ""; }
  
  public String getAuthor() { return this.authorLastName; }

  public ILoDoc gatherAll(ILoDoc seen) {
    if (seen.contains(this)) {
      return seen;
    }
    return this.bibliography.gatherAllHelp(new ConsLoDoc(this, seen));
  }
}

interface ILoDoc {
  // Recursively triggers gathering on every item inside this list
  ILoDoc gatherAllHelp(ILoDoc seen);
  
  // Checks if a specific document physically exists inside this list structure
  boolean contains(IDocument doc);
  
  // Filters out wiki articles, extracting only the valid formatted Book string records
  ILoString generateBookStrings();
}

class ConsLoDoc implements ILoDoc {
  IDocument first;
  ILoDoc rest;

  ConsLoDoc(IDocument first, ILoDoc rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoDoc gatherAllHelp(ILoDoc seen) {
    ILoDoc updatedWithFirst = this.first.gatherAll(seen);
    return this.rest.gatherAllHelp(updatedWithFirst);
  }

  public boolean contains(IDocument doc) {
    // Identity check protects against processing exact duplicates
    return this.first == doc || this.rest.contains(doc);
  }

  public ILoString generateBookStrings() {
    if (this.first.isBook()) {
      return new ConsLoString(this.first.formatEntry(), this.rest.generateBookStrings());
    } else {
      return this.rest.generateBookStrings();
    }
  }
}

class MtLoDoc implements ILoDoc {
  MtLoDoc() {}
  public ILoDoc gatherAllHelp(ILoDoc seen) { return seen; }
  public boolean contains(IDocument doc) { return false; }
  public ILoString generateBookStrings() { return new MtLoString(); }
}

interface ILoString {
  // Sorts alphabetical entries via insertion sort
  ILoString sort();
  
  // Helper for insertion sort
  ILoString insert(String item);
  
  // Strips text duplicates out of our list
  ILoString removeDuplicates();
  
  // Helper to check string existence
  boolean containsStr(String item);
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  public ILoString insert(String item) {
    if (item.compareTo(this.first) <= 0) {
      return new ConsLoString(item, this);
    } else {
      return new ConsLoString(this.first, this.rest.insert(item));
    }
  }

  public ILoString removeDuplicates() {
    if (this.rest.containsStr(this.first)) {
      return this.rest.removeDuplicates();
    } else {
      return new ConsLoString(this.first, this.rest.removeDuplicates());
    }
  }

  public boolean containsStr(String item) {
    return this.first.equals(item) || this.rest.containsStr(item);
  }
}

class MtLoString implements ILoString {
  MtLoString() {}
  public ILoString sort() { return this; }
  public ILoString insert(String item) { return new ConsLoString(item, this); }
  public ILoString removeDuplicates() { return this; }
  public boolean containsStr(String item) { return false; }
}

class BibliographyUtils {
  // The master pipeline algorithm to clean, filter, deduplicate and sort
  public static ILoString createBibliography(IDocument rootDoc) {
    ILoDoc totalGraph = rootDoc.gatherAll(new MtLoDoc());
    ILoString bookEntries = totalGraph.generateBookStrings();
    return bookEntries.removeDuplicates().sort();
  }
}

class ExamplesBibliography {
  // Setting up an interconnected web of bibliographies (including a cyclic look-back reference)
  IDocument cyclicBook = new Book("Jane", "Doe", "Advanced Coding", "O'Reilly", new MtLoDoc());
  
  IDocument wiki2 = new Wiki("John", "Smith", "History of Java", "wiki.org/java", 
      new ConsLoDoc(this.cyclicBook, new MtLoDoc()));
      
  IDocument book2 = new Book("Alan", "Turing", "Computing Machinery", "Oxford", 
      new ConsLoDoc(this.wiki2, new MtLoDoc()));
      
  IDocument book1 = new Book("Jane", "Doe", "Advanced Coding", "O'Reilly", // Intentional copy of cyclicBook values
      new ConsLoDoc(this.book2, new MtLoDoc())); 
      
  IDocument seedWiki = new Wiki("Main", "Researcher", "My Paper", "wiki.org/paper",
      new ConsLoDoc(this.book1, new ConsLoDoc(this.book2, new MtLoDoc())));

  // Injecting the cycle dependency path back into the root loop
  void initCycle() {
     ((Book)this.cyclicBook).bibliography = new ConsLoDoc(this.book1, new MtLoDoc());
  }

  boolean testBibliographyPipeline(Tester t) {
    this.initCycle();
    ILoString finalResult = BibliographyUtils.createBibliography(this.seedWiki);
    
    // Output should be completely alphabetized by last name, and display exactly 2 non-duplicate items
    ILoString expectedList = new ConsLoString("Doe, Jane. \"Advanced Coding\".",
                             new ConsLoString("Turing, Alan. \"Computing Machinery\".", 
            new MtLoString()));

    return t.checkExpect(finalResult, expectedList);
  }
}