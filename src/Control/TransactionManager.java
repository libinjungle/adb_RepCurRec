package Control;

import java.util.List;

public class TransactionManager {
  private List<Transaction> waitTrans;
  private List<Transaction> abortedTrans;
  
  public List<Transaction> getAbortedTrans() {
    return this.abortedTrans;
  }
  
  public void commit_transaction(Transaction t) {
    
  }
  
  public void start_transaction(Transaction t) {
    
  }
  
  /**
   * All previous and following operations will be invalid.
   * Clear all locks on tran locklist. Add this transaction to abortedTrans. 
   * 
   * NOTE: In commit method, should first check if this transaction is aborted.
   * @param t
   */

  
  public Transaction nextTransaction() {
    return null;
  }
  
  public boolean isAborted(Transaction tran) {
    if (getAbortedTrans().contains(tran)) {
      return true;
    }
    return false;
  }
  /**
   * If transaction 
   * @param t
   */
  public void aquire_lock(Transaction t) {
    
  }
}
