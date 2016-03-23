package Control;

enum LockState {
  READ, WRITE
}
public class Lock {
  LockState state;
  Variable var;
  int siteID;
  Transaction tran;
  
  public Lock(LockState locktype, Variable var, int siteID, Transaction tran) {
    this.state = locktype;
    this.var = var;
    this.siteID = siteID;
    this.tran = tran;
  } 
  
  @Override
  public int hashCode() {
    int result = 31;
    result = result * 17 + this.state.hashCode();
    result = result * 17 + this.var.hashCode();
    result = result * 17 + this.siteID;
    result = result * 17 + this.tran.hashCode();
    return result;
  }
  
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Lock)) {
      return false; 
    }
    Lock another = (Lock) other;
    return this.state.equals(another.state)
        && this.var.equals(another.var)
        && this.siteID == another.siteID
        && this.tran.equals(another.tran);
  }
  
  public Transaction getTran() {
    return this.tran;
  }
  
  public String getVarName() {
    return this.var.getName();
  }
  
  public int getSiteID() {
    return this.siteID;
  }
  
  public LockState getLockState() {
    return this.state;
  }
  
  public void setLockState(LockState state) {
    this.state = state;
  }
}
