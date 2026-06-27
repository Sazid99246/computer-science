////
//// DO NOT MODIFY THIS FILE
////
//// You don't need to submit it, but you should make sure it compiles.
//// Further explanation appears below.
////

import cs3500.hw01.duration.Duration;
import cs3500.hw01.publication.Publication;

/**
 * This class is provided to check that your code implements the expected API.
 * If your code compiles with an unmodified version of this class, then it very
 * likely will also compile with the tests that we use to evaluate your code.
 */
public class Hw01TypeChecks {
  static Publication snapl =
          new cs3500.hw01.publication.ConferenceProceedings(
                  "Lerner", "Benjamin",
                  "The 3rd Summit on Advances In Programming Languages",
                  "Providence, RI", 2019, "LIPIcs"
                  );

  static void durationHasFormatMethod(Duration duration) {
    String result = duration.format("Template");
  }

  public Hw01TypeChecks() {
    throw new RuntimeException("uninstantiable");
  }
}
