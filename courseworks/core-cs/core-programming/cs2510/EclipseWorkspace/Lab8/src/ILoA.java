// Represents a List of Accounts
interface ILoA {
  // Finds the account with the given account number
  Account find(int acctNo);

  // EFFECT: Removes the account with the given account number from the list
  // Returns the new list structure
  ILoA remove(int acctNo);
}