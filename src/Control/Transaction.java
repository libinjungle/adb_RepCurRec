package Control;

import java.util.List;

enum Type {
  READ, RO, WRITE;
}
/**
 * Transaction should hold a lock list.
 * @author BINLI
 *
 */
public class Transaction {
  private int startTime;
  private int id;
  private Type type;
  private List<Lock> lockList;
  
  public Transaction(int id, int startTime, Type type) {
    this.startTime = startTime;
    this.id = id;
    this.type = type;
    System.out.format("T" + id + ", %-5s", "started");
  }
  
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Transaction)) {
      return false;
    }
    Transaction another = (Transaction) other;
    return this.id == another.id
        && this.startTime == another.startTime
        && this.type.equals(another.type);
  }
  
  @Override
  public int hashCode() {
    int result = 31;
    result = result * 17 + this.id;
    result = result * 17 + this.startTime;
    result = result * 17 + this.type.hashCode();
    return result;
  }
  public Type getState() {
    return this.type;
  }
  
  public int getStartTime() {
    return this.startTime;
  }
  
  public int getID() {
    return this.id;
  }
  
  public List<Lock> getLocks() {
    return this.lockList;
  }
  
  /** Check if transaction holds read lock. */
  public boolean hasReadLock(String varName) {
    for (Lock lock : lockList) {
      if (lock.getVarName().equals(varName) 
          && lock.getLockState().equals(Type.READ)) {
        return true;
      }
    }
    return false;
  }
  
  /** Check if transaction holds write lock. */
  public boolean hasWriteLock(String varName) {
    for (Lock lock : lockList) {
      if (lock.getVarName().equals(varName) && lock.getLockState().equals(Type.WRITE)){
        return true;
      }
    }
    return false;
  }
  
  public void addWriteLock(String varName) {
    
  }
  
  /** Implement wait-die protocol. */
  public void waitForYounger() {
    
  }
  
  public void abort() {
    
  }
  
  /**
   * Read from a random alive site for variable.
   * 
   * @param site
   * @param variable
   */
  public void read(List<Site> siteList, String varName) {
    
  }
  
  /**
   * Write value to variables on the all sites.
   * @param site
   * @param variable
   * @param value
   */
  public void write(List<Site> siteList, String varName, int value) {
    
  }
  
  /**
   * End this transaction. Release corresponding lock.
   */
  public void end() {
    
  }
}
