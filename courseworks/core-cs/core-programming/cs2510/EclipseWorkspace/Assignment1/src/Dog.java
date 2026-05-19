/*
 +------------------------+
 | Dog                    |
 +------------------------+
 | String name            |
 | String breed           |
 | int yob                |
 | String state           |
 | boolean hypoallergenic |
 +------------------------+
 */

// represents a dog
class Dog {
  String name;
  String breed;
  int yob; // year of birth
  String state; // short form in 2 letters
  boolean hypoallergenic;
  
  // constructor
  Dog(String name, String breed, int yob, String state, boolean hypoallergenic) {
    this.name = name;
    this.breed = breed;
    this.yob = yob;
    this.state = state;
    this.hypoallergenic = hypoallergenic;
  }
}

class ExamplesDog {
  ExamplesDog() { }
  
  Dog huffle = new Dog("Hufflepuff", "Wheaten Terrier", 2012, "TX", true);
  Dog pearl = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);
  Dog buddy = new Dog("Buddy", "Golden Retriever", 2018, "CA", false);
}