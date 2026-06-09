// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {
  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean contains(Person that) {
    return this.first.username.equals(that.username) || this.rest.contains(that);
  }

  public int countCommon(ILoBuddy thatBuddies) {
    if (thatBuddies.contains(this.first)) {
      return 1 + this.rest.countCommon(thatBuddies);
    }
    return this.rest.countCommon(thatBuddies);
  }

  public boolean hasExtendedBuddyHelp(Person target, ILoBuddy visited) {
    return this.first.hasExtendedBuddyHelp(target, visited)
        || this.rest.hasExtendedBuddyHelp(target, visited);
  }

  public ILoBuddy partyCountHelp(ILoBuddy visited) {
    ILoBuddy updatedVisited = this.first.partyCountHelp(visited);
    return this.rest.partyCountHelp(updatedVisited);
  }

  public int length() {
    return 1 + this.rest.length();
  }
}