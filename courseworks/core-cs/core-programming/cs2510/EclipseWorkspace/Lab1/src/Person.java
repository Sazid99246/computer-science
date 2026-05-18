import tester.*;

/*
+-----------------+
| Person          | 
+-----------------+
| String name     |
| int age         |--+
| String gender   |  |
+-----------------+  |
                     v
           +-----------------+
           | Address         |
           +-----------------+
           | String city     |
           | String state    |
           +-----------------+
*/

// to represent a person
class Person {
  String name;
  int age;
  String gender;
  Address address;
  
  // constructor
  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

// to represent a Person's address
class Address {
  String city;
  String state;
  
  // constructor
  Address(String city, String state) {
    this.city = city;
    this.state = state;
  }
}

//to represent examples and tests for persons
class ExamplesPerson {
  
  ExamplesPerson() {}
  
  // example address
  Address boston = new Address("Boston", "MA");
  Address warwick = new Address("Warwick", "RI");
  Address nashua = new Address("Nashua", "NH");
  Address chicago = new Address("Chicago", "IL");
  Address miami = new Address("Miami", "FL");
  
  // example person
  Person tim = new Person("Tim", 23, "male", this.boston);
  Person kate = new Person("Kate", 22, "Female", this.warwick);
  Person rebecca = new Person("Rebecca", 31, "Non-binary", this.nashua);
  Person alex = new Person("Alex", 28, "Male", this.chicago);
  Person sophia = new Person("Sophia", 35, "Female", this.miami);
}