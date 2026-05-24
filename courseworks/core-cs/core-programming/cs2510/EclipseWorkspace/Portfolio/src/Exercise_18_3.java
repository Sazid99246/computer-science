/**
 * +--------------+ | ITaxiVehicle | +--------------+ | / \ --- |
 * +------------------+ | ATaxiVehile | +------------------+ | int idNum | | int
 * passengers | | int pricePerMile | +------------------+ | / \ --- |
 * ----------------------------------------------- | | | +----------+
 * +---------------+ +----------------+ | Cab | | Limo | | Van | +----------+
 * +---------------+ +----------------+ | | | int minRental | | boolean access |
 * +----------+ +---------------+ +----------------+
 */

interface ITaxiVehicle {
}

abstract class TaxiVehicle implements ITaxiVehicle {
  int idNum;
  int passengers;
  int pricePerMile;

  TaxiVehicle(int idNum, int passengers, int pricePerMile) {
    this.idNum = idNum;
    this.passengers = passengers;
    this.pricePerMile = pricePerMile;
  }
}

class Cab extends TaxiVehicle {
  Cab(int idNum, int passengers, int pricePerMile) {
    super(idNum, passengers, pricePerMile);
  }
}

class Limo extends TaxiVehicle {
  int minRental;

  Limo(int idNum, int passengers, int pricePerMile, int minRental) {
    super(idNum, passengers, pricePerMile);

    this.minRental = minRental;
  }

}

class Van extends TaxiVehicle {
  boolean access;

  Van(int idNum, int passengers, int pricePerMile, boolean access) {
    super(idNum, passengers, pricePerMile);

    this.access = access;
  }
}