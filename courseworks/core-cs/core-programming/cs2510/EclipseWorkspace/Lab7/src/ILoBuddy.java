// represents a list of Person's buddies
interface ILoBuddy {
  // Determines if this list contains the given person
  boolean contains(Person that);

  // Counts how many buddies in this list are also in that list
  int countCommon(ILoBuddy thatBuddies);

  // Helper for extended buddy search tracking already visited people
  boolean hasExtendedBuddyHelp(Person target, ILoBuddy visited);

  // Helper for party count accumulating unique people invited
  ILoBuddy partyCountHelp(ILoBuddy visited);

  // Returns the total number of items in this list
  int length();
}