interface IPred<T> {
  boolean apply(T t);
}

interface IComparator<T> {
  int compare(T t1, T t2);
}

class BookByAuthor implements IPred<Book> {
  public boolean apply(Book b) {
    return b.author.equals("JKR");
  };
}

class RunnerIsInFirst50 implements IPred<Runner> {
  public boolean apply(Runner r) {
    return r.pos <= 50;
  }
}

interface IFunc<A, R> {
  R apply(A arg);
}

interface IList<T> {
  IList<T> filter(IPred<T> pred);

  IList<T> sort(IComparator<T> comp);

  int length();

  IList<T> insert(T element, IComparator<T> comp);

  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U, U> func, U base);
}

class MtList<T> implements IList<T> {
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  public IList<T> sort(IComparator<T> comp) {
    return this;
  }

  public int length() {
    return 0;
  }

  public IList<T> insert(T element, IComparator<T> comp) {
    // Inserting into an empty list results in a list with one element
    return new ConsList<T>(element, this);
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  public IList<T> sort(IComparator<T> comp) {
    return this.rest.sort(comp).insert(this.first, comp);
  }

  public IList<T> insert(T element, IComparator<T> comp) {
    if (comp.compare(element, this.first) <= 0) {
      return new ConsList<T>(element, this);
    }
    else {
      return new ConsList<T>(this.first, this.rest.insert(element, comp));
    }
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return func.apply(this.first, this.rest.foldr(func, base));
  }
}

class RunnerName implements IFunc<Runner, String> {
  public String apply(Runner r) {
    return r.name;
  }
}

class CirclePerimeter implements IFunc<Circle, Double> {
  public Double apply(Circle c) {
    return 2.0 * Math.PI * c.radius;
  }
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

class SumPricesOfBooks implements IFunc2<Book, Integer, Integer> {
  public Integer apply(Book b, Integer sum) {
    return b.price() + sum;
  }
}

class Utils {
  Integer totalPrice(IList<Book> books) {
    return books.foldr(new SumPricesOfBooks(), 0);
  }
}

class ExamplesIPred {
  ExamplesIPred() {
  }

  IPred<Book> byAuthor = new BookByAuthor();
  IPred<Runner> inFirst50 = new RunnerIsInFirst50();

  IList<Integer> ints = new ConsList<Integer>(1, new ConsList<Integer>(4, new MtList<Integer>()));
  IList<Double> dbls = new ConsList<Double>(1.5, new ConsList<Double>(4.3, new MtList<Double>()));
  IList<Boolean> bools = new ConsList<Boolean>(true, new MtList<Boolean>());

  IList<String> abc = new ConsList<String>("a",
      new ConsList<String>("b", new ConsList<String>("c", new MtList<String>())));

  IList<Circle> circs = new ConsList<Circle>(new Circle(3, 4, 5), new MtList<Circle>());
  IList<Double> circPerims = circs.map(new CirclePerimeter());

}