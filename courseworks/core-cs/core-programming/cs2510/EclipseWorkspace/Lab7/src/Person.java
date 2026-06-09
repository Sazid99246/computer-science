// represents a Person with a user name and a list of buddies
class Person {
  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  // EFFECT: Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    this.buddies = new ConsLoBuddy(buddy, this.buddies);
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return this.buddies.contains(that);
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    return this.buddies.countCommon(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.hasExtendedBuddyHelp(that, new MTLoBuddy());
  }

  // Helper for extended search that tracks visited nodes to break cycles
  boolean hasExtendedBuddyHelp(Person target, ILoBuddy visited) {
    if (this.username.equals(target.username)) {
      return true;
    }
    if (visited.contains(this)) {
      return false;
    }
    return this.buddies.hasExtendedBuddyHelp(target, new ConsLoBuddy(this, visited));
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return this.partyCountHelp(new MTLoBuddy()).length();
  }

  // Helper for accumulating all unique people reached in the network
  ILoBuddy partyCountHelp(ILoBuddy visited) {
    if (visited.contains(this)) {
      return visited;
    }
    ILoBuddy updatedVisited = new ConsLoBuddy(this, visited);
    return this.buddies.partyCountHelp(updatedVisited);
  }
}