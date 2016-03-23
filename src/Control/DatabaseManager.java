package Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Keep an alive site list and failed site list.
public class DatabaseManager {
  private Map<Integer, Site> siteMap = new HashMap<>();
  private List<Site> aliveSites;
  private List<Site> downSites;
  private Map<String, Integer> var_value;
  
  
  
  TransactionManager TM = new TransactionManager();

  public List<Site> getAliveSites() {
    return this.aliveSites;
  }
  public void initialize() {
    for (int i=1; i<= 10; i++) {
      Map<String, Variable> varOnSite = new HashMap<>();
      for (int j=1; j<=20; j++) {
        if (j % 2 == 1) {
          if (i == 1 + j%10) {
            varOnSite.put("x"+j, new Variable("x"+j, 10*j));
          }
        } else {
          varOnSite.put("x"+j, new Variable("x"+j, 10*j));
        }
      }
      siteMap.put(i, new Site(i, varOnSite));
    }
  }
  
  public void printInitMap() {
    for (int i=1; i<=20; i++) {
      if (i == 1) {
        System.out.printf("%9s", "-----");
      } else if (i == 20) {
        System.out.printf("%5s\n", "-----");
      } else {
        System.out.printf("%5s", "-----");
      }
    }
    for (int i=1; i<=20; i++) {
      if (i == 1) {
        System.out.printf("%9s", "x"+i);
      } else {
        System.out.printf("%5s", "x"+i);
      }
    }
    System.out.println();
    for (Map.Entry<Integer, Site> entry : siteMap.entrySet()) {
      System.out.printf("%4s", "S"+entry.getKey()+":");
      Map<String, Variable> variables = entry.getValue().getVariables();
      int index = 1;
      for(int i=1; i<=20; i++) {
        Variable var = variables.get("x"+index);
        String name = var.getName();
        if (i != Integer.parseInt(name.substring(1))) {
          System.out.printf("%5s", "-");
        } else {
          System.out.printf("%5s", var.getValue());
          index++;
        }
      }
      System.out.println();
    }
  }
  

  /**
   * if ReadOnly, get the value from snapshot. If transaction has shared lock, find the site id that
   * this shared lock on and get the value. If transaction has write lock, find the site id that this
   * write lock on and get the value.
   * @param tran
   * @param varName
   * @return
   */
  public boolean read(Transaction tran, String varName) {
    
  }
  
  /**
   * Writes new value to var.
   * If transaction already has write lock, update values.
   * If not, for each site, check if other transactions hold lock on this var, 
   * then check if wait-die.
   * 
   * @param tran
   * @param varName
   * @param value
   * @return
   */
  
  public boolean write(Transaction tran, String varName, int value) {
    if (TM.getAbortedTrans().contains(tran)) {
      return false;
    }
    if (tran.hasWriteLock(varName)) {
      for (Site site : aliveSites) {
        site.updateVarMap(varName, value);
        System.out.println("Transaction "+tran.getID()+" write " + varName
            +" to "+value+" on site " + site.getID());
      }
      return true;
    } 
    // If can not write variable on this site, return false.
    for (Site site : aliveSites) {
      if (!site.canWrite(tran, varName)) {
        if (site.shouldAbort(tran, varName)) {
          
        } else {
          
        }
        return false;
      }
    }
    // If got here, means that every site is able to write var.
    for (Site site : aliveSites) {
      if (site.getVariables().containsKey(varName)) {
        Lock lock = new Lock(LockState.WRITE, site.getVariables().get(varName), 
            site.getID(), tran);
        site.addLock(varName, lock);
        tran.getLocks().add(lock);
        site.updateVarMap(varName, value);
      }
    }
    return true;

  }
  
  public Site findSite(int id) {
    for (Site site : getAliveSites()) {
      if (site.getID() == id) {
        return site;
      }
    }
    return null;
  }
  
  /**
   * Back up the variable value of the site of each lock in transaction's locktable. 
   * Delete the lock on this site. Clear transaction locktable. Add transaction to abort list.
   * All previous and following operations will be invalid.
   *  
   * NOTE: In commit method, should first check if this transaction is aborted.
   * @param t
   */
  public void abort(Transaction tran) {
    if (TM.isAborted(tran)) {
      return;
    }
    // Remove locks of this tran on all sites.
    for (Lock lock : tran.getLocks()) {
      Site site = findSite(lock.getSiteID());
      site.deleteLock(lock.getVarName(), lock);
      // Back up variable on site to be the cur variable value on siteMap.
      Site globalSite = this.siteMap.get(lock.getSiteID());
      Variable var = globalSite.getVariables().get(lock.getVarName());
      int value = var.getValue();
      Map<String, Variable> varsOnSite= site.getVariables();
      varsOnSite.get(lock.getVarName()).setValue(value);
    }
    tran.getLocks().clear();
    TM.getAbortedTrans().add(tran);
  }
  
  public static void main(String[] args) {
    DatabaseManager manager = new DatabaseManager();
    manager.initialize();
    manager.printInitMap();
  }
  
}
