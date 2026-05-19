import tester.Tester;

// to represent a date
class Date {
  int day;
  int month;
  int year;
  
  // constructor
  Date(int day, int month, int year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }
  
  /*
   * TEMPLATE
   * Fields:
   * ... this.day ...   -- int
   * ... this.month ... -- int
   * ... this.year ...  -- int
   */
}

// to represent a range of temperature
class TemperatureRange {
  int high;
  int low;
  
  // constructor
  TemperatureRange(int high, int low) {
    this.high = high;
    this.low = low;
  }
  
  /*
   * TEMPLATE
   * Fields:
   * ... this.high ...  -- int
   * ... this.low ...   -- int
   */
}

// to represent a weather record
class WeatherRecord {
  Date d;
  TemperatureRange today;
  TemperatureRange normal;
  TemperatureRange record;
  double precipitation;
  
  // constructor
  WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record, double precipitation) {
    this.d = d;
    this.today = today;
    this.normal = normal;
    this.record = record;
    this.precipitation = precipitation;
  }
  
  /* TEMPLATE
     Fields:
     ... this.d ...                 -- Date
     ... this.today ...             -- TemperatureRange
     ... this.normal ...            -- TemperatureRange
     ... this.record ...            -- TemperatureRange
     ... this.precipitation ...     -- double
     
     Methods:
     ... this.withinRange() ...     -- boolean
     ... this.rainyDay(double) ...  -- boolean
     ... this.recordDay() ...       -- boolean
   */
  
  // determines whether today.high and today.low is between normal.high and normal.low
  boolean withinRange() {
    return this.today.high <= this.normal.high && this.today.low >= this.normal.low;
  }
  
  // determines whether precipitation is higher than the given value
  boolean rainyDay(double that) {
    return this.precipitation > that;
  }
  
  // determines whether the temperature broke either the high or the low record.
  boolean recordDay() {
    return this.today.high > this.record.high || this.today.low < this.record.low;
  }
}


//examples and tests for WeatherRecord
class ExamplesWeather {
  // dates
  Date d1 = new Date(19, 5, 2026);
  Date d2 = new Date(10, 1, 2026);
  Date d3 = new Date(25, 12, 2025);

  // temperature ranges for first weather record
  TemperatureRange today1 = new TemperatureRange(32, 24);
  TemperatureRange normal1 = new TemperatureRange(35, 22);
  TemperatureRange record1 = new TemperatureRange(40, 18);

  // temperature ranges for second weather record
  TemperatureRange today2 = new TemperatureRange(42, 15);
  TemperatureRange normal2 = new TemperatureRange(36, 20);
  TemperatureRange record2 = new TemperatureRange(41, 14);

  // temperature ranges for third weather record
  TemperatureRange today3 = new TemperatureRange(12, -3);
  TemperatureRange normal3 = new TemperatureRange(15, 0);
  TemperatureRange record3 = new TemperatureRange(20, -2);

  // weather records
  WeatherRecord wr1 =
     new WeatherRecord(d1, today1, normal1, record1, 2.5);
  
  WeatherRecord wr2 =
     new WeatherRecord(d2, today2, normal2, record2, 8.0);
  
  WeatherRecord wr3 =
     new WeatherRecord(d3, today3, normal3, record3, 0.5);

  // tests for withinRange
  boolean testWithinRange(Tester t) {
   return t.checkExpect(this.wr1.withinRange(), true)
       && t.checkExpect(this.wr2.withinRange(), false)
       && t.checkExpect(this.wr3.withinRange(), false);
  }
  
  // tests for rainyDay
  boolean testRainyDay(Tester t) {
   return t.checkExpect(this.wr1.rainyDay(1.0), true)
       && t.checkExpect(this.wr1.rainyDay(3.0), false)
       && t.checkExpect(this.wr2.rainyDay(5.0), true)
       && t.checkExpect(this.wr3.rainyDay(1.0), false);
  }
  
  // tests for recordDay
  boolean testRecordDay(Tester t) {
   return t.checkExpect(this.wr1.recordDay(), false)
       && t.checkExpect(this.wr2.recordDay(), true)
       && t.checkExpect(this.wr3.recordDay(), true);
  }
}