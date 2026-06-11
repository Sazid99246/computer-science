public class Bank {
  String name;
  ILoA accounts;

  public Bank(String name) {
    this.name = name;
    this.accounts = new MtLoA();
  }

  // EFFECT: Add a new account to this Bank's list
  public void openAcct(Account acct) {
    this.accounts = new ConsLoA(acct, this.accounts);
  }

  // EFFECT: Deposits funds into the account with the matching account number
  public void deposit(int acctNo, int amount) {
    Account target = this.accounts.find(acctNo); // Throws exception if not found
    target.deposit(amount); // Throws exception if transaction invalid
  }

  // EFFECT: Withdraws funds from the account with the matching account number
  public void withdraw(int acctNo, int amount) {
    Account target = this.accounts.find(acctNo); // Throws exception if not found
    target.withdraw(amount); // Throws exception if transaction invalid
  }

  // EFFECT: Remove the given account from this Bank
  public void removeAccount(int acctNo) {
    this.accounts = this.accounts.remove(acctNo); // Updates list structure
  }
}