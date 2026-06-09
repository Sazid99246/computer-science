import tester.*;

// runs tests for the buddies problem
public class ExamplesBuddies {
  Person ann, bob, cole, dan, ed, fay, gabi, hank, jan, kim, len;

  // Initializes the sample social network graph data
  void initData() {
    ann = new Person("Ann");
    bob = new Person("Bob");
    cole = new Person("Cole");
    dan = new Person("Dan");
    ed = new Person("Ed");
    fay = new Person("Fay");
    gabi = new Person("Gabi");
    hank = new Person("Hank");
    jan = new Person("Jan");
    kim = new Person("Kim");
    len = new Person("Len");

    ann.addBuddy(cole);
    ann.addBuddy(bob); // Note order: builds list backwards
    bob.addBuddy(hank);
    bob.addBuddy(ed);
    bob.addBuddy(ann);
    cole.addBuddy(dan);
    dan.addBuddy(cole);
    ed.addBuddy(fay);
    fay.addBuddy(gabi);
    fay.addBuddy(ed);
    gabi.addBuddy(fay);
    gabi.addBuddy(ed);
    // Hank has no buddies
    jan.addBuddy(len);
    jan.addBuddy(kim);
    kim.addBuddy(len);
    kim.addBuddy(jan);
    len.addBuddy(kim);
    len.addBuddy(jan);
  }

  // Test direct buddy relationships
  void testDirectBuddies(Tester t) {
    initData();
    t.checkExpect(ann.hasDirectBuddy(bob), true);
    t.checkExpect(ann.hasDirectBuddy(dan), false);
    t.checkExpect(hank.hasDirectBuddy(bob), false);
  }

  // Test the count of common friends
  void testCommonBuddies(Tester t) {
    initData();
    t.checkExpect(fay.countCommonBuddies(gabi), 1); // Both share Ed and Fay/Gabi respectively
    t.checkExpect(ann.countCommonBuddies(jan), 0);
  }

  // Test if network paths connect individuals through extended paths
  void testExtendedBuddies(Tester t) {
    initData();
    t.checkExpect(ann.hasExtendedBuddy(gabi), true); // Ann -> Bob -> Ed -> Fay -> Gabi
    t.checkExpect(ann.hasExtendedBuddy(jan), false); // Disconnected networks
    t.checkExpect(dan.hasExtendedBuddy(cole), true); // Small cycle
  }

  // Test total party attendance counts
  void testPartyCount(Tester t) {
    initData();
    t.checkExpect(ann.partyCount(), 8); // Ann, Bob, Cole, Dan, Ed, Fay, Gabi, Hank
    t.checkExpect(hank.partyCount(), 1); // Only Hank shows up
    t.checkExpect(jan.partyCount(), 3); // Jan, Kim, Len cluster
  }
}