// Represents the empty List of Accounts
class MtLoA implements ILoA {
  public Account find(int acctNo) {
    throw new RuntimeException("Account #" + acctNo + " does not exist");
  }

  public ILoA remove(int acctNo) {
    throw new RuntimeException("Account #" + acctNo + " does not exist");
  }
}