import tester.*;

// =======================
// IPet interface
// =======================
interface IPet {
    // does this pet have the given name?
    boolean sameName(String name);
}

// =======================
// Cat class
// =======================
class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;

    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

// =======================
// Dog class
// =======================
class Dog implements IPet {
    String name;
    String kind;
    boolean male;

    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

// =======================
// NoPet class
// =======================
class NoPet implements IPet {

    NoPet() { }

    public boolean sameName(String name) {
        return false;
    }
}

// =======================
// Person2 class
// =======================
class Person2 {
    String name;
    IPet pet;
    int age;

    Person2(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    // is this person older than the given person?
    boolean isOlder(Person2 other) {
        return this.age > other.age;
    }

    // does the name of this person's pet match the given name?
    boolean sameNamePet(String name) {
        return this.pet.sameName(name);
    }

    // returns a person whose pet has perished
    Person2 perish() {
        return new Person2(this.name, new NoPet(), this.age);
    }
}

// =======================
// Examples + Tests
// =======================
class ExamplesPets {

    Cat whiskers = new Cat("Whiskers", "Persian", true);
    Cat luna = new Cat("Luna", "Siamese", false);

    Dog max = new Dog("Max", "Labrador", true);
    Dog bella = new Dog("Bella", "Poodle", false);

    NoPet none = new NoPet();

    Person2 alice = new Person2("Alice", whiskers, 30);
    Person2 bob = new Person2("Bob", luna, 25);
    Person2 charlie = new Person2("Charlie", max, 40);
    Person2 diana = new Person2("Diana", bella, 35);

    Person2 eva = new Person2("Eva", none, 28);
    Person2 frank = new Person2("Frank", none, 50);

    // -----------------------
    // isOlder tests
    // -----------------------
    boolean testIsOlder(Tester t) {
        return t.checkExpect(alice.isOlder(bob), true)
            && t.checkExpect(bob.isOlder(charlie), false)
            && t.checkExpect(charlie.isOlder(diana), true)
            && t.checkExpect(diana.isOlder(alice), true);
    }

    // -----------------------
    // sameNamePet tests
    // -----------------------
    boolean testSameNamePet(Tester t) {
        return t.checkExpect(alice.sameNamePet("Whiskers"), true)
            && t.checkExpect(bob.sameNamePet("Whiskers"), false)
            && t.checkExpect(charlie.sameNamePet("Max"), true)
            && t.checkExpect(diana.sameNamePet("Bella"), true)
            && t.checkExpect(eva.sameNamePet("Anything"), false)
            && t.checkExpect(frank.sameNamePet("Max"), false);
    }

    // -----------------------
    // perish tests
    // -----------------------
    boolean testPerish(Tester t) {
        Person2 aliceAfter = alice.perish();
        Person2 charlieAfter = charlie.perish();

        return t.checkExpect(aliceAfter.pet instanceof NoPet, true)
            && t.checkExpect(charlieAfter.pet instanceof NoPet, true)
            && t.checkExpect(aliceAfter.name, "Alice")
            && t.checkExpect(aliceAfter.age, 30);
    }
}