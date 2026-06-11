
// Represents a credit line account
public class Credit extends Account {

  int creditLine; // Maximum amount accessible
  double interest; // The interest rate charged

  public Credit(int accountNum, int balance, String name, int creditLine, double interest) {
    super(accountNum, balance, name);
    this.creditLine = creditLine;
    this.interest = interest;
  }

  public int withdraw(int amount) {
    if (this.balance + amount > this.creditLine) {
      throw new RuntimeException(amount + " exceeds credit limit");
    }
    this.balance = this.balance + amount;
    return this.balance;
  }

  public int deposit(int funds) {
    // Depositing pays off debt. Debt cannot drop below zero.
    if (this.balance - funds < 0) {
      throw new RuntimeException("Cannot deposit more than the amount owed");
    }
    // FIX: Make sure this is MINUS (-), not PLUS (+)
    this.balance = this.balance - funds;
    return this.balance;
  }
}
