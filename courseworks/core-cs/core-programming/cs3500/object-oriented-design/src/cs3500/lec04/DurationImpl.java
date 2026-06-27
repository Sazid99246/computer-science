package cs3500.lec04;

/**
 * Durations represented as hours, minutes, and seconds.
 */
public final class DurationImpl implements Duration {
  private final int hours;
  private final int minutes;
  private final int seconds;

  /**
   * Constructs a duration in terms of its length in hours, minutes, and
   * seconds.
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @throws IllegalArgumentException if any argument is negative
   */
  public DurationImpl(int hours, int minutes, int seconds) {
    if (hours < 0 || minutes < 0 || seconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }

    if (seconds > 59) {
      minutes += seconds / 60;
      seconds %= 60;
    }

    if (minutes > 59) {
      hours += minutes / 60;
      minutes %= 60;
    }

    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  /**
   * Constructs a duration in terms of its length in seconds.
   *
   * @param inSeconds the number of seconds (non-negative)
   * @throws IllegalArgumentException {@code inSeconds} is negative
   */
  public DurationImpl(long inSeconds) {
    if (inSeconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }

    this.seconds = (int) (inSeconds % 60);
    this.minutes = (int) (inSeconds / 60 % 60);
    this.hours = (int) (inSeconds / 3600);  // overflow :(
  }

  @Override
  public long inSeconds() {
    return 3600 * hours + 60 * minutes + seconds;
  }

  @Override
  public String asHms() {
    return String.format("%d:%02d:%02d", hours, minutes, seconds);
  }

  @Override
  public Duration plus(Duration that) {
    return new DurationImpl(this.inSeconds() + that.inSeconds());
  }

  @Override
  public String toString() {
    return asHms();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (! (that instanceof Duration)) {
      return false;
    }

    return ((Duration) that).inSeconds() == this.inSeconds();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(inSeconds());
  }

  @Override
  public int compareTo(Duration that) {
    return Long.compare(this.inSeconds(), that.inSeconds());
  }
}
