/*
 +-----------------+
 | Listing         |
 +-----------------+
 | House house     |
 +-----------------+
         |
         v
 +-----------------+
 | House           |
 +-----------------|
 | String kind     |
 | int rooms       |
 | Address address |----
 | int price       |   |
 +-----------------+   |
                       |
                       v
               +-------------------+
               | Address           |
               +-------------------+
               | int streetNumber  |
               | String streetName |
               | String city       |
               +-------------------+
*/

class Address {
  int streetNumber;
  String streetName;
  String city;
  
  // constructor
  Address(int number, String name, String city) {
    this.streetNumber = number;
    this.streetName = name;
    this.city = city;
  }
}

class House {
  String kind;
  int rooms;
  Address address;
  int price;
  
  //  constructor
  House(String kind, int rooms, Address address, int price) {
    this.kind = kind;
    this.rooms = rooms;
    this.address = address;
    this.price = price;
  }
}

class Listing {
  House house;

  Listing(House house) {
    this.house = house;
  }
}

class ExamplesHouse {
  // example addresses
  Address a1 = new Address(23, "Maple Street", "Brookline");
  Address a2 = new Address(5, "Joye Road", "Newton");
  Address a3 = new Address(83, "Winslow Road", "Waltham");

  // example houses
  House h1 = new House("Ranch", 7, a1, 375000);
  House h2 = new House("Colonial", 9, a2, 450000);
  House h3 = new House("Cape", 6, a3, 235000);

  // listings
  Listing l1 = new Listing(h1);
  Listing l2 = new Listing(h2);
  Listing l3 = new Listing(h3);
}