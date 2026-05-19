/*
                                +----------+
                                | IAccount |
                                +----------+
                                +----------+
                                      |
                                     / \
                                     ---
                                      |
           -------------------------------------------------------           
           |                          |                           |           
  +----------------+     +---------------------+     +---------------------+
  | Checking       |     | Savings             |     | CD                  |
  +----------------+     +---------------------+     +---------------------+
  | int id         |     | int id              |     | int id              |
  | String name    |     | String name         |     | String name         |
  | int balance    |     | int balance         |     | int balance         |
  | int minBalance |     | double interestRate |     | double interestRate |
  +----------------+     +---------------------+     | String maturityDate |
                                                     +---------------------+
 */


// interface for account
interface IAccount { }

// class for checking account
class Checking implements IAccount {
  int id;
  String name;
  int balance;
  int minBalance;
  
  // constructor
  Checking(int id, String name, int balance, int minBalance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.minBalance = minBalance;
  }
}

// class for savings account
class Savings implements IAccount {
  int id;
  String name;
  int balance;
  double interestRate;
  
  // constructor
  Savings(int id, String name, int balance, double interestRate) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.interestRate = interestRate;
  }
}

// class for certificate of deposit 
class CD implements IAccount {
  int id;
  String name;
  int balance;
  double interestRate;
  String maturityDate;
  
  // constructor
  CD(int id, String name, int balance, double interestRate, String maturityDate) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.interestRate = interestRate;
    this.maturityDate = maturityDate;
  }
}


class ExamplesIAccount {
  Checking c1 = new Checking(1729,"Earl Gray", 1250, 500);
  CD cd1 = new CD(4104, "Ima Flatt", 10123, 4.0, "June 1, 2005");
  Savings s1 = new Savings(2992, "Annie Proulx", 800, 3.5);
}