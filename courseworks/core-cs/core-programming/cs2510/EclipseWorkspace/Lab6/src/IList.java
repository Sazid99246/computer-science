// Generic list interface
interface IList<T> {
  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U> f, U base);

  <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup);
}

// Empty generic list
class MtList<T> implements IList<T> {
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }

  public <U> U foldr(IFunc2<T, U> f, U base) {
    return base;
  }

  public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
    return backup;
  }
}

// Non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFunc2<T, U> f, U base) {
    return f.apply(this.first, this.rest.foldr(f, base));
  }

  public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
    U result = convert.apply(this.first);
    if (pred.apply(result)) {
      return result;
    }
    else {
      return this.rest.findSolutionOrElse(convert, pred, backup);
    }
  }
}

// Generic pairs
class Pair<X, Y> {
  X x;
  Y y;

  Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }
}