// Represents a non-empty List of Accounts
class ConsLoA implements ILoA {
  Account first;
  ILoA rest;

  public ConsLoA(Account first, ILoA rest) {
    this.first = first;
    this.rest = rest;
  }

  public Account find(int acctNo) {
    if (this.first.accountNum == acctNo) {
      return this.first;
    }
    return this.rest.find(acctNo);
  }

  public ILoA remove(int acctNo) {
    if (this.first.accountNum == acctNo) {
      return this.rest; // Skip the found account
    }
    return new ConsLoA(this.first, this.rest.remove(acctNo));
  }
}