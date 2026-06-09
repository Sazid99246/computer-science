// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

  public boolean contains(Person that) {
    return false;
  }

  public int countCommon(ILoBuddy thatBuddies) {
    return 0;
  }

  public boolean hasExtendedBuddyHelp(Person target, ILoBuddy visited) {
    return false;
  }

  public ILoBuddy partyCountHelp(ILoBuddy visited) {
    return visited;
  }

  public int length() {
    return 0;
  }
}