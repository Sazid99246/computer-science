import tester.*;

interface IEntertainment {
  // compute the total price of this Entertainment
  double totalPrice();

  // computes the minutes of entertainment of this IEntertainment
  int duration();

  // produce a String that shows the name and price of this IEntertainment
  String format();

  // is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);

  boolean sameMagazine(Magazine that);

  boolean sameTVSeries(TVSeries that);

  boolean samePodcast(Podcast that);
}

abstract class AEntertainment implements IEntertainment {
  String name;
  double price;
  int installments;

  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  // computes the price of a yearly subscription to this AEntertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  // computes the minutes of entertainment of this TVSeries
  public int duration() {
    return this.installments * 50;
  }

  public boolean sameMagazine(Magazine that) {
    return false;
  }

  public boolean sameTVSeries(TVSeries that) {
    return false;
  }

  public boolean samePodcast(Podcast that) {
    return false;
  }

  // produce a String that shows the name and price of this Magazine
  public String format() {
    return this.name + ", " + this.price + ".";
  }
}

class Magazine extends AEntertainment {
  String genre;
  int pages;

  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;
  }

  // computes the minutes of entertainment of this Magazine, (includes all
  // installments)
  @Override
  public int duration() {
    return this.pages * 5;
  }

  public boolean sameEntertainment(IEntertainment that) {
    return that.sameMagazine(this);
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameMagazine(Magazine that) {
    return this.name.equals(that.name) && Math.abs(this.price - that.price) < 0.001
        && this.genre.equals(that.genre) && this.pages == that.pages
        && this.installments == that.installments;
  }
}

class TVSeries extends AEntertainment {
  String corporation;

  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  // is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameTVSeries(this);
  }

  @Override
  public boolean sameTVSeries(TVSeries that) {
    return this.name.equals(that.name) && Math.abs(this.price - that.price) < 0.001
        && this.installments == that.installments && this.corporation.equals(that.corporation);
  }
}

class Podcast extends AEntertainment {
  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  // is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.samePodcast(this);
  }

  @Override
  public boolean samePodcast(Podcast that) {
    return this.name.equals(that.name) && Math.abs(this.price - that.price) < 0.001
        && this.installments == that.installments;
  }
}

class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment vogue = new Magazine("Vogue", 5.00, "Fashion", 100, 12);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment breakingBad = new TVSeries("Breaking Bad", 2.99, 62, "AMC");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);
  IEntertainment daily = new Podcast("The Daily", 0.0, 260);

  // Tests for Total Price (Price * Installments)
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 30.6, .0001)
        && t.checkInexact(this.vogue.totalPrice(), 60.0, .0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 68.25, .0001)
        && t.checkInexact(this.breakingBad.totalPrice(), 185.38, .001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.daily.totalPrice(), 0.0, .001);
  }

  // Tests for Duration
  // Magazines: pages * 5 | Others: installments * 50
  boolean testDuration(Tester t) {
    return t.checkExpect(this.rollingStone.duration(), 300) // 60 * 5
        && t.checkExpect(this.vogue.duration(), 500) // 100 * 5
        && t.checkExpect(this.houseOfCards.duration(), 650) // 13 * 50
        && t.checkExpect(this.breakingBad.duration(), 3100) // 62 * 50
        && t.checkExpect(this.serial.duration(), 400) // 8 * 50
        && t.checkExpect(this.daily.duration(), 13000); // 260 * 50
  }

  // Tests for Format (Name, Price.)
  boolean testFormat(Tester t) {
    return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.")
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.serial.format(), "Serial, 0.0.");
  }

  // Tests for Equality (Double Dispatch)
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(this.rollingStone.sameEntertainment(this.rollingStone), true)
        && t.checkExpect(this.rollingStone.sameEntertainment(this.vogue), false)
        // Check Magazine vs Podcast (Should be false via default in AEntertainment)
        && t.checkExpect(this.rollingStone.sameEntertainment(this.serial), false)
        // Check exact duplicate Podcast
        && t.checkExpect(this.serial.sameEntertainment(new Podcast("Serial", 0.0, 8)), true)
        // Check Podcast vs Podcast with different episodes
        && t.checkExpect(this.serial.sameEntertainment(this.daily), false);
  }
}