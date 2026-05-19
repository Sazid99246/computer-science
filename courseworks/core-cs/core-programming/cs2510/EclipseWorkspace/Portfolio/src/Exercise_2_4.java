class Automobile {
  String model;
  int price;
  double mileage;
  boolean used;
  
  Automobile(String model, int price, double mileage, boolean used) {
    this.model = model;
    this.price = price;
    this.mileage = mileage;
    this.used = used;
  }
}

class ExamplesAutomobile {
  ExamplesAutomobile() {};
  
  Automobile a1 = new Automobile("Toyota Corolla", 22000, 35.5, false);

  Automobile a2 = new Automobile("Honda Civic", 18000, 32.0, true);

  Automobile a3 = new Automobile("Ford Mustang", 45000, 22.3, false);

  Automobile a4 = new Automobile("BMW X5", 30000, 25.8, true); 
}