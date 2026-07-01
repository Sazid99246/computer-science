package cs3500.regexp;

/**
 * A regular expression.  These patterns are capable ot testing a string to see if they match
 * the pattern described by this expression.
 */
public interface Regexp {
  boolean matches(String target);
}
