// Programming Languages, Dan Grossman, CSE341

// Bonus example on higher-order functions with plain objects and generics,
// drawing connections between functions and objects

// (This file has not been tested.)

// (You shouldn't do any of this explicitly now that Java 8 and later 
//  have syntactic support for lambdas as shown at the end, 
//  but this pulls together concepts better for 341.)

interface Func<B,A> {
    B m(A x);
}
interface Pred<A> {
    boolean m(A x);
}

class List<T> {
    T       head;
    List<T> tail;
    List(T x, List<T> xs) {
	head = x;
	tail = xs;
    }

    // * the advantage of a static method is it allows xs to be null
    //    -- a more OO way would be a subclass for empty lists (see below)
    // * a more efficient way in Java would be a messy while loop
    //   where you keep a pointer to the previous element and mutate it
    //   -- (try it if you don't believe it's messy)
    //   -- it's more efficent because Java VM doesn't optimize tail calls
    //      (maybe some day)
    static <A,B> List<B> map(Func<B,A> f, List<A> xs) {
	if(xs==null)
	    return null;
	return new List<B>(f.m(xs.head), map(f,xs.tail));
    }

    static <A> List<A> filter(Pred<A> f, List<A> xs) {
	if(xs==null)
	    return null;
	if(f.m(xs.head))
	    return new List<A>(xs.head, filter(f,xs.tail));
	return filter(f,xs.tail);
    }

    // * again recursion would be more elegant but less efficient
    // * again an instance method would be more common, but then
    //   all clients have to special-case null 
    static <A> int length(List<A> xs) {
	int ans = 0;
	while(xs != null) {
	    ++ans;
	    xs = xs.tail;
	}
	return ans;
    }
}

class ExampleClients {
    // no environment needed
    static List<Integer> doubleAll(List<Integer> xs) {
	return List.map((new Func<Integer,Integer>() { 
		             public Integer m(Integer x) { return x * 2; } 
                         }),
			xs);
    }
    // the key point here is that the "final int n" is in the environment
    // without inner classes, we'd need a class definition with an explicit
    // field to hold n and pass n to the constructor as shown next
    // (In Java 8 and later, you can leave final off, but it is inferred --
    //  if countNs does mutate n, then the inner object won't be allowed to use
    // it.)
    static int countNs(List<Integer> xs, final int n) {
	return List.length(List.filter((new Pred<Integer>() { 
			                   public boolean m(Integer x) { return x==n; } 
		                       }),
				       xs));
    }

    static int countNs2(List<Integer> xs, int n) {
        return List.length(List.filter(new ForCountNs2(n), xs));
    }
}

class ForCountNs2 implements Pred<Integer> {
    int n; // aha, explicit environment, created by constructor
    ForCountNs2(int _n) { n = _n; }
    public boolean m(Integer x) { return x == n; }
}

// now here's a version that does the more OO thing.  If we use null instead
// instance methods, then clients have to check the null case, which is far
// less convenient, so we choose instead /not/ to represent empty lists with
// null.
abstract class List2<T> {
    abstract <B> List2<B> map(Func<B,T> f);
    abstract List2<T> filter(Pred<T> f);
    abstract int length();
}

class NonEmptyList<T> extends List2<T> {
    T head;
    List2<T> tail;
    NonEmptyList(T x, List2<T> xs) {
        head = x;
        tail = xs;
    }
    // ooh covariant subtyping on return type
    <B> NonEmptyList<B> map(Func<B,T> f) {
        return new NonEmptyList<B>(f.m(head), tail.map(f));
    }
    List2<T> filter(Pred<T> f) {
        if(f.m(head))
	    return new NonEmptyList<T>(head, tail.filter(f));
	return tail.filter(f);
    }
    int length() {
        return 1 + tail.length();
    }
}
class Null<T> extends List2<T> {
    Null() {} // could omit this as it's implied
    // ooh covariant subtyping on return type
    <B> Null<B> map(Func<B,T> f) {
        return new Null<B>(); 
        // the above is "right" for the type system but wasteful
        // this will rightfully generate a warning but would be okay:
        // return (Null<B>)this;
    }
    Null<T> filter(Pred<T> f) {
        return this;
    }
    int length() {
        return 0;
    }
}

class ExampleClients2 {
    static List2<Integer> doubleAll(List2<Integer> xs) {
	return xs.map(new Func<Integer,Integer>() { 
		             public Integer m(Integer x) { return x * 2; } 
                      });
    }
    // the key point here is that the "final int n" is in the environment
    // without inner classes, we'd need a class definition with an explicit
    // field to hold n and pass n to the constructor
    static int countNs(List2<Integer> xs, final int n) {
	return xs.filter(new Pred<Integer>() { 
	                   public boolean m(Integer x) { return x==n; } 
		         })
                  .length();
    }
}

// now lambdas with implicit conversion to one-method interfaces
// basically syntactic sugar for clients
class ExampleClients3 {
    static List2<Integer> doubleAll(List2<Integer> xs) {
	return xs.map((Integer x) -> x * 2);
    }
    static int countNs(List2<Integer> xs, final int n) {
	return xs.filter((Integer x) -> x == n).length();
    }
}
