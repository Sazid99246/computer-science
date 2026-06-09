// Represents functions of signature A -> R
interface IFunc<A, R> {
  R apply(A input);
}

// Represents a two-argument function for folding
interface IFunc2<T, U> {
  U apply(T first, U accum);
}

// Predicate interface (T -> Boolean)
interface IPred<T> extends IFunc<T, Boolean> {
}