import tester.*;

// Bank Account Examples and Tests
public class Examples {

  public Examples() {
    reset();
  }

  // Instructor baseline examples
  Account check1;
  Account savings1;

  // Additional required examples
  Account check2;
  Account savings2;
  Account credit1;
  Account credit2;

  // Bank examples for testing structural list mutations
  Bank chaseBank;
  Bank emptyBank;

  // Initializes/resets accounts to clean baselines before every mutation test
  public void reset() {
    // Instructor baseline setup
    check1 = new Checking(1, 100, "First Checking Account", 20);
    savings1 = new Savings(4, 200, "First Savings Account", 2.5);

    // Expanded setup following the same organization
    check2 = new Checking(2, 50, "Second Checking Account", 10);
    savings2 = new Savings(3, 500, "Second Savings Account", 1.5);

    // Credit fields: accountNum, balance (debt), name, creditLine, interest
    credit1 = new Credit(5, 0, "First Credit Line", 1000, 18.5);
    credit2 = new Credit(6, 400, "Second Credit Line", 500, 22.0);

    // Initialize banks
    emptyBank = new Bank("Empty Bank");
    chaseBank = new Bank("Chase");

    // Populate bank with accounts (Note: openAcct adds elements to the front of our
    // list)
    chaseBank.openAcct(credit2); // List: credit2 -> MtLoA
    chaseBank.openAcct(savings2); // List: savings2 -> credit2 -> MtLoA
    chaseBank.openAcct(check1); // List: check1 -> savings2 -> credit2 -> MtLoA
  }

  // Tests the exceptions we expect to be thrown when performing "illegal" actions
  public void testExceptions(Tester t) {
    reset();

    // 1. Checking failure: dropping below minimum limit ($20)
    t.checkException("Checking withdraw drops below minimum balance limit",
        new RuntimeException("1000 is not available"), this.check1, "withdraw", 1000);

    // 2. Savings failure: dropping below zero
    t.checkException("Savings withdraw cannot drop below zero",
        new RuntimeException("550 is not available"), this.savings2, "withdraw", 550);

    // 3. Credit withdrawal failure: borrowing past maximum allowed credit line
    t.checkException("Credit withdraw cannot exceed max credit limit",
        new RuntimeException("150 exceeds credit limit"), this.credit2, "withdraw", 150);

    // 4. Credit deposit failure: trying to pay back more than what is currently
    // owed
    t.checkException("Credit deposit cannot drop debt below zero",
        new RuntimeException("Cannot deposit more than the amount owed"), this.credit2, "deposit",
        450);

    // 5. Bank lookup failure: trying to interact with an account number that
    // doesn't exist
    t.checkException("Bank deposit fails for missing account",
        new RuntimeException("Account #999 does not exist"), this.chaseBank, "deposit", 999, 100);

    t.checkException("Bank withdraw fails for missing account",
        new RuntimeException("Account #999 does not exist"), this.chaseBank, "withdraw", 999, 100);

    t.checkException("Bank removeAccount fails for missing account",
        new RuntimeException("Account #999 does not exist"), this.chaseBank, "removeAccount", 999);
  }

  // Test successful transaction mutations and verify state updates
  public void testTransactions(Tester t) {
    reset();

    // --- WITHDRAW TESTS ---

    // Checking: decreases balance
    t.checkExpect(this.check1.withdraw(25), 75);
    t.checkExpect(this.check1, new Checking(1, 75, "First Checking Account", 20));

    reset();

    // Savings: decreases balance
    t.checkExpect(this.savings2.withdraw(100), 400);
    t.checkExpect(this.savings2, new Savings(3, 400, "Second Savings Account", 1.5));

    reset();

    // Credit withdrawal: increases debt owed
    t.checkExpect(this.credit2.withdraw(50), 450);
    t.checkExpect(this.credit2, new Credit(6, 450, "Second Credit Line", 500, 22.0));

    reset();

    // --- DEPOSIT TESTS ---

    // Checking: increases balance
    t.checkExpect(this.check1.deposit(50), 150);
    t.checkExpect(this.check1, new Checking(1, 150, "First Checking Account", 20));

    reset();

    // Credit deposit: decreases debt owed
    t.checkExpect(this.credit2.deposit(200), 200);
    t.checkExpect(this.credit2, new Credit(6, 200, "Second Credit Line", 500, 22.0));
  }

  // Test Bank structural modifications (openAcct and removeAccount)
  public void testBankStructure(Tester t) {
    reset();

    // Verify empty bank starts empty
    t.checkExpect(this.emptyBank.accounts, new MtLoA());

    // Test openAcct side effects on emptyBank
    this.emptyBank.openAcct(check2);
    t.checkExpect(this.emptyBank.accounts, new ConsLoA(check2, new MtLoA()));

    reset();

    // Verify initial structure of chaseBank before removal
    ILoA initialChaseList = new ConsLoA(check1,
        new ConsLoA(savings2, new ConsLoA(credit2, new MtLoA())));
    t.checkExpect(this.chaseBank.accounts, initialChaseList);

    // Test removeAccount (Removes intermediate savings2 account with accountNum =
    // 3)
    this.chaseBank.removeAccount(3);
    ILoA expectedChaseListAfterRemove = new ConsLoA(check1, new ConsLoA(credit2, new MtLoA()));
    t.checkExpect(this.chaseBank.accounts, expectedChaseListAfterRemove);
  }

  // Test Bank-level proxy operations (deposit and withdraw by account number)
  public void testBankProxyTransactions(Tester t) {
    reset();

    // Test bank-routed deposit tracking to checking account (#1)
    this.chaseBank.deposit(1, 50);
    t.checkExpect(this.check1, new Checking(1, 150, "First Checking Account", 20));

    reset();

    // Test bank-routed withdrawal tracking to credit line account (#6)
    this.chaseBank.withdraw(6, 50);
    t.checkExpect(this.credit2, new Credit(6, 450, "Second Credit Line", 500, 22.0));
  }
}