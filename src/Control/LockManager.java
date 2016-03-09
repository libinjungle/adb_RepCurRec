package Control;

public class LockManager {
  private Lock lock;
  public Lock getReadLock() {
    if (lock.getLockState() != LockState.READ) {
      lock.setLockState(LockState.READ);
    }
    return lock;
  }
  
  public Lock getLock() {
    return this.lock;
  }
  
  public void getWriteLock() {
    
  }
  
}
