package Control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

enum State {
  FAILED, ALIVE;
}

/**
 * When read or write, needs to read or write site by site. When processing each site, 
 * check if var on this site is readable or writable. If other transaction holds lock on
 * var, the var is not writable.
 * 
 * @author BINLI
 *
 */
public class Site {
  // map is easier to update instead of list.
  private Map<String, Variable> varMap;
  private State state;
  private int id;
  // Map var name to its corresponding locks.
  private Map<String, List<Lock>> lockTable; 
  
  public Site(int id, Map<String, Variable> varMap) {
    this.id = id;
    this.varMap = varMap;
  }
  
  public int getID() {
    return this.id;
  }
  
  public Map<String, Variable> getVariables() {
    return this.varMap;
  }
  
  public Map<String, List<Lock>> getLockTable() {
    return this.lockTable;
  }
  
  public void updateVarMap(String varName, int value) {
    if (getVariables().containsKey(varName)) {
      Variable newVar = new Variable(varName, value);
      getVariables().put(varName, newVar);
    }
  }
  
  public void addLock(String varName, Lock lock) {
    List<Lock> lockList = new ArrayList<>();
    lockList.add(lock);
    this.lockTable.put(varName, lockList);
  }
  
  public void deleteLock(String varName, Lock lock) {
    if (getLockTable().containsKey(varName)) {
      List<Lock> locks = getLockTable().get(varName);
      if (locks.contains(lock)) {
        locks.remove(lock);
        getLockTable().put(varName, locks);
      }
    }
  }
  
  /** Check if other transaction hold lock on var. */
  public boolean canWrite(Transaction tran, String varName) {
    if (lockTable.containsKey(varName)) {
      for (Lock lock : lockTable.get(varName)) {
        if (!lock.getTran().equals(tran)) {
          return false;
        }
      }
    }
    return true;
  }
  
  /** Check if transaction should abort because wait-die. */
  public boolean shouldAbort(Transaction tran, String varName) {
    if (lockTable.containsKey(varName)) {
      for (Lock lock : lockTable.get(varName)) {
        if (lock.getTran().getStartTime() < tran.getStartTime()) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void dump() {
    System.out.printf("%4s", "S"+getID());
    for (int i=1; i<=20; i++) {
      if (getVariables().containsKey("x"+i)) {
        System.out.printf("%5s", getVariables().get("x"+i));
      } else {
        System.out.printf("%5s", "-");
      }
    }
  }
  
  
  public void fail() {
    this.state = State.FAILED;
  }
  
  public boolean isFailed() {
    return this.state == State.FAILED;
    
  }
  
  public void recover() {
    this.state = State.ALIVE;
  }
}
