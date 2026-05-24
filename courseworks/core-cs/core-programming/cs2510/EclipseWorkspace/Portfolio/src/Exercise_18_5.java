import tester.*;

interface IVehicle {
  double cost(double cp);
}

//Refactored AVehicle.java
abstract class AVehicle implements IVehicle {
  double tank;

  AVehicle(double tank) {
    this.tank = tank;
  }

  // The method is lifted here and is no longer abstract
  public double cost(double cp) {
    return this.tank * cp;
  }
}

class Car extends AVehicle {
  Car(double tank) {
    super(tank);
  }
}

class Bus extends AVehicle {
  Bus(double tank) {
    super(tank);
  }
}

class Truck extends AVehicle {
  Truck(double tank) {
    super(tank);
  }
}

class ExamplesVehicle {
  ExamplesVehicle() {};
  IVehicle car1 = new Car(15.0);    // 15-gallon tank
  IVehicle truck1 = new Truck(30.0); // 30-gallon tank
  IVehicle bus1 = new Bus(50.0);   // 50-gallon tank

  // Test the cost method on the original hierarchy
  boolean testVehicleCost(Tester t) {
      return t.checkInexact(this.car1.cost(3.50), 52.5, 0.001)
          && t.checkInexact(this.truck1.cost(3.50), 105.0, 0.001)
          && t.checkInexact(this.bus1.cost(3.50), 175.0, 0.001);
  }
}