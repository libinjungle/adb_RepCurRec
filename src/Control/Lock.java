package Control;

enum LockState {
  READ, WRITE
}
public class Lock {
  private LockState state;
  public void setLockState(LockState state) {
    this.state = state;
  }
  
  public LockState getLockState() {
    return this.state;
  }
}
