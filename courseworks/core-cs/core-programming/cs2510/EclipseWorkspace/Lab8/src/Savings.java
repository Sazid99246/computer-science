
// Represents a savings account
public class Savings extends Account {

  double interest; // The interest rate

  public Savings(int accountNum, int balance, String name, double interest) {
    super(accountNum, balance, name);
    this.interest = interest;
  }

  public int withdraw(int amount) {
    if (this.balance - amount < 0) {
      throw new RuntimeException(amount + " is not available");
    }
    this.balance = this.balance - amount;
    return this.balance;
  }

  // For Credit: Depositing pays off debt. Debt cannot drop below zero.
  public int deposit(int funds) {
    if (this.balance - funds < 0) {
      throw new RuntimeException("Cannot deposit more than the amount owed");
    }
    this.balance = this.balance - funds;
    return this.balance;
  }
}
